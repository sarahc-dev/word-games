class NumberGetterStdin: NumberGetter {
    override fun getNumberOfWordsFromUser(): String? {
        return readlnOrNull()
    }
}