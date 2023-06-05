package crm

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

class CRMUserService(
    private val database: Database,
) {
    fun findById(userId: Long): CrmUser? {
        return transaction(database) { CrmUser.findById(userId) }
    }

    fun updateUser(
        crmUser: CrmUser,
        newEmail: String,
        crmCompany: CrmCompany,
    ) {
        if (crmUser.email == newEmail) return

        val newUserType = if (crmCompany.isCompanyDomain(newEmail)) {
            UserType.EMPLOYEE
        } else {
            UserType.CUSTOMER
        }

        transaction(database) {
            crmUser.email = newEmail
            crmUser.type = newUserType.name
        }

        crmCompany.updateNumberOfEmployees(newUserType)
    }
}
