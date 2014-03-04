package cm.app.catf.cmd

import cm.app.catf.util.InternalLogger

/**
 * Created by eyang on 3/4/14.
 *
 * Command interface
 */
trait Command extends InternalLogger {

  def name: String

  def exec(conf: Config): Unit
}
