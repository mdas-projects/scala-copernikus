package copernikus

trait TelescopeDaoInterface {
  def get(id: Int): Telescope
  def save(id: Telescope): Telescope
}

object TelescopeDao extends TelescopeDaoInterface {
  var map: Map[Int, Telescope] = Map()

  def get(id: Int): Telescope = {
    map.get(id) match {
      case Some(telescope) => telescope
      case None            => throw new Exception("No telescope with id " + id)
    }
  }
  def save(t: Telescope): Telescope = {
    map += (t.id -> t)
    t
  }
}
