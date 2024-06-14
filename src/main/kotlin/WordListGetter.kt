interface WordListGetter {
    fun getList(): List<String>
    fun minWordLength(): Int
    fun maxWordLength(): Int
}