package pw.ian.sysadmincraft

case class Process(id: Int, memoryUsage: Long)

object ProcessAdmin {

  def processes: Map[Int, Process] = {
    Map()
  }

  def kill(process: Int) = {

  }

}
