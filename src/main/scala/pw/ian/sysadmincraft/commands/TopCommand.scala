package pw.ian.sysadmincraft.commands

import org.bukkit.{Location, ChatColor}
import org.bukkit.command.{Command, CommandSender, CommandExecutor}
import org.bukkit.entity.Player
import pw.ian.sysadmincraft.SysAdmincraft
import pw.ian.sysadmincraft.world.WorldConstants._

case class TopCommand(plugin: SysAdmincraft) extends CommandExecutor {

  override def onCommand(sender: CommandSender, command: Command, label: String, args: Array[String]): Boolean = {
    if (!sender.isInstanceOf[Player]){
      sender.sendMessage(ChatColor.RED + "You can't use this command from the console.")
      return true
    }
    val player = sender.asInstanceOf[Player]
    player.teleport(new Location(plugin.world, PILLAR_WIDTH + PATHWAY_WIDTH / 2,
      MAX_HEIGHT / 2, PILLAR_WIDTH + PATHWAY_WIDTH / 2, 90f, 180f))
    true
  }

}
