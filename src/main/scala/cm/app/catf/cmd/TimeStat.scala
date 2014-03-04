package cm.app.catf.cmd

/**
 * Created by eyang on 3/4/14.
 *
 *
$ egrep "Test (start|end) for" logcat.txt
03-03 10:29:49.830 I/System.out(13108): Test start for setupSharedTestFixture
03-03 10:30:48.850 I/System.out(13108): Test end for setupSharedTestFixture
 */

import scala.io._
import java.nio.charset.CodingErrorAction
import cm.app.catf.util.Format

class TimeStat extends Command {

  override val name: String = TimeStat.name

  // in case of malformed encoding
  implicit val codec = Codec("UTF-8")
  codec.onMalformedInput(CodingErrorAction.REPLACE)
  codec.onUnmappableCharacter(CodingErrorAction.REPLACE)

  def fromLogcat(path: String) = {
    val REGEX = """(\d{2}\-\d{2} \d{2}:\d{2}:\d{2}\.\d{3}).*Test (start|end) for (\w+)""".r

    var testMap = Map[String, TestTimeStat]()
    Source.fromFile(path).getLines().
      filter(REGEX.findFirstIn(_).isDefined).
      foreach(l => {

      val REGEX(dateStr, typeStr, name) = l

      val existTest = testMap.get(name)
      typeStr match {
        case "start" =>
          testMap += name -> existTest.fold(TestTimeStat(name, startAt = dateStr))(t => t.copy(startAt = dateStr))
        case "end" =>
          testMap += name -> existTest.fold(TestTimeStat(name, endAt = dateStr))(t => t.copy(endAt = dateStr))
      }

    })

    testMap.values.toSeq
  }

  private implicit def millisFromStr(dateStr: String): Long = {
    import java.text._

    val dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS")
    dateFormat.parse(dateStr).getTime
  }

  case class TestTimeStat(name: String, startAt: Long = 0L, endAt: Long = 0L)

  override def exec(conf: Config): Unit = {
    val r = fromLogcat(conf.file).sortBy(t => t.endAt - t.startAt)

    r.foreach(t => info("%s => %s".format(t.name, Format.millisToReadableTime(t.endAt - t.startAt))))
    info(Format.millisToReadableTime(r.map(t => t.endAt - t.startAt).sum))
  }
}

object TimeStat {
  val name = "stat"
}
