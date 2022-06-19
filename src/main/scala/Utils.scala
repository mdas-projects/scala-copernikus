package copernikus

// Creamos metodos para la definicion de nuestros utils

def randomDouble(): Double = {
  val r = scala.util.Random
  r.nextDouble()
}

def randomInt(limit: Int): Int = {
  val r = scala.util.Random
  r.nextInt(limit)
}

def cleanElectromagneticNoise(element: Int): Int = {
  element.abs
}

def createFakeAlignments(total: Int) =
  (0 until total).toList.map(i => (i, randomInt(90)))

def createFakeImage(rows: Int, columns: Int) = {
  (0 until rows).toList.map(_ =>
    (0 until columns).toList.map(_ => randomInt(255))
  )
}
