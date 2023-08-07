package example.chapter06.auditmanager.functional

import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import kotlin.test.assertEquals

class ArchitectureFunctionalTest {
    @Test
    fun a_new_file_is_created_when_the_current_file_overflows() {
        val sut = ArchitectureFunctional.AuditManager(3)
        val fileList = listOf(
            FileContent("audit_1.txt", emptyList()),
            FileContent(
                "audit_2.txt",
                listOf(
                    "Peter;2019-04-06T16:30:00",
                    "Jane;2019-04-06T16:40:00",
                    "Jack;2019-04-06T17:00:00",
                ),
            ),
        )

        val update = sut.addRecord(
            fileList = fileList,
            visitorName = "Alice",
            timeOfVisit = LocalDateTime.parse("2023-08-08T03:00:00"),
        )

//        assertEquals("audit_3.txt", update.fileName)
//        assertEquals("Alice;2023-08-08T03:00:00", update.newContent)
        assertEquals(
            FileUpdate("audit_3.txt", "Alice;2023-08-08T03:00:00"),
            update,
        )
    }
}
