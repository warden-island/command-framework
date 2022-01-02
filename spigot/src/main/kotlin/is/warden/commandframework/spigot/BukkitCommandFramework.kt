package `is`.warden.commandframework.spigot

import `is`.warden.commandframework.shared.CommandFramework
import `is`.warden.commandframework.shared.command.AbstractCommand
import `is`.warden.commandframework.shared.command.context.CommandContext
import `is`.warden.commandframework.shared.command.descriptor.CommandDescriptor
import `is`.warden.commandframework.shared.util.AdapterDsl
import `is`.warden.commandframework.shared.util.getOrCreateChild
import `is`.warden.commandframework.spigot.wrapper.CommandWrapper
import org.bukkit.command.CommandSender
import org.bukkit.craftbukkit.v1_16_R3.CraftServer
import org.bukkit.craftbukkit.v1_16_R3.command.CraftCommandMap
import org.bukkit.plugin.java.JavaPlugin
import kotlin.reflect.KClass

class BukkitCommandFramework(
    private val plugin: JavaPlugin
) : CommandFramework {

    private val commandMap = (plugin.server as CraftServer).commandMap as CraftCommandMap
    override val typeAdapters = mutableMapOf<KClass<*>, AdapterDsl<Any>>().apply {
        put(String::class) { it }
    }

    override fun <E : Any> registerTypeAdapter(clazz: KClass<E>, adapterDsl: AdapterDsl<E>) {
        typeAdapters[clazz] = adapterDsl
    }

    override fun <E : Any> getTypeAdapter(clazz: KClass<E>): AdapterDsl<E>? {
        return typeAdapters[clazz] as AdapterDsl<E>?
    }

    override fun registerCommand(commandDescriptor: CommandDescriptor<*>) {
        val command = getOrCreateWrapper<CommandSender>(commandDescriptor.name).command.getOrCreateChild(commandDescriptor.name)
        command.description = commandDescriptor.description
        command.aliases = commandDescriptor.aliases
        command.executionDsl = commandDescriptor.executionDsl as suspend (CommandContext<CommandSender>) -> Unit
    }

    private fun <T : CommandSender> getOrCreateWrapper(name: String): CommandWrapper<T> {
        val index = name.indexOf('.')
        val rootName = if(index == -1) name else name.substring(0..index)

        var command = commandMap.getCommand(rootName)
        if(command != null && (command !is CommandWrapper<*> || command.commandFramework != this)) {
            error("tried to register child on a non-wrapper/non-owned command")
        } else {
            command = CommandWrapper(this, AbstractCommand<T>(rootName))
            commandMap.register(plugin.name, command)
        }

        return command
    }



}