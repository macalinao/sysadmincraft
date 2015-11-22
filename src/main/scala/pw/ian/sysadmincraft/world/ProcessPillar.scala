package pw.ian.sysadmincraft.world

import org.bukkit.Material
import org.bukkit.block.{Sign, Block}
import org.bukkit.entity.LivingEntity
import pw.ian.sysadmincraft.system.SysProcess
import pw.ian.sysadmincraft.world.WorldConstants._

case class ProcessPillar(index: Int, base: Block, var process: SysProcess) {

  var height = 0
  update(process)

  def update(process: SysProcess) = {
    assert(this.process.name == process.name)
    val newHeight = memToHeight(process.totalMemory)
    if (newHeight > height) {
      construct(height + 1, newHeight, Material.GOLD_BLOCK)
    } else {
      destruct(newHeight + 1, height)
    }
    updateStats()
    this.process = process
    this.height = newHeight
  }

  def kill() = process.kill()

  private def memToHeight(memoryUsage: Long) = {
    Math.max(WorldConstants.MAX_HEIGHT,
      ((memoryUsage.toDouble / MAX_MEMORY) * MAX_HEIGHT).toInt)
  }

  private def updateStats(): Unit = {
    val block = base.getRelative(1, 2, -1)
    block.setType(Material.SIGN)
    val sign = block.getState.asInstanceOf[Sign]
    sign.setLine(0, process.name)
    sign.update()
  }

  /**
   * Spawns a mob that represents this process
   *
   * Should be:
   * - have name of process as name
   * - be a different mob depending on memory size
   *
   * @return the entity
   */
  private def setupMob(): LivingEntity = {
    return null // cyrus
  }

  /**
   * The fence replaces the base of the tower with air and a fence
   */
  private def setupFence(): Unit = {
    //set all of the blocks in bottom 4 rows of the pillar to glass
    for{
      z <- 0 until 4
      x <- 0 until PILLAR_WIDTH
      y <- 0 until PILLAR_WIDTH
    } base.getRelative(x, y, z).setType(Material.GLASS)

    //then make a hole inside those blocks to house the mobs
    for{
      z <- 0 until 4
      x <- 1 until PILLAR_WIDTH - 1
      y <- 0 until PILLAR_WIDTH - 1
    } base.getRelative(x, y, z).setType(Material.AIR)
    return null
  }

  private def construct(startHeight: Int, endHeight: Int, blockType: Material): Unit =
    blocks(startHeight, endHeight).foreach(_.setType(blockType))

  private def destruct(startHeight: Int, endHeight: Int): Unit =
    blocks(startHeight, endHeight).foreach(_.setType(Material.AIR))

  private def blocks(startHeight: Int, endHeight: Int): IndexedSeq[Block] =
    for {
      level <- startHeight to endHeight
      x <- base.getX until base.getX + PILLAR_WIDTH
      z <- base.getZ until base.getZ + PILLAR_WIDTH
    } yield base.getWorld.getBlockAt(x, level + START_HEIGHT, z)

}
