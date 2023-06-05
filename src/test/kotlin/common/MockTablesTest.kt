package common

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class MockTablesTest(private vararg val tables: Table) {
    val database = Database.connect(
        url = "jdbc:h2:mem:test;MODE=MySQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;",
        driver = "org.h2.Driver",
        user = "sa",
        password = "",
    )

    @BeforeAll
    fun setup() {
        transaction(database) {
            SchemaUtils.create(*tables)
        }
    }

    @AfterEach
    fun clear() {
        transaction(database) {
            tables.forEach { it.deleteAll() }
        }
    }

    @AfterAll
    fun dropTables() {
        transaction(database) {
            SchemaUtils.drop(*tables)
        }
    }
}
