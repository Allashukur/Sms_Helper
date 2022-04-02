package com.example.smshelper.models

data class Sms_data(
    var id: String,
    var address: String,
    var msg: String,
    var readState: String,
    var time: String,
    var folderName: String
) {
}