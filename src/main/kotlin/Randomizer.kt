class Randomizer: RandomizerPort {
    override fun getRandomElementFromList(list: List<String>): String {
        return list.random()
    }

    override fun getShuffledWord(word: String): String {
        return word.toList().shuffled().joinToString("")
    }
}