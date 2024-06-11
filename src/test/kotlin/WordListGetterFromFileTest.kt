import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class WordListGetterFromFileTest {
    @Test
    fun `given a text file, returns a list of words`() {
        val expected = true
        val actual = WordListGetterFromFile("words.txt").getList().isNotEmpty()
        assertEquals(expected, actual)
    }

    @Test
    fun `given a text file, returns given list of words`() {
        val expected = true
        val wordsList = WordListGetterFromFile("words.txt").getList()
        val actual = wordsList.containsAll(listOf("abalone", "abrosaurus", "angry", "potassium", "queen", "ravioli"))
        assertEquals(expected, actual)
    }
}