package cm.app.catf

import cm.app.catf.cmd._
import cm.app.catf.util.InternalLogger
import scala.Some
import cm.app.catf.cmd.Config

/**
 * Created by eyang on 3/4/14.
 */
object Main extends App with InternalLogger /*with Command*/ {

  val cmdStat = new TimeStat
  val cmdParseTestResult = new TestResultParser
  val cmdSplitLogcat = new LogSplit

  val cmdMap = Map[String, Command](
    cmdStat.name -> cmdStat,
    cmdParseTestResult.name -> cmdParseTestResult,
    cmdSplitLogcat.name -> cmdSplitLogcat
  )

  val parser = new CatfOptionParser
  val conf = parser.parse(args, Config())

  conf match {
    case Some(c) =>
      cmdMap.get(c.cmd) match {
        case Some(cmd) => cmd.exec(c)
        case None =>
          println("Unknown command {}", c.cmd)
          parser.showUsage
      }

    case None =>
    // println("Cannot extract the configuration!")
  }
}
