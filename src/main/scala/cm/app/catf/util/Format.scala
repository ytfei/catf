package cm.app.catf.util

import java.text.SimpleDateFormat
import java.sql.Date

/**
 * Created by eyang on 3/4/14.
 */
object Format {
  implicit def millisToReadableTime(millis: Long): String =
    if (millis <= 0)
      "-1"
    else {
      val mi = millis % 1000

      val totalSec = millis / 1000
      val s = totalSec % 60

      val totalMin = totalSec / 60
      val m = totalMin % 60

      val h = totalMin / 60

      "%d:%d:%d.%d".format(h, m, s, mi)
    }

  def millisToFileTag(mills: Long): String = {
    val format = new SimpleDateFormat("yyyyMMdd_hhmmss")
    format.format(new Date(mills))
  }
}
