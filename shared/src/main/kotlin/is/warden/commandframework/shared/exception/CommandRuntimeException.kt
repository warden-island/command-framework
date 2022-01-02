package `is`.warden.commandframework.shared.exception

class CommandRuntimeException(
    override val message: String
): Exception(message)