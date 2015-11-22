package pw.ian.sysadmincraft.commands

import org.bukkit.command.{Command, CommandExecutor, CommandSender}
import org.bukkit.entity.Player
import org.bukkit.{ChatColor, Location}
import pw.ian.sysadmincraft.SysAdmincraft
import pw.ian.sysadmincraft.system.SystemOverview
import pw.ian.sysadmincraft.world.WorldConstants._

case class PsCommand(plugin: SysAdmincraft) extends CommandExecutor {

  override def onCommand(sender: CommandSender, command: Command, label: String, args: Array[String]): Boolean = {
    sender.sendMessage(ChatColor.YELLOW + "Running processes:")
    plugin.pillarManager.pillars.values.grouped(8).foreach { pillar =>
      sender.sendMessage(pillar.map(_.process.name).mkString(", "))
    }
    true
  }

}
