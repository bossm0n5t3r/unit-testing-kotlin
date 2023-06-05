package crm

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.kodein.di.instance

fun crmRun() {
    val database: Database by kodeinCRM.instance(tag = "local")
    val crmMainService: CRMMainService by kodeinCRM.instance()
    val crmCompanyService: CRMCompanyService by kodeinCRM.instance()
    val crmUserService: CRMUserService by kodeinCRM.instance()

    onCRMTables(database) {
        val company = crmCompanyService.getCompany()
        println("company: ${company?.domainName}, ${company?.numberOfEmployees}")

        val user = crmUserService.findById(1L)
        println("user: ${user?.id?.value}, ${user?.email}, ${user?.type}")

        crmMainService.changeEmail(1, "test@email.com")

        val updatedCompany = crmCompanyService.getCompany()
        println("updatedCompany: ${updatedCompany?.domainName}, ${updatedCompany?.numberOfEmployees}")
    }
}

fun onCRMTables(database: Database, block: () -> Unit) {
    transaction(database) {
        SchemaUtils.create(CrmUsers, CrmCompanies)

        val companyDomainName = "test.com"
        for (i in 1..10) {
            CrmUser.new {
                this.email = "user$i@$companyDomainName"
                this.type = UserType.EMPLOYEE.name
            }
        }
        CrmCompany.new {
            this.domainName = companyDomainName
            this.numberOfEmployees = 10
        }

        block()

        CrmUsers.deleteAll()
        CrmCompanies.deleteAll()
        SchemaUtils.drop(CrmUsers, CrmCompanies)
    }
}
