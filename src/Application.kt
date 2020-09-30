package com.example.ktor

import com.example.ktor.advanced.dataClassResponse
import com.example.ktor.advanced.helloworld
import com.example.ktor.database.Product
import com.example.ktor.database.ProductDao
import io.ktor.application.*
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.JacksonConverter
import io.ktor.request.receive
import io.ktor.response.*
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import org.jetbrains.exposed.sql.Database


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

//    routing{
//        this.root()
//        this.rootpost()
//        this.getColors()
//    }

    install(StatusPages){
        exception<Throwable>{e->
            call.respondText(e.localizedMessage,ContentType.Text.Plain,
                HttpStatusCode.InternalServerError)
        }
    }
    install(ContentNegotiation){
        register(ContentType.Application.Json, JacksonConverter())
    }

    val dao = ProductDao(Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver"))
    dao.init()
    routing{
        this.helloworld()
        this.dataClassResponse()

        post("/addProduct") {
            val product = call.receive<Product>()
            dao.createProduct(product.title, product.description, product.price)
        }

        get("/products") {
            call.respond(mapOf("products" to dao.getAllProducts()))
        }
    }


}

