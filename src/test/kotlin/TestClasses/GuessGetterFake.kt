package TestClasses

import GuessGetter

class GuessGetterFake(private val guess: String?): GuessGetter {
    override fun getGuessFromUser(): String? {
        return guess
    }
}