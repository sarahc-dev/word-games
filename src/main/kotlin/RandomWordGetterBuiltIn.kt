class RandomWordGetterBuiltIn: RandomWordGetter {
    override fun getRandomWordFromList(list: List<String>): String {
        return list.random()
    }

    override fun getWordShuffled(word: String): String {
        return word.toList().shuffled().joinToString("")
    }
}