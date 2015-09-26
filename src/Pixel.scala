import java.awt.Color

import scala.Array._

object Pixel {
    def pixelMap(pixelFunc: Color => Color, pixelArray: Array[Array[Color]]): Array[Array[Color]] = {
        val width: Int = pixelArray.length
        val height: Int = pixelArray(0).length
        val pixelArrayMapped: Array[Array[Color]] = ofDim[Color](width, height)

        for(j <- 0 until height) {
            for(i <- 0 until width) {
                pixelArrayMapped(i)(j) = pixelFunc(pixelArray(i)(j))
            }
        }
        pixelArrayMapped
    }

    def shift(pixels: Array[Array[Color]]): Array[Array[Color]] = {
        val shift = (colour: Color) => new Color(colour.getBlue,
                                                 colour.getRed,
                                                 colour.getGreen,
                                                 colour.getAlpha)
        pixelMap(shift, pixels)
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
        val half = (colour: Color) => {
            val colourArray = Array(colour.getRed, colour.getGreen, colour.getBlue)
            val colourArrayHalf = new Array[Int](3)
            val length = colourArray.length
            for(x <- 0 until length) {
                colourArrayHalf(x) = colourArray(x) - (colourArray(x) - colourArray((x - 1 + length) % length))/2
            }
            new Color(colourArrayHalf(0), colourArrayHalf(1), colourArrayHalf(2), colour.getAlpha)
        }
        pixelMap(half, pixels)
    }

    def perms(pixels: Array[Array[Color]], intPerm: Vector[Int]): Array[Array[Color]] = {
        val perm = (colour: Color) => Colour.permPixel(colour, intPerm)
        pixelMap(perm, pixels)
    }
}
