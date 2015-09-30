import java.awt.Color
import java.awt.image.BufferedImage
import java.io.{FileNotFoundException, File}
import javax.imageio.ImageIO

import scala.Array._

object IO {
    def importImage(path: String): Array[Array[Color]] = {
        var image: BufferedImage = null
        var pixels: Array[Array[Color]] = null

        try {
            image = ImageIO.read(new File(path))
            val width: Int = image.getWidth
            val height: Int = image.getHeight
            pixels = ofDim[Color](width, height)
            for(j <- 0 until height) {
                for(i <- 0 until width) {
                    //pixels(i)(j) = new Color(image.getRGB(i, j), true)
                    pixels(i)(j) = new Color(image.getRGB(i, j))
                }
            }
        } catch {
            case e: Exception => e.printStackTrace()
        }
        pixels
    }

    def exportImage(path: String, pixels: Array[Array[Color]], format: String) {
        val width: Int = pixels.length
        val height: Int = pixels(0).length
        //val image: BufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
        val image: BufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)

        for(j <- 0 until height) {
            for(i <- 0 until width) {
              image.setRGB(i, j, pixels(i)(j).getRGB)
            }
        }

        try {
            ImageIO.write(image, format, new File(path))
        } catch {
            case e: Exception => e.printStackTrace()
        }
    }

    def checkArgs(args: Array[String], argCount: Int, message: String) {
        if(args.length != argCount) {
            throw new IllegalArgumentException(message)
        }
    }

    def checkDir(dirPath: String) {
        val dir = new File(dirPath)
        if(!dir.exists || !dir.isDirectory) {
            throw new FileNotFoundException(dirPath)
        }
    }

    def checkFile(filePath: String) {
        val file = new File(filePath)
        if(!file.exists || file.isDirectory) {
            throw new FileNotFoundException(filePath)
        }
    }
}
