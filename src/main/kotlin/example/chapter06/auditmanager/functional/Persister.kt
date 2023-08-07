package example.chapter06.auditmanager.functional

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.readLines

class Persister {
    fun readDirectory(directoryName: String): List<FileContent> {
        val path = Paths.get(directoryName)
        return Files
            .walk(path)
            .toList()
            .map {
                FileContent(it.fileName.toString(), it.readLines())
            }
    }

    fun applyUpdate(directoryName: String, update: FileUpdate) {
        val filePath = Paths.get(directoryName, update.fileName)
        File(filePath.toString()).writeText(update.newContent)
    }
}
