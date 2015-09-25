import java.io.File

object Glitch {
    def main(args: Array[String]) {

        if(args.length != 2) {
            val message = "usage: scala Glitch <path to image> <path to output dir>"
            throw new IllegalArgumentException(message)
        }
        
        //val imageTypes: Map[Symbol, String] = Map('JPG -> "jpg", 'PNG -> "png")
        
        val in: String = args(0)
        val out: String = args(1)

        if(in.isEmpty) {
            val message = "Empty input path"
            throw new IllegalArgumentException(message)
        }

        if(out.isEmpty) {
            val message = "Empty output path"
            throw new IllegalArgumentException(message)
        }

        val inPathSplit = in.split(File.separator)
        val fileName = inPathSplit(inPathSplit.size - 1)
        val fileNameSplit = fileName.split('.')
        val fileExt = fileNameSplit(fileNameSplit.length - 1)

        val pixels = IO.importImage(in)
        
        //var transforms = Array(Pixel.shift _, Pixel.slide _)
        //var transforms = Array(Pixel.slide _)
        
        //transforms.map(func => pixels = func(pixels))

        for(perm <- Colour.intPerms) {
            val pixelPerm = Pixel.perms(pixels, perm.toVector)
            IO.exportImage(out + perm.toString + "." + fileExt, pixelPerm, fileExt)
        }
        
        //ImageArray.exportImage(out, pixels, imageTypes('JPG))
    }
}

