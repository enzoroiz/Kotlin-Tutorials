package com.enzoroiz.patterns.structural

fun main(args: Array<String>) {
    val productRepository = ProductRepository(
        ProductCache(),
        ProductDatabase(),
        ProductService()
    )

    println("Product #1")
    println(productRepository.getProductById(1)?.name ?: "Product with id 1 wasn't found")
    println("############################")

    var products = productRepository.getProducts()
    println("${products.size} products retrieved")
    products.forEach { println(it.toString()) }
    println("############################")

    println("Product #1")
    println(productRepository.getProductById(1)?.name ?: "Product with id 1 wasn't found")
    println("############################")

    productRepository.delete(products.first())
    products = productRepository.getProducts()
    println("${products.size} products retrieved")
    products.forEach { println(it.toString()) }
    println("############################")

    productRepository.delete(products.last())
    products = productRepository.getProducts()
    println("${products.size} products retrieved")
    products.forEach { println(it.toString()) }
    println("############################")

    productRepository.save(products.first().copy(price = 2.5))
    products = productRepository.getProducts()
    println("${products.size} products retrieved")
    products.forEach { println(it.toString()) }
}

// Facade Patters intends to hide the complexity of a subsystem from the client
// The client can get(), save(), delete() but doesn't need to be aware of the API layer, database and cache layers
// Though the client "can" use the service, database and cache, the Facade makes the life easier by exposing
// the necessary methods and handling the complexity of dealing with it all, under the hood

data class Product(
    val id: Long,
    val name: String,
    val price: Double
)

class ProductRepository (
    private val productCache: ProductCache,
    private val productDatabase: ProductDatabase,
    private val productService: ProductService
) {
    fun getProducts(): List<Product> {
        val cachedProducts = productCache.getAll()
        if (cachedProducts.isEmpty()) {
            val databaseProducts = productDatabase.findAll()
            return if (databaseProducts.isEmpty()) {
                val serviceProducts = productService.fetchProducts()
                serviceProducts.forEach {
                    productCache.update(it)
                    productDatabase.save(it)
                }

                serviceProducts
            } else {
                databaseProducts.forEach { productCache.update(it) }
                databaseProducts
            }
        }

        return cachedProducts
    }

    fun getProductById(id: Long): Product? {
        val cachedProduct = productCache.getById(id)
        return cachedProduct?.let { cachedProduct } ?: run {
            val databaseProduct = productDatabase.findById(id)
            databaseProduct?.let {
                productCache.update(it)
                return databaseProduct
            }
        }
    }

    fun save(product: Product) {
        productCache.update(product)
        productDatabase.save(product)
    }

    fun delete(product: Product) {
        productCache.remove(product.id)
        productDatabase.delete(product.id)
    }
}

class ProductCache {
    private val products = mutableMapOf<Long, Product>()

    fun update(product: Product) {
        products[product.id] = product
    }

    fun remove(id: Long) {
        products.remove(id)
    }

    fun getAll(): List<Product> {
        return products.values.toList()
    }

    fun getById(id: Long): Product? = products[id]
}

class ProductDatabase {
    private val products = mutableMapOf<Long, Product>()

    fun save(product: Product) {
        products[product.id] = product
    }

    fun delete(id: Long) {
        products.remove(id)
    }

    fun findAll(): List<Product> {
        return products.values.toList()
    }

    fun findById(id: Long): Product? = products[id]
}

class ProductService {
    private val products = listOf(
        Product(
            id = 1,
            name = "Orange Juice",
            price = 1.0
        ),
        Product(
            id = 2,
            name = "Milk 1.5% Fat",
            price = 2.0
        ),
        Product(
            id = 3,
            name = "Strawberries",
            price = 3.0
        ),
        Product(
            id = 4,
            name = "Corona Beer",
            price = 2.5
        ),
        Product(
            id = 5,
            name = "Honey and Salt Peanuts & Cashew Nuts",
            price = 1.5
        ),
        Product(
            id = 6,
            name = "Haribo Gummy Bear",
            price = 0.6
        )
    )

    fun fetchProducts() = products
}