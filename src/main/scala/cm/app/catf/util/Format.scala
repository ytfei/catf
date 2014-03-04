package cm.app.catf.util

/**
 * Created by eyang on 3/4/14.
 */
object Format {
  implicit def millisToReadableTime(millis: Long): String = {
    val mi = millis % 1000

    val totalSec = millis / 1000
    val s = totalSec % 60

    val totalMin = totalSec / 60
    val m = totalMin % 60

    val h = totalMin / 60

    "%d:%d:%d.%d".format(h, m, s, mi)
  }
}
