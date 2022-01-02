package `is`.warden.commandframework.shared.util

import `is`.warden.commandframework.shared.exception.CommandParsingException
import `is`.warden.commandframework.shared.exception.CommandRuntimeException

typealias AdapterDsl<E> = (String) -> E

fun parsingError(message: String): Nothing = throw CommandParsingException(message)
fun runtimeError(message: String): Nothing = throw CommandRuntimeException(message)