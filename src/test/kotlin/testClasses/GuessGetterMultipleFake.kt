package testClasses

import GuessGetter

class GuessGetterMultipleFake(private val guesses: List<String>): GuessGetter {
    private var count = 0

    override fun getGuessFromUser(): String {
        val guess = guesses[count]
        count++
        return guess
    }
}