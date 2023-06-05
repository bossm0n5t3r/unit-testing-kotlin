package crm

import common.MockTablesTest
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import net.datafaker.Faker
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class CRMMainServiceTest : MockTablesTest(CrmUsers, CrmCompanies) {
    private val crmUserService = mockk<CRMUserService>()
    private val crmCompanyService = mockk<CRMCompanyService>()
    private val crmMessageBusService = mockk<CRMMessageBusService>()
    private val sut = CRMMainService(crmUserService, crmCompanyService, crmMessageBusService)
    private val faker = Faker()

    @Test
    fun `just return when user not found`() {
        // given
        val userId = faker.number().randomNumber()
        val newEmail = faker.internet().emailAddress()
        every { crmUserService.findById(userId) } returns null

        // when, then
        assertDoesNotThrow { sut.changeEmail(userId, newEmail) }
    }

    @Test
    fun `just return when company not found`() {
        // given
        val userId = faker.number().randomNumber()
        val newEmail = faker.internet().emailAddress()
        every { crmUserService.findById(userId) } returns mockk()
        every { crmCompanyService.getCompany() } returns null

        // when, then
        assertDoesNotThrow { sut.changeEmail(userId, newEmail) }
    }

    @Test
    fun `update user and send message when company found`() {
        // given
        val userId = faker.number().randomNumber()
        val newEmail = faker.internet().emailAddress()

        val user = mockk<CrmUser>()
        val company = mockk<CrmCompany>()
        every { crmUserService.findById(userId) } returns user
        every { crmCompanyService.getCompany() } returns company

        every { crmUserService.updateUser(user, newEmail, company) } just Runs
        every { crmMessageBusService.sendEmailChangedMessage(userId, newEmail) } just Runs

        // when, then
        assertDoesNotThrow { sut.changeEmail(userId, newEmail) }
        verify {
            crmUserService.updateUser(user, newEmail, company)
            crmMessageBusService.sendEmailChangedMessage(userId, newEmail)
        }
    }
}
