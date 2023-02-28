package com.typeduke.plugins

import com.typeduke.routes.customerRouting
import getOrderRoute
import io.ktor.server.routing.*
import io.ktor.server.application.*
import listOrdersRoute
import totalizeOrderRoute

fun Application.configureRouting() {
    routing {
        // Customer
        customerRouting()
        // Order
        listOrdersRoute()
        getOrderRoute()
        totalizeOrderRoute()
    }
}
