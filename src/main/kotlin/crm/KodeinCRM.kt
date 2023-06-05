package crm

import common.databaseModule
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val kodeinCRM = DI {
    import(databaseModule)
    bindSingleton<CRMCompanyService> { CRMCompanyService(instance(tag = "local")) }
    bindSingleton<CRMUserService> { CRMUserService(instance(tag = "local")) }
    bindSingleton<CRMMessageBusService> { CRMMessageBusService() }
    bindSingleton<CRMMainService> { CRMMainService(instance(), instance(), instance()) }
}
