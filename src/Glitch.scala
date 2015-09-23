import java.awt._
import java.awt.image._
import javax.imageio._
import java.io._

import Array._
import List._

object Glitch {
    def main(args: Array[String]) {
        
        Colour.perms(new Color(12, 15, 23))
        
        if(args.length != 2) {
            val message = "usage: scala Glitch <path to image> <path for new image>"
            throw new IllegalArgumentException(message)
        }
        
        val imageTypes: Map[Symbol, String] = Map('JPG -> "jpg")
        
        val in: String = args(0)
        val out: String = args(1)
        
        var pixels = ImageArray.importImage(in)
        
        //pixels = ImageManip.shift(pixels)
        //pixels = ImageManip.slide(pixels)
        //pixels = ImageManip.halfway(pixels)
        
        //var transforms = Array(ImageManip.shift _, ImageManip.slide _)
        //var transforms = Array(ImageManip.slide _)
        
        //transforms.map(func => pixels = func(pixels))
        
        var intperms = (1 to 3).toList.permutations
        
        for(perm <- intperms) {
            var pixelperm = ImageManip.perms(pixels, perm.toVector)
            ImageArray.exportImage(out + perm.toString + ".jpg", pixelperm, imageTypes('JPG))
        }
        
        //ImageArray.exportImage(out, pixels, imageTypes('JPG))
    }
}

object Colour {
    def perms(colour: Color): Vector[Color] = {
        var intperms = (1 to 3).toList.permutations
        var collist = Vector[Color]()
        for(perm <- intperms) {
            var r = int2rgb(colour, perm(0))
            var g = int2rgb(colour, perm(1))
            var b = int2rgb(colour, perm(2))
            var col = new Color(r, g, b)
            collist = collist :+ col
        }
        collist
    }
    
    def int2rgb(colour: Color, int: Int): Int = {
        int match {
            case 1 => colour.getRed
            case 2 => colour.getGreen
            case 3 => colour.getBlue
            case _ => throw new IndexOutOfBoundsException()
        }
    }
    
    def permPixel(colour: Color, intperm: Vector[Int]): Color = {
        if(intperm.length == 3) {
            var r = int2rgb(colour, intperm(0))
            var g = int2rgb(colour, intperm(1))
            var b = int2rgb(colour, intperm(2))
            new Color(r, g, b)
        } else {
            throw new Exception("Perm list must be 3 elements.")
        }
    }
}

object ImageManip {
    def shift(pixels: Array[Array[Color]]): Array[Array[Color]] = {
        var width: Int = pixels.length
        var height: Int = pixels(0).length
        var pixelshift: Array[Array[Color]] = ofDim[Color](width, height)
        
        for(j <- 0 until height) {
            for(i <- 0 until width) {
                var col = pixels(i)(j)
                pixelshift(i)(j) = new Color(col.getBlue, col.getRed, col.getGreen)
            }
        }
        pixelshift
    }
    
    def slide(pixels: Array[Array[Color]]): Array[Array[Color]] = {
        var width: Int = pixels.length
        var height: Int = pixels(0).length
        var pixelslide: Array[Array[Color]] = ofDim[Color](width, height)
        
        for(j <- 0 until height) {
            for(i <- 0 until width) {
                pixelslide(i)(j) = pixels((i - j + width) % width)(j)
            }
        }
        pixelslide
    }
    
    def halfway(pixels: Array[Array[Color]]): Array[Array[Color]] = {
        var width: Int = pixels.length
        var height: Int = pixels(0).length
        var pixelhalf: Array[Array[Color]] = ofDim[Color](width, height)
        
        for(j <- 0 until height) {
            for(i <- 0 until width) {
                var col = pixels(i)(j)
                var cols = Array(col.getRed, col.getGreen, col.getBlue)
                var colshalf = new Array[Int](3)
                var len = cols.length
                for(x <- 0 until len) {
                    colshalf(x) = cols(x) - (cols(x) - cols((x - 1 + len) % len))/2
                }
                pixelhalf(i)(j) = new Color(colshalf(0), colshalf(1), colshalf(2))
            }
        }
        pixelhalf
    }
    
    def perms(pixels: Array[Array[Color]], intperm: Vector[Int]): Array[Array[Color]] = {
        var width: Int = pixels.length
        var height: Int = pixels(0).length
        var pixelperm: Array[Array[Color]] = ofDim[Color](width, height)
        
        for(j <- 0 until height) {
            for(i <- 0 until width) {
                pixelperm(i)(j) = Colour.permPixel(pixels(i)(j), intperm)
            }
        }
        pixelperm
    }
}

object ImageArray {
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
                    pixels(i)(j) = new Color(image.getRGB(i, j))
                }
            }
        } catch {
            case e: Exception => println(e)
        }
        pixels
    }
    
    def exportImage(path: String, pixels: Array[Array[Color]], format: String) {
        var width: Int = pixels.length
        var height: Int = pixels(0).length
        var image: BufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        
        for(j <- 0 until height) {
            for(i <- 0 until width) {
                image.setRGB(i, j, pixels(i)(j).getRGB)
            }
        }
        
        try { 
            ImageIO.write(image, format, new File(path))
        } catch {
            case e: Exception => println(e)
        }
    }
}