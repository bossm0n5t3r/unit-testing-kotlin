package example.chapter06.auditmanager.mock

import java.nio.file.Paths
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ArchitectureMocks {
    class AuditManager(
        private val maxEntriesPerFile: Int,
        private val directoryName: String,
        private val fileSystem: IFileSystem,
    ) {
        private fun getIndex(filePath: String): Int {
            // File name example: audit_1.txt
            return filePath
                .split(".")
                .first()
                .split("_")
                .last()
                .toInt()
        }

        private fun sortByIndex(fileList: List<String>): List<String> {
            return fileList.sortedBy { getIndex(it) }
        }

        fun addRecord(visitorName: String, timeOfVisit: LocalDateTime) {
            val resourcesPath = Paths.get("", directoryName)
            val filePathList = fileSystem.getFiles(directoryName)
            val sortedFilePathList = sortByIndex(filePathList)

            val newRecord = "$visitorName;${DateTimeFormatter.ISO_DATE_TIME.format(timeOfVisit)}"

            if (sortedFilePathList.isEmpty()) {
                fileSystem.writeAllText(
                    filePath = Paths.get(resourcesPath.toString(), "audit_1.txt").toString(),
                    content = newRecord,
                )
                return
            }

            val lastFilePath = Paths.get(sortedFilePathList.last())
            val lines = fileSystem.readAllLines(lastFilePath.toString())

            if (lines.count() < maxEntriesPerFile) {
                fileSystem.writeAllText(
                    filePath = lastFilePath.toString(),
                    content = "${lines.joinToString("\r\n")}\r\n$newRecord",
                )
            } else {
                val newIndex = sortedFilePathList.size + 1
                val newName = "audit_$newIndex.txt"
                fileSystem.writeAllText(
                    filePath = Paths.get(resourcesPath.toString(), newName).toString(),
                    content = newRecord,
                )
            }
        }
    }
}
