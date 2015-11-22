package pw.ian.sysadmincraft.world


import java.util.UUID

import org.bukkit.World
import pw.ian.sysadmincraft.system.{SysProcess, ProcessAdmin}
import pw.ian.sysadmincraft.SysAdmincraft
import pw.ian.sysadmincraft.world.WorldConstants._

case class PillarManager(plugin: SysAdmincraft, world: World) {

  // process.name -> pillar
  var pillars = Map[String, ProcessPillar]()

  var taken = Set[Int]()

  def initPillars(): List[ProcessPillar] = {
    ProcessAdmin.processes().sortBy(-_.totalMemory).zipWithIndex.map { case (process, index) =>
      buildPillar(index, process)
    }
  }

  def refresh(processes: Iterable[SysProcess]) = {
    processes.foreach { process =>
      pillars.get(process.name) match {
        case Some(pillar) => pillar.update(process)
        case None => buildPillar(nextFreeIndex, process)
      }
    }
    // Destroy pillars that are missing
    (pillars.keySet &~ processes.map(_.name).toSet).foreach { name =>
      removePillar(pillars.get(name).get)
    }
  }

  def buildPillar(index: Int, process: SysProcess) = {
    val pillar = ProcessPillar(index, blockFromIndex(index), process)
    pillars += process.name -> pillar
    taken += index
    pillar
  }

  def handleDeath(name: String): Unit = {
    pillars.get(name) match {
      case Some(pillar) => destroyPillar(pillar)
      case None =>
    }
  }

  def destroyPillar(pillar: ProcessPillar) = {
    removePillar(pillar)
    pillar.kill()
    plugin.getServer.broadcastMessage(s"Process ${pillar.process.name} has been killed.")
  }

  def removePillar(pillar: ProcessPillar) = {
    taken -= pillar.index
    pillars -= pillar.process.name
    pillar.teardown()
  }

  private def nextFreeIndex: Int = Stream.from(0).find(!taken.contains(_)).get

  private def blockFromIndex(index: Int) = {
    val i = PillarManagerUtils.spiralIndex(index)
    world.getBlockAt(i._1 * PILLAR_DISTANCE + (PILLAR_DISTANCE / 2),
      START_HEIGHT, i._2 * PILLAR_DISTANCE + (PILLAR_DISTANCE / 2))
  }
}

object PillarManagerUtils {

  def spiralIndex(n: Int): (Int, Int) = {
    // given n an index in the squared spiral
    // p the sum of point in inner square
    // a the position on the current square
    // n = p + a
    val r = (Math.floor((Math.sqrt(n + 1) - 1) / 2) + 1).toInt

    // compute radius : inverse arithmetic sum of 8+16+24+...=
    val p = (8 * r * (r - 1)) / 2
    // compute total point on radius -1 : arithmetic sum of 8+16+24+...

    val en = r * 2
    // points by face

    val a = (1 + n - p) % (r * 8)
    // compute de position and shift it so the first is (-r,-r) but (-r+1,-r)
    // so square can connect

    val m = Math.floor(a / (r * 2)).toInt

    (
      m match {
        case 0 => a - r
        case 1 => r
        case 2 => r - (a % en)
        case 3 => -r
      },
      m match {
        case 0 => -r
        case 1 => (a % en) - r
        case 2 => r
        case 3 => r - (a % en)
      }
    )
  }


}