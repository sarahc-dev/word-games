import java.io.File
import java.nio.file.Path

class WordListGetterFromFile(private val filepath: String): WordListGetter {
    override fun getList(): List<String> {
        val cwd = Path.of("").toAbsolutePath()
        val file = File("$cwd/src/main/$filepath")
        return file.bufferedReader().readLines()
    }
}