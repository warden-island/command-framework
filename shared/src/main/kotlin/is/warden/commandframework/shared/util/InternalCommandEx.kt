package `is`.warden.commandframework.shared.util

import `is`.warden.commandframework.shared.command.AbstractCommand
import `is`.warden.commandframework.shared.command.Command

fun <T> Command<T>.getOrCreateChild(name: String): Command<T> {
    var lowercaseName = name.lowercase()
    val rootCommandName = this.name.lowercase()

    var index = lowercaseName.indexOf(".")
    if(index == -1) {
        if(rootCommandName == lowercaseName) {
            return this
        }

        lowercaseName = lowercaseName.substring(index+1..lowercaseName.length)
        index = lowercaseName.indexOf(".")
    }

    val desiredChildName = lowercaseName.substring(0..index)
    val child = this.children.getOrPut(desiredChildName) { AbstractCommand(desiredChildName) }

    return if(index == -1) child else child.getOrCreateChild(lowercaseName.substring(index+1..lowercaseName.length))
}