class GuessGetterStdin: GuessGetter {
    override fun getGuessFromUser(): String? {
        return readlnOrNull()
    }
}