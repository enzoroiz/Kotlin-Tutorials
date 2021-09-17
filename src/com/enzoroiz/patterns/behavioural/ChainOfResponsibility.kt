package com.enzoroiz.patterns.behavioural

import com.enzoroiz.patterns.behavioural.MenuItemType.*

fun main(args: Array<String>) {
    val burgerItemProcessor = BurgerItemProcessor()
    val friesItemProcessor = FriesItemProcessor()
    val drinkItemProcessor = DrinkItemProcessor()

    burgerItemProcessor.next = friesItemProcessor
    friesItemProcessor.next = drinkItemProcessor

    val menuOrder = Order(
        items = mapOf(
            BURGER to MenuItem("Cheese Burger", 8.5),
            FRIES to MenuItem("Regular Fries", 4.0),
            DRINK to MenuItem("Coke", 2.5)
        )
    )

    val burgerWithDrink = Order(
        items = mapOf(
            BURGER to MenuItem("Bacon BBQ Burger", 10.5),
            DRINK to MenuItem("Sprite", 2.5)
        )
    )

    val burgerWithFries = Order(
        items = mapOf(
            BURGER to MenuItem("Double Bacon BBQ Burger", 13.0),
            FRIES to MenuItem("Sweet Potato Fries", 6.0)
        )
    )

    burgerItemProcessor.process(menuOrder).apply {
        println("Your order total is EUR$cost")
    }

    println("############################")
    burgerItemProcessor.process(burgerWithDrink).apply {
        println("Your order total is EUR$cost")
    }

    println("############################")
    burgerItemProcessor.process(burgerWithFries).apply {
        println("Your order total is EUR$cost")
    }
}

// Chain Of Responsibility Pattern process a request sent to a chain of possible responsible (handlers) to that request
// Each handler has a reference to the next handler in the chain that it can pass the request to
// Each handler then can check if it can handle the request, handle it and forward the request
// Or in case it's not its responsibility, then forward to the next handler 

enum class MenuItemType {
    BURGER,
    FRIES,
    DRINK,
}

data class Order(
    val items: Map<MenuItemType, MenuItem>,
    val cost: Double? = null
)

data class MenuItem(
    val name: String,
    val cost: Double
)

abstract class OrderItemProcessor {
    abstract var next: OrderItemProcessor?
    abstract fun process(order: Order): Order
}

class BurgerItemProcessor : OrderItemProcessor() {
    override var next: OrderItemProcessor? = null

    override fun process(order: Order): Order {
        var updatedOrder = order
        order.items[BURGER]?.let { item ->
            println("Adding Burger: ${item.name} - EUR${item.cost}")
            updatedOrder = order.copy(cost = order.cost?.let { order.cost + item.cost } ?: item.cost)
        }

        return next?.process(updatedOrder) ?: updatedOrder
    }
}

class FriesItemProcessor : OrderItemProcessor() {
    override var next: OrderItemProcessor? = null

    override fun process(order: Order): Order {
        var updatedOrder = order
        order.items[FRIES]?.let { item ->
            val hasBurger = order.items.containsKey(BURGER)
            val friesPrice = if (hasBurger) item.cost / 2 else item.cost

            updatedOrder = order.copy(cost = order.cost?.let { order.cost + friesPrice } ?: friesPrice)
            println("Adding Fries: ${item.name} - EUR${friesPrice}")
        }

        return next?.process(updatedOrder) ?: updatedOrder
    }
}

class DrinkItemProcessor : OrderItemProcessor() {
    override var next: OrderItemProcessor? = null

    override fun process(order: Order): Order {
        var updatedOrder = order
        order.items[DRINK]?.let { item ->
            val hasBurger = order.items.containsKey(BURGER)
            val hasFries = order.items.containsKey(FRIES)
            val drinkPrice = if (hasBurger && hasFries) item.cost / 2 else item.cost

            updatedOrder = order.copy(cost = order.cost?.let { order.cost + drinkPrice } ?: drinkPrice)
            println("Adding Drink: ${item.name} - EUR${drinkPrice}")
        }

        return next?.process(updatedOrder) ?: updatedOrder
    }
}