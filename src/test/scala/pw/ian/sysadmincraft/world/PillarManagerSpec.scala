import org.specs2.mutable.Specification
import pw.ian.sysadmincraft.world.PillarManagerUtils

class PillarManagerSpec extends Specification {

  "spiralIndex" >> {

    "should be unique" >> {

      val list = (0 to 10000).map(PillarManagerUtils.spiralIndex)
      list.toSet.size must_== list.size

    }

  }

}