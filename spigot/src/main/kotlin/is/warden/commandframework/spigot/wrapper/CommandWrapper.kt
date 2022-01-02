package `is`.warden.commandframework.spigot.wrapper

import `is`.warden.commandframework.shared.CommandFramework
import `is`.warden.commandframework.shared.command.Command
import `is`.warden.commandframework.shared.exception.CommandParsingException
import `is`.warden.commandframework.shared.exception.CommandRuntimeException
import kotlinx.coroutines.runBlocking
import org.bukkit.command.CommandSender
import org.bukkit.command.Command as BukkitCommand

class CommandWrapper<T : CommandSender>(
    internal val commandFramework: CommandFramework,
    internal val command: Command<T>
) : BukkitCommand(command.name, command.description, command.usageMessage, command.aliases) {

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean = runBlocking {
        try {
            println("execute wrapper")
            command.execute(commandFramework, sender as T, commandLabel, args)
        } catch (e: CommandParsingException) {
            sender.sendMessage(e.message)
            return@runBlocking true
        } catch (e: CommandRuntimeException) {
            sender.sendMessage(e.message)
            return@runBlocking true
        } catch (e: Exception) {
            e.printStackTrace()
            sender.sendMessage("An unknown error ocurred while trying to execute this command!")
            return@runBlocking false
        }

        return@runBlocking true
    }

}