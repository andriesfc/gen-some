package generate.cmd

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.validate
import com.github.ajalt.clikt.parameters.types.int
import com.github.javafaker.Faker
import com.github.javafaker.Name

class Fake : CliktCommand(
    name = "fake",
    help = "Generates fake data"
) {

    private val faker = Faker()

    val howMany by option(
        names = arrayOf("-n", "--how-many"),
        help = "How many fake things you want to create.",
    ).int()
        .default(value = 1)
        .validate { n -> require(n > 0) { "Can only generate 1 or more values" } }

    init {
        subcommands(Names())
    }

    override fun run() = Unit

    inner class Names : CliktCommand(help = "Generate fake names") {
        override fun run() {
            generateSequence { this@Fake.faker.name() }
                .take(this@Fake.howMany)
                .map(this::data)
                .forEach(::println)
        }

        private fun data(name: Name): Any {
            return name.name()
        }
    }

}

