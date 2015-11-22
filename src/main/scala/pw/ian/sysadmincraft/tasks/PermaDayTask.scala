package pw.ian.sysadmincraft.tasks

import org.bukkit.scheduler.BukkitRunnable
import pw.ian.sysadmincraft.SysAdmincraft

case class PermaDayTask(plugin: SysAdmincraft) extends BukkitRunnable {

  override def run(): Unit = {
    plugin.world.setTime(6000L)
  }

}
