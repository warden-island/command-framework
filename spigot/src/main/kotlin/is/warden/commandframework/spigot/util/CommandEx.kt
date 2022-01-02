package `is`.warden.commandframework.spigot.util

import `is`.warden.commandframework.shared.command.context.CommandContext
import `is`.warden.commandframework.shared.command.descriptor.CommandDescriptor
import org.bukkit.command.CommandSender

fun command(
    name: String,
    description: String = "",
    usage: String = "",
    vararg aliases: String = arrayOf(),
    executionDsl: suspend CommandContext<CommandSender>.() -> Unit
): CommandDescriptor<CommandSender> {
    return CommandDescriptor(
        name,
        description,
        usage,
        aliases.toList(),
        executionDsl
    )
}

//fun command(
//    name: String,
//    builderDsl: CommandBuilder<CommandSender>.() -> Unit = {}
//): CommandDescriptor<CommandSender> {
//    val builder = CommandBuilder<CommandSender>(name)
//    builderDsl(builder)
//    return builder.build()
//}

