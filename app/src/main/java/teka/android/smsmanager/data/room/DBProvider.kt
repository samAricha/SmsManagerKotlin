package teka.android.smsmanager.data.room

import android.content.Context
import android.telephony.SmsManager
import androidx.room.Room

object DBProvider {

    private var instance: SmsManagerDB? = null

    fun getDatabase(context: Context): SmsManagerDB {
        if (instance == null) {
            instance = Room.databaseBuilder(
                context.applicationContext,
                SmsManagerDB::class.java,
                "sms-manager-database"
            ).build()
        }
        return instance!!
    }


}