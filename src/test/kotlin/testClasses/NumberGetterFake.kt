package testClasses

import NumberGetter

class NumberGetterFake(private val listOfInputs: List<String>): NumberGetter {
    private var index = 0

    override fun getNumberOfWordsFromUser(): String {
        val input = listOfInputs[index]
        index++
        return input
    }
}