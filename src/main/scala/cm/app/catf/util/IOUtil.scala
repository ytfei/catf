package cm.app.catf.util

import java.io._

/**
 * Created by eyang on 3/5/14.
 */
object IOUtil {
  def withOutputStream(path: String, f: (DataOutputStream) => Unit) = {
    val file = new File(path)
    val out = new DataOutputStream(new FileOutputStream(file))
    try {
      f(out)
    } finally {
      out.flush()
      out.close()
    }
  }

  def withPrintWriter(path: String, f: (PrintWriter) => Unit) = {
    val file = new File(path)
    val out = new PrintWriter(new FileOutputStream(file))
    try {
      f(out)
    } finally {
      out.flush()
      out.close()
    }
  }

}
