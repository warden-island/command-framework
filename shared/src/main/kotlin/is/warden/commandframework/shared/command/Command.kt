package `is`.warden.commandframework.shared.command

import `is`.warden.commandframework.shared.command.context.CommandContext
import `is`.warden.commandframework.shared.CommandFramework

interface Command<T> {

    val name: String
    var description: String
    var usageMessage: String
    var aliases: List<String>
    var executionDsl: suspend (CommandContext<T>) -> Unit
    val children: MutableMap<String, Command<T>>

    suspend fun onCommand(context: CommandContext<T>)
    suspend fun execute(commandFramework: CommandFramework, sender: T, commandLabel: String, args: Array<out String>)
}