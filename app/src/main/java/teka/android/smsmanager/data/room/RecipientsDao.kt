package teka.android.smsmanager.data.room

import androidx.room.*

@Dao
interface RecipientsDao {

        @Query("SELECT * FROM recipients")
        fun getAllRecipients(): List<Recipient>

        @Insert
        fun insertRecipient(recipient: Recipient)

        @Update
        fun updateRecipient(recipient: Recipient)

        @Delete
        fun deleteRecipient(recipient: Recipient)


}