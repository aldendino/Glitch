import java.io.File
import javax.swing.JOptionPane

object Glitch {
    def main(args: Array[String]) {

        //glitch2(args, avg)
        glitch1(args, dig)

        //val pixels = IO.importImage(in)
        
        //var transforms = Array(Pixel.shift _, Pixel.slide _)
        //var transforms = Array(Pixel.slide _)
        
        //transforms.map(func => pixels = func(pixels))

        //for(perm <- Colour.intPerms) {
        //    val pixelPerm = Pixel.perms(pixels, perm.toVector)
        //    IO.exportImage(out + perm.toString + "." + fileExt, pixelPerm, fileExt)
        //}

        //IO.exportImage(out, pixels, fileExt)
    }

    def glitch1(args: Array[String], func: (String, String) => Unit) {
        val message = "usage: scala Glitch <path to image> <path to output dir>"
        IO.checkArgs(args, 2, message)

        val in: String = args(0)
        val out: String = args(1)

        IO.checkFile(in)
        IO.checkDir(out)

        func(in, out)
    }

    def glitch2(args: Array[String], func: (String, String, String) => Unit) {
        val message = "usage: scala Glitch <path to image1> <path to image2> <path to output dir>"
        IO.checkArgs(args, 3, message)

        val in1: String = args(0)
        val in2: String = args(1)
        val out: String = args(2)

        IO.checkFile(in1)
        IO.checkFile(in2)
        IO.checkDir(out)

        func(in1, in2, out)
    }

    def avg(in1: String, in2: String, out: String) {
        val pixels1 = IO.importImage(in1)
        val pixels2 = IO.importImage(in2)

        val pixel3 = Pixel.average(pixels1, pixels2)

        //val path1 = new Path(in1)
        //val path2 = new Path(in2)
        val out3 = PathUtils.appendFileToPath(out, "out.jpg")

        IO.exportImage(out3, pixel3, "jpg")
    }

    def dig(in: String, out: String): Unit = {
        println(in + ":" + out)
        val pixels = IO.importImage(in)
        val pixelsOut = Pixel.digitize(pixels, 25, 25, 0.25)
        val filename: String = JOptionPane.showInputDialog(null, "Enter the name of the output file", "Output file", JOptionPane.PLAIN_MESSAGE, null, null, "out.jpg") match {
            case string: String => string
            case _ => null
        }
        if(filename != null) {
            println(filename)
            val stringOut = PathUtils.appendFileToPath(out, filename)
            var result = JOptionPane.YES_OPTION
            if(new File(stringOut).isFile) {
                result = JOptionPane.showConfirmDialog(null, "Would you like to overwrite \"" + stringOut + "\"?", "File already exists!", JOptionPane.YES_NO_OPTION)
            }
            if(result == JOptionPane.YES_OPTION) {
                IO.exportImage(stringOut, pixelsOut, "jpg")
            }
        }
    }
}

