@file:Suppress("MemberVisibilityCanBePrivate")

package generate.cmd

import com.github.ajalt.clikt.core.CliktCommand
import java.util.*

class Guid : CliktCommand(help = "Generates a GUID") {
    override fun run() {
        println(UUID.randomUUID())
    }
}