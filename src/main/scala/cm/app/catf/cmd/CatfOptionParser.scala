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
  // opt[String]('d', "demo").action((x, c) => c.copy(demo = x))

  version("version").text("show current version")

  help("help").text("prints this usage text")

  /**
   * option for command stat
   */
  cmd(TimeStat.cmdName)
    .text("stat the logcat")
    .action((_, c) => c.copy(cmd = TimeStat.cmdName)).children(
      arg[String]("<file>").text("data file to be analyzed").action((x, c) => c.copy(file = x))
    )

  /**
   * option for command stat
   */
  cmd(TestResultParser.cmdName)
    .text("parse the test result")
    .action((_, c) => c.copy(cmd = TestResultParser.cmdName)).children(
      opt[String]('f', "logcat").text("logcat file").action((x, c) => c.copy(logcat = x)).required(),
      opt[String]('o', "output").text("Output file name").action((x, c) => c.copy(output = x)).optional(),
      arg[String]("<file>").text("junit test output (xml)").action((x, c) => c.copy(file = x))
    )

  /**
   * option for command split
   */
  cmd(LogSplit.cmdName)
    .text("filter out log data with the specific regex.")
    .action((_, c) => c.copy(cmd = LogSplit.cmdName)).children(
      opt[String]('r', "regex").text("REGEX to filter out lines").action((x, c) => c.copy(regex = x)).required(),
      opt[String]('o', "output").text("Output file name").action((x, c) => c.copy(output = x)).optional(),

      arg[String]("<file>").text("data file to be analyzed").action((x, c) => c.copy(file = x))
    )

  checkConfig(c => if (c.cmd.size > 0) success else failure("No command specified."))

  override def showUsageOnError = true
}

case class Config(cmd: String = "",
                  file: String = "",
                  regex: String = "",
                  logcat: String = "",
                  output: String = "")