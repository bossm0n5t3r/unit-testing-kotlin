package example.chapter07.common

import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

val databaseModule = module {
    single {
        Database.connect(
            url = "jdbc:h2:mem:test;MODE=MySQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;",
            driver = "org.h2.Driver",
            user = "sa",
            password = "",
        )
    }
}
