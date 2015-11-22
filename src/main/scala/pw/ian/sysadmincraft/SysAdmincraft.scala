package pw.ian.sysadmincraft

import scala.sys.process._
import org.bukkit.World
import org.bukkit.plugin.java.JavaPlugin
import pw.ian.sysadmincraft.world.{PillarManager, PillarWorldCreator}

class SysAdmincraft extends JavaPlugin {

  var world: World = null

  var pillarManager: PillarManager = null

  override def onEnable() = {
    getServer.getPluginManager.registerEvents(new PlayerListener(), this)
    world = PillarWorldCreator.create("sysadmincraft")
    pillarManager = PillarManager(this, world)
  }

  override def onDisable = {
    getLogger.info("Deleting world sysadmincraft...")

    // Delete the world
    "rm -rf sysadmincraft/" !

    getLogger.info("World deleted.")
  }

}
