import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class UnscrambleTest {
    @Test
    fun `if user input matches the word, returns success message`() {
        // Arrange
        val mockInput = fun(): String { return "random" }
        val expected = "You guessed correct!"
        // Act
        val actual = Unscramble().guessWord("random", mockInput)
        //Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `if user input does not match the word, returns fail message`() {
        val mockInput = fun(): String { return "no idea" }
        val expected = "Oops, the correct word was: random"
        val actual = Unscramble().guessWord("random", mockInput)
        assertEquals(expected, actual)
    }

    @Test
    fun `if user input is 'Exit', returns empty string`() {
        val mockInput = fun(): String { return "Exit" }
        val expected = ""
        val actual = Unscramble().guessWord("random", mockInput)
        assertEquals(expected, actual)
    }

    @Test
    fun `if users enters nothing, returns fail message`() {
        val mockInput = fun(): String? { return null }
        val expected = "Oops, the correct word was: random"
        val actual = Unscramble().guessWord("random", mockInput)
        assertEquals(expected, actual)
    }
}