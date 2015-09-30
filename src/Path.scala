import java.io.File

class Path(path: String) {
    val inPathSplit = path.split(File.separator)
    val fileName = inPathSplit(inPathSplit.size - 1)
    val fileNameSplit = fileName.split('.')
    val fileId = ""
    val fileExt = fileNameSplit(fileNameSplit.length - 1)
}

