import java.io.File
import java.nio.file.Path

class WordListGetterFromFile(private val filepath: String): WordListGetter {
    private val list: List<String>
    init {
        val cwd = Path.of("").toAbsolutePath()
        val file = File("$cwd/src/main/$filepath")
        list = file.bufferedReader().readLines()
    }

    override fun getList(): List<String> {
        return list
    }

    override fun minWordLength(): Int {
        return list.minWith( compareBy { it.length }).length
    }

    override fun maxWordLength(): Int {
        return list.maxWith( compareBy { it.length }).length
    }
}