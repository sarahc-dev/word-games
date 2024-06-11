import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class WordListGetterHardcodedTest {
    @Test
    fun `it returns a list of words`() {
        val expected = true
        val actual = WordListGetterHardcoded().getList().isNotEmpty()
        assertEquals(expected, actual)
    }
}