import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import testClasses.*

private const val success = "You guessed correct!"
private const val failureWasWords = "Oops, the correct word was: words"
private const val retryNumberInput = "Oops that's not a number. Try again.\n"

class DomainTest {
    val wordListGetterTest = WordListGetterTest()
    val testMessageDisplay = MessageDisplayFake()
    val testMessageDisplayList = MessageDisplayListFake()
    val testNumberOfWordsGetter = NumberOfWordsGetterFake(listOf("2"))

    @Nested
    @DisplayName("Run")
    inner class Run {
        @Test
        fun `if user input matches the word, returns success message`() {
            // Arrange
            val randomWordGetterReturnsLogic = RandomWordGetterFake("logic")
            val guessGetterReturnsLogic = GuessGetterFake("logic")

            val expected = success

            // Act
            Domain(
                wordListGetterTest,
                testMessageDisplay,
                randomWordGetterReturnsLogic,
                guessGetterReturnsLogic,
                testNumberOfWordsGetter
            ).run()
            val actual = testMessageDisplay.getLastMessageForTest()

            // Assert
            assertEquals(expected, actual)
        }

        @Test
        fun `if user input is a valid word but does not match, returns success message`() {
            val randomWordGetterReturnsWords = RandomWordGetterFake("words")
            val guessGetterReturnsSword = GuessGetterFake("sword")
            val expected = success
            Domain(
                wordListGetterTest,
                testMessageDisplay,
                randomWordGetterReturnsWords,
                guessGetterReturnsSword,
                testNumberOfWordsGetter
            ).run()
            val actual = testMessageDisplay.getLastMessageForTest()
            assertEquals(expected, actual)
        }

        @Test
        fun `if user input is not a valid word, returns fail message`() {
            val randomWordGetterReturnsWords = RandomWordGetterFake("words")
            val guessGetterReturnsHello = GuessGetterFake("hello")
            val expected = failureWasWords
            Domain(
                wordListGetterTest,
                testMessageDisplay,
                randomWordGetterReturnsWords,
                guessGetterReturnsHello,
                testNumberOfWordsGetter
            ).run()
            val actual = testMessageDisplay.getLastMessageForTest()
            assertEquals(expected, actual)
        }

        @Test
        fun `if user input is valid chars but not a word, returns fail message`() {
            val randomWordGetterReturnsWords = RandomWordGetterFake("words")
            val guessGetterReturnsOrdsw = GuessGetterFake("ordsw")
            val expected = failureWasWords
            Domain(
                wordListGetterTest,
                testMessageDisplay,
                randomWordGetterReturnsWords,
                guessGetterReturnsOrdsw,
                testNumberOfWordsGetter
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
                wordListGetterTest,
                testMessageDisplay,
                randomWordGetterReturnsWords,
                guessGetterReturnsEmptyString,
                testNumberOfWordsGetter
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
                wordListGetterTest,
                testMessageDisplay,
                randomWordGetterReturnsWords,
                guessGetterReturnsNull,
                testNumberOfWordsGetter
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
                wordListGetterTest,
                testMessageDisplay,
                randomWordGetterReturnsWords,
                guessGetterReturnsQuit,
                testNumberOfWordsGetter
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
                wordListGetterTest,
                testMessageDisplayList,
                randomWordGetterReturnsWords,
                guessGetterReturnsWords,
                testNumberOfWordsGetter
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
                wordListGetterTest,
                testMessageDisplayList,
                randomWordGetterReturnsWords,
                guessGetterReturnsQuit,
                testNumberOfWordsGetter
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
                wordListGetterTest,
                testMessageDisplayList,
                randomWordGetterReturnsWords,
                guessGetterGuesses,
                testNumberOfWordsGetter
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
            val numberOfWordsGetter = NumberOfWordsGetterFake(listOf("4"))

            val expectedSuccessMessage = success
            val expectedFailMessage = failureWasWords
            val expectedCount = 4

            Domain(
                wordListGetterTest,
                testMessageDisplayList,
                randomWordGetterReturnsWords,
                guessGetterGuesses,
                numberOfWordsGetter
            ).run()

            val messageList = testMessageDisplayList.getMessagesForTest()
            val actualCount = messageList.count { it == expectedSuccessMessage || it == expectedFailMessage }
            assertEquals(expectedCount, actualCount)
        }

        @Test
        fun `if user does not enter a number, it continues until they do`() {
            val randomWordGetterReturnsWords = RandomWordGetterFake("words")
            val guessGetterGuesses = GuessGetterMultipleFake(listOf("words"))
            val numberOfWordsGetter = NumberOfWordsGetterFake(listOf("no", "ok", "1"))
            val expectedIncorrectInputCount = 2
            val expectedSuccessCount = 1

            Domain(
                wordListGetterTest,
                testMessageDisplayList,
                randomWordGetterReturnsWords,
                guessGetterGuesses,
                numberOfWordsGetter
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
            val numberOfWordsGetter = NumberOfWordsGetterFake(listOf("q!"))
            val messagesNotExpected = listOf(success, failureWasWords, retryNumberInput)

            Domain(
                wordListGetterTest,
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
}