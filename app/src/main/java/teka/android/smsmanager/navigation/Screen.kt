package teka.android.smsmanager.navigation

sealed class Screen(val route: String) {
    object SendSms : Screen(route = "send_sms_screen")
    object Recipients : Screen(route = "recipients_list_screen")
    object addRecipient : Screen(route = "add_recipient")
}