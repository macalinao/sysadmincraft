package pw.ian.sysadmincraft.system

import pw.ian.sysadmincraft.world.WorldConstants

import sys.process._

case class SysProcess(ids: Set[Int], realMemory: Long, virtualMemory: Long, name: String) {

  def totalMemory = realMemory + virtualMemory

  def memAmt: Double = Math.min(totalMemory.toDouble / WorldConstants.MAX_MEMORY, 1)

  def kill() = {
    // ids.foreach(x => s"kill -9 $x" !)
  }

}

object ProcessAdmin {

  def processes(): List[SysProcess] = {
    findUserProcesses().map { case(key, value) =>
      SysProcess(value._1, value._2, value._3, key)
    }.toList
  }

  private def findUserProcesses(): Map[String, (Set[Int], Long, Long)] = {
    val rawProcessOutput = "ps axco user,pid,rss,vsz,command" !!

    rawProcessOutput.split('\n').tail.map(_.split(' ').filter(!_.isEmpty))
      .filter(!_(0).startsWith("root"))
      .filter(!_(0).startsWith("_"))
      .map(_.tail)
      .map(x => x(3) -> Array(x(0), x(1), x(2)))
      .groupBy(_._1)
      .mapValues(_.map(_._2))
      .mapValues(_.map(x => (x(0).toInt, x(1).toLong, x(2).toLong / 1024)))
      .mapValues {
        _.foldLeft((Set[Int](), 0L, 0L)) { (acc, v) => (acc._1 + v._1, acc._2 + v._2, acc._3 + v._3) }
      }
  }


}
