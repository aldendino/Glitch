import java.io.File

object PathUtils {
    def appendFileToPath(path: String, file: String): String = {
        if(path.isEmpty) throw new IllegalArgumentException("Path is empty.")
        if(file.isEmpty) throw new IllegalArgumentException("Path is empty.")
        if(path.matches(".*" + File.separator + "$")) {
            path + file
        } else {
            path + File.separator + file
        }
    }
}
