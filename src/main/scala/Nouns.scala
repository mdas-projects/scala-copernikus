package copernikus

case class Telescope(
    id: Int,
    name: String,
    sunshadePowerPercent: Double,
    positionX: Double,
    positionY: Double,
    positionZ: Double,
    activeSunshade: Boolean,

    
) {}

case class PrimaryMirror(
    id: Int,
    segments: List[HexagonalSegment]
) {}

case class HexagonalSegment(
    id: Int,
    flexDegrees: Int = 0
) {}

case class InfraredImage(
    id: Int,
    date: String,
    matrix: List[List[String]]
) {}

enum Module:
    case Computer, Comunicater, Memory, Propulser, IA
