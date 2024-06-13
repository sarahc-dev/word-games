package testClasses

import MessageDisplay

class MessageDisplayListFake : MessageDisplay {
    private val testMessages: MutableList<String> = mutableListOf()
    override fun displayMessageToUser(message: String) {
        testMessages.add(message)
    }

    override fun displayInlineMessageToUser(message: String) {
        testMessages.add(message)
    }

    fun getMessagesForTest(): List<String> {
        return testMessages
    }
}
