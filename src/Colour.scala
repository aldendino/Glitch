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
}