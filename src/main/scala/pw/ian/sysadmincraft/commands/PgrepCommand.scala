package pw.ian.sysadmincraft.commands

import org.bukkit.ChatColor._
import org.bukkit.command.{Command, CommandSender, CommandExecutor}
import org.bukkit.entity.Player
import pw.ian.sysadmincraft.SysAdmincraft

case class PgrepCommand(plugin: SysAdmincraft) extends CommandExecutor {

  override def onCommand(sender: CommandSender, command: Command, label: String, args: Array[String]): Boolean = {
    if (!sender.isInstanceOf[Player]) {
      sender.sendMessage(RED + "You can't use this command from the console.")
      return true
    }
    val player = sender.asInstanceOf[Player]

    if (args.length == 0) {
      player.sendMessage(RED + "Usage: /pgrep <process name>")
      return true
    }

    val search = args.toList.mkString(" ")
    plugin.pillarManager.pillars.keys.find { key =>
      key.startsWith(search)
    } match {
      case Some(key) => {
        val pillar = plugin.pillarManager.pillars.get(key).get
        player.teleport(pillar.location)
        player.sendMessage(YELLOW + s"Teleporting to process ${pillar.process.name}")
      }
      case None => {
        player.sendMessage(RED + s"Couldn't find process '$search'")
      }
    }
    true
  }

}
