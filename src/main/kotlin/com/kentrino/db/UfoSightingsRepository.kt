package com.kentrino.db

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.transaction
import org.koin.core.KoinComponent
import org.koin.core.inject

class UfoSightingsRepository : KoinComponent {
    private val database by inject<Database>()

    suspend fun create(sighting: UfoSighting): Long {
        return transaction(database) {
            UfoSightings
                    .insert {
                        it[date] = sighting.date
                        it[city] = sighting.city
                        it[state] = sighting.state
                        it[country] = sighting.country
                        it[shape] = sighting.shape
                        it[duration] = sighting.duration
                        it[comments] = sighting.comments
                        it[latitude] = sighting.latitude
                        it[longitude] = sighting.longitude
                    }[UfoSightings.id].value
        }
    }

    suspend fun find(id: Long): UfoSighting? {
        return transaction(database) {
            UfoSightings
                    .select { UfoSightings.id eq id }
                    .map { it.toDto() }
                    .singleOrNull()
        }
    }

    suspend fun findAll(size: Int): List<UfoSighting> {
        return transaction(database) {
            UfoSightings
                    .selectAll()
                    .orderBy(UfoSightings.date, SortOrder.DESC)
                    .limit(size)
                    .map { it.toDto() }
        }
    }

    private fun ResultRow.toDto(): UfoSighting {
        return UfoSighting(
                id = this[UfoSightings.id].value,
                date = this[UfoSightings.date],
                city = this[UfoSightings.city],
                state = this[UfoSightings.state],
                country = this[UfoSightings.country],
                shape = this[UfoSightings.shape],
                duration = this[UfoSightings.duration],
                comments = this[UfoSightings.comments],
                latitude = this[UfoSightings.latitude],
                longitude = this[UfoSightings.longitude]
        )
    }
}
