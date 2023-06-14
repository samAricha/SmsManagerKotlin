package teka.android.smsmanager.presentation.send_sms_screen

import android.content.Context
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class SmsViewModel : ViewModel() {
    var phoneNumber by mutableStateOf("")
    var message by mutableStateOf("")

    fun sendMessage(context: Context) {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            showToast("Failed to send message: ${exception.message}", context)
        }

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {

            try {
                    val sendJob = async(Dispatchers.IO) {

                        val smsManager: SmsManager = SmsManager.getDefault()
                        smsManager.sendTextMessage(phoneNumber, null, message, null, null)

                    }
                    sendJob.await() // Wait for the sendJob to complete

                    // Display a success message to the user on the main thread
                showToastOnMainThread("All messages sent", context)


            } catch (e: Exception) {
                Log.e("MessageSendingError", "Failed to send message: ${e.message}", e)

                showToastOnMainThread("Failed to send messages: $e", context)

                e.printStackTrace()
            }

        }


    }

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


}