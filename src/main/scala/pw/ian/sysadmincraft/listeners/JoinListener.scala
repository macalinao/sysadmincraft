package pw.ian.sysadmincraft.listeners

import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.{EventHandler, Listener}
import org.bukkit.inventory.ItemStack
import org.bukkit.{Material, GameMode, Location}
import pw.ian.sysadmincraft.SysAdmincraft
import pw.ian.sysadmincraft.world.WorldConstants._

case class JoinListener(plugin: SysAdmincraft) extends Listener {

  @EventHandler
  def onPlayerJoin(event: PlayerJoinEvent): Unit = {
    event.getPlayer.setGameMode(GameMode.CREATIVE)
    event.getPlayer.teleport(new Location(plugin.world, 0, START_HEIGHT + 1, 0))
    event.getPlayer.getInventory.addItem(new ItemStack(Material.IRON_SWORD, 1))
  }

}
