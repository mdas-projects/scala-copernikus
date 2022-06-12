package copernikus

trait TelescopeDaoInterface {
  def get(id: Int): Telescope
  def save(id: Telescope): Unit
}

trait PrimaryMirrorDaoInterface {
  def get(id: Int): PrimaryMirror
  def save(id: PrimaryMirror): Unit
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
