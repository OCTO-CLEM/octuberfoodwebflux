package fr.webflux.myeat.domain

data class Order(
    val id: String,
    val company: String,
    val price: Int,
    val timeToDeliver: Int,
    val deliverName: String,
    val details: List<String>,
)
