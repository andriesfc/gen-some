package generate.cmd

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.multiple
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.long
import org.hashids.Hashids

class HashIds : CliktCommand(
    name = "hashid",
    help = "Generate Hash ID based on known numbers. Hash ID can be " +
            "salted to make sure they cannot be unpacked without the salt."
) {
    val salt by option("--salt", help = "Salt to use when packing, or unpacking an list of IDs.")

    private lateinit var hashids: Hashids

    override fun run() {
        hashids = when (salt) {
            null -> Hashids()
            else -> Hashids(salt)
        }
    }

    init {
        subcommands(Pack(), Unpack())
    }

    private inner class Pack : CliktCommand(
        help = "Packs a list sequence of numbers into single hash."
    ) {
        val ids: List<Long> by argument(help = "List of IDs to encode into a single hash")
            .long()
            .multiple(required = true)

        override fun run() {
            println(this@HashIds.hashids.encode(*ids.toLongArray()))
        }
    }

    private inner class Unpack : CliktCommand(
        help = "Unpacks a single hash into list of its constituent numbers."
    ) {
        val packedHash by argument()

        override fun run() {
            println(this@HashIds.hashids.decode(packedHash).joinToString(separator = " "))
        }
    }
}