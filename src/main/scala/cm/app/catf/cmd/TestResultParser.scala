package cm.app.catf.cmd

import scala.xml._

/**
 * Created by eyang on 3/4/14.
 */
class TestResultParser extends Command {
  override def exec(conf: Config): Unit = {
    val data = XML.loadFile(conf.file)

    val cases = (data \\ "testcase").map(e => {
      val time = e.attribute("time").map(_.text.toDouble)
      val f = (e \ "failure").map(f => {
        Failure((f \ "@message").text, (f \ "@type").text, f.text)
      })

      TestCase((e \ "@name").text, (e \ "@classname").text, time, if (f.size > 0) Some(f.head) else None)
    })

    var i = 0
    for (c <- cases) {
      if (c.failure.isDefined) {
        i += 1

        println(s"TestCase: ${c.name} \t ${c.clazz}")

        c.failure.foreach(f => println(f.desc))

        println
      }
    }

    println(s"Total failures: ${i}")

    for (c <- cases) {
      println(s"TestCase: ${c.name} \t ${c.clazz}\t is failed: ${c.failure.isDefined}")
      println
    }
    println(s"Total cases: ${cases.size}")


    for (c <- cases) {
      val result = if (c.failure.isDefined) "Failed" else c.time.get
      println(s"${c.name}\t${result}")
    }
  }

  private case class TestCase(name: String, clazz: String, time: Option[Double] = None, failure: Option[Failure] = None)

  private case class Failure(message: String, typeStr: String, desc: String)

  override val name: String = TestResultParser.name
}

object TestResultParser {
  val name = "res"
}

