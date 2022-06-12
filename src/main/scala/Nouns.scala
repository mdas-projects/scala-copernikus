package copernikus

case class Telescope(
    id: Int,
    name: String,
    powerPercent: Double,
    positionX: Double,
    positionY: Double,
    positionZ: Double,
    active: Boolean,
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

