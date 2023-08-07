package example.chapter07.crm

import example.chapter07.common.databaseModule
import org.koin.dsl.module

val crmKoinModule = module {
    includes(databaseModule)
    single { CRMCompanyService(get()) }
    single { CRMUserService(get()) }
    single { CRMMessageBusService() }
    single { CRMMainService(get(), get(), get()) }
}
