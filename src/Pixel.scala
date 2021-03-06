import java.awt.Color
import java.util.Random

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

    def average(pixels1: Array[Array[Color]], pixels2: Array[Array[Color]]): Array[Array[Color]] = {
        val width1: Int = pixels1.length
        val height1: Int = pixels1(0).length
        val width2: Int = pixels2.length
        val height2: Int = pixels2(0).length
        val width3: Int = Math.min(width1, width2)
        val height3: Int = Math.min(height1, height2)
        val pixelAverage: Array[Array[Color]] = ofDim[Color](width3, height3)

        for(j <- 0 until height3) {
            for(i <- 0 until width3) {
                pixelAverage(i)(j) = Colour.average(pixels1(i)(j), pixels2(i)(j))
            }
        }
        pixelAverage
    }

    def digitize(pixels: Array[Array[Color]], horizontalSplit: Int, verticalSplit: Int, percentDigital: Double): Array[Array[Color]] = {
        val width: Int = pixels.length
        val height: Int = pixels(0).length

        if(width < horizontalSplit || height < verticalSplit) return pixels
        if(percentDigital < 0.0 || percentDigital >= 1.0)
            throw new IllegalArgumentException("Percentage must be between 0.0 and 1.0")

        val pixelDigital: Array[Array[Color]] = pixelMap((pixel: Color) => pixel, pixels)

        val horizontalSectionSize = width / horizontalSplit
        val verticalSectionSize = height / verticalSplit

        for(j <- 0 until verticalSplit) {
            for(i <- 0 until horizontalSplit) {
                if(Math.random < percentDigital) {
                    var colourList = List[Color]()
                    for (l <- (verticalSectionSize * j) until (verticalSectionSize * (j + 1))) {
                        for (k <- (horizontalSectionSize * i) until (horizontalSectionSize * (i + 1))) {
                            colourList = colourList :+ pixels(k)(l)
                        }
                    }
                    val digitalColour = Colour.average(colourList)
                    for (l <- (verticalSectionSize * j) until (verticalSectionSize * (j + 1))) {
                        for (k <- (horizontalSectionSize * i) until (horizontalSectionSize * (i + 1))) {
                            pixelDigital(k)(l) = digitalColour
                        }
                    }
                }
            }
        }
        pixelDigital
    }

    def perms(pixels: Array[Array[Color]], intPerm: Vector[Int]): Array[Array[Color]] = {
        val perm = (colour: Color) => Colour.permPixel(colour, intPerm)
        pixelMap(perm, pixels)
    }
}
