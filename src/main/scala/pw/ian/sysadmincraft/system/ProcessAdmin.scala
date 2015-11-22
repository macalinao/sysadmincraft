package pw.ian.sysadmincraft.system

import pw.ian.sysadmincraft.world.WorldConstants

import sys.process._

case class SysProcess(ids: Set[Int], realMemory: Long, virtualMemory: Long, name: String) {

  def totalMemory = realMemory + virtualMemory

  def memAmt: Double = Math.min(totalMemory / WorldConstants.MAX_MEMORY, 1)

  def kill() = {
    // ids.foreach(x => s"kill -9 $x" !)
  }

}

object ProcessAdmin {

  def processes: Map[Int, SysProcess] = {
    findUserProcesses.map { x =>
        x(0).toInt -> SysProcess(
          Set(x(0).toInt), x(1).toLong, x(2).toLong, x(3).toString
        )
    }.toMap
  }

  private def findUserProcesses : Array[Array[String]] = {
    val rawProcessOutput = "ps axco user,pid,rss,vsz,command" !!

    rawProcessOutput.split('\n').tail.map(_.split(' ').filter(!_.isEmpty))
      .filter(!_ (0).startsWith("root"))
      .filter(!_ (0).startsWith("_"))
      .map(_.tail)
  }
}
