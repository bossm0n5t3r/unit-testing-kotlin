package example.chapter07

import example.chapter07.crm.CRMApplication
import example.chapter07.crm.crmKoinModule
import org.koin.core.context.startKoin

fun main(args: Array<String>) {
    startKoin {
        modules(crmKoinModule)
    }
    CRMApplication().run()
}
