package teka.android.smsmanager.presentation.send_sms_screen

import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import teka.android.smsmanager.data.room.Recipient
import teka.android.smsmanager.data.room.RecipientsDao

class SmsViewModel(private val recipientDao: RecipientsDao) : ViewModel() {
    var phoneNumber by mutableStateOf("")
    var message by mutableStateOf("")
    //private val appContext = application.applicationContext
    val smsManager1: SmsManager = SmsManager.getDefault()


    fun sendMessage(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val recipients = recipientDao.getAllRecipients().first()

                val sendJobs = recipients.map { recipient ->
                    val phoneNumber = recipient.phone
                    val message = "Hello, this is a broadcast message!"

                    async {
                        try {
                            val smsManager: SmsManager = SmsManager.getDefault()
                            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
                        } catch (e: Exception) {
                            Log.e("MessageSendingError", "Failed to send message: ${e.message}", e)
                            throw e
                        }
                    }
                }

                sendJobs.awaitAll() // Wait for all sending operations to complete

                showToastOnMainThread("All messages sent", context)
            } catch (e: Exception) {
                Log.e("MessageSendingError", "Failed to send messages: ${e.message}", e)
                showToastOnMainThread("Failed to send messages: ${e.message}", context)
            }
        }
    }


//    fun sendMessagesToAllRecipients(appContext: Context) {
//
//        viewModelScope.launch(Dispatchers.IO) {
//            val recipients = recipientDao.getAllRecipients().first()
//
//            recipients.forEach { recipient ->
//                val phoneNumber = recipient.phone
//                val message = "Hello, this is a broadcast message!"
//                sendSmsAsync(phoneNumber, message, appContext = appContext)
//            }
//
//        }
//    }


//    fun sendMessage(context: Context) {
//        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
//            showToast("Failed to send message: ${exception.message}", context)
//        }
//
//        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
//
//            try {
//
//                val allRecipients: Flow<List<Recipient>> = recipientDao.getAllRecipients()
//                val recipients = recipientDao.getAllRecipients().first()
//                val recipient1 = recipients[4]
//                Log.d("RECIPIENTS", "$recipient1")
//
//
//
//                recipients.forEach { recipient ->
//                    val phoneNumber = recipient.phone
//                    val message = "Hello, this is a broadcast message!"
//
//                    smsManager.sendTextMessage(phoneNumber, null, message, null, null)
//                }
//
//
//
////                    val sendJob = async(Dispatchers.IO) {
////
////                        val smsManager: SmsManager = SmsManager.getDefault()
////                        smsManager.sendTextMessage(phoneNumber, null, message, null, null)
////
////                    }
////                    sendJob.await() // Wait for the sendJob to complete
//
//                    // Display a success message to the user on the main thread
//                showToastOnMainThread("All messages sent", context)
//
//
//            } catch (e: Exception) {
//                Log.e("MessageSendingError", "Failed to send message: ${e.message}", e)
//
//                showToastOnMainThread("Failed to send messages: $e", context)
//
//                e.printStackTrace()
//            }
//
//        }
//
//
//    }

    private fun showToastOnMainThread(message: String, context: Context) {
        // Display the Toast message on the main thread using runOnUiThread
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }


    private fun showToast(message: String, context: Context) {
        // Display the Toast message using the application context
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }



    private suspend fun sendSmsAsync(phoneNumber: String, message: String, appContext: Context) {

        return suspendCancellableCoroutine { continuation ->
            val sentIntent = PendingIntent.getBroadcast(
                appContext,
                0,
                Intent("SMS_SENT"),
                0
            )

            val deliveredIntent = PendingIntent.getBroadcast(
                appContext,
                0,
                Intent("SMS_DELIVERED"),
                0
            )

            smsManager1.sendTextMessage(
                phoneNumber,
                null,
                message,
                sentIntent,
                deliveredIntent
            )

            continuation.invokeOnCancellation {
                // Handle cancellation if needed
            }
        }
    }


}


class SmsViewModelFactory(private val recipientDao: RecipientsDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SmsViewModel::class.java)) {
            return SmsViewModel(recipientDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}