package generate

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.output.CliktHelpFormatter
import com.github.ajalt.mordant.TermColors
import generate.cmd.Fake
import generate.cmd.Guid
import generate.cmd.HashIds


fun main(args: Array<String>) = App().subcommands(HashIds(), Guid(), Fake()).main(args.toList())

private class App : CliktCommand(name = "gen", help = "Generates things for you.") {

    init {
        context { helpFormatter = ColorHelpFormatter() }
    }

    override fun run() = Unit

    private class ColorHelpFormatter : CliktHelpFormatter() {
        private val tc = TermColors(TermColors.Level.ANSI256)
        override fun renderArgumentName(name: String): String = tc.yellow(super.renderArgumentName(name))
        override fun renderOptionName(name: String): String = tc.brightGreen(super.renderOptionName(name))
        override fun renderSubcommandName(name: String): String = tc.brightYellow(super.renderSubcommandName(name))
    }
}
