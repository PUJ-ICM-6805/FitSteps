package com.example.fitsteps.screens.social

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitsteps.R
import com.example.fitsteps.authentication.User
import com.example.fitsteps.permissions.ContactsPermissionTextProvider
import com.example.fitsteps.permissions.MainViewModel
import com.example.fitsteps.permissions.PermissionDialog
import com.example.fitsteps.ui.theme.Blue
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.Red
import com.example.fitsteps.ui.theme.White
import com.example.fitsteps.ui.theme.customFontFamily
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


@Composable
fun SocialScreen(userContactsViewModel: UserContactsViewModel = remember { UserContactsViewModel() }) {
    val viewModel = viewModel<MainViewModel>()
    val isContactsPermissionGranted by viewModel.isContactsPermissionGranted
    val dialogQueue = viewModel.visiblePermissionDialogQueue
    val contactsPermissionResultContracts = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            viewModel.onPermissionResult(
                permission = android.Manifest.permission.READ_CONTACTS,
                isGranted = isGranted

            )
        })
    val context = LocalContext.current
    var contacts by remember { mutableStateOf<List<Contact>>(emptyList()) }
    val userid = Firebase.auth.currentUser?.uid
    val usersContactsRef = FirebaseFirestore.getInstance().collection("users_contacts")
    var mPhoneNumber by remember { mutableStateOf("") }
    var finalContacts: List<String> = emptyList()
    var phoneNumberEntered by remember { mutableStateOf<String?>(null) }
    val usersFromContacts = remember { mutableStateListOf<User>() }

    if (userid != null) {
        //buscamos en la colección de usuarios el documento que tenga como campo userid el id del usuario actual
        usersContactsRef.whereEqualTo("userid", userid).get()
            .addOnSuccessListener { documents ->
                //si el documento existe, entonces el usuario ya ha ingresado sus contactos
                if (documents.size() > 0) {
                    userContactsViewModel.userExists.value = true
                    //obtenemos los contactos del usuario
                    val queriedContacts = documents.documents[0].get("contacts") as List<String>
                    //actualizamos el valor de los contactos del usuario en el viewmodel
                    userContactsViewModel.userPhoneNumber.value = documents.documents[0].id
                    userContactsViewModel.userContacts = queriedContacts.toMutableList()
                    Log.d("DocumentSnapshot data", "Document exists!")
                    Log.d("DocumentSnapshot data", contacts.toString())
                } else {
                    userContactsViewModel.userExists.value = false
                    Log.d("DocumentSnapshot data", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("numero", "Error getting documents: ", exception)
            }
    }

    Log.d("numeroIF", userContactsViewModel.userPhoneNumber.value)
    if (isContactsPermissionGranted && userContactsViewModel.userExists.value) {
        Log.d("llamando1", "TRUE TRUE")
        //lanzamos un coroutine para obtener los usuarios que tienen la app instalada
        LaunchedEffect(Unit) {
            contacts = getContacts(context)
            finalContacts = getContactsUsingAppSync(usersContactsRef, contacts)
            userContactsViewModel.uploadUserContacts(finalContacts)
            Log.d("contactos", finalContacts.toString())
            getContactsUsersByUserID(usersContactsRef, finalContacts, usersFromContacts)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White),
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.TopStart),
                    text = stringResource(id = R.string.social),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 30.sp,
                        fontStyle = FontStyle.Normal,
                        color = DarkBlue,
                    )
                )

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                SearchBar()
            }
            val users = usersFromContacts.toSet()
            LazyColumn {
                items(users.toList()) { contact ->
                    ContactItem(contact = contact)
                }
            }
        }
    } else if (!isContactsPermissionGranted && !userContactsViewModel.userExists.value) {
        Log.d("llamando1", "FALSE FALSE")
        MainMenu(
            modifier = Modifier
                .fillMaxSize()
                .background(White),
            onClicked = {
                contactsPermissionResultContracts.launch(Manifest.permission.READ_CONTACTS)
            }
        )
        val activity = LocalContext.current as Activity
        dialogQueue
            .reversed()
            .forEach { permission ->
                PermissionDialog(
                    permissionTextProvider = when (permission) {
                        android.Manifest.permission.READ_CONTACTS -> {
                            ContactsPermissionTextProvider()
                        }

                        else -> return@forEach
                    },
                    isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                        activity,
                        permission
                    ),
                    onDismiss = viewModel::dismissDialog,
                    onOkClick = { contactsPermissionResultContracts.launch(android.Manifest.permission.READ_CONTACTS) },
                    onGoToAppSettingsClick = { activity.openAppSettings() },
                )
                PhoneNumberScreen(
                    contactsViewModel = userContactsViewModel,
                    onPhoneNumberEntered = {
                        mPhoneNumber = it
                        contactsPermissionResultContracts.launch(Manifest.permission.READ_CONTACTS)
                        Log.d("numero", userContactsViewModel.userPhoneNumber.value)
                        Log.d("numero", mPhoneNumber)
                        userContactsViewModel.setPhoneNumber(mPhoneNumber)
                        contacts = getContacts(context)
                        userContactsViewModel.userid = userid
                        getContactsUsingAppSync(usersContactsRef, contacts) { result ->
                            finalContacts = result
                            Log.d("contactos", finalContacts.toString())
                            userContactsViewModel.uploadUserContacts(finalContacts)
                            phoneNumberEntered = it
                        }

                    }
                )
            }
    } else if (isContactsPermissionGranted && !userContactsViewModel.userExists.value) {
        Log.d("llamando1", "TRUE FALSE")
        PhoneNumberScreen(
            contactsViewModel = userContactsViewModel,
            onPhoneNumberEntered = {
                mPhoneNumber = it
                Log.d("numero", userContactsViewModel.userPhoneNumber.value)
                Log.d("numero", mPhoneNumber)
                userContactsViewModel.setPhoneNumber(mPhoneNumber)
                contacts = getContacts(context)
                userContactsViewModel.userid = userid
                getContactsUsingAppSync(usersContactsRef, contacts) { result ->
                    finalContacts = result
                    userContactsViewModel.uploadUserContacts(finalContacts)
                    phoneNumberEntered = it
                }

            }
        )
    } else {
        // Muestra MainMenu automáticamente cuando no hay interacción aún
        LaunchedEffect(Unit) {
            contactsPermissionResultContracts.launch(Manifest.permission.READ_CONTACTS)
        }
        MainMenu(
            modifier = Modifier
                .fillMaxSize()
                .background(White),
            onClicked = {
                contactsPermissionResultContracts.launch(Manifest.permission.READ_CONTACTS)
            }
        )
    }
    //variable con la actividad en la que estamos
    val activity = LocalContext.current as Activity
    dialogQueue
        .reversed()
        .forEach { permission ->
            PermissionDialog(
                permissionTextProvider = when (permission) {
                    android.Manifest.permission.READ_CONTACTS -> {
                        ContactsPermissionTextProvider()
                    }

                    else -> return@forEach
                },
                isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                    activity,
                    permission
                ),
                onDismiss = viewModel::dismissDialog,
                onOkClick = { contactsPermissionResultContracts.launch(android.Manifest.permission.READ_CONTACTS) },
                onGoToAppSettingsClick = { activity.openAppSettings() },
            )
        }
    if (phoneNumberEntered != null) {
        LaunchedEffect(Unit) {
            contacts = getContacts(context)
            finalContacts = getContactsUsingAppSync(usersContactsRef, contacts)
            userContactsViewModel.uploadUserContacts(finalContacts)
            Log.d("contactos", finalContacts.toString())
            getContactsUsersByUserID(usersContactsRef, finalContacts, usersFromContacts)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White),
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.TopStart),
                    text = stringResource(id = R.string.social),
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 30.sp,
                        fontStyle = FontStyle.Normal,
                        color = DarkBlue,
                    )
                )

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                SearchBar()
            }
            val users = usersFromContacts.toSet()
            LazyColumn {
                items(users.toList()) { contact ->
                    ContactItem(contact = contact)
                }
            }
        }
    }
}

@Composable
fun MainMenu(modifier: Modifier, onClicked: () -> Unit) {
    Column(
        modifier = modifier,
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.TopStart),
                text = stringResource(id = R.string.social),
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 30.sp,
                    fontStyle = FontStyle.Normal,
                    color = DarkBlue,
                )
            )

        }
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            SearchBar()
        }
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 40.dp, top = 20.dp, start = 20.dp, end = 20.dp),
            text = stringResource(id = R.string.labelSocial),
            color = Blue,
            fontSize = 15.sp,
            style = TextStyle(
                fontFamily = customFontFamily,
                fontWeight = FontWeight.Medium,
                fontStyle = FontStyle.Normal,
            ),
            textAlign = TextAlign.Center,
        )
        Box(
            modifier = Modifier
                .padding(bottom = 20.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,

            ) {
            Image(
                painter = painterResource(id = R.drawable.social_contacts),
                contentDescription = "",
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp),
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            SocialButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                    .height(70.dp),
                onClicked = {
                    onClicked()
                }
            )
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    text: String = stringResource(id = R.string.search),
    label: String = "",
) {
    Surface(
        modifier = modifier
            .padding(vertical = 10.dp, horizontal = 20.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(LightBlue)
            .fillMaxWidth()
            .wrapContentHeight(),
        color = backgroundColor,
    ) {
        TextField(
            value = "",
            onValueChange = { },
            placeholder = {
                Text(
                    text = "Buscar",
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Light,
                        fontStyle = FontStyle.Normal,
                        fontSize = 22.sp,
                        color = LightBlue,
                    )
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = backgroundColor,
                focusedIndicatorColor = DarkBlue,
                cursorColor = DarkBlue,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            modifier = Modifier
                .height(60.dp)
        )
    }
}


@Composable
fun SocialButton(
    modifier: Modifier = Modifier,
    text: String = stringResource(id = R.string.contacts),
    shape: Shape = RoundedCornerShape(20.dp),
    borderColor: Color = Color.LightGray,
    backgroundColor: Color = Red,
    colorText: Color = MaterialTheme.colorScheme.onSecondary,
    onClicked: () -> Unit = {},
    style: TextStyle = TextStyle(
        fontFamily = customFontFamily,
        fontWeight = FontWeight.Medium,
        fontStyle = FontStyle.Normal,
    ),
) {
    var clicked by remember { mutableStateOf(false) }
    androidx.compose.material.Surface(
        modifier = modifier
            .height(70.dp)
            .clickable {
                clicked = !clicked
                onClicked()
            },
        shape = shape,
        border = BorderStroke(width = 1.dp, color = borderColor),
        color = backgroundColor,
        elevation = 2.dp
    ) {
        Box(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .height(IntrinsicSize.Max),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = colorText,
                fontSize = 20.sp,
                style = style,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 5.dp),
            )
        }
    }
}

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also {
        startActivity(it)
    }
}

@SuppressLint("Range")
fun getContacts(context: Context): List<Contact> {
    val contacts = mutableListOf<Contact>()
    val contentResolver: ContentResolver = context.contentResolver
    val cursor: Cursor? = contentResolver.query(
        ContactsContract.Contacts.CONTENT_URI,
        arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
        ),
        null,
        null,
        null
    )

    if (cursor != null && cursor.count > 0) {
        while (cursor.moveToNext()) {
            val id: String = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
            val name: String =
                cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY))

            val phoneCursor: Cursor? = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                arrayOf(id),
                null
            )

            if (phoneCursor != null && phoneCursor.moveToFirst()) {
                val phoneNumberIndex =
                    phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                if (phoneNumberIndex != -1) {
                    var phoneNumber: String = phoneCursor.getString(phoneNumberIndex)
                    //aplicamos regex para quitar espacios intermedios guiones y parentesis
                    phoneNumber = phoneNumber.replace("[\\s\\-\\(\\)]".toRegex(), "")
                    contacts.add(Contact(id.toInt(), name, phoneNumber))
                }
            }

            phoneCursor?.close()
        }
    }

    cursor?.close()
    return contacts
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContactItem(contact: User) { //TODO mejorar la estética y estructura de la lista
    ListItem(
        text = { Text(contact.user_name + " " + contact.experience) },
        icon = {
            Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = null
            )
        }
    )
}

@Composable
fun PhoneNumberScreen(
    onPhoneNumberEntered: (String) -> Unit,
    contactsViewModel: UserContactsViewModel = UserContactsViewModel()
) {
    var showDialog by remember { mutableStateOf(true) }
    var phoneNumber by remember { mutableStateOf("") }
    Log.d("showDialog", showDialog.toString())
    if (showDialog) {
        Log.d("showDialog", showDialog.toString())
        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },
            title = {
                Text(text = "Ingresa tu número de teléfono")
            },
            text = {
                TextField(
                    value = phoneNumber,
                    onValueChange = {
                        phoneNumber = it
                    },
                    label = {
                        Text(text = "Número telefónico")
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Aquí puedes realizar alguna acción con el número de teléfono ingresado
                        showDialog = false
                        onPhoneNumberEntered(phoneNumber)
                    }
                ) {
                    Text(text = "Aceptar")
                }
            },
        )
    }
}

private fun getContactsUsingAppSync(
    usersContactsRef: CollectionReference,
    contacts: List<Contact>,
    onResult: (List<String>) -> Unit
): List<String> {
    val contactsUsingApp = ArrayList<String>()
    var count = 0

    for (contact in contacts) {
        usersContactsRef.document(contact.phoneNumber).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    contactsUsingApp.add(contact.phoneNumber)
                }

                count++
                if (count == contacts.size) {
                    // All documents have been checked
                    onResult(contactsUsingApp)
                }
            }
            .addOnFailureListener { e ->
                Log.w("DatabaseUtils", "Error checking document existence", e)
                count++
                if (count == contacts.size) {
                    // All documents have been checked
                    onResult(contactsUsingApp)
                }
            }
    }
    return contactsUsingApp
}

private suspend fun getContactsUsingAppSync(
    usersContactsRef: CollectionReference,
    contacts: List<Contact>
): List<String> = suspendCoroutine { continuation ->
    val contactsUsingApp = ArrayList<String>()
    var count = 0

    for (contact in contacts) {
        usersContactsRef.document(contact.phoneNumber).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    contactsUsingApp.add(contact.phoneNumber)
                }

                count++
                if (count == contacts.size) {
                    // Todos los documentos han sido verificados
                    continuation.resume(contactsUsingApp)
                }
            }
            .addOnFailureListener { e ->
                Log.w("DatabaseUtils", "Error checking document existence", e)
                count++
                if (count == contacts.size) {
                    // Todos los documentos han sido verificados
                    continuation.resume(contactsUsingApp)
                }
            }
    }
}
private suspend fun getContactsUsersByUserID(
    usersContactsRef: CollectionReference,
    contacts: List<String>,
    usersFromContacts: SnapshotStateList<User>
) = suspendCoroutine { continuation ->
    val users = ArrayList<User>()
    var count = 0
    val database = FirebaseFirestore.getInstance()
    val usersRef = database.collection("users")

    for (contact in contacts) {
        usersContactsRef.document(contact).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val userid = documentSnapshot.getString("userid")
                    if (userid != null) {
                        Log.d("userId", userid)
                        usersRef
                            .whereEqualTo("userId", userid)
                            .get()
                            .addOnSuccessListener { result ->
                                for (document in result) {
                                    Log.d("DatabaseUtils", "${document.id} => ${document.data}")
                                    if (document.exists()) {
                                        usersFromContacts += document.toObject(User::class.java)
                                        Log.d("DatabaseUtils", "usersData: $usersFromContacts")
                                    } else {
                                        Log.d("DatabaseUtils", "No such document")
                                    }
                                }
                            }
                    } else {
                        Log.d("DatabaseUtils", "userid is null or does not exist")
                    }
                }

                count++
                if (count == contacts.size) {
                    // Todos los documentos han sido verificados
                    continuation.resume(users)
                }
            }
            .addOnFailureListener { e ->
                Log.w("DatabaseUtils", "Error checking document existence", e)
                count++
                if (count == contacts.size) {
                    // Todos los documentos han sido verificados
                    continuation.resume(users)
                }
            }
    }
}
@Composable
@Preview
fun SocialScreenPreview() {
    SocialScreen()
}