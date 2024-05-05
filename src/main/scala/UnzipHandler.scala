package opheliaindigo.abletonproject.ingest;

import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.OutputStream
import java.io.File
import java.util.zip.GZIPInputStream

object UnzipHandler:
  val BUFFERSIZE = 4096
  val buffer = new Array[Byte](BUFFERSIZE)

  def unZip(source: String, target: String) =
    FileIsExist(source) {
      val zipFileIS = new FileInputStream(source)
      val gzipFileIS = new GZIPInputStream(zipFileIS)

      val fos = new FileOutputStream(target)
      saveFile(gzipFileIS, fos)

    }

  def FileIsExist(path: String)(expr: => Any) =
    if (new File(path).exists)
      expr

  def saveFile(fis: GZIPInputStream, fos: OutputStream) =
    def bufferReader(fis: GZIPInputStream)(buffer: Array[Byte]) =
      (fis.read(buffer), buffer)

    def writeToFile(
        reader: (Array[Byte]) => Tuple2[Int, Array[Byte]],
        fos: OutputStream
    ): Boolean =
      val (length, data) = reader(buffer)
      if (length >= 0)
        fos.write(data, 0, length)
        writeToFile(reader, fos)
      else true

    try writeToFile(bufferReader(fis), fos)
    finally
      fis.close
      fos.close
