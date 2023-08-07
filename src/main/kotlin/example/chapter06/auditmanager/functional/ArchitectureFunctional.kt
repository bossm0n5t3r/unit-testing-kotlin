package example.chapter06.auditmanager.functional

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ArchitectureFunctional {
    class AuditManager(
        private val maxEntriesPerFile: Int,
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

        private fun sortByIndex(fileList: List<FileContent>): List<FileContent> {
            return fileList.sortedBy { getIndex(it.fileName) }
        }

        fun addRecord(
            fileList: List<FileContent>,
            visitorName: String,
            timeOfVisit: LocalDateTime,
        ): FileUpdate {
            val sorted = sortByIndex(fileList)
            val newRecord = "$visitorName;${DateTimeFormatter.ISO_DATE_TIME.format(timeOfVisit)}"

            val currentFile = sorted.lastOrNull()
                ?: return FileUpdate("audit_1.txt", newRecord)

            val lineList = currentFile.lineList

            return if (lineList.size < maxEntriesPerFile) {
                val newContent = (lineList + newRecord).joinToString("\r\n")
                FileUpdate(currentFile.fileName, newContent)
            } else {
                val newIndex = sorted.size + 1
                val newName = "audit_$newIndex.txt"
                FileUpdate(newName, newRecord)
            }
        }
    }

    class ApplicationService(
        private val directoryName: String,
        maxEntriesPerFile: Int,
    ) {
        private val auditManager = AuditManager(maxEntriesPerFile)
        private val persister = Persister()

        fun addRecord(visitorName: String, timeOfVisit: LocalDateTime) {
            val fileList = persister.readDirectory(directoryName)
            val update = auditManager.addRecord(
                fileList = fileList,
                visitorName = visitorName,
                timeOfVisit = timeOfVisit,
            )
            persister.applyUpdate(directoryName, update)
        }
    }
}
