package pw.ian.sysadmincraft.commands

import org.bukkit.{Location, ChatColor}
import org.bukkit.command.{Command, CommandSender, CommandExecutor}
import org.bukkit.entity.Player
import pw.ian.sysadmincraft.SysAdmincraft
import pw.ian.sysadmincraft.system.SystemOverview
import pw.ian.sysadmincraft.world.WorldConstants._

case class TopCommand(plugin: SysAdmincraft) extends CommandExecutor {

  override def onCommand(sender: CommandSender, command: Command, label: String, args: Array[String]): Boolean = {
    if (!sender.isInstanceOf[Player]){
      sender.sendMessage(ChatColor.RED + "You can't use this command from the console.")
      return true
    }
    val player = sender.asInstanceOf[Player]
    player.teleport(new Location(plugin.world, PILLAR_WIDTH + PATHWAY_WIDTH / 2,
      MAX_HEIGHT / 2, PILLAR_WIDTH + PATHWAY_WIDTH / 2, 90f, 0f))

    player.sendMessage(ChatColor.GREEN + SystemOverview.uname())
    player.sendMessage(ChatColor.BLUE + SystemOverview.uptime())
    SystemOverview.memory().foreach { msg =>
      player.sendMessage(ChatColor.YELLOW + msg)
    }
    player.sendMessage(ChatColor.GREEN + "Total memory: " + ChatColor.YELLOW + SystemOverview.totalMem() + " MB")

    true
  }

}
