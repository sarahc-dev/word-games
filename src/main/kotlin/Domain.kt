class Domain(
    wordListGetter: WordListGetter,
    messageDisplay: MessageDisplay,
    private val randomWordGetter: RandomWordGetter,
    private val guessGetter: GuessGetter,
    private val numberOfWordsGetter: NumberOfWordsGetter
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
        displayMessage("You will be shown some scrambled 5-letter words.")
        displayMessage("You have one chance to guess the word!")
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
        val userInput = numberOfWordsGetter.getNumberOfWordsFromUser()

        return when {
            userInput == "q!" -> null
            userInput?.toIntOrNull() != null -> userInput.toInt()
            else -> {
                displayMessage("Oops that's not a number. Try again.\n")
                askUserForNumberOfWords()
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
        val gameList = filterListByWordLength(5)
        displayWelcomeMessage()
        val numberOfWords = askUserForNumberOfWords() ?: return

        for (i in 1..numberOfWords) {
            if (!runGame(gameList)) return
        }
    }
}