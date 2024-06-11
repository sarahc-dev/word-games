import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class RandomWordGetterBuiltInTest {
    @Test
    fun `getRandomElementFromList returns an element from list`() {
        val list = listOf("pound", "trice", "hired", "comma", "logic")
        val randomWordGeneratorBuiltIn = RandomWordGetterBuiltIn()
        val elements = (1..20).map { randomWordGeneratorBuiltIn.getRandomWordFromList(list)}
        val checkElementsAreInList = elements.all { list.contains(it) }
        val checkElementsNotAllSame = !elements.all { it == elements[0] }

        assertEquals(true, checkElementsAreInList)
        assertEquals(true, checkElementsNotAllSame)
    }

    @Test
    fun `getShuffledWord returns the word shuffled`() {
        val word = "pound"
        val result = RandomWordGetterBuiltIn().getWordShuffled(word)
        val actual = word.toList().sorted() == result.toList().sorted()
        assertNotEquals(word, result)
        assertEquals(true, actual)
    }
}