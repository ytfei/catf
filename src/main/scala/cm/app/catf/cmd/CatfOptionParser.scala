package cm.app.catf.cmd

import scopt.OptionParser

/**
 * Created by eyang on 3/4/14.
 */
class CatfOptionParser extends OptionParser[Config]("catf") {

  head("catf", "0.1")

  /**
   * common option
   */
  opt[String]('d', "demo").action((x, c) => c.copy(demo = x))

  version("version").text("show current version")

  help("help").text("prints this usage text")

  /**
   * option for command stat
   */
  cmd(TimeStat.name)
    .text("stat the logcat")
    .action((_, c) => c.copy(cmd = TimeStat.name)).children(
      opt[String]('f', "file").action((x, c) => c.copy(file = x))
    )

  /**
   * option for command stat
   */
  cmd(TestResultParser.name)
    .text("parse the test result")
    .action((_, c) => c.copy(cmd = TestResultParser.name)).children(
      opt[String]('f', "file").action((x, c) => c.copy(file = x))
    )

  checkConfig(c => if (c.cmd.size > 0) success else failure("No command specified."))

  override def showUsageOnError = true
}

/**
 * Tag trait
 */
case class Config(cmd: String = "", demo: String = "", file: String = "")