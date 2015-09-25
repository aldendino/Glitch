import java.awt.Color

import scala.Array._

object Pixel {
    def shift(pixels: Array[Array[Color]]): Array[Array[Color]] = {
        val width: Int = pixels.length
        val height: Int = pixels(0).length
        val pixelShift: Array[Array[Color]] = ofDim[Color](width, height)

        for(j <- 0 until height) {
            for(i <- 0 until width) {
                val col = pixels(i)(j)
                pixelShift(i)(j) = new Color(col.getBlue, col.getRed, col.getGreen, col.getAlpha)
            }
        }
        pixelShift
    }

    def slide(pixels: Array[Array[Color]]): Array[Array[Color]] = {
        val width: Int = pixels.length
        val height: Int = pixels(0).length
        val pixelSlide: Array[Array[Color]] = ofDim[Color](width, height)

        for(j <- 0 until height) {
            for(i <- 0 until width) {
                pixelSlide(i)(j) = pixels((i - j + width) % width)(j)
            }
        }
        pixelSlide
    }

    def halfway(pixels: Array[Array[Color]]): Array[Array[Color]] = {
        val width: Int = pixels.length
        val height: Int = pixels(0).length
        val pixelHalf: Array[Array[Color]] = ofDim[Color](width, height)

        for(j <- 0 until height) {
            for(i <- 0 until width) {
                val col = pixels(i)(j)
                val cols = Array(col.getRed, col.getGreen, col.getBlue)
                val colsHalf = new Array[Int](3)
                val len = cols.length
                for(x <- 0 until len) {
                    colsHalf(x) = cols(x) - (cols(x) - cols((x - 1 + len) % len))/2
                }
                pixelHalf(i)(j) = new Color(colsHalf(0), colsHalf(1), colsHalf(2))
            }
        }
        pixelHalf
    }

    def perms(pixels: Array[Array[Color]], intPerm: Vector[Int]): Array[Array[Color]] = {
        val width: Int = pixels.length
        val height: Int = pixels(0).length
        val pixelPerm: Array[Array[Color]] = ofDim[Color](width, height)

        for(j <- 0 until height) {
            for(i <- 0 until width) {
                pixelPerm(i)(j) = Colour.permPixel(pixels(i)(j), intPerm)
            }
        }
        pixelPerm
    }
}
