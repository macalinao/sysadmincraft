package pw.ian.sysadmincraft.system

import sys.process._

case class SysProcess(ids: Set[Int], realMemory: Long, virtualMemory: Long, name: String) {

  def totalMemory = realMemory + virtualMemory

  def kill = ids.foreach(ProcessAdmin.kill)

}

object ProcessAdmin {

  def processes: Map[Int, SysProcess] = {
    findUserProcesses.map { x =>
        x(0).toInt -> SysProcess(
          Set(x(0).toInt), x(1).toLong, x(2).toLong, x(3).toString
        )
    }.toMap
  }

  def kill(processId: Int) = {

  }

  private def findUserProcesses : Array[Array[String]] = {
    val rawProcessOutput = "ps axco user,pid,rss,vsz,command" !!

    rawProcessOutput.split('\n').tail.map(_.split(' ').filter(!_.isEmpty))
      .filter(!_ (0).startsWith("root"))
      .filter(!_ (0).startsWith("_"))
      .map(_.tail)
  }
}
