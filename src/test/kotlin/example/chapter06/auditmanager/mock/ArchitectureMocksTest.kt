package example.chapter06.auditmanager.mock

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ArchitectureMocksTest {
    @Test
    fun a_new_file_is_created_for_the_first_entry() {
        val fileSystemMock = mockk<IFileSystem>()
        every { fileSystemMock.getFiles("audits") } returns emptyList()
        every { fileSystemMock.writeAllText(any(), any()) } answers { callOriginal() }

        val sut = ArchitectureMocks.AuditManager(
            maxEntriesPerFile = 3,
            directoryName = "audits",
            fileSystem = fileSystemMock,
        )

        sut.addRecord("Peter", LocalDateTime.parse("2023-08-07T03:00:00"))

        verify {
            fileSystemMock.writeAllText(
                filePath = "audits/audit_1.txt",
                content = "Peter;2023-08-07T03:00:00",
            )
        }
    }

    @Test
    fun a_new_file_is_created_when_the_current_file_overflows() {
        val fileSystemMock = mockk<IFileSystem>()
        every { fileSystemMock.getFiles("audits") } returns listOf(
            "audits/audit_1.txt",
            "audits/audit_2.txt",
        )
        every { fileSystemMock.readAllLines("audits/audit_2.txt") } returns listOf(
            "Peter; 2019-04-06T16:30:00",
            "Jane; 2019-04-06T16:40:00",
            "Jack; 2019-04-06T17:00:00",
        )
        every { fileSystemMock.writeAllText(any(), any()) } answers { callOriginal() }
        val sut = ArchitectureMocks.AuditManager(
            maxEntriesPerFile = 3,
            directoryName = "audits",
            fileSystem = fileSystemMock,
        )

        sut.addRecord(
            visitorName = "Alice",
            timeOfVisit = LocalDateTime.parse("2023-08-07T03:00:00"),
        )

        verify {
            fileSystemMock.writeAllText(
                filePath = "audits/audit_3.txt",
                content = "Alice;2023-08-07T03:00:00",
            )
        }
    }
}
