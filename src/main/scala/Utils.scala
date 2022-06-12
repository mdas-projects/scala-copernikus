package copernikus

def randomDouble(): Double = {
  val r = scala.util.Random
  r.nextDouble()
}

def randomInt(limit: Int): Int = {
  val r = scala.util.Random
  r.nextInt(limit)
}
