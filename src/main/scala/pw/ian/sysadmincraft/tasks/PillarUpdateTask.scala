package pw.ian.sysadmincraft.tasks

import org.bukkit.scheduler.BukkitRunnable
import pw.ian.sysadmincraft.SysAdmincraft
import pw.ian.sysadmincraft.system.ProcessAdmin

case class PillarUpdateTask(plugin: SysAdmincraft) extends BukkitRunnable {

  override def run() = {
    plugin.pillarManager.refresh(ProcessAdmin.processes())
  }

}
