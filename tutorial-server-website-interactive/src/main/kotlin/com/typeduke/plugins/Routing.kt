package com.typeduke.plugins

import com.typeduke.models.Article
import com.typeduke.models.articles
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.util.*

fun Application.configureRouting() {
    routing {
        static("/static") {
            resources("files")
        }
        get("/") {
            this.call.respondRedirect("articles")
        }
        route("articles") {
            // Show a list of articles
            get {
                this.call.respond(FreeMarkerContent("index.ftl", mapOf("articles" to articles)))
            }
            // Show a page with fields for creating a new article
            get("new") {
                this.call.respond(FreeMarkerContent("new.ftl", model = null))
            }
            // Save an article
            post {
                val formParameters = this.call.receiveParameters()
                val title = formParameters.getOrFail("title")
                val body = formParameters.getOrFail("body")
                val newEntry = Article.newEntry(title, body)
                articles.add(newEntry)
                this.call.respondRedirect("/articles/${newEntry.id}")
            }
            // Show an article with a specific ID
            get("{id}") {
                val id = this.call.parameters.getOrFail<Int>("id").toInt()
                this.call.respond(FreeMarkerContent("show.ftl", mapOf("article" to articles.find { it.id == id })))
            }
            // Show a page with fields for editing an article
            get("{id}/edit") {
                val id = this.call.parameters.getOrFail<Int>("id").toInt()
                this.call.respond(FreeMarkerContent("edit.ftl", mapOf("article" to articles.find { it.id == id })))
            }
            // Update or delete an article
            post("{id}") {
                val id = this.call.parameters.getOrFail<Int>("id").toInt()
                val formParameters = this.call.receiveParameters()
                when (formParameters.getOrFail("_action")) {
                    "update" -> {
                        val index = articles.indexOf(articles.find { it.id == id })
                        val title = formParameters.getOrFail("title")
                        val body = formParameters.getOrFail("body")
                        articles[index].title = title
                        articles[index].body = body
                        this.call.respondRedirect("/articles/$id")
                    }
                    "delete" -> {
                        articles.removeIf { it.id == id }
                        this.call.respondRedirect("/articles")
                    }
                }
            }
        }
    }
}
