import TestClasses.GuessGetterFake
import TestClasses.MessageDisplayFake
import TestClasses.RandomWordGetterFake
import TestClasses.WordListGetterTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class DomainTest {
    val wordListGetterTest = WordListGetterTest()
    val testMessageDisplay = MessageDisplayFake()

    @Nested
    @DisplayName("Runs unscramble game")
    inner class Run {
        @Test
        fun `if user input matches the word, returns success message`() {
            // Arrange
            val randomWordGetterReturnsLogic = RandomWordGetterFake("logic")
            val guessGetterReturnsLogic = GuessGetterFake("logic")

            val expected = "You guessed correct!"

            // Act
            Domain(
                wordListGetterTest,
                testMessageDisplay,
                randomWordGetterReturnsLogic,
                guessGetterReturnsLogic
            ).run()
            val actual = testMessageDisplay.getLastMessageForTest()

            // Assert
            assertEquals(expected, actual)
        }

        @Test
        fun `if user input is a valid word but does not match, returns success message`() {
            val randomWordGetterReturnsWords = RandomWordGetterFake("words")
            val guessGetterReturnsSword = GuessGetterFake("sword")
            val expected = "You guessed correct!"
            Domain(
                wordListGetterTest,
                testMessageDisplay,
                randomWordGetterReturnsWords,
                guessGetterReturnsSword
            ).run()
            val actual = testMessageDisplay.getLastMessageForTest()
            assertEquals(expected, actual)
        }

        @Test
        fun `if user input is not a valid word, returns fail message`() {
            val randomWordGetterReturnsWords = RandomWordGetterFake("words")
            val guessGetterReturnsHello = GuessGetterFake("hello")
            val expected = "Oops, the correct word was: words"
            Domain(
                wordListGetterTest,
                testMessageDisplay,
                randomWordGetterReturnsWords,
                guessGetterReturnsHello
            ).run()
            val actual = testMessageDisplay.getLastMessageForTest()
            assertEquals(expected, actual)
        }

        @Test
        fun `if user input is valid chars but not a word, returns fail message`() {
            val randomWordGetterReturnsWords = RandomWordGetterFake("words")
            val guessGetterReturnsOrdsw = GuessGetterFake("ordsw")
            val expected = "Oops, the correct word was: words"
            Domain(
                wordListGetterTest,
                testMessageDisplay,
                randomWordGetterReturnsWords,
                guessGetterReturnsOrdsw
            ).run()
            val actual = testMessageDisplay.getLastMessageForTest()
            assertEquals(expected, actual)
        }

        @Test
        fun `if user input is empty string, returns fails message`() {
            val randomWordGetterReturnsWords = RandomWordGetterFake("words")
            val guessGetterReturnsEmptyString = GuessGetterFake("")
            val expected = "Oops, the correct word was: words"
            Domain(
                wordListGetterTest,
                testMessageDisplay,
                randomWordGetterReturnsWords,
                guessGetterReturnsEmptyString
            ).run()
            val actual = testMessageDisplay.getLastMessageForTest()
            assertEquals(expected, actual)
        }

        @Test
        fun `if user input is null, returns fails message`() {
            val randomWordGetterReturnsWords = RandomWordGetterFake("words")
            val guessGetterReturnsNull = GuessGetterFake(null)
            val expected = "Oops, the correct word was: words"
            Domain(
                wordListGetterTest,
                testMessageDisplay,
                randomWordGetterReturnsWords,
                guessGetterReturnsNull
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
                guessGetterReturnsQuit
            ).run()
            val actual = testMessageDisplay.getLastMessageForTest()
            assertEquals(expected, actual)
        }
    }
}