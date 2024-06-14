package testClasses

import NumberOfWordsGetter

class NumberOfWordsGetterFake(private val listOfInputs: List<String>): NumberOfWordsGetter {
    private var index = 0

    override fun getNumberOfWordsFromUser(): String {
        val input = listOfInputs[index]
        index++
        return input
    }
}