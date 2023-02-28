import com.typeduke.models.orderStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.listOrdersRoute() {
    get("/order") {
        if (orderStorage.isNotEmpty()) {
            this.call.respond(orderStorage)
        } else {
            this.call.respondText("No orders found", status = HttpStatusCode.OK)
        }
    }
}

fun Route.getOrderRoute() {
    get("/order/{id?}") {
        val id = this.call.parameters["id"] ?: return@get this.call.respondText(
            "Missing ID",
            status = HttpStatusCode.BadRequest
        )
        val order = orderStorage.find { it.number == id } ?: return@get this.call.respondText(
            "No order with ID $id",
            status = HttpStatusCode.NotFound
        )
        this.call.respond(order)
    }
}

fun Route.totalizeOrderRoute() {
    get("/order/{id?}/total") {
        val id = this.call.parameters["id"] ?: return@get this.call.respondText(
            "Missing ID",
            status = HttpStatusCode.BadRequest
        )
        val order = orderStorage.find { it.number == id } ?: return@get this.call.respondText(
            "No order with ID $id",
            status = HttpStatusCode.NotFound
        )
        val total = order.contents.sumOf { it.price * it.amount }
        this.call.respond(total)
    }
}
