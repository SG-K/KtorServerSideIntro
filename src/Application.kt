package com.example.ktor

import io.ktor.application.*
import io.ktor.http.ContentType
import io.ktor.request.receive
import io.ktor.response.*
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

private val users = "{\"Users\": [\"Harvey\",\"Mike\",\"Jessica\"]}"

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    routing{

        get("/"){
            call.respondText("Hello Ktor", ContentType.Text.Plain)
        }

        post {
            val data = call.receive<String>()
            call.respondText("received data :  $data", ContentType.Text.Plain)
        }

        get("/users"){
            call.respondText(users)
        }

    }


}

