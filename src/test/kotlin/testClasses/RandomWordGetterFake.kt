package testClasses

import RandomWordGetter

class RandomWordGetterFake(private val testWord: String): RandomWordGetter {
    override fun getRandomWordFromList(list: List<String>): String {
        return testWord
    }

    override fun getWordShuffled(word: String): String {
        return testWord.toList().shuffled().joinToString("")
    }
}