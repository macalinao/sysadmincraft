package pw.ian.sysadmincraft

import org.bukkit.plugin.java.JavaPlugin

class SysAdmincraft extends JavaPlugin {

  override def onEnable() = {
    getServer.getPluginManager.registerEvents(new PlayerListener(), this)
  }

  override def onDisable = {
  }

}
