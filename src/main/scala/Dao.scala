package copernikus

trait TelescopeDaoInterface {
  def get(id: Int): Telescope
  def save(id: Telescope): Unit
}

trait PrimaryMirrorDaoInterface {
  def get(id: Int): PrimaryMirror
  def save(id: PrimaryMirror): Unit
}

object PrimaryMirrorDao extends PrimaryMirrorDaoInterface {
  var map: Map[Int, PrimaryMirror] = Map()

  def get(id: Int): PrimaryMirror = {
    map.get(id) match {
      case Some(mirror) => mirror
      case None         => throw new Exception("No PrimaryMirror with id " + id)
    }
  }

  def save(p: PrimaryMirror): Unit = {
    map += (p.id -> p)
  }
}

object TelescopeDao extends TelescopeDaoInterface {
  var map: Map[Int, Telescope] = Map()

  def get(id: Int): Telescope = {
    map.get(id) match {
      case Some(telescope) => telescope
      case None            => throw new Exception("No telescope with id " + id)
    }
  }
  def save(t: Telescope): Unit = {
    map += (t.id -> t)
  }
}
