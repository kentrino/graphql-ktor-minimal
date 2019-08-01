package com.kentrino.graphql

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.kentrino.db.User

class RootResolver : GraphQLQueryResolver {
    fun users() : List<User> {
        return listOf(
                User(id = 1, name = "Taro", bookId = 3),
                User(id = 2, name = "Hanako", bookId = 2),
                User(id = 3, name = "Jiro", bookId = 1)
        )
    }
}
