package common

import org.jetbrains.exposed.sql.Database
import org.kodein.di.DI
import org.kodein.di.bindSingleton

val databaseModule = DI.Module("database") {
    bindSingleton<Database>(tag = "local") {
        Database.connect(
            url = "jdbc:h2:mem:test;MODE=MySQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;",
            driver = "org.h2.Driver",
            user = "sa",
            password = "",
        )
    }
}
