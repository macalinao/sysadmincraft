package pw.ian.sysadmincraft.world

import java.util.Random

import org.bukkit.{Material, World}
import org.bukkit.generator.ChunkGenerator
import org.bukkit.generator.ChunkGenerator.BiomeGrid

class PillarWorldGenerator extends ChunkGenerator {

  override def generateBlockSections(world: World, random: Random, x: Int, z: Int, biomeGrid: BiomeGrid): Array[Array[Byte]] = {
    val ret = Array.ofDim[Byte](16, 4096)
    (0 to 4095).foreach(ret(0)(_) = Material.GRASS.getId.asInstanceOf[Byte])
    ret
  }

}
