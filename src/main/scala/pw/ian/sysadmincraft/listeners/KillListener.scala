package pw.ian.sysadmincraft.listeners

import org.bukkit.event.{EventHandler, Listener}
import org.bukkit.event.entity.EntityDeathEvent
import pw.ian.sysadmincraft.SysAdmincraft

case class KillListener(plugin: SysAdmincraft) extends Listener {

  @EventHandler
  def onEntityDeath(event: EntityDeathEvent): Unit = {
    plugin.pillarManager.handleDeath(event.getEntity.getUniqueId)
  }
}
