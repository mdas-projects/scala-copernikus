package copernikus

val totalHexagonalSegment = 18

// Case class en nuestras entidades ya que nos permite tener parametros inmutables y sintaxis de inicialización compacta (Minimal class).
case class Telescope(
    id: Int,
    name: String,
    sunshadePowerPercent: Double,
    positionX: Double,
    positionY: Double,
    positionZ: Double,
    activeSunshade: Boolean,
    primaryMirror: Option[PrimaryMirror],
    images: Option[List[InfraredImage]]
) {}

case class PrimaryMirror(
    id: Int,
    segments: List[HexagonalSegment]
) {}

case class HexagonalSegment(
    id: Int,
    degrees: Int = 0
) {}

case class InfraredImage(
    groupImageId: Int,
    order: Int,
    date: String,
    matrix: List[List[Int]]
) {}

enum Module:
  case Computer, Comunicater, Memory, Propulser, IA
