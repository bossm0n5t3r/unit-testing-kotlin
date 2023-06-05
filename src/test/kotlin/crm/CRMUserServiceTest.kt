package crm

import common.MockTablesTest
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import net.datafaker.Faker
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Test
import kotlin.random.Random

class CRMUserServiceTest : MockTablesTest(CrmUsers) {
    private val sut = CRMUserService(database)
    private val faker = Faker()

    @Test
    fun `findById returns null when user is not found`() {
        assertThat(sut.findById(Random.nextLong())).isNull()
    }

    @Test
    fun `findById returns user when user is found`() {
        val userEmail = faker.internet().emailAddress()
        val user = transaction(database) {
            CrmUser.new {
                email = userEmail
                type = UserType.CUSTOMER.name
            }
        }

        val foundUser = sut.findById(user.id.value)
        assertThat(foundUser).isNotNull
        assertThat(foundUser?.email).isEqualTo(userEmail)
    }

    @Test
    fun `updateUser does not update user when email is the same`() {
        val userEmail = faker.internet().emailAddress()
        val user = transaction(database) {
            CrmUser.new {
                email = userEmail
                type = UserType.CUSTOMER.name
            }
        }

        sut.updateUser(user, userEmail, mockk())

        assertThat(user.email).isEqualTo(userEmail)
    }

    @Test
    fun `updateUser updates user and call company updateNumberOfEmployees when email is different`() {
        val user = transaction(database) {
            CrmUser.new {
                email = faker.internet().emailAddress()
                type = UserType.CUSTOMER.name
            }
        }
        val newEmail = faker.internet().emailAddress()
        val crmCompany = mockk<CrmCompany> {
            every { isCompanyDomain(any()) } returns true
            justRun { updateNumberOfEmployees(any()) }
        }

        sut.updateUser(user, newEmail, crmCompany)

        assertThat(user.email).isEqualTo(newEmail)
        verify(exactly = 1) { crmCompany.updateNumberOfEmployees(any()) }
    }
}
