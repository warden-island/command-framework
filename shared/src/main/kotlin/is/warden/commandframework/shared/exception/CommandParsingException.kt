package `is`.warden.commandframework.shared.exception

class CommandParsingException(
    override val message: String
): Exception(message)