package pw.ian.sysadmincraft.world

import org.specs2.mutable.Specification

class PillarManagerSpec extends Specification {

  "spiralIndex" >> {

    "should be unique" >> {

      val list = (0 to 10000).map(PillarManagerUtils.spiralIndex)
      list.toSet.size must_== list.size

    }

  }

}