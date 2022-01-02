package `is`.warden.commandframework.shared.command.context

import `is`.warden.commandframework.shared.CommandFramework
import `is`.warden.commandframework.shared.command.Command
import `is`.warden.commandframework.shared.util.AdapterDsl
import `is`.warden.commandframework.shared.util.runtimeError
import java.util.*
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass

class CommandContext<out T>(
    private val commandFramework: CommandFramework,
    val sender: T,
    command: Command<T>,
    private val args: Deque<String>
) {

    val usageMessage = command.usageMessage

    fun <E : Any> getTypeAdapter(clazz: KClass<E>): AdapterDsl<E>? = commandFramework.getTypeAdapter(clazz)

    fun nextArg(): String? = runCatching { args.pop() }.getOrNull()

    fun nextArgsJoined(): String? = args.joinToString(" ").ifEmpty { null }

    inline fun <reified E : Any> parseSingle(errorMessage: String = ""): E {
        val typeAdapter = getTypeAdapter(E::class) ?: runtimeError("typeAdapter not found, contact an admin.")
        val arg = nextArg() ?: runtimeError(errorMessage.ifBlank { usageMessage })
        return runCatching { typeAdapter(arg) }.getOrElse { throw it }
    }

    inline fun <reified E : Any> parseAll(errorMessage: String = ""): E {
        val typeAdapter = getTypeAdapter(E::class) ?: runtimeError("typeAdapter not found, contact an admin.")
        val arg = nextArgsJoined() ?: runtimeError(errorMessage.ifBlank { usageMessage })
        return runCatching { typeAdapter(arg) }.getOrElse { throw it }
    }

    inline fun <reified E : Any> parseSingleLazy(errorMessage: String = "") = ReadOnlyProperty<Nothing?, E> { _, _ ->
        val typeAdapter = getTypeAdapter(E::class) ?: runtimeError("typeAdapter not found, contact an admin.")
        val arg = nextArg() ?: runtimeError(errorMessage.ifBlank { usageMessage })
        return@ReadOnlyProperty runCatching { typeAdapter(arg) }.getOrElse { throw it }
    }

    inline fun <reified E : Any> parseAllLazy(errorMessage: String = "") = ReadOnlyProperty<Nothing?, E> { _, _ ->
        val typeAdapter = getTypeAdapter(E::class) ?: runtimeError("typeAdapter not found, contact an admin.")
        val arg = nextArgsJoined() ?: runtimeError(errorMessage.ifBlank { usageMessage })
        return@ReadOnlyProperty runCatching { typeAdapter(arg) }.getOrElse { throw it }
    }

}