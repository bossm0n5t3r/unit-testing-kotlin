package crm

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

class CRMCompanyService(
    private val database: Database,
) {
    fun getCompany(): CrmCompany? {
        return transaction(database) { CrmCompany.all().sortedBy { it.id } }.lastOrNull()
    }
}
