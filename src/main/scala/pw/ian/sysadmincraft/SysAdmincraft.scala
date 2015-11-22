package pw.ian.sysadmincraft

import pw.ian.sysadmincraft.commands.{TopCommand, PgrepCommand}
import pw.ian.sysadmincraft.listeners.{KillListener, JoinListener}

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
    getServer.getPluginManager.registerEvents(new KillListener(this), this)
    getCommand("top").setExecutor(TopCommand(this))
    getCommand("pgrep").setExecutor(PgrepCommand(this))
    new PillarUpdateTask(this).runTaskTimer(this, 100L, 100L)
  }

  override def onDisable() = {
    getLogger.info("Deleting world sysadmincraft...")

    // Delete the world
    "rm -rf sysadmincraft/" !

    getLogger.info("World deleted.")
  }

}
