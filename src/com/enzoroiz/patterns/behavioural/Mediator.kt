package com.enzoroiz.patterns.behavioural

import java.util.*
import kotlin.random.Random

fun main(args: Array<String>) {
    val mediator = RestaurantMediator()
    val waiters = listOf(
        Waiter(mediator, "John Beckham"),
        Waiter(mediator, "Marta Milner"),
        Waiter(mediator, "James Messi"),
        Waiter(mediator, "Kevin Jesus")
    )

    val chefs = listOf(
        Chef(mediator, "Pep Klopp"),
        Chef(mediator, "Pia Guardiola"),
        Chef(mediator, "Jurgen Allegri")
    )

    val customers = listOf(
        Customer(mediator, "Enzo Roiz"),
        Customer(mediator, "Enzo Rodriguez"),
        Customer(mediator, "Enzo R.")
    )

    val orders = listOf(
        CustomerOrder(
            customers[0],
            listOf(
                CustomerOrderItem(
                    "Water 200ml",
                    4.50
                ),
                CustomerOrderItem(
                        "Panini",
                        8.50
                )
            )
        ),
        CustomerOrder(
            customers[1],
            listOf(
                CustomerOrderItem(
                    "Corona Beer 355ml",
                    8.50
                ),
                CustomerOrderItem(
                    "Burger",
                    17.50
                )
            )
        ),
        CustomerOrder(
            customers[2],
            listOf(
                CustomerOrderItem(
                    "Coca Cola 500ml",
                    6.50
                ),
                CustomerOrderItem(
                    "Fries",
                    9.50
                )
            )
        )
    )

    mediator.run {
        addWaiters(waiters)
        addChefs(chefs)
    }

    println("############################")
    customers.forEach { it.askForTable() }
    println("############################")
    orders.forEach { it.customer.makeOrder(it.orderItems) }
    println("############################")
    chefs.forEach { while (it.hasOrders()) { it.deliverOrder() } }
    println("############################")
    customers.forEach { it.askForBill() }
    println("############################")
    waiters.last().sumUpOrders()
}

// Mediator pattern aims to centralize the communication across objects at one given point
// Instead of the objects (Customer, Waiter, Chef) having to know and manage the interactions within each other,
// they delegate it to the mediator (assigns waiters to customers, chefs to orders, etc...)

class RestaurantMediator {
    private val waiters = mutableListOf<Waiter>()
    private val customers = mutableListOf<Customer>()
    private val chefs = mutableListOf<Chef>()
    private val orders = mutableListOf<CustomerOrder>()

    fun askForTable(customer: Customer) {
        findWaiter().greet(customer)
        customers.add(customer)
    }

    fun askForBill(customer: Customer) {
        println("See you soon ${customer.name}!")
        customers.remove(customer)
    }

    fun addWaiters(newWaiters: List<Waiter>) {
        waiters.clear()
        waiters.addAll(newWaiters)
    }

    fun addChefs(newChefs: List<Chef>) {
        chefs.clear()
        chefs.addAll(newChefs)
    }

    fun makeOrder(customerOrder: CustomerOrder) {
        orders.add(customerOrder)
        findWaiter().writeOrder(customerOrder)
        findChef().setOrder(customerOrder)
    }

    fun deliverOrder(customerOrder: CustomerOrder) {
        findWaiter().deliverOrder(customerOrder)
    }

    fun sumUpOrders(): Double {
        var ordersTotal = 0.0
        orders.forEach { order -> order.orderItems.forEach { ordersTotal += it.price } }

        return ordersTotal
    }

    private fun findWaiter(): Waiter {
        if (waiters.isEmpty()) throw Exception("Restaurant can't work without waiters. Please add some.")

        return waiters[Random.nextInt(0, waiters.size)]
    }

    private fun findChef(): Chef {
        if (waiters.isEmpty()) throw Exception("Restaurant can't work without chefs. Please add some.")

        return chefs[Random.nextInt(0, chefs.size)]
    }
}

class Chef(private val mediator: RestaurantMediator, private val name: String) {
    private val orders: Queue<CustomerOrder> = LinkedList()

    fun hasOrders(): Boolean {
        return orders.isNotEmpty()
    }

    fun setOrder(customerOrder: CustomerOrder) {
        println("Chef $name is preparing the order:")
        println(customerOrder)
        println()
        orders.add(customerOrder)
    }

    fun deliverOrder() {
        orders.poll()?.let {
            println("Chef delivering order to waiter:")
            println(it)
            println()
            mediator.deliverOrder(it)
        }?: println("I have no orders to work on, actually.")
    }
}

class Waiter(private val mediator: RestaurantMediator, private val name: String) {

    fun greet(customer: Customer) {
        println("Welcome to our restaurant ${customer.name}, my name is $name and I will assist you today!")
    }

    fun writeOrder(customerOrder: CustomerOrder) {
        println("Waiter $name got the order:")
        println(customerOrder)
        println()
    }

    fun deliverOrder(customerOrder: CustomerOrder) {
        println("Waiter delivering order to client:")
        println(customerOrder)
        println()
    }

    fun sumUpOrders() {
        println("Summing up all the orders we got. Total was: ${mediator.sumUpOrders()}")
    }
}

class Customer(private val mediator: RestaurantMediator, val name: String) {
    fun askForTable() {
        mediator.askForTable(this)
    }

    fun askForBill() {
        mediator.askForBill(this)
    }

    fun makeOrder(orderItems: List<CustomerOrderItem>) {
        mediator.makeOrder(CustomerOrder(this, orderItems))
    }
}

data class CustomerOrder(
    val customer: Customer,
    val orderItems: List<CustomerOrderItem>
) {
    override fun toString(): String {
        return """
            Customer: ${customer.name}
            Items: $orderItems
        """.trimIndent()
    }
}

data class CustomerOrderItem(
    val name: String,
    val price: Double
) {
    override fun toString(): String {
        return "$name / $price"
    }
}