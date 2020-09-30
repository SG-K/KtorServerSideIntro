package com.example.ktor.database

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.Closeable
import org.jetbrains.exposed.sql.*


data class Product(
    val id: Int, val title: String,
    val description: String,
    val price: Int
)


object Products : Table(){
    val id = integer("id").primaryKey().autoIncrement()
    val title = varchar("title", 100)
    val description = varchar("description", 500)
    val price = integer("price")
}




//region Dao related code

class ProductDao(val db: Database) : DAOInterface {

    override fun init() = transaction(db) {
        SchemaUtils.create(Products)
    }

    override fun createProduct(title: String, description: String, price: Int) = transaction(db){
        Products.insert {
            it[Products.title] = title
            it[Products.description] = description
            it[Products.price] = price
        }
        Unit
    }

    override fun updateProduct(id: Int, title: String, description: String, price: Int) = transaction(db){
        Products.update({ Products.id eq id }) {
            it[Products.title] = title
            it[Products.description] = description
            it[Products.price] = price
        }
        Unit
    }

    override fun deleteProduct(id: Int) = transaction(db){
        Products.deleteWhere { Products.id eq id }
        Unit
    }

    override fun getProduct(id: Int) = transaction(db) {
        Products.select { Products.id eq id }.map {
            Product(
                it[Products.id], it[Products.title], it[Products.description], it[Products.price]
            )
        }.singleOrNull()
    }

    override fun getAllProducts()= transaction(db) {
        Products.selectAll().map {
            Product(
                it[Products.id], it[Products.title], it[Products.description], it[Products.price]
            )
        }
    }

    override fun close() {}
}


interface DAOInterface : Closeable {
    fun init()
    fun createProduct(title: String, description: String, price: Int)
    fun updateProduct(id: Int, title: String, description: String, price: Int)
    fun deleteProduct(id: Int)
    fun getProduct(id: Int): Product?
    fun getAllProducts(): List<Product>
}

//endregion
