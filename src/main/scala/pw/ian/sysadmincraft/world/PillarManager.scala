package pw.ian.sysadmincraft.world

import org.bukkit.World
import pw.ian.sysadmincraft.{ProcessAdmin, SysAdmincraft}
import pw.ian.sysadmincraft.world.WorldConstants._

case class PillarManager(plugin: SysAdmincraft, world: World) {

  val pillars = List[ProcessPillar]()

  def initPillars: List[ProcessPillar] = {
    ProcessAdmin.processes.values.zipWithIndex.map { case (process, index) =>
      ProcessPillar(blockFromIndex(index), process)
    }.toList
  }

  private def blockFromIndex(index: Int) = {
    val i = spiralIndex(index)
    world.getBlockAt(i._1 * PILLAR_DISTANCE + (PILLAR_DISTANCE / 2),
      START_HEIGHT, i._2 * PILLAR_DISTANCE + (PILLAR_DISTANCE / 2))
  }

  private def spiralIndex(n: Int): (Int, Int) = {
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