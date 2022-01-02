package `is`.warden.commandframework.shared.command

import `is`.warden.commandframework.shared.command.context.CommandContext
import `is`.warden.commandframework.shared.CommandFramework
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class AbstractCommand<T>(
    override val name: String,
    override var description: String = "Description missing.",
    override var usageMessage: String = "A parameter is missing.",
    override var aliases: List<String> = listOf(),
    override var executionDsl: suspend (CommandContext<T>) -> Unit = {}
) : Command<T> {

    override val children = HashMap<String, Command<T>>()

    override suspend fun execute(
        commandFramework: CommandFramework,
        sender: T,
        commandLabel: String,
        args: Array<out String>
    ) = withContext(Dispatchers.IO) {
        if (children.isEmpty() || args.isEmpty()) {
            return@withContext onCommand(
                CommandContext(
                    commandFramework,
                    sender,
                    this@AbstractCommand,
                    ArrayDeque(args.toList())
                )
            )
        }

        val cmdName = args[0].lowercase()
        val child = children.values.find { it.name == cmdName || it.aliases.contains(cmdName) }
            ?: return@withContext onCommand(
                CommandContext(
                    commandFramework,
                    sender,
                    this@AbstractCommand,
                    ArrayDeque(args.toList())
                )
            )

        child.execute(commandFramework, sender, commandLabel, args.sliceArray(1..args.size))
    }

    override suspend fun onCommand(context: CommandContext<T>) {
        executionDsl(context)
    }
}