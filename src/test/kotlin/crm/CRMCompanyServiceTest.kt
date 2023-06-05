package crm

import common.MockTablesTest
import net.datafaker.Faker
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Test
import kotlin.random.Random

class CRMCompanyServiceTest : MockTablesTest(CrmCompanies) {
    private val sut = CRMCompanyService(database)
    private val faker = Faker()

    @Test
    fun `getCompany returns null when there is no companies`() {
        val result = sut.getCompany()

        assertThat(result).isNull()
    }

    @Test
    fun `getCompany returns the last company`() {
        // given
        val lastCompanyDomainName = faker.domain().validDomain("com")

        transaction(database) {
            CrmCompany.new {
                domainName = "Company1.com"
                numberOfEmployees = Random.nextLong()
            }
            CrmCompany.new {
                domainName = lastCompanyDomainName
                numberOfEmployees = Random.nextLong()
            }
        }

        val result = sut.getCompany()

        assertThat(result).isNotNull
        assertThat(result?.domainName).isEqualTo(lastCompanyDomainName)
    }
}
