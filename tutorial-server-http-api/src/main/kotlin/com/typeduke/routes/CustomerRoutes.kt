package com.typeduke.routes

import com.typeduke.models.Customer
import com.typeduke.models.customerStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerRouting() {
    route("/customer") {
        get {
            if (customerStorage.isNotEmpty()) {
                this.call.respond(customerStorage)
            } else {
                this.call.respondText("No customers found", status = HttpStatusCode.OK)
            }
        }
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get this.call.respondText(
                "Missing ID",
                status = HttpStatusCode.BadRequest
            )
            val customer = customerStorage.find { it.id == id } ?: return@get this.call.respondText(
                "No customer with ID $id",
                status = HttpStatusCode.NotFound
            )
            this.call.respond(customer)
        }
        post {
            val customer = this.call.receive<Customer>()
            customerStorage.add(customer)
            this.call.respondText("Customer stored correctly", status = HttpStatusCode.Created)
        }
        delete("{id?}") {
            val id = this.call.parameters["id"] ?: return@delete this.call.respondText(
                "Missing ID",
                status = HttpStatusCode.BadRequest
            )
            if (customerStorage.removeIf { it.id == id }) {
                this.call.respondText("Customer removed correctly", status = HttpStatusCode.Accepted)
            } else {
                this.call.respondText(
                    "No customer with ID $id",
                    status = HttpStatusCode.NotFound
                )
            }
        }
    }
}
