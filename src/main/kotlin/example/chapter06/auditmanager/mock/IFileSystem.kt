package example.chapter06.auditmanager.mock

interface IFileSystem {
    fun getFiles(directoryName: String): List<String>
    fun writeAllText(filePath: String, content: String)
    fun readAllLines(filePath: String): List<String>
}
