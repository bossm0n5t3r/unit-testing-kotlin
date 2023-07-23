import crm.CRMApplication
import crm.crmKoinModule
import org.koin.core.context.startKoin

fun main(args: Array<String>) {
    startKoin {
        modules(crmKoinModule)
    }
    CRMApplication().run()
}
