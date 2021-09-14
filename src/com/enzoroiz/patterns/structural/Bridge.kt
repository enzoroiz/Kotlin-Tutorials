package com.enzoroiz.patterns.structural

fun main(args: Array<String>) {
    val n26SavingsAccount = N26Bank(SavingsAccount())
    val n26ActiveAccount = N26Bank(ActiveAccount())
    val deutscheBankSavingsAccount = DeutscheBank(SavingsAccount())
    val deutscheBankActiveAccount = DeutscheBank(ActiveAccount())

    println("############################")
    println("N26")
    n26SavingsAccount.accountType.deposit(100)
    n26ActiveAccount.accountType.withdraw(200)
    n26ActiveAccount.loan(300)
    println("############################")
    println("Deutsche Bank")
    deutscheBankActiveAccount.accountType.deposit(400)
    deutscheBankSavingsAccount.accountType.withdraw(500)
    deutscheBankActiveAccount.loan(600)
}

// Instead of having the account type coupled inside of each different bank
// Through composition it's separated the logic for Account Type and BankAccount
// The BankAccount now 'has an' account type and it acts like a Bridge
// Between the Refined Abstraction (N26BankAccount) and the Implementers (AccountType / SavingsAccount - ActiveAccount)
// Basically you separate the BankAccount hierarchy from the AccountType hierarchy

interface AccountType {
    fun deposit(amount: Int)
    fun withdraw(amount: Int)
}

class SavingsAccount: AccountType {
    override fun deposit(amount: Int) {
        println("You deposited $amount in your Savings Account")
    }

    override fun withdraw(amount: Int) {
        println("You withdrew $amount from your Savings Account")
    }
}

class ActiveAccount: AccountType {
    override fun deposit(amount: Int) {
        println("You deposited $amount in your Active Account")
    }

    override fun withdraw(amount: Int) {
        println("You withdrew $amount from your Active Account")
    }
}

abstract class BankAccount {
    abstract val accountType: AccountType
    abstract fun loan(amount: Int)
}

class N26Bank(override val accountType: AccountType) : BankAccount() {
    override fun loan(amount: Int) {
        println("Your request with N26 for $amount was approved with no taxes")
    }
}

class DeutscheBank(override val accountType: AccountType) : BankAccount() {
    override fun loan(amount: Int) {
        println("Your request with Deutsche Bank for $amount was approved with 1% interest per month")
    }
}
