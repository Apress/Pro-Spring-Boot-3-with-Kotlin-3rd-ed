package com.apress.users.actuator

data class LogEventConfig (
    var enabled:Boolean = false,
    var prefix:String = ">> ",
    var postfix:String = " <<"
)
