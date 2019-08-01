package com.kentrino

import com.kentrino.graphql.GraphQLHandler
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.Logger.SLF4JLogger
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
import java.util.concurrent.ConcurrentHashMap


fun main(args: Array<String>) {
    embeddedServer(
            factory = Netty,
            host = "0.0.0.0",
            port = 10080,
            configure = {
                callGroupSize = 200
            },
            module = {
                injectDependencies()
                main()
            }
    ).start()
}

fun Application.injectDependencies() {
    install(ContentNegotiation) {
        jackson {}
    }

    install(Koin) {
        SLF4JLogger()
        modules(module())
    }
}

data class GraphQLRequest(val query: String, val operationName: String?, val variables: Map<String, Any>?)

fun Application.main() {
    routing {
        val handler by inject<GraphQLHandler>()
        post<GraphQLRequest>("/graphql") {
            val query = it.query
            val operationName = it.operationName ?: ""
            val variables = it.variables ?: mapOf()
            val context = ConcurrentHashMap<String, Any>()
            call.respond(
                    handler.execute(query, operationName, variables, context).toSpecification()
            )
        }
    }
}
