package fr.webflux.myeat.domain

data class PreOrder(
    val company: String,
    val price: Int,
    val details: List<String>,
    val typeDelivery: String
)
