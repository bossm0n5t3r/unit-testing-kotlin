package example.chapter05

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.datafaker.Faker
import org.junit.jupiter.api.Test
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.test.assertEquals

class EmailControllerTest {
    private val faker = Faker()

    @Test
    fun sending_a_greetings_email() {
        val mockEmailGateway = mockk<IEmailGateway>(relaxed = true)
        val sut = EmailController(mockEmailGateway)
        val email = faker.internet().emailAddress()

        sut.greetUser(email)

        verify(exactly = 1) {
            mockEmailGateway.sendGreetingsEmail(email)
        }
    }

    @Test
    fun creating_a_report() {
        val randomNumberOfUsers = Random.nextInt(0..10)
        val stub = mockk<IDatabase> {
            every { getNumberOfUsers() } returns randomNumberOfUsers
        }
        val sut = EmailController(stub)

        val report = sut.createReport()

        assertEquals(report.numberOfUsers, randomNumberOfUsers)
        /**
         * 스텁으로 상호 작용 검증은 깨지기 쉬운 테스트의 예
         *
         * 이러한 관행을 과잉 명세 (overspecification) 라고 부른다.
         */
//        verify(exactly = 1) {
//            stub.getNumberOfUsers()
//        }
    }
}
