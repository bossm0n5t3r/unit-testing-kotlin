package example.chapter07.crm

import common.MockTablesTest
import net.datafaker.Faker
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.random.Random
import kotlin.random.nextLong

class CrmCompanyTest : MockTablesTest(CrmCompanies) {
    private val faker = Faker()

    @Test
    fun isCompanyDomainTest() {
        // given
        val companyEmail = faker.internet().emailAddress()
        val sut = transaction(database) {
            CrmCompany.new {
                domainName = companyEmail.split("@")[1]
                numberOfEmployees = 1
            }
        }

        // when, then
        assertTrue(sut.isCompanyDomain(companyEmail))
    }

    @Test
    fun `when UserType is EMPLOYEE, numberOfEmployees is increased`() {
        // given
        val currentNumberOfEmployees = Random.nextLong(0L..10L)
        val company = transaction(database) {
            CrmCompany.new {
                domainName = faker.domain().validDomain("com")
                numberOfEmployees = currentNumberOfEmployees
            }
        }

        // when
        transaction(database) { company.updateNumberOfEmployees(UserType.EMPLOYEE) }

        // then
        assertThat(company.numberOfEmployees).isEqualTo(currentNumberOfEmployees + 1)
    }

    @Test
    fun `when UserType is CUSTOMER, numberOfEmployees is decreased if numberOfEmployees is positive`() {
        // given
        val currentNumberOfEmployees = Random.nextLong(1L..10L)
        val company = transaction(database) {
            CrmCompany.new {
                domainName = faker.domain().validDomain("com")
                numberOfEmployees = currentNumberOfEmployees
            }
        }

        // when
        transaction(database) { company.updateNumberOfEmployees(UserType.CUSTOMER) }

        // then
        assertThat(company.numberOfEmployees).isEqualTo(currentNumberOfEmployees - 1)
    }

    @Test
    fun `when UserType is CUSTOMER, numberOfEmployees is not decreased if numberOfEmployees is zero`() {
        // given
        val company = transaction(database) {
            CrmCompany.new {
                domainName = faker.domain().validDomain("com")
                numberOfEmployees = 0L
            }
        }

        // when
        transaction(database) { company.updateNumberOfEmployees(UserType.CUSTOMER) }

        // then
        assertThat(company.numberOfEmployees).isEqualTo(0L)
    }
}
