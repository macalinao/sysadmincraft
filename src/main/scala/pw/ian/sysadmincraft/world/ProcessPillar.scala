package pw.ian.sysadmincraft.world

import org.bukkit.Material
import org.bukkit.block.{Sign, Block}
import org.bukkit.entity.{EntityType, LivingEntity}
import pw.ian.sysadmincraft.system.SysProcess
import pw.ian.sysadmincraft.world.WorldConstants._

case class ProcessPillar(index: Int, base: Block, var process: SysProcess) {

  var height = 0
  update(process)
  val mob = setupMob()

  def update(process: SysProcess) = {
    assert(this.process.name == process.name)
    val newHeight = memToHeight(process.totalMemory)
    if (newHeight > height) {
      construct(height + 1, newHeight, Material.GOLD_BLOCK)
    } else {
      destruct(newHeight + 1, height)
    }
    destroyCpu()
    buildCpu(cpuToHeight(process.cpuPct))
    setupFence()
    updateStats()
    this.process = process
    this.height = newHeight
  }

  def teardown() = {
    destruct(0, height)
    destroyCpu()
    clearBase()
    base.getRelative(0, 2, -1).setType(Material.AIR)
  }

  def kill() = {
    mob.remove()
    process.kill()
  }

  def location = {
    val location = base.getLocation.add(PILLAR_WIDTH / 2, 0, -2)
    location.setPitch(0f)
    location.setYaw(0f)
    location
  }

  private def memToHeight(memoryUsage: Long) = {
    Math.min(WorldConstants.MAX_HEIGHT,
      ((memoryUsage.toDouble / MAX_MEMORY) * MAX_HEIGHT).toInt)
  }

  private def cpuToHeight(cpuPct: Double): Int = {
    (WorldConstants.MAX_HEIGHT * cpuPct / 100d).toInt
  }

  private def updateStats(): Unit = {
    val leftBlock = base.getRelative(PILLAR_WIDTH - 1, 2, -1)
    if (leftBlock.getType != Material.WALL_SIGN) {
      leftBlock.setType(Material.WALL_SIGN)
    }
    val leftSign = leftBlock.getState.asInstanceOf[Sign]
    leftSign.setLine(0, process.name)
    leftSign.setLine(1, "Real: " + process.realMemory)
    leftSign.setLine(2, "Virtual: " + process.virtualMemory)
    leftSign.setLine(3, "Count: " + process.ids.size)
    leftSign.update(true)
    val rightBlock = base.getRelative(0, 2, -1)
    if (rightBlock.getType != Material.WALL_SIGN) {
      rightBlock.setType(Material.WALL_SIGN)
    }
    val rightSign = rightBlock.getState.asInstanceOf[Sign]
    rightSign.setLine(0, f"CPU %%: ${process.cpuPct}%.2f")
    rightSign.setLine(1, f"MEM %%: ${process.memPct}%.2f")
    rightSign.setLine(2, s"Stat: ${process.stat}")
    rightSign.setLine(3, s"Time: ${process.time}")
    rightSign.update(true)
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
    entity
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

    // Bottom hole
    for {
      x <- 0 until PILLAR_WIDTH
      y <- -MOB_HOUSE_DEPTH until 0 // dig underground
      z <- 0 until PILLAR_WIDTH
    } base.getRelative(x, y, z).setType(Material.AIR)

    // Top hole
    for {
      x <- 1 until PILLAR_WIDTH - 1
      y <- 0 until MOB_HOUSE_HEIGHT - 1
      z <- 0 until PILLAR_WIDTH - 1
    } base.getRelative(x, y, z).setType(Material.AIR)
  }

  private def clearBase(): Unit = {

    //set all of the blocks in bottom 4 rows of the pillar to air
    for {
      x <- 0 until PILLAR_WIDTH
      y <- 0 until MOB_HOUSE_HEIGHT
      z <- 0 until PILLAR_WIDTH
    } base.getRelative(x, y, z).setType(Material.AIR)

    // Bottom hole
    for {
      x <- 0 until PILLAR_WIDTH
      y <- -MOB_HOUSE_DEPTH until 0 // dig underground
      z <- 0 until PILLAR_WIDTH
    } base.getRelative(x, y, z).setType(Material.GRASS)

  }

  private def construct(startHeight: Int, endHeight: Int, blockType: Material): Unit =
    blocks(startHeight, endHeight).foreach(_.setType(blockType))

  private def destruct(startHeight: Int, endHeight: Int): Unit =
    blocks(startHeight, endHeight).foreach(_.setType(Material.AIR))

  private def destroyCpu() = {
    for {
      x <- List(0, 3)
      y <- 0 until MAX_HEIGHT
      z <- List(0, 3)
    } base.getRelative(x, y, z).setType(Material.AIR)
  }

  private def buildCpu(height: Int) = {
    for {
      x <- List(0, 3)
      y <- 0 until height
      z <- List(0, 3)
    } base.getRelative(x, y, z).setType(Material.DIAMOND_BLOCK)
  }

  private def blocks(startHeight: Int, endHeight: Int): IndexedSeq[Block] =
    for {
      level <- startHeight to endHeight
      x <- base.getX + PILLAR_PADDING until base.getX + PILLAR_WIDTH - PILLAR_PADDING
      z <- base.getZ + PILLAR_PADDING until base.getZ + PILLAR_WIDTH - PILLAR_PADDING
    } yield base.getWorld.getBlockAt(x, level + START_HEIGHT + MOB_HOUSE_HEIGHT - 1, z)

}
