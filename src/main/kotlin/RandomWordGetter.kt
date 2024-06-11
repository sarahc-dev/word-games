interface RandomWordGetter {
    fun getRandomWordFromList(list: List<String>): String
    fun getWordShuffled(word: String): String
}