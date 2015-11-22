package pw.ian.sysadmincraft.system

import sys.process._

object SystemOverview {

  def uname(): String = {
    "uname -a" !!
  }

  def uptime(): String = {
    "uptime" !!
  }

  def totalMem(): Int = {
    (("ps caxm -orss" !!).split("\n").tail.map(_.trim.toLong).sum / 1024).toInt
  }

}
