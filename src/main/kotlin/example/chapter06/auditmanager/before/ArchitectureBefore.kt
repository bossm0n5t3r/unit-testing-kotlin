package example.chapter06.auditmanager.before

import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ArchitectureBefore {
    class AuditManager(
        private val maxEntriesPerFile: Int,
        private val directoryName: String,
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
            val resourcesPath = Paths.get("").toAbsolutePath()
            val filePathList = Files.walk(resourcesPath).toList()
            val sortedFilePathList = sortByIndex(filePathList.map { it.toString() })

            val newRecord = "$visitorName;${DateTimeFormatter.ISO_DATE_TIME.format(timeOfVisit)}"

            if (sortedFilePathList.isEmpty()) {
                Paths.get(resourcesPath.toString(), "audit_1.txt").toFile().writeText(newRecord)
                return
            }

            val lastFilePath = Paths.get(sortedFilePathList.last())
            val lines = lastFilePath.toFile().readLines()

            if (lines.count() < maxEntriesPerFile) {
                lastFilePath.toFile().writeText("${lines.joinToString("\r\n")}\r\n$newRecord")
            } else {
                val newIndex = sortedFilePathList.size + 1
                val newName = "audit_$newIndex.txt"
                val newFile = Paths.get(resourcesPath.toString(), newName).toFile()
                newFile.writeText(newRecord)
            }
        }
    }
}
