package pw.ian.sysadmincraft.commands

import org.bukkit.command.{Command, CommandSender, CommandExecutor}
import pw.ian.sysadmincraft.SysAdmincraft

case class PgrepCommand(plugin: SysAdmincraft) extends CommandExecutor {

  override def onCommand(sender: CommandSender, command: Command, label: String, args: Array[String]): Boolean = {
    true
  }

}
