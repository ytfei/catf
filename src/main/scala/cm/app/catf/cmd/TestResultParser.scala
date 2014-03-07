package cm.app.catf.cmd

import scala.xml._
import cm.app.catf.util.Format

/**
 * Created by eyang on 3/4/14.
 */
class TestResultParser extends Command {

  import TestResultParser._
  import TimeStat.TestTimeStat

  override def exec(conf: Config): Unit = {
    val cases = parseTestResult(conf.file)
    val casesWithTime = TimeStat.fromLogcat(conf.logcat)

    val processed = cases.map(c => {
      val t = casesWithTime.get(c.name)
      t.fold(c)(x => c.copy(time = x.elapse / 1000))
    }).sortBy(_.time)

    for (c <- processed) {
      val result = if (c.failure.isDefined) "Failed" else "Passed"
      val time = (c.time * 1000).toLong
      println(s"${c.name}\t${Format.millisToReadableTime(time)}\t${result}")
    }
  }

  override val name: String = TestResultParser.cmdName
}

object TestResultParser {
  val cmdName = "res"

  def parseTestResult(path: String) = {
    val data = XML.loadFile(path)

    val cases = (data \\ "testcase").map(e => {
      val time = e.attribute("time").map(_.text.toDouble)
      val f = (e \ "failure").map(f => {
        Failure((f \ "@message").text, (f \ "@type").text, f.text)
      })

      TestCase((e \ "@name").text, (e \ "@classname").text, time.getOrElse(0.0), if (f.size > 0) Some(f.head) else None)
    })

    cases
  }

  case class TestCase(name: String, clazz: String, time: Double = 0.0, failure: Option[Failure] = None)

  case class Failure(message: String, typeStr: String, desc: String)

}

