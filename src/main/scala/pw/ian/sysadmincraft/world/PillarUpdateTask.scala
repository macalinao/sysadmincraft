package pw.ian.sysadmincraft.world

import org.bukkit.scheduler.{BukkitRunnable, BukkitTask}
import pw.ian.sysadmincraft.SysAdmincraft
import pw.ian.sysadmincraft.system.ProcessAdmin

case class PillarUpdateTask(plugin: SysAdmincraft) extends BukkitRunnable {

  override def run() = {
    plugin.pillarManager.refresh(ProcessAdmin.processes.values)
  }

}
