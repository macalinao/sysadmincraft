package pw.ian.sysadmincraft.world

import org.bukkit.Material
import org.bukkit.block.Block
import pw.ian.sysadmincraft.Process

case class ProcessPillar(base: Block, var process: Process) {

  var height = 0

  def init = update(process)

  def update(process: Process) = {
    assert(this.process.id == process.id)
    this.process = process
  }

  private def form(height: Int): Unit = {
    if (height == 0) {
      formInitial()
      return
    }
  }

  private def formInitial(): Unit = {
  }

}

object PillarUtil {

  def construct(base: Block, startHeight: Int, endHeight: Int, blockType: Material): Unit =
    blocks(base, startHeight, endHeight).foreach(_.setType(blockType))

  def destruct(base: Block, startHeight: Int, endHeight: Int): Unit =
    blocks(base, startHeight, endHeight).foreach(_.setType(Material.AIR))

  def blocks(base: Block, startHeight: Int, endHeight: Int): IndexedSeq[Block] =
    for {
      level <- startHeight to endHeight
      x <- base.getX to base.getX + 4
      z <- base.getZ to base.getZ + 4
    } yield base.getWorld.getBlockAt(x, level + 64, z)

}
