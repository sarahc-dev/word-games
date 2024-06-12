class InputFromCommandLine: InputPort {
    override fun getInput(): String? {
        return readlnOrNull()
    }
}