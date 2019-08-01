package com.kentrino.graphql

import com.coxautodev.graphql.tools.SchemaParser
import graphql.ExecutionInput
import graphql.ExecutionResult
import graphql.GraphQL
import org.koin.core.KoinComponent


class GraphQLHandler: KoinComponent {
    private val graphql : GraphQL
        get() {
            val schema = SchemaParser
                    .newParser()
                    .file("graphql/main.graphql")
                    .resolvers(RootResolver(), UserResolver())
                    .build()
                    .makeExecutableSchema()
            return GraphQL.newGraphQL(schema).build()
        }

    fun execute(query: String, operationName: String, variables: Map<String, Any>, context: Any): ExecutionResult {
        return graphql.execute(
                ExecutionInput.newExecutionInput()
                        .query(query)
                        .operationName(operationName)
                        .variables(variables)
                        .context(context)
                        // .dataLoaderRegistry(dataLoaderProvider.provideDataLoaderRegistry())
        )
    }
}
