package crm

import common.databaseModule
import org.koin.dsl.module

val crmKoinModule = module {
    includes(databaseModule)
    single { CRMCompanyService(get()) }
    single { CRMUserService(get()) }
    single { CRMMessageBusService() }
    single { CRMMainService(get(), get(), get()) }
}
