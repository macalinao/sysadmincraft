package pw.ian.sysadmincraft.world

import org.bukkit.Material
import org.bukkit.block.{Sign, Block}
import pw.ian.sysadmincraft.Process
import pw.ian.sysadmincraft.world.WorldConstants._

case class ProcessPillar(base: Block, var process: Process) {

  var height = 0
  update(process)

  def update(process: Process) = {
    assert(this.process.id == process.id)
    val newHeight = memToHeight(process.memoryUsage)
    if (newHeight > height) {
      construct(height + 1, newHeight, Material.GOLD_BLOCK)
    } else {
      destruct(newHeight + 1, height)
    }
    updateStats
    this.process = process
    this.height = newHeight
  }

  private def memToHeight(memoryUsage: Long) = {
    Math.max(WorldConstants.MAX_HEIGHT,
      ((memoryUsage.toDouble / MAX_MEMORY) * MAX_HEIGHT).toInt)
  }

  private def updateStats: Unit = {
    val block = base.getRelative(1, 2, -1)
    block.setType(Material.SIGN)
    val sign = block.getState.asInstanceOf[Sign]
    // sign.setLine(0, process.name)
    sign.update()
  }

  private def construct(startHeight: Int, endHeight: Int, blockType: Material): Unit =
    PillarUtil.blocks(base, startHeight, endHeight).foreach(_.setType(blockType))

  private def destruct(startHeight: Int, endHeight: Int): Unit =
    PillarUtil.blocks(base, startHeight, endHeight).foreach(_.setType(Material.AIR))

}

object PillarUtil {

  def blocks(base: Block, startHeight: Int, endHeight: Int): IndexedSeq[Block] =
    for {
      level <- startHeight to endHeight
      x <- base.getX to base.getX + PILLAR_WIDTH
      z <- base.getZ to base.getZ + PILLAR_WIDTH
    } yield base.getWorld.getBlockAt(x, level + START_HEIGHT, z)

}
