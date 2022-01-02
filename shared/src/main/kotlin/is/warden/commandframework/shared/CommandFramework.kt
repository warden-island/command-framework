package `is`.warden.commandframework.shared

import `is`.warden.commandframework.shared.command.descriptor.CommandDescriptor
import `is`.warden.commandframework.shared.util.AdapterDsl
import kotlin.reflect.KClass

interface CommandFramework {
    val typeAdapters: MutableMap<KClass<*>, AdapterDsl<Any>>

    fun <E : Any> registerTypeAdapter(clazz: KClass<E>, adapterDsl: AdapterDsl<E>)
    fun <E : Any> getTypeAdapter(clazz: KClass<E>): AdapterDsl<E>?
    fun registerCommand(commandDescriptor: CommandDescriptor<*>)
}