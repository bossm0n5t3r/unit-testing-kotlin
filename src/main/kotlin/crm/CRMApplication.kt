package crm

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.random.Random

class CRMApplication : KoinComponent {
    fun run() {
        val database: Database by inject()
        val crmMainService: CRMMainService by inject()
        val crmCompanyService: CRMCompanyService by inject()
        val crmUserService: CRMUserService by inject()

        onCRMTables(database) {
            val company = crmCompanyService.getCompany()
            println("company: ${company?.domainName}, ${company?.numberOfEmployees}")

            val user = crmUserService.findById((1..NUMBER_OF_EMPLOYEES).random())
            println("user: ${user?.id?.value}, ${user?.email}, ${user?.type}")

            crmMainService.changeEmail(1, "test@email.com")

            val updatedCompany = crmCompanyService.getCompany()
            println("updatedCompany: ${updatedCompany?.domainName}, ${updatedCompany?.numberOfEmployees}")
        }
    }

    private fun onCRMTables(database: Database, block: () -> Unit) {
        transaction(database) {
            SchemaUtils.create(CrmUsers, CrmCompanies)

            val companyDomainName = "test.com"
            for (i in 1..NUMBER_OF_EMPLOYEES) {
                CrmUser.new {
                    this.email = "user$i@$companyDomainName"
                    this.type = UserType.EMPLOYEE.name
                    this.isEmailConfirmed = Random.nextBoolean()
                }
            }
            CrmCompany.new {
                this.domainName = companyDomainName
                this.numberOfEmployees = NUMBER_OF_EMPLOYEES
            }

            block()

            CrmUsers.deleteAll()
            CrmCompanies.deleteAll()
            SchemaUtils.drop(CrmUsers, CrmCompanies)
        }
    }

    companion object {
        private const val NUMBER_OF_EMPLOYEES = 10L
    }
}
