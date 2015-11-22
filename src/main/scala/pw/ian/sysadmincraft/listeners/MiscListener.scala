package pw.ian.sysadmincraft.listeners

import org.bukkit.entity.EntityType
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.{EventHandler, Listener}
import org.bukkit.inventory.ItemStack
import org.bukkit.{Material, GameMode, Location}
import pw.ian.sysadmincraft.SysAdmincraft
import pw.ian.sysadmincraft.world.WorldConstants._

case class MiscListener(plugin: SysAdmincraft) extends Listener {

  @EventHandler
  def onPlayerJoin(event: PlayerJoinEvent): Unit = {
    event.getPlayer.setGameMode(GameMode.CREATIVE)
    event.getPlayer.teleport(new Location(plugin.world, 0, START_HEIGHT + 1, 0))
    event.getPlayer.getInventory.addItem(new ItemStack(Material.IRON_SWORD, 1))
  }

  @EventHandler
  def onBlockBreak(event: BlockBreakEvent): Unit = {
    event.setCancelled(true)
  }

  @EventHandler
  def onCreatureSpawn(event: CreatureSpawnEvent): Unit = {
    if (event.getSpawnReason == SpawnReason.NATURAL || event.getEntityType == EntityType.SLIME) {
      event.setCancelled(true)
    }
  }

}
