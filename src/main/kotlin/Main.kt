fun main() {
    // adapters
    val wordListGetter: WordListGetter = WordListGetterFromFile("words.txt")
    val messageDisplay: MessageDisplay = MessageDisplayStdout()
    val randomWordGetter: RandomWordGetter = RandomWordGetterBuiltIn()
    val guessGetter: GuessGetter = GuessGetterStdin()

    // domain
    val app = Domain(wordListGetter, messageDisplay, randomWordGetter, guessGetter)
    app.run()
}