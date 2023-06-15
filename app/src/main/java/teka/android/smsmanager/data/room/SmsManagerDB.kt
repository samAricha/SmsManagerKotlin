package teka.android.smsmanager.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Recipient::class], version = 1)
abstract class SmsManagerDB: RoomDatabase() {

        abstract fun recipientsDao(): RecipientsDao

}