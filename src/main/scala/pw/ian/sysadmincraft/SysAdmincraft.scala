package pw.ian.sysadmincraft

import scala.sys.process._
import org.bukkit.World
import org.bukkit.plugin.java.JavaPlugin
import pw.ian.sysadmincraft.world.{PillarUpdateTask, PillarManager, PillarWorldCreator}

class SysAdmincraft extends JavaPlugin {

  var world: World = null

  var pillarManager: PillarManager = null

  override def onEnable() = {
    world = PillarWorldCreator.create("sysadmincraft")
    pillarManager = PillarManager(this, world)
    pillarManager.initPillars()
    getServer.getPluginManager.registerEvents(new JoinListener(this), this)
    new PillarUpdateTask(this).runTaskTimer(this, 5000L, 5000L)
  }

  override def onDisable() = {
    getLogger.info("Deleting world sysadmincraft...")

    // Delete the world
    "rm -rf sysadmincraft/" !

    getLogger.info("World deleted.")
  }

}
