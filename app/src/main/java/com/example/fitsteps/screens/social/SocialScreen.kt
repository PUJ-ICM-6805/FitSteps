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
import android.telephony.TelephonyManager
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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitsteps.R
import com.example.fitsteps.authentication.DatabaseUtils
import com.example.fitsteps.authentication.User
import com.example.fitsteps.firebaseData.firebaseBodyMeasuresData.Measures
import com.example.fitsteps.permissions.ContactsPermissionTextProvider
import com.example.fitsteps.permissions.MainViewModel
import com.example.fitsteps.permissions.PermissionDialog
import com.example.fitsteps.permissions.PhonePermissionTextProvider
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
import kotlinx.coroutines.tasks.await
import java.util.UUID
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


@SuppressLint("MissingPermission")
@Composable
fun SocialScreen(contactsViewModel: UserContactsViewModel = UserContactsViewModel()) {
    val userid = Firebase.auth.currentUser?.uid
    val viewModel = viewModel<MainViewModel>()
    val dialogQueue = viewModel.visiblePermissionDialogQueue
    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            perms.keys.forEach { permission ->
                viewModel.onPermissionResult(
                    permission = permission,
                    isGranted = perms[permission] == true
                )
            }
        }
    )

    val isContactsPermissionGranted by viewModel.isContactsPermissionGranted
    val isPhonePermissionGranted by viewModel.isPhonePermissionGranted
    val context = LocalContext.current
    var contacts by remember { mutableStateOf<List<Contact>>(emptyList()) }
    val usersContactsRef = FirebaseFirestore.getInstance().collection("users_contacts")
    var finalContacts: List<String> = emptyList()
    val usersFromContacts = remember { mutableStateListOf<User>() }
    val tMgr = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    var mPhoneNumber = ""
    val lifecycleEvent = rememberLifecycleEvent()

    LaunchedEffect(isContactsPermissionGranted && isPhonePermissionGranted) {
        if (isContactsPermissionGranted && isPhonePermissionGranted) {
            usersFromContacts.clear()
            contacts = getContacts(context)
            try {
                mPhoneNumber = tMgr.line1Number
            } catch (ex: NullPointerException) {
            }

            if (mPhoneNumber.equals("")) {
                val sharedPreferences =
                    context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                mPhoneNumber = sharedPreferences.getString("phoneNumber", "") ?: ""

                // Si no hay un número almacenado, generar uno único
                if (mPhoneNumber.isBlank()) {
                    val uniqueID = UUID.randomUUID().toString()
                    mPhoneNumber = uniqueID

                    with(sharedPreferences.edit()) {
                        putString("phoneNumber", mPhoneNumber)
                        apply()
                    }
                }
            } //TODO en lugar de pedir permisos para obtener el número de teléfono, se puede pedir a la hora de registrarse
            finalContacts = getContactsUsingAppSync(usersContactsRef, contacts)
            if (userid != null) {
                contactsViewModel.userid = userid
                contactsViewModel.userPhoneNumber.value = mPhoneNumber
                Log.d("contactos antes de fstore", contacts.toString())
                contactsViewModel.uploadUserContacts(finalContacts)
                getContactsUsersByUserID(usersContactsRef, finalContacts, usersFromContacts)
            } else {
                Log.d("Guardado de contactos", "userid es null")
            }
        } else {
            multiplePermissionResultLauncher.launch(
                arrayOf(
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.READ_PHONE_STATE
                )
            )
        }
    }
    LaunchedEffect(contacts) {
        usersFromContacts.clear()
        Log.d("numero", mPhoneNumber)
        finalContacts = getContactsUsingAppSync(usersContactsRef, contacts)
        if (userid != null && mPhoneNumber.isNotBlank()) {
            contactsViewModel.userid = userid
            contactsViewModel.userPhoneNumber.value = mPhoneNumber
            contactsViewModel.uploadUserContacts(finalContacts)
            getContactsUsersByUserID(usersContactsRef, finalContacts, usersFromContacts)
        } else {
            Log.d("Guardado de contactos", "userid es null")
        }
    }
    LaunchedEffect(lifecycleEvent) {
        if (isContactsPermissionGranted && isPhonePermissionGranted) {
            usersFromContacts.clear()
            if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
                contacts = getContacts(context)
                try {
                    mPhoneNumber = tMgr.line1Number
                } catch (ex: NullPointerException) {
                }

                if (mPhoneNumber.equals("")) {
                    val sharedPreferences =
                        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                    mPhoneNumber = sharedPreferences.getString("phoneNumber", "") ?: ""

                    // Si no hay un número almacenado, generar uno único
                    if (mPhoneNumber.isBlank()) {
                        val uniqueID = UUID.randomUUID().toString()
                        mPhoneNumber = uniqueID

                        with(sharedPreferences.edit()) {
                            putString("phoneNumber", mPhoneNumber)
                            apply()
                        }
                    }
                } //TODO en lugar de pedir permisos para obtener el número de teléfono, se puede pedir a la hora de registrarse
                finalContacts = getContactsUsingAppSync(usersContactsRef, contacts)
                if (userid != null) {
                    contactsViewModel.userid = userid
                    contactsViewModel.userPhoneNumber.value = mPhoneNumber
                    Log.d("contactos antes de fstore", contacts.toString())
                    contactsViewModel.uploadUserContacts(finalContacts)
                    getContactsUsersByUserID(usersContactsRef, finalContacts, usersFromContacts)
                } else {
                    Log.d("Guardado de contactos", "userid es null")
                }
            } else {
                multiplePermissionResultLauncher.launch(
                    arrayOf(
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_PHONE_STATE
                    )
                )
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose { }
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
        Log.d("usuarios finales", usersFromContacts.toString())
        val users: Set<User> = usersFromContacts.toSet()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(White),
        ) {
            items(users.toList()) { contact ->
                ContactItem(contact)
            }
        }

        if (!isContactsPermissionGranted) {
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
                        multiplePermissionResultLauncher.launch(
                            arrayOf(
                                Manifest.permission.READ_CONTACTS,
                                Manifest.permission.READ_PHONE_STATE
                            )
                        )
                    }
                )
            }
        }
        //variable con la actividad en la que estamos
        val activity = LocalContext.current as Activity
        dialogQueue
            .reversed()
            .forEach { permission ->
                PermissionDialog(
                    permissionTextProvider = when (permission) {
                        Manifest.permission.READ_CONTACTS -> {
                            ContactsPermissionTextProvider()
                        }

                        Manifest.permission.READ_PHONE_STATE -> {
                            PhonePermissionTextProvider()
                        }

                        else -> return@forEach
                    },
                    isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                        activity,
                        permission
                    ),
                    onDismiss = viewModel::dismissDialog,
                    onOkClick = {
                        viewModel.dismissDialog()
                        multiplePermissionResultLauncher.launch(
                            arrayOf(
                                permission
                            )
                        )
                    },
                    onGoToAppSettingsClick = { activity.openAppSettings() },
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
                    val phoneNumber: String = phoneCursor.getString(phoneNumberIndex)
                    contacts.add(Contact(id.toInt(), name, phoneNumber))
                }
            }

            phoneCursor?.close()
        }
    }

    cursor?.close()
    return contacts
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContactItem(contact: User) {
    ListItem(
        text = { Text(contact.user_name + " " + contact.experience + " ") },
        icon = {
            Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = null
            )
        }
    )
}

@Composable
fun rememberLifecycleEvent(lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current): Lifecycle.Event {
    var state by remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            state = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    return state
}

@Composable
@Preview
fun SocialScreenPreview() {
    SocialScreen()
}