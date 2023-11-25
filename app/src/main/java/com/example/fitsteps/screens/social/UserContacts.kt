package com.example.fitsteps.screens.social

data class UserContacts(
    val userid: String = "",
    var contacts: List<String> = emptyList()
) {
    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "userid" to this.userid,
            "contacts" to this.contacts,
        )
    }
}