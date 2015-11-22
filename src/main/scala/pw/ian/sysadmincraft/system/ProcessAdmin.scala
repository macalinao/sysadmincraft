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

  def processes: Map[String, SysProcess] = {
    findUserProcesses.map { case(key, value) =>
        key -> SysProcess(
          value._1, value._2, value._3, key
        )
    }
  }

  private def findUserProcesses : Map[String, (Set[Int], Long, Long)] = {
    val rawProcessOutput = "ps axco user,pid,rss,vsz,command" !!

    rawProcessOutput.split('\n').tail.map(_.split(' ').filter(!_.isEmpty))
      .filter(!_(0).startsWith("root"))
      .filter(!_(0).startsWith("_"))
      .map(_.tail)
      .map(x => x(3) -> Array(x(0), x(1), x(2)))
      .groupBy(_._1)
      .map { case(key, value) => key -> value.map(_._2) }

      .map { case(key, value) => key -> value.map {x => (x(0).toInt, x(1).toLong, x(2).toLong / 1024) }}
      .map { case(key, value) => key -> value.foldLeft((Set[Int](), 0L, 0L)) {
        (acc, v) => (acc._1 + v._1, acc._2 + v._2, acc._3 + v._3) }
      }
//      .map { case(key, value) => key -> (value(0).reduce{ (acc, v) => Set(acc) union Set(v)} -> value.reduce{ (acc, v) => Array(acc(1) + v(1), acc(2) + v(2))} )}
//      .map { case(key, value) => key -> value.reduce { (acc, v) => Array(Set(acc(0)) union Set(v(0)), acc(1) + v(1), acc(2) + v(2)) }}
  }


}
