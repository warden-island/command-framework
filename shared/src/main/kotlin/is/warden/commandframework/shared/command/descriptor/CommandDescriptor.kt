package `is`.warden.commandframework.shared.command.descriptor

import `is`.warden.commandframework.shared.command.context.CommandContext

class CommandDescriptor<T>(
    val name: String,
    val description: String = "",
    val usage: String = "",
    val aliases: List<String> = listOf(),
    val executionDsl: suspend (CommandContext<T>) -> Unit
)