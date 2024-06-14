package testClasses

import WordListGetter

class WordListGetterFake: WordListGetter {
    override fun getList(): List<String> {
        return listOf("pound", "trice", "hired", "words", "sword", "logic", "test")
    }

    override fun minWordLength(): Int {
        return 4
    }

    override fun maxWordLength(): Int {
        return 5
    }
}