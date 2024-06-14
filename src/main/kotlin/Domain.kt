class Domain(
    private val wordListGetter: WordListGetter,
    messageDisplay: MessageDisplay,
    private val randomWordGetter: RandomWordGetter,
    private val guessGetter: GuessGetter,
    private val numberGetter: NumberGetter
) {
    private val list = wordListGetter.getList()
    private val displayMessage = messageDisplay::displayMessageToUser
    private val displayMessageInline = messageDisplay::displayInlineMessageToUser

    private val asciiTitle = " __        __            _    ____                           \n" +
            " \\ \\      / /__  _ __ __| |  / ___| __ _ _ __ ___   ___  ___ \n" +
            "  \\ \\ /\\ / / _ \\| '__/ _` | | |  _ / _` | '_ ` _ \\ / _ \\/ __|\n" +
            "   \\ V  V / (_) | | | (_| | | |_| | (_| | | | | | |  __/\\__ \\\n" +
            "    \\_/\\_/ \\___/|_|  \\__,_|  \\____|\\__,_|_| |_| |_|\\___||___/\n" +
            "                                                             "

    private fun displayWelcomeMessage() {
        displayMessage(asciiTitle)
        displayMessage("Welcome to Word Games!")
        displayMessage("You will be shown a scrambled word.")
        displayMessage("You have one chance to guess each word!")
        displayMessage("(To exit, type 'q!' and press Enter)\n")
    }

    private fun filterListByWordLength(length: Int): List<String> {
        return list.filter { it.length == length}
    }

    private fun checkUserInputIsValid(word: String, input: String?): Boolean {
        val inputSorted = input?.toList()?.sorted()
        if (inputSorted != word.toList().sorted()) return false
        return list.contains(input)
    }

    private fun askUserForNumberOfWords(): Int? {
        displayMessage("How many words would you like to play?")
        val userInput = numberGetter.getNumberOfWordsFromUser()

        return when {
            userInput == "q!" -> null
            userInput?.toIntOrNull() != null && userInput.toInt() > 0 -> userInput.toInt()
            else -> {
                displayMessage("Oops that's not a valid number. Try again.\n")
                askUserForNumberOfWords()
            }
        }
    }

    private fun askUserForWordLength(min: Int, max: Int): Int? {
        displayMessage("What length should the words be?")
        displayMessage("(Please enter a number between $min and $max)")
        val userInput = numberGetter.getNumberOfWordsFromUser()

        return when {
            userInput == "q!" -> null
            userInput?.toIntOrNull() != null && userInput.toInt() >= min && userInput.toInt() <= max -> userInput.toInt()
            else -> {
                displayMessage("Oops that's not a valid number. Try again.\n")
                askUserForWordLength(min, max)
            }
        }
    }

    private fun runGame(wordList: List<String>): Boolean {
        val randomWord = randomWordGetter.getRandomWordFromList(wordList)
        val shuffledWord = randomWordGetter.getWordShuffled(randomWord)
        displayMessage("\nYour word is: $shuffledWord")
        displayMessageInline("Your guess: ")
        val userGuess = guessGetter.getGuessFromUser()
        if (userGuess == "q!") return false
        val isValid = checkUserInputIsValid(shuffledWord, userGuess)
        if (isValid) displayMessage("You guessed correct!") else displayMessage("Oops, the correct word was: $randomWord")
        return true
    }

    fun run() {
        displayWelcomeMessage()
        val numberOfWords = askUserForNumberOfWords() ?: return
        val lengthOfWords = askUserForWordLength(wordListGetter.minWordLength(), wordListGetter.maxWordLength()) ?: return
        val gameList = filterListByWordLength(lengthOfWords)

        for (i in 1..numberOfWords) {
            if (!runGame(gameList)) return
        }
    }
}