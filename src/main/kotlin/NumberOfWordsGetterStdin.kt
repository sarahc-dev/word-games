class NumberOfWordsGetterStdin: NumberOfWordsGetter {
    override fun getNumberOfWordsFromUser(): String? {
        return readlnOrNull()
    }
}