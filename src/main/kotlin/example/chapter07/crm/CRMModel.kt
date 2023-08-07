package example.chapter07.crm

import example.chapter07.common.BaseLongEntity
import example.chapter07.common.BaseLongEntityClass
import example.chapter07.common.BaseLongIdTable
import org.jetbrains.exposed.dao.id.EntityID

object CrmUsers : BaseLongIdTable("crm_user_info") {
    val email = varchar("email", 100)
    val type = varchar("type", 20)
    val isEmailConfirmed = bool("is_email_confirmed")
}

class CrmUser(id: EntityID<Long>) : BaseLongEntity(id, CrmUsers) {
    companion object : BaseLongEntityClass<CrmUser>(CrmUsers)

    var email by CrmUsers.email
    var type by CrmUsers.type
    var isEmailConfirmed by CrmUsers.isEmailConfirmed

    fun canChangeEmail() = if (isEmailConfirmed) {
        "Can't change a confirmed email"
    } else {
        null
    }
}

object CrmCompanies : BaseLongIdTable("crm_company_info") {
    val domainName = varchar("domain_name", 100)
    val numberOfEmployees = long("number_of_employees")
}

class CrmCompany(id: EntityID<Long>) : BaseLongEntity(id, CrmCompanies) {
    companion object : BaseLongEntityClass<CrmCompany>(CrmCompanies)

    var domainName by CrmCompanies.domainName
    var numberOfEmployees by CrmCompanies.numberOfEmployees

    fun isCompanyDomain(email: String): Boolean {
        return email.split("@")[1] == domainName
    }

    fun updateNumberOfEmployees(userType: UserType) {
        val currentNumberOfEmployees = numberOfEmployees
        val updatedNumberOfEmployees = when (userType) {
            UserType.CUSTOMER -> {
                if (currentNumberOfEmployees > 0) {
                    currentNumberOfEmployees - 1
                } else {
                    0
                }
            }
            UserType.EMPLOYEE -> currentNumberOfEmployees + 1
        }

        numberOfEmployees = updatedNumberOfEmployees
    }
}

enum class UserType {
    CUSTOMER,
    EMPLOYEE,
}
