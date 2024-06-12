interface RandomizerPort {
    fun getRandomElementFromList(list: List<String>): String
    fun getShuffledWord(word: String): String
}