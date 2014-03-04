package cm.app.catf.util

import org.slf4j.{Marker, Logger, LoggerFactory}

/**
 * Created by eyang on 3/4/14.
 */
trait InternalLogger {
  self =>

  lazy val log = LoggerFactory.getLogger(self.getClass)


  def error(msg: String, t: Throwable): Unit = log.error(msg, t)

  def error(format: String, arguments: AnyRef*): Unit = log.error(format, arguments)

  def error(format: String, arg1: scala.Any, arg2: scala.Any): Unit = log.error(format, arg1, arg2)

  def error(format: String, arg: scala.Any): Unit = log.error(format, arg)

  def error(msg: String): Unit = log.error(msg)

  def isErrorEnabled: Boolean = log.isErrorEnabled


  def warn(msg: String, t: Throwable): Unit = log.warn(msg, t)

  def warn(format: String, arg1: scala.Any, arg2: scala.Any): Unit = log.warn(format, arg1, arg2)

  def warn(format: String, arguments: AnyRef*): Unit = log.warn(format, arguments)

  def warn(format: String, arg: scala.Any): Unit = log.warn(format, arg)

  def warn(msg: String): Unit = log.warn(msg)

  def isWarnEnabled: Boolean = log.isWarnEnabled


  def info(msg: String, t: Throwable): Unit = log.info(msg, t)

  def info(format: String, arguments: AnyRef*): Unit = log.info(format, arguments)

  def info(format: String, arg1: scala.Any, arg2: scala.Any): Unit = log.info(format, arg1, arg2)

  def info(format: String, arg: scala.Any): Unit = log.info(format, arg)

  def info(msg: String): Unit = log.info(msg)

  def isInfoEnabled: Boolean = log.isInfoEnabled


  def debug(msg: String, t: Throwable): Unit = log.debug(msg, t)

  def debug(format: String, arguments: AnyRef*): Unit = log.debug(format, arguments)

  def debug(format: String, arg1: scala.Any, arg2: scala.Any): Unit = log.debug(format, arg1, arg2)

  def debug(format: String, arg: scala.Any): Unit = log.debug(format, arg)

  def debug(msg: String): Unit = log.debug(msg)

  def isDebugEnabled: Boolean = log.isDebugEnabled


  def trace(msg: String, t: Throwable): Unit = log.trace(msg, t)

  def trace(format: String, arguments: AnyRef*): Unit = log.trace(format, arguments)

  def trace(format: String, arg1: scala.Any, arg2: scala.Any): Unit = log.trace(format, arg1, arg2)

  def trace(format: String, arg: scala.Any): Unit = log.trace(format, arg)

  def trace(msg: String): Unit = log.trace(msg)

  def isTraceEnabled: Boolean = log.isTraceEnabled

  def getName: String = log.getName
}
