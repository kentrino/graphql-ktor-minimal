package com.kentrino.graphql

import com.coxautodev.graphql.tools.GraphQLResolver
import com.kentrino.db.Book
import com.kentrino.db.User

class UserResolver: GraphQLResolver<User> {
    private val books: List<Book> = listOf(
            Book(id = 1, title = "a"),
            Book(id = 2, title = "b"),
            Book(id = 3, title = "c")
    )
    
    fun book(user: User): Book? {
        return books.find { it.id == user.id }
    }
}
