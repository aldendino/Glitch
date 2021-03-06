import java.awt.Color

object Colour {
    def intPerms: Iterator[List[Int]] = (1 to 3).toList.permutations

    def perms(colour: Color): Vector[Color] = {
        var colList = Vector[Color]()
        for(perm <- intPerms) {
            val r = int2rgb(colour, perm.head)
            val g = int2rgb(colour, perm(1))
            val b = int2rgb(colour, perm(2))
            val col = new Color(r, g, b)
            colList = colList :+ col
        }
        colList
    }

    def int2rgb(colour: Color, int: Int): Int = {
        int match {
            case 1 => colour.getRed
            case 2 => colour.getGreen
            case 3 => colour.getBlue
            case _ => throw new IndexOutOfBoundsException()
        }
    }

    def permPixel(colour: Color, intPerm: Vector[Int]): Color = {
        if(intPerm.length == 3) {
            val r = int2rgb(colour, intPerm.head)
            val g = int2rgb(colour, intPerm(1))
            val b = int2rgb(colour, intPerm(2))
            new Color(r, g, b, colour.getAlpha)
        } else {
            throw new Exception("Perm list must be 3 elements.")
        }
    }

    def average(colour1: Color, colour2: Color): Color = {
        val averageInt = (first: Int, second: Int) => (first + second)/2
        new Color(averageInt(colour1.getRed, colour2.getRed),
                  averageInt(colour1.getGreen, colour2.getGreen),
                  averageInt(colour1.getBlue, colour2.getBlue))
    }

    def average(colours: List[Color]): Color = {
        var redSum: Int = 0
        var greenSum: Int = 0
        var blueSum: Int = 0
        val colourCount: Double = colours.length
        for(colour <- colours) {
            redSum += colour.getRed
            greenSum += colour.getGreen
            blueSum += colour.getBlue
        }
        val redAvg: Int = (redSum / colourCount).toInt
        val greenAvg: Int = (greenSum / colourCount).toInt
        val blueAvg: Int = (blueSum / colourCount).toInt
        new Color(redAvg, greenAvg, blueAvg)
    }
}