class MessageDisplayStdout: MessageDisplay {
    override fun displayMessageToUser(message: String) {
        println(message)
    }

    override fun displayInlineMessageToUser(message: String) {
        print(message)
    }
}