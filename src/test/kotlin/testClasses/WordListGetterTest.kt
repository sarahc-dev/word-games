package testClasses

import WordListGetter

class WordListGetterTest: WordListGetter {
    override fun getList(): List<String> {
        return listOf("pound", "trice", "hired", "words", "sword", "logic")
    }
}