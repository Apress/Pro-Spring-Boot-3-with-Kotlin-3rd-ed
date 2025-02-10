package com.apress.users.events

data class UserActivatedEvent(
    var email: String? = null,
    var active:Boolean = false
){
    companion object {
        var name:String = "ACTIVATION_STATUS"
    }
}
