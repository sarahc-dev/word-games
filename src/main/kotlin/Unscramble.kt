class Unscramble {
    private val words = listOf("pound", "trice", "hired", "comma", "logic")

    private fun getInput():String? = readlnOrNull()

    private fun printWelcomeMessage() {
        println("Welcome to Word Games!")
        println("You will be shown a scrambled 5-letter word.")
        println("You have one chance to guess the word!")
        println("(To exit, type 'Exit' and press Enter)\n")
    }

    fun run() {
        val randomWord = words.random()
        val result = guessWord(randomWord, ::getInput)
        println(result)
    }

    fun guessWord(word: String, userInput: () -> String?): String {
        val scrambledWord = word.toList().shuffled().joinToString("")
        printWelcomeMessage()
        println("Your word is: $scrambledWord")
        print("Your guess: ")
        val result = userInput()
        return when (result) {
            "Exit" -> ""
            word -> "You guessed correct!"
            else -> "Oops, the correct word was: $word"
        }
    }
}