package pw.ian.sysadmincraft.world

import org.bukkit.Material
import org.bukkit.block.{Sign, Block}
import org.bukkit.entity.{EntityType, LivingEntity}
import pw.ian.sysadmincraft.system.SysProcess
import pw.ian.sysadmincraft.world.WorldConstants._

case class ProcessPillar(index: Int, base: Block, var process: SysProcess) {

  var height = 0
  update(process)
  setupMob()

  def update(process: SysProcess) = {
    assert(this.process.name == process.name)
    val newHeight = memToHeight(process.totalMemory)
    if (newHeight > height) {
      construct(height + 1, newHeight, Material.GOLD_BLOCK)
    } else {
      destruct(newHeight + 1, height)
    }

    for {
      x <- 0 until PILLAR_WIDTH
      y <- 0 to 2
      z <- 0 until PILLAR_WIDTH
    } base.getRelative(x, y, z).setType(Material.AIR)

    setupFence()
    updateStats()
    this.process = process
    this.height = newHeight
  }

  def destroy() = {
    destruct(0, height)
    base.getRelative(0, 2, -1).setType(Material.AIR)
    kill()
  }

  def kill() = {
    process.kill()
  }

  private def memToHeight(memoryUsage: Long) = {
    Math.min(WorldConstants.MAX_HEIGHT,
      ((memoryUsage.toDouble / MAX_MEMORY) * MAX_HEIGHT).toInt)
  }

  private def updateStats(): Unit = {
    val block = base.getRelative(0, 2, -1)
    block.setType(Material.WALL_SIGN)
    val sign = block.getState.asInstanceOf[Sign]
    sign.setLine(0, process.name)
    sign.setLine(1, "Real: " + process.realMemory)
    sign.setLine(2, "Virtual: " + process.virtualMemory)
    sign.setLine(3, "Count: " + process.ids.size)
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
  private def setupMob() = {
    val entity = base.getWorld.spawnEntity(base.getLocation.add(PILLAR_WIDTH / 2, -MOB_HOUSE_DEPTH, PILLAR_WIDTH / 2), process.memAmt match {
      case x if x <= 0.2 => EntityType.CHICKEN
      case x if x <= 0.4 => EntityType.PIG
      case x if x <= 0.6 => EntityType.ZOMBIE
      case x if x <= 0.8 => EntityType.SPIDER
      case _ => EntityType.BLAZE
    }).asInstanceOf[LivingEntity]
    entity.setCustomName(process.name)
    entity.setCustomNameVisible(true)
  }

  /**
   * The fence replaces the base of the tower with air and a fence
   */
  private def setupFence(): Unit = {
    //set all of the blocks in bottom 4 rows of the pillar to glass
    for {
      x <- 0 until PILLAR_WIDTH
      y <- 0 until MOB_HOUSE_HEIGHT
      z <- 0 until PILLAR_WIDTH
    } base.getRelative(x, y, z).setType(Material.GLASS)

    //then make a hole inside those blocks to house the mobs
    for {
      x <- 1 until PILLAR_WIDTH - 1
      y <- -MOB_HOUSE_DEPTH until MOB_HOUSE_HEIGHT // dig underground
      z <- 1 until PILLAR_WIDTH - 1
    } base.getRelative(x, y, z).setType(Material.AIR)

    // hole
    for {
      y <- 0 until MOB_HOUSE_HEIGHT - 1
      z <- 1 until PILLAR_WIDTH - 1
    } base.getRelative(0, y, z).setType(Material.GLASS)
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
