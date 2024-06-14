class WordListGetterHardcoded: WordListGetter {
    private var list = listOf("pound", "trice", "hired", "comma", "logic")

    override fun getList(): List<String> {
        return list
    }

    override fun minWordLength(): Int {
        return list.minWith( compareBy { it.length }).length
    }

    override fun maxWordLength(): Int {
        return list.maxWith( compareBy { it.length }).length
    }
}