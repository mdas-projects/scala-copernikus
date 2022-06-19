package copernikus

val r = scala.util.Random

def randomHOF(randomType: RandomType) = {
  randomType match {
    case RandomType.Int => (limit: Int) => r.nextInt(limit)
    case RandomType.Double => () => r.nextDouble()
  }
}

def cleanElectromagneticNoise(element: Int): Int = {
  element.abs
}

enum RandomType:
  case Int, Double