package pw.ian.sysadmincraft.listeners

import org.bukkit.entity.LivingEntity
import org.bukkit.event.{EventHandler, Listener}
import org.bukkit.event.entity.{EntityDamageEvent, EntityDeathEvent}
import pw.ian.sysadmincraft.SysAdmincraft

case class KillListener(plugin: SysAdmincraft) extends Listener {

  @EventHandler
  def onEntityDeath(event: EntityDeathEvent): Unit = {
    plugin.pillarManager.handleDeath(event.getEntity.getCustomName)
  }

  @EventHandler
  def onEntityDamage(event: EntityDamageEvent): Unit = {
    if (event.getCause != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
      event.setCancelled(true)
    }
  }
}
