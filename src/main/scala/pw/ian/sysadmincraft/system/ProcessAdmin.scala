package pw.ian.sysadmincraft.system

import pw.ian.sysadmincraft.world.WorldConstants

import sys.process._

case class SysProcess(name: String, ids: Set[Int], realMemory: Long, virtualMemory: Long,
                      cpuPct: Double, memPct: Double, stat: String, time: String) {

  def totalMemory = realMemory + virtualMemory

  def memAmt: Double = Math.min(totalMemory.toDouble / WorldConstants.MAX_MEMORY, 1)

  def kill() = {
    ids.foreach(x => s"kill -9 $x" !)
  }

}

object ProcessAdmin {

  def processes(): List[SysProcess] = {
    findUserProcesses().map { case(key, value) =>
      SysProcess(key, value._1, value._2, value._3, value._4, value._5, value._6, value._7)
    }.toList
  }

  private def findUserProcesses(): Map[String, (Set[Int], Long, Long, Double, Double, String, String)] = {
    rawProcessOutput().split('\n').tail.map(_.split(' ').filter(!_.isEmpty))
      .filter(!_(0).startsWith("root"))
      .filter(!_(0).startsWith("_"))
      .map(_.tail)
      .map(x => x.slice(7, x.length).mkString(" ") -> (
        x(0).toInt, // pid
        x(1).toLong, // rss
        x(2).toLong / 1024, // vsz
        x(3).toDouble, // cpu
        x(4).toDouble, // mem
        x(5), // stat
        x(6) // time
      ))
      .groupBy(_._1) // Group by process name
      .mapValues(_.map(_._2)) //
      .mapValues {
        _.foldLeft((Set[Int](), 0L, 0L, 0d, 0d, "", "")) { (acc, v) =>
          (acc._1 + v._1, acc._2 + v._2, acc._3 + v._3,
          acc._4 + v._4, acc._5 + v._5, v._6, v._7)
        }
      }
  }

  private def rawProcessOutput(): String = {
    "ps axco user,pid,rss,vsz,%cpu,%mem,stat,time,command" !!
  }


}
