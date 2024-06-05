import java.io.File
import java.nio.file.Path

class Unscramble {
    private fun getInput():String? = readlnOrNull()

    private fun printWelcomeMessage() {
        println("Welcome to Word Games!")
        println("You will be shown a scrambled 5-letter word.")
        println("You have one chance to guess the word!")
        println("(To exit, type 'Exit' and press Enter)\n")
    }

    fun run() {
        val result = guessWord(getRandomWord(), ::getInput)
        println(result)
    }

    fun getRandomWord(): String {
        val cwd = Path.of("").toAbsolutePath()
        val file = File("$cwd/src/main/words.txt")
        val listOf5LetterWords = file.bufferedReader().readLines().filter { it.length == 5}
        return listOf5LetterWords.random()
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