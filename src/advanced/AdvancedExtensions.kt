package com.example.ktor.advanced

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
//import org.jetbrains.exposed.sql.Table

fun Routing.helloworld(){
    post("/"){
        call.respondText("Hello World", ContentType.Text.Plain)
    }
}



data class Reposnses( val status : String)

fun Routing.dataClassResponse(){
    post("/dataclass"){
//        call.respondText("Hello World", ContentType.Text.Plain)
        var data = call.receive<Reposnses>()
        data = data
        call.respond(Reposnses(status = "$data"))
    }
}


