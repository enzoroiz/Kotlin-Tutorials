package com.enzoroiz.patterns.creational

fun main(args: Array<String>) {
    val windowsViewWidgetsFactory = ViewWidgetsFactoryProvider.getWidgetsFactory(ViewWidgetsFactoryProvider.OperationalSystem.WINDOWS)
    windowsViewWidgetsFactory.createButton()
    windowsViewWidgetsFactory.createTextView()
    windowsViewWidgetsFactory.createEditText()

    val linuxViewWidgetsFactory = ViewWidgetsFactoryProvider.getWidgetsFactory(ViewWidgetsFactoryProvider.OperationalSystem.LINUX)
    linuxViewWidgetsFactory.createButton()
    linuxViewWidgetsFactory.createTextView()
    linuxViewWidgetsFactory.createEditText()
}

object ViewWidgetsFactoryProvider {
    enum class OperationalSystem {
        WINDOWS,
        LINUX
    }

    fun getWidgetsFactory(os: OperationalSystem): ViewWidgetsFactory {
       return when (os) {
            OperationalSystem.WINDOWS -> WindowsViewWidgetsFactory
            OperationalSystem.LINUX -> LinuxViewWidgetsFactory
        }
    }
}

interface ViewWidgetsFactory {
    fun createButton(): Button
    fun createTextView(): TextView
    fun createEditText(): EditText
}

interface Widget
abstract class Button: Widget
abstract class TextView: Widget
abstract class EditText: Widget

class WindowsButton: Button()
class WindowsTextView: TextView()
class WindowsEditText: EditText()

object WindowsViewWidgetsFactory: ViewWidgetsFactory {
    override fun createButton(): Button {
        println("Windows - Button")
        return WindowsButton()
    }

    override fun createTextView(): TextView {
        println("Windows - TextView")
        return WindowsTextView()
    }

    override fun createEditText(): EditText {
        println("Windows - EditText")
        return WindowsEditText()
    }
}

class LinuxButton: Button()
class LinuxTextView: TextView()
class LinuxEditText: EditText()

object LinuxViewWidgetsFactory: ViewWidgetsFactory {
    override fun createButton(): Button {
        println("Linux - Button")
        return LinuxButton()
    }

    override fun createTextView(): TextView {
        println("Linux - TextView")
        return LinuxTextView()
    }

    override fun createEditText(): EditText {
        println("Linux - EditText")
        return LinuxEditText()
    }
}