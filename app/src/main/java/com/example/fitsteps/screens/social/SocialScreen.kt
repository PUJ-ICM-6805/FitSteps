package com.example.fitsteps.screens.social

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
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
import com.example.fitsteps.screens.userProfileAvatar
import com.example.fitsteps.ui.theme.Blue
import com.example.fitsteps.ui.theme.DarkBlue
import com.example.fitsteps.ui.theme.LightBlue
import com.example.fitsteps.ui.theme.Red
import com.example.fitsteps.ui.theme.White
import com.example.fitsteps.ui.theme.customFontFamily
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


@Composable
fun SocialScreen(
    userContactsViewModel: UserContactsViewModel = remember { UserContactsViewModel() }
) {
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
    val usersStatus = remember { mutableStateListOf<User>() }
    val usersFromContacts = remember { mutableStateListOf<User>() }
    var filteredContacts by remember { mutableStateOf<List<User>>(emptyList()) }

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
        phoneNumberEntered = userContactsViewModel.userPhoneNumber.value
        val users = usersFromContacts.toSet()
        for(user in users) {
            for(usersT in usersStatus) {
                Log.d("Verificacion", (user.userId == usersT.userId).toString())
                if(user.userId == usersT.userId) {
                    user.active = usersT.active
                }
            }
        }
        userContactsViewModel.usersByContacts = users.toList().toMutableList()
        Log.d("contactos", userContactsViewModel.usersByContacts.toString())
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
                SearchBar(
                    onSearchEntered = { searchString ->
                        Log.d("SearchBar", "Search text: $searchString")
                        // Update the filteredContacts list based on the search string
                        filteredContacts = if (searchString.isBlank() || searchString.isEmpty()) {
                            Log.d("SearchBar", "Search text is blank or empty")
                            users.toList()
                        } else {
                            users.toList().filter { user ->
                                user.user_name.contains(searchString, ignoreCase = true)
                            }
                        }
                    }
                )
            }
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp),
                text = stringResource(id = R.string.gymbro),
                color = Blue,
                fontSize = 15.sp,
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Normal,
                ),
                textAlign = TextAlign.Center,
            )
            //entrar a dial de llamada y poner su numero
            val localContext = LocalContext.current
            if (filteredContacts.isEmpty()) {
                filteredContacts = users.toList()
            }
            Log.d("contactos", filteredContacts.toString())
            LazyColumn {
                items(filteredContacts) { contact ->
                    // Obtener el estado "online" del modelo de vista
                    val isOnline = contact.active
                    ContactItem(contact = contact, isOnline = isOnline, onClicked = {
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse("tel:${contact.phoneNumber}")
                        localContext.startActivity(intent)
                    })
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    contactsPermissionResultContracts.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
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
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            contactsPermissionResultContracts.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                        Log.d("numero", userContactsViewModel.userPhoneNumber.value)
                        Log.d("numero", mPhoneNumber)
                        userContactsViewModel.setPhoneNumber(mPhoneNumber)
                        contacts = getContacts(context)
                        userContactsViewModel.userid = userid
                        phoneNumberEntered = it

                    }
                )
            }
    } else if (isContactsPermissionGranted && !userContactsViewModel.userExists.value) {
        if(phoneNumberEntered == null || phoneNumberEntered == "") {
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
                    phoneNumberEntered = it

                }
            )
        }
    } else {
        // Muestra MainMenu automáticamente cuando no hay interacción aún
        LaunchedEffect(Unit) {
            contactsPermissionResultContracts.launch(Manifest.permission.READ_CONTACTS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                contactsPermissionResultContracts.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
        MainMenu(
            modifier = Modifier
                .fillMaxSize()
                .background(White),
            onClicked = {
                contactsPermissionResultContracts.launch(Manifest.permission.READ_CONTACTS)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    contactsPermissionResultContracts.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
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

            FirebaseFirestore.getInstance().collection("users_statuses").
            addSnapshotListener(object : com.google.firebase.firestore.EventListener<QuerySnapshot>{
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.w("DatabaseUtils", "Listen failed.", error)
                        return
                    }
                    if (value != null) {
                        for (doc in value) {
                            //cruzamos estos resultados con los de usersFromContacts
                            val user = doc.toObject(User::class.java)
                            user.userId = doc.id
                            user.active = doc.getBoolean("online")!!
                            Log.d("DatabaseUtils", "usersData: $user")
                            usersStatus += user
                        }
                    }
                }
            })
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White),
        ) {
            val users = usersFromContacts.toSet()
            for(user in users) {
                for(usersT in usersStatus) {
                    Log.d("Verificacion", (user.userId == usersT.userId).toString())
                    if(user.userId == usersT.userId) {
                        user.active = usersT.active
                    }
                    if(user.userId == userid){
                        //lo eliminamos de la lista
                        usersFromContacts.remove(user)
                    }
                }

            }

            userContactsViewModel.usersByContacts = users.toList().toMutableList()
            Log.d("contactos", userContactsViewModel.usersByContacts.toString())


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
                SearchBar(
                    onSearchEntered = { searchString ->
                        // Update the filteredContacts list based on the search string
                        filteredContacts = users.filter { user ->
                            user.user_name.contains(searchString, ignoreCase = true)
                        }
                    }
                )
            }
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp),
                text = stringResource(id = R.string.gymbro),
                color = Blue,
                fontSize = 15.sp,
                style = TextStyle(
                    fontFamily = customFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Normal,
                ),
                textAlign = TextAlign.Center,
            )
            //entrar a dial de llamada y poner su numero
            val localContext = LocalContext.current
            LazyColumn {
                items(filteredContacts.toList()) { contact ->
                    // Obtener el estado "online" del modelo de vista
                    val isOnline = contact.active
                    ContactItem(contact = contact, isOnline = isOnline, onClicked = {
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse("tel:${contact.phoneNumber}")
                        localContext.startActivity(intent)
                    })
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
            SearchBar(
            )
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
    onSearchEntered: (String) -> Unit = {},
    backgroundColor: Color = Color.White,
) {
    var searchText by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }
    var isSearchEntered by remember { mutableStateOf(false) }

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
            value = searchText.text,
            onValueChange = {
                searchText = TextFieldValue(it)
                Log.d("SearchBar", "Search text: ${searchText.text}")
                isSearchEntered = false
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            singleLine = true,
            keyboardActions = KeyboardActions(
                onDone = {
                    isSearchEntered = true
                    Log.d("SearchBar", "Search entered: ${searchText.text}")
                }
            ),
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
   onSearchEntered(searchText.text)
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
                    //si el phoneNumber contiene un +57 al inicio, se elimina
                    if (phoneNumber.contains("+57")) {
                        phoneNumber = phoneNumber.replace("+57", "")
                    }
                    contacts.add(Contact(id.toInt(), name, phoneNumber))
                }
            }

            phoneCursor?.close()
        }
    }

    cursor?.close()
    return contacts
}


@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ContactItem(contact: User, isOnline: Boolean, onClicked: () -> Unit = {}) {
    // Agregar un círculo verde para indicar "online" o gris para indicar "offline"
    val onlineIndicatorColor = if (isOnline) Color.Green else Color.Gray
    var clicked by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClicked),
        shape = RoundedCornerShape(8.dp),
        color = Color.White,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${contact.user_name} ${contact.experience}",
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(onlineIndicatorColor, shape = CircleShape)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = userProfileAvatar(contact.avatar),
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
        }
    }
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
                Text(text = "Ingresa tu número de teléfono",
                    style = TextStyle(
                        fontFamily = customFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontStyle = FontStyle.Normal,
                        fontSize = 20.sp,
                        color = Red,
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp))
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
                    singleLine = true
                    ,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        focusedIndicatorColor = DarkBlue,
                        cursorColor = DarkBlue,
                        unfocusedIndicatorColor = DarkBlue,
                        disabledIndicatorColor = DarkBlue,
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
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
                                        val user = document.toObject(User::class.java)
                                        user.phoneNumber = contact
                                        usersFromContacts += user
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