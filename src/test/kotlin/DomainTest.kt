import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import testClasses.*

private const val success = "You guessed correct!"
private const val failureWasWords = "Oops, the correct word was: words"
private const val retryNumberInput = "Oops that's not a valid number. Try again.\n"

class DomainTest {
    val testWordList = WordListGetterFake()
    val testMessageDisplay = MessageDisplayFake()
    val testMessageDisplayList = MessageDisplayListFake()
    val testNumberInputs1Word5Letters = NumberGetterFake(listOf("1", "5"))
    val testNumberInputs2Word5Letters = NumberGetterFake(listOf("2", "5"))

    @Nested
    @DisplayName("Run")
    inner class Run {
        @Test
        fun `if user input matches the word, returns success message`() {
            // Arrange
            val randomWordReturnsLogic = RandomWordGetterFake("logic")
            val guessGetterReturnsLogic = GuessGetterFake("logic")

            val expected = success

            // Act
            Domain(
                testWordList,
                testMessageDisplay,
                randomWordReturnsLogic,
                guessGetterReturnsLogic,
                testNumberInputs1Word5Letters
            ).run()
            val actual = testMessageDisplay.getLastMessageForTest()

            // Assert
            assertEquals(expected, actual)
        }

        @Test
        fun `if user input is a valid word but does not match, returns success message`() {
            val randomWordReturnsWords = RandomWordGetterFake("words")
            val guessGetterReturnsSword = GuessGetterFake("sword")
            val expected = success
            Domain(
                testWordList,
                testMessageDisplay,
                randomWordReturnsWords,
                guessGetterReturnsSword,
                testNumberInputs1Word5Letters
            ).run()
            val actual = testMessageDisplay.getLastMessageForTest()
            assertEquals(expected, actual)
        }

        @Test
        fun `if user input is not a valid word, returns fail message`() {
            val randomWordReturnsWords = RandomWordGetterFake("words")
            val guessGetterReturnsHello = GuessGetterFake("hello")
            val expected = failureWasWords
            Domain(
                testWordList,
                testMessageDisplay,
                randomWordReturnsWords,
                guessGetterReturnsHello,
                testNumberInputs1Word5Letters
            ).run()
            val actual = testMessageDisplay.getLastMessageForTest()
            assertEquals(expected, actual)
        }

        @Test
        fun `if user input is valid chars but not a word, returns fail message`() {
            val randomWordReturnsWords = RandomWordGetterFake("words")
            val guessGetterReturnsOrdsw = GuessGetterFake("ordsw")
            val expected = failureWasWords
            Domain(
                testWordList,
                testMessageDisplay,
                randomWordReturnsWords,
                guessGetterReturnsOrdsw,
                testNumberInputs1Word5Letters
            ).run()
            val actual = testMessageDisplay.getLastMessageForTest()
            assertEquals(expected, actual)
        }

        @Test
        fun `if user input is empty string, returns fails message`() {
            val randomWordGetterReturnsWords = RandomWordGetterFake("words")
            val guessGetterReturnsEmptyString = GuessGetterFake("")
            val expected = failureWasWords
            Domain(
                testWordList,
                testMessageDisplay,
                randomWordGetterReturnsWords,
                guessGetterReturnsEmptyString,
                testNumberInputs1Word5Letters
            ).run()
            val actual = testMessageDisplay.getLastMessageForTest()
            assertEquals(expected, actual)
        }

        @Test
        fun `if user input is null, returns fails message`() {
            val randomWordGetterReturnsWords = RandomWordGetterFake("words")
            val guessGetterReturnsNull = GuessGetterFake(null)
            val expected = failureWasWords
            Domain(
                testWordList,
                testMessageDisplay,
                randomWordGetterReturnsWords,
                guessGetterReturnsNull,
                testNumberInputs1Word5Letters
            ).run()
            val actual = testMessageDisplay.getLastMessageForTest()
            assertEquals(expected, actual)
        }

        @Test
        fun `if user input is 'q!', returns without displaying result`() {
            val randomWordGetterReturnsWords = RandomWordGetterFake("words")
            val guessGetterReturnsQuit = GuessGetterFake("q!")
            val expected = "Your guess: "
            Domain(
                testWordList,
                testMessageDisplay,
                randomWordGetterReturnsWords,
                guessGetterReturnsQuit,
                testNumberInputs1Word5Letters
            ).run()
            val actual = testMessageDisplay.getLastMessageForTest()
            assertEquals(expected, actual)
        }
    }

    @Nested
    @DisplayName("Run multiple times")
    inner class RunMultipleTimes {
        @Test
        fun `if user correctly guesses word twice, returns success message twice`() {
            val randomWordGetterReturnsWords = RandomWordGetterFake("words")
            val guessGetterReturnsWords = GuessGetterFake("words")
            val expectedMessage = success
            val expectedCount = 2

            Domain(
                testWordList,
                testMessageDisplayList,
                randomWordGetterReturnsWords,
                guessGetterReturnsWords,
                testNumberInputs2Word5Letters
            ).run()

            val actual = testMessageDisplayList.getMessagesForTest().count { it == expectedMessage }
            assertEquals(expectedCount, actual)
        }

        @Test
        fun `if user input is q! in first attempt, it ends program`() {
            val randomWordGetterReturnsWords = RandomWordGetterFake("words")
            val guessGetterReturnsQuit = GuessGetterFake("q!")
            val expectedMessage = "Your guess: "
            val expectedCount = 1

            Domain(
                testWordList,
                testMessageDisplayList,
                randomWordGetterReturnsWords,
                guessGetterReturnsQuit,
                testNumberInputs2Word5Letters
            ).run()

            val messageList = testMessageDisplayList.getMessagesForTest()
            val actualCount = messageList.count { it == expectedMessage }
            val isLastMessage = messageList.last() == expectedMessage
            assertEquals(expectedCount, actualCount)
            assertEquals(true, isLastMessage)
        }

        @Test
        fun `it returns correct result messages for multiple plays`() {
            val randomWordGetterReturnsWords = RandomWordGetterFake("words")
            val guessGetterGuesses = GuessGetterMultipleFake(listOf("words", "test"))
            val firstExpectedMessage = success
            val secondExpectedMessage = failureWasWords

            Domain(
                testWordList,
                testMessageDisplayList,
                randomWordGetterReturnsWords,
                guessGetterGuesses,
                testNumberInputs2Word5Letters
            ).run()

            val messageList = testMessageDisplayList.getMessagesForTest()
            val firstExpectedMessageCount = messageList.count { it == firstExpectedMessage }
            val secondExpectedMessageCount = messageList.count { it == secondExpectedMessage }
            val isCorrectSequence = messageList.indexOf(firstExpectedMessage) < messageList.indexOf(secondExpectedMessage)
            assertEquals(1, firstExpectedMessageCount)
            assertEquals(1, secondExpectedMessageCount)
            assertEquals(true, isCorrectSequence)
        }

        @Test
        fun `if user specifies 4 words, plays game 4 times`() {
            val randomWordGetterReturnsWords = RandomWordGetterFake("words")
            val guessGetterGuesses = GuessGetterMultipleFake(listOf("words", "test", "guess", "dog"))
            val testNumberInputs4Word5Letters = NumberGetterFake(listOf("4", "5"))

            val expectedSuccessMessage = success
            val expectedFailMessage = failureWasWords
            val expectedCount = 4

            Domain(
                testWordList,
                testMessageDisplayList,
                randomWordGetterReturnsWords,
                guessGetterGuesses,
                testNumberInputs4Word5Letters
            ).run()

            val messageList = testMessageDisplayList.getMessagesForTest()
            val actualCount = messageList.count { it == expectedSuccessMessage || it == expectedFailMessage }
            assertEquals(expectedCount, actualCount)
        }

        @Test
        fun `if user does not enter a number, it continues until they do`() {
            val randomWordGetterReturnsWords = RandomWordGetterFake("words")
            val guessGetterGuesses = GuessGetterMultipleFake(listOf("words"))
            val numberInputs = NumberGetterFake(listOf("no", "ok", "1", "5"))
            val expectedIncorrectInputCount = 2
            val expectedSuccessCount = 1

            Domain(
                testWordList,
                testMessageDisplayList,
                randomWordGetterReturnsWords,
                guessGetterGuesses,
                numberInputs
            ).run()

            val messageList = testMessageDisplayList.getMessagesForTest()
            val actualIncorrectInputCount = messageList.count { it == retryNumberInput }
            val actualSuccessCount = messageList.count { it == success }
            assertEquals(expectedIncorrectInputCount, actualIncorrectInputCount)
            assertEquals(expectedSuccessCount, actualSuccessCount)
        }

        @Test
        fun `if user quits game and does not enter number, it ends program`() {
            val randomWordGetterReturnsWords = RandomWordGetterFake("words")
            val guessGetterGuesses = GuessGetterMultipleFake(listOf("words"))
            val numberOfWordsGetter = NumberGetterFake(listOf("q!"))
            val messagesNotExpected = listOf(success, failureWasWords, retryNumberInput)

            Domain(
                testWordList,
                testMessageDisplayList,
                randomWordGetterReturnsWords,
                guessGetterGuesses,
                numberOfWordsGetter
            ).run()

            val messageList = testMessageDisplayList.getMessagesForTest()
            val actual = messagesNotExpected.all { it !in messageList }
            assertEquals(true, actual)
        }
    }

    @Nested
    @DisplayName("Run with user selected word length")
    inner class RunWithUserSelectedWordLength {
        @Test
        fun `if user inputs words of length 4, it plays game with 4 letter words`() {
            val randomWordGetterReturnsWords = RandomWordGetterFake("test")
            val guessGetterGuesses = GuessGetterMultipleFake(listOf("test"))
            val numberInputs = NumberGetterFake(listOf("1", "4"))

            Domain(
                testWordList,
                testMessageDisplayList,
                randomWordGetterReturnsWords,
                guessGetterGuesses,
                numberInputs
            ).run()

            val messageList = testMessageDisplayList.getMessagesForTest()
            assertEquals(true, messageList.contains(success))
        }

        @Test
        fun `if user inputs word length less than exists, asks to try again`() {
            val randomWordGetterReturnsWords = RandomWordGetterFake("test")
            val guessGetterGuesses = GuessGetterMultipleFake(listOf("test"))
            val numberInputs = NumberGetterFake(listOf("1", "1", "4"))

            Domain(
                testWordList,
                testMessageDisplayList,
                randomWordGetterReturnsWords,
                guessGetterGuesses,
                numberInputs
            ).run()

            val messageList = testMessageDisplayList.getMessagesForTest()
            val actual = messageList.containsAll(listOf(retryNumberInput, success))
            assertEquals(true, actual)
        }

        @Test
        fun `if user inputs word length greater than exists, asks to try again`() {
            val randomWordGetterReturnsWords = RandomWordGetterFake("test")
            val guessGetterGuesses = GuessGetterMultipleFake(listOf("test"))
            val numberInputs = NumberGetterFake(listOf("1", "8", "4"))

            Domain(
                testWordList,
                testMessageDisplayList,
                randomWordGetterReturnsWords,
                guessGetterGuesses,
                numberInputs
            ).run()

            val messageList = testMessageDisplayList.getMessagesForTest()
            val actual = messageList.containsAll(listOf(retryNumberInput, success))
            assertEquals(true, actual)
        }

        @Test
        fun `if user does not enter a number for word length, it continues until they do`() {
            val randomWordGetterReturnsWords = RandomWordGetterFake("words")
            val guessGetterGuesses = GuessGetterMultipleFake(listOf("words"))
            val numberInputs = NumberGetterFake(listOf("1", "ok", "no", "what", "5"))
            val expectedIncorrectInputCount = 3
            val expectedSuccessCount = 1

            Domain(
                testWordList,
                testMessageDisplayList,
                randomWordGetterReturnsWords,
                guessGetterGuesses,
                numberInputs
            ).run()

            val messageList = testMessageDisplayList.getMessagesForTest()
            val actualIncorrectInputCount = messageList.count { it == retryNumberInput }
            val actualSuccessCount = messageList.count { it == success }
            assertEquals(expectedIncorrectInputCount, actualIncorrectInputCount)
            assertEquals(expectedSuccessCount, actualSuccessCount)
        }
    }
}