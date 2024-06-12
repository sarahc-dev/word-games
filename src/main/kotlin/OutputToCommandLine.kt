class OutputToCommandLine: OutputPort {
    override fun display(message: String) {
        println(message)
    }

    override fun displayInline(message: String) {
        print(message)
    }
}