package com.enzoroiz.patterns.behavioural

import kotlin.random.Random

fun main(args: Array<String>) {
    val payPalPaymentProvider = PayPalPaymentProvider()
    val creditCardPaymentProvider = CreditCardPaymentProvider()

    println("############################")
    payPalPaymentProvider.pay(
        PayPalPaymentProvider.PayPalPaymentData(
            amount = 100.0,
            email = "paypal@payments.try"
        )
    )

    println()
    println("############################")
    payPalPaymentProvider.pay(
        PayPalPaymentProvider.PayPalPaymentData(
            amount = 150000.0,
            email = "rich_user@money.try"
        )
    )

    println()
    println("############################")
    creditCardPaymentProvider.pay(
        CreditCardPaymentProvider.CreditCardPaymentData(
            amount = 12.0,
            cardNumber = "4242 4242 4242 4242",
            cardCVV = "424",
            cardExpiryDate = "24/02/2024"
        )
    )

    println()
    println("############################")
    creditCardPaymentProvider.pay(
        CreditCardPaymentProvider.CreditCardPaymentData(
            amount = 12500.0,
            cardNumber = "1212 1212 1212",
            cardCVV = "121",
            cardExpiryDate = "12/12/2022"
        )
    )
}

// Template pattern goal is to provide a structure to be used when we run a certain algorithm
// Given that we have a Payment Provider we know they can follow the operations pre-processing, processing, post-processing
// Our template exposes the method pay() to be used.
// By using this method we know that the algorithm for paying will run a proper sequence of steps.
// Depending on the Payment Provider we are implementing we might opt to override or not certain operations.

interface PaymentProcessingData
interface PaymentResultData

abstract class PaymentProviderTemplate<P : PaymentProcessingData, R : PaymentResultData> {

    fun pay(paymentProcessingData: P): R {
        prePaymentProcess(paymentProcessingData)
        val paymentResultData = processPayment(paymentProcessingData)
        postPaymentProcess(paymentProcessingData, paymentResultData)
        return paymentResultData
    }

    open fun prePaymentProcess(paymentProcessingData: P) {
        println("No pre processing needed for the Payment Provider: ${javaClass.simpleName}")
    }

    abstract fun processPayment(paymentProcessingData: P): R

    open fun postPaymentProcess(paymentProcessingData: P, paymentResultData: R) {
        println("No pre processing needed for the Payment Provider: ${javaClass.simpleName}")
    }
}

class PayPalPaymentProvider :
    PaymentProviderTemplate<PayPalPaymentProvider.PayPalPaymentData, PayPalPaymentProvider.PayPalResultData>() {
    data class PayPalPaymentData(
        val amount: Double,
        val email: String
    ) : PaymentProcessingData

    data class PayPalResultData(
        val transactionId: String,
        val success: Boolean
    ) : PaymentResultData

    override fun prePaymentProcess(paymentProcessingData: PayPalPaymentData) {
        println("Pre processing PayPal Payment")
        println("User logged in with PayPal")
    }

    override fun processPayment(paymentProcessingData: PayPalPaymentData): PayPalResultData {
        println("Processing payment of USD${paymentProcessingData.amount} with PayPal. Please wait...")
        return PayPalResultData(
            transactionId = Random.nextLong(0, 100000).toString(),
            success = Random.nextBoolean()
        )
    }

    override fun postPaymentProcess(paymentProcessingData: PayPalPaymentData, paymentResultData: PayPalResultData) {
        println("Post processing PayPal payment")
        if (paymentResultData.success) {
            println("Transaction ${paymentResultData.transactionId} was successful.")
            println("Want to save PayPal as default payment method?")
        } else {
            println("Transaction ${paymentResultData.transactionId} was denied.")
            println("Please check your data and try again.")
        }
    }
}

class CreditCardPaymentProvider :
    PaymentProviderTemplate<CreditCardPaymentProvider.CreditCardPaymentData, CreditCardPaymentProvider.CreditCardResultData>() {
    data class CreditCardPaymentData(
        val amount: Double,
        val cardNumber: String,
        val cardCVV: String,
        val cardExpiryDate: String
    ) : PaymentProcessingData

    data class CreditCardResultData(
        val transactionCode: String = "",
        val success: Boolean
    ) : PaymentResultData

    override fun processPayment(paymentProcessingData: CreditCardPaymentData): CreditCardResultData {
        println("Processing payment of USD${paymentProcessingData.amount} with credit card. Please wait...")
        return CreditCardResultData(
            transactionCode = Random.nextLong(0, 100000).toString(),
            success = Random.nextBoolean()
        )
    }

    override fun postPaymentProcess(
        paymentProcessingData: CreditCardPaymentData,
        paymentResultData: CreditCardResultData
    ) {
        println("Post processing credit card payment")
        if (paymentResultData.success) {
            println("Payment successfully processed.")
            println("Save the code ${paymentResultData.transactionCode} for future reference.")
        } else {
            println("Payment could not be processed.")
            println("Contact your bank and inform the code ${paymentResultData.transactionCode} for more details.")
        }
    }
}