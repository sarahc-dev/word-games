# Word Games

- Check if you have Java and the Kotlin compiler installed:

```bash
$ java -version
$ kotlinc -version
```

- Commands to compile and run program:

```bash
$ kotlinc src/main/kotlin/*.kt -include-runtime -d src/main/kotlin/unscramble.jar
$ java -jar src/main/kotlin/unscramble.jar
```

- To run the tests:

```bash
$ ./gradlew test
```

## Install the command-line compiler

You can install the latest version manually or via a package manager. 

Follow the instructions [here](https://kotlinlang.org/docs/command-line.html) to install the compiler.

