package testClasses

import MessageDisplay

class MessageDisplayFake: MessageDisplay {
    private var testMessage = ""
    override fun displayMessageToUser(message: String) {
        testMessage = message
    }
    override fun displayInlineMessageToUser(message: String) {
        testMessage = message
    }

    fun getLastMessageForTest(): String {
        return testMessage
    }
}