package com.enzoroiz.challenge5.kotlin

import com.enzoroiz.challenge5.java.EmployeeJ

fun main(args: Array<String>) {
    val employee = EmployeeJ("Jane", "Smith", 2000)
    val value = 3
    employee.lastName = "Jones"
    employee.salaryLast3Years = floatArrayOf(50000.25f, 54000.60f, 56800.42f)
}

class Challenge5 {

}