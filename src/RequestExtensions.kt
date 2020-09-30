package com.example.ktor

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.request.receive
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post

fun Routing.root(){
    get("/"){
        call.respondText("From Ktor", ContentType.Text.Plain)
    }
}

fun Routing.rootpost(){
    post {
        val data = call.receive<String>()
        call.respondText("received data :  $data", ContentType.Text.Plain)
    }
}

fun Routing.getColors(){
    val colors = "{\"Colors\": [\"Red\",\"Blue\",\"Green\"]}"
    get("/colors"){
        call.respondText(colors)
    }
}