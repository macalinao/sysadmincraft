package pw.ian.sysadmincraft

import org.bukkit.{Location, GameMode}
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.{EventHandler, Listener}
import pw.ian.sysadmincraft.world.WorldConstants._

case class JoinListener(plugin: SysAdmincraft) extends Listener {

  @EventHandler
  def onPlayerJoin(event: PlayerJoinEvent): Unit = {
    event.getPlayer.setGameMode(GameMode.CREATIVE)
    event.getPlayer.teleport(new Location(plugin.world, 0, START_HEIGHT + 1, 0))
  }

}
