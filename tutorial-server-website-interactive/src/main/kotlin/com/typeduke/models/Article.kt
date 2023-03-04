package com.typeduke.models

import java.util.concurrent.atomic.AtomicInteger

class Article
private constructor(val id: Int, var title: String, var body: String) {
    companion object {
        private val idCounter = AtomicInteger()

        fun newEntry(title: String, body: String) = Article(this.idCounter.getAndIncrement(), title, body)
    }
}

val articles = mutableListOf(Article.newEntry(
    "The drive to develop...",
    "...is what keeps me going."
))
