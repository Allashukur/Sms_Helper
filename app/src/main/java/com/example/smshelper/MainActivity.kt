package com.example.smshelper

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.*
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.Telephony
import android.telephony.SmsManager
import android.telephony.SmsMessage
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.smshelper.data_base_room.database.AppDataBase
import com.example.smshelper.data_base_room.entity.Date_model
import com.example.smshelper.data_base_room.entity.Province_model
import com.example.smshelper.data_base_room.entity.Workers_model
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    var number = ""
    var smsMessage = ""
    var postion = -1;

    val PERMISSION_ALL = 1
    var PERMISSIONS = arrayOf(
        Manifest.permission.SEND_SMS,
        Manifest.permission.RECEIVE_SMS,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.READ_SMS,
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_CONTACTS,
    )


    lateinit var appDataBase: AppDataBase
    var provinceList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appDataBase = AppDataBase.getInstanse(this)



        if (!newMultiPermissin(this, *PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL)
        }

    }


    fun newMultiPermissin(context: Context, vararg permissions: String): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            todayDate()
            liveSms()
        }
    }

    private fun todayDate() {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val currentDate = sdf.format(Date())
        val todayDate = appDataBase.getDateDao().dateBy(currentDate.toString()) as Date_model

        if (todayDate == null) {
            appDataBase.getDateDao().addDate(Date_model(date = currentDate.toString()))
            val todayDate = appDataBase.getDateDao().dateBy(currentDate.toString())
            loadProvines(todayDate.id)
        }
    }

    private fun loadProvines(date_id: Int) {
        appDataBase.getProvinceDao()
            .addProvince(Province_model(province_name = "Aндижон", date_id = date_id))
        appDataBase.getProvinceDao()
            .addProvince(Province_model(province_name = "Фарғона", date_id = date_id))
        appDataBase.getProvinceDao()
            .addProvince(Province_model(province_name = "Наманган", date_id = date_id))
        appDataBase.getProvinceDao()
            .addProvince(Province_model(province_name = "Тошкент", date_id = date_id))
        appDataBase.getProvinceDao()
            .addProvince(Province_model(province_name = "Самарканд", date_id = date_id))
        appDataBase.getProvinceDao()
            .addProvince(Province_model(province_name = "Кашкадарё", date_id = date_id))
        appDataBase.getProvinceDao()
            .addProvince(Province_model(province_name = "Сурхандарё", date_id = date_id))
        appDataBase.getProvinceDao()
            .addProvince(Province_model(province_name = "Сирдарё", date_id = date_id))
        appDataBase.getProvinceDao()
            .addProvince(Province_model(province_name = "Бухоро", date_id = date_id))
        appDataBase.getProvinceDao()
            .addProvince(Province_model(province_name = "Навоий", date_id = date_id))
        appDataBase.getProvinceDao()
            .addProvince(Province_model(province_name = "Жиззах", date_id = date_id))
        appDataBase.getProvinceDao()
            .addProvince(Province_model(province_name = "Хоразм", date_id = date_id))
        appDataBase.getProvinceDao()
            .addProvince(Province_model(province_name = "Нукус", date_id = date_id))


        loadContact(date_id)
    }

    fun liveSms() {
        var br = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                var smstext: String = ""
                var phoneNumber: String = ""
                for (sms in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {

//                        smsMessage = sms.displayMessageBody
//                        number = sms.originatingAddress.toString()

                    smstext += sms.displayMessageBody
                    phoneNumber = sms.originatingAddress.toString()
//                    sms.displayMessageBody
//                ailogic(sms.displayMessageBody, sms.originatingAddress.toString())

                    Log.d("DEBUGSMS", "orginal : ${sms.displayMessageBody}")


                }
                Log.d("DEBUGSMS", "FORI  : $smstext")
                Log.d("DEBUGSMS", "FORI  : $phoneNumber")
                if (smstext.isNotEmpty() && phoneNumber.isNotEmpty()) {
                    ailogic(smstext, phoneNumber)
                }
            }
        }
        registerReceiver(br, IntentFilter("android.provider.Telephony.SMS_RECEIVED"))
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun ailogic(smstexts: String, number: String) {
//        var text = "ishboshladi"
//        var text1 = "ишбошлади"
        var text = "бошлади"
        var text1 = "boshladi"
        var text2 = "чикди"
        var text3 = "chiqdi"
        var text4 = "ishlayapman"
        var text5 = "ишлаяпман"


        if (number.isNotEmpty() && smstexts.isNotEmpty()) {

            var smstext = smstexts.toString().replace(" ", "").lowercase()
            Log.d("DEBUGSMS", "$smstext")
            if (smstext.contains(text) || smstext.contains(text1) || smstext.contains(text2) || smstext.contains(text3) || smstext.contains(text4)||smstext.contains(text5)) {

                val sdf = SimpleDateFormat("dd/MM/yyyy")
                val currentDate = sdf.format(Date())
                val todayDate =
                    appDataBase.getDateDao().dateBy(currentDate.toString()) as Date_model
                val listWorkes =
                    appDataBase.getWorekersDao().getByProvinceIdList(todayDate.id)
                loop@ for (worker in listWorkes) {
                    val trimnumber = worker.number.replace(" ", "")
                    if (trimnumber.equals(number) && worker.working == false) {
                        var posWosrker = posWosrker(todayDate.id)
                        if (posWosrker == -1) {
                            var pos = 1
                            worker.working = true
                            worker.message = smstexts
                            worker.sms_position = posWosrker
                            appDataBase.getWorekersDao().editWorker(worker)
                            sendSms(trimnumber, pos)
                            break@loop
                        } else {
                            posWosrker++
                            worker.working = true
                            worker.message = smstexts
                            worker.sms_position = posWosrker
                            appDataBase.getWorekersDao().editWorker(worker)
                            sendSms(trimnumber, posWosrker)
                            break@loop
                        }
                    }
                }

            }
        }

    }

    fun findProvince(contactName: String, number: String, date_id: Int) {
        var contact = contactName.lowercase()

        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val currentDate = sdf.format(Date())
        val todayDate = appDataBase.getDateDao().dateBy(currentDate.toString()) as Date_model
        val listProvince = appDataBase.getProvinceDao().getDateByIdListProvince(todayDate.id)

        if (contact.contains("xorazm") || contact
                .contains("хоразм")
        ) {
            val listWorkers = appDataBase.getWorekersDao().getByIdList2(listProvince[11].id)
            saveContactRoom(listWorkers, contactName, date_id, listProvince[11].id, number)
        } else if (contact.contains("nukus")
            || contact
                .contains("нукус")

        ) {
            val listWorkers = appDataBase.getWorekersDao().getByIdList2(listProvince[12].id)
            saveContactRoom(listWorkers, contactName, date_id, listProvince[12].id, number)
        } else if (contact.contains("jizzax")
            || contact
                .contains("жиззах")

        ) {
            val listWorkers = appDataBase.getWorekersDao().getByIdList2(listProvince[10].id)
            saveContactRoom(listWorkers, contactName, date_id, listProvince[10].id, number)
        } else if (contact.contains("navoiy")
            || contact
                .contains("навоий")

        ) {
            val listWorkers = appDataBase.getWorekersDao().getByIdList2(listProvince[9].id)
            saveContactRoom(listWorkers, contactName, date_id, listProvince[9].id, number)
        } else if (contact.contains("buxoro")
            || contact
                .contains("бухоро")

        ) {
            val listWorkers = appDataBase.getWorekersDao().getByIdList2(listProvince[8].id)
            saveContactRoom(listWorkers, contactName, date_id, listProvince[8].id, number)
        } else if (contact.contains("sirdaryo") || contact.contains("сирдарё")) {
            val listWorkers = appDataBase.getWorekersDao().getByIdList2(listProvince[7].id)
            saveContactRoom(listWorkers, contactName, date_id, listProvince[7].id, number)
        } else if (contact.contains("surxondaryo") || contact.contains("сурхондарё")) {
            val listWorkers = appDataBase.getWorekersDao().getByIdList2(listProvince[6].id)
            saveContactRoom(listWorkers, contactName, date_id, listProvince[6].id, number)
        } else if (contact.contains("qashqadaryo") || contact.contains("кашкадарё")) {
            val listWorkers = appDataBase.getWorekersDao().getByIdList2(listProvince[5].id)
            saveContactRoom(listWorkers, contactName, date_id, listProvince[5].id, number)
        } else if (contact.contains("samarqand") || contact.contains("самарканд")) {
            val listWorkers = appDataBase.getWorekersDao().getByIdList2(listProvince[4].id)
            saveContactRoom(listWorkers, contactName, date_id, listProvince[4].id, number)
        } else if (contact.contains("toshkent") || contact.contains("тошкент")) {
            val listWorkers = appDataBase.getWorekersDao().getByIdList2(listProvince[3].id)
            saveContactRoom(listWorkers, contactName, date_id, listProvince[3].id, number)
        } else if (contact.contains("namangan") || contact.contains("наманган")) {
            val listWorkers = appDataBase.getWorekersDao().getByIdList2(listProvince[2].id)
            saveContactRoom(listWorkers, contactName, date_id, listProvince[2].id, number)
        } else if (contact.contains("farg'ona") || contact.contains("фаргона")) {
            val listWorkers = appDataBase.getWorekersDao().getByIdList2(listProvince[1].id)
            saveContactRoom(listWorkers, contactName, date_id, listProvince[1].id, number)
        } else if (contact.contains("andijon") || contact.contains("андижон")) {
            val listWorkers = appDataBase.getWorekersDao().getByIdList2(listProvince[0].id)
            saveContactRoom(listWorkers, contactName, date_id, listProvince[0].id, number)
        }


    }

    private fun saveContactRoom(
        listWorkers: List<Workers_model>,
        contactName: String,
        date_id: Int,
        province_id: Int, number: String
    ) {
        var addContact = false

        if (listWorkers.isNotEmpty()) {
            for (i in listWorkers) {
                if (i.number.contentEquals(number)) {
                    addContact = true
                }
            }
        } else {
            appDataBase.getWorekersDao().addWorkers(
                Workers_model(
                    name = contactName,
                    number = number,
                    message = "",
                    working = false,
                    date_id_model = date_id,
                    province_id = province_id
                )
            )
            addContact = true
        }
        if (addContact == false) {
            appDataBase.getWorekersDao().addWorkers(
                Workers_model(
                    name = contactName,
                    number = number,
                    message = "",
                    working = false,
                    date_id_model = date_id,
                    province_id = province_id
                )
            )

        }

    }

    private fun posWosrker(date_id: Int): Int {
        var list = ArrayList<Workers_model>()
        list = appDataBase.getWorekersDao()
            .getByProvinceIdList(date_id) as ArrayList<Workers_model>
        if (list != null && list.size != 0) {
            var pos: Int = -1
            for (i in list) {
                if (i.sms_position > pos) {
                    pos = i.sms_position
                }
            }
            return pos
        }
        return -1
    }

    @SuppressLint("Range")
    private fun loadContact(date_id: Int) {
        val resolver: ContentResolver = this.contentResolver;
        val phones: Cursor? = resolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        if (phones != null) {
            while (phones.moveToNext()) {
                val phoneName =
                    phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                        .toString().lowercase()
                val phoneNumber =
                    phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        .toString().trim()

                findProvince(phoneName, phoneNumber, date_id)
            }
            phones.close()
        }

    }

    fun sendSms(phoneNumber: String, worekerPosition: Int) {
//        val sent = PendingIntent.getBroadcast(baseContext, 0, Intent("sent"), 0)
//        val deliver = PendingIntent.getBroadcast(baseContext, 0, Intent("delivered"), 0)

//        val intent = Intent(this, MainActivity::class.java)
//        val pi = PendingIntent.getActivity(this, 0, intent, FLAG_UPDATE_CURRENT)

        var sms = SmsManager.getDefault()
//        val intent = Intent(SENT)


        sms.sendTextMessage(phoneNumber, null, worekerPosition.toString(), null, null)
    }


}