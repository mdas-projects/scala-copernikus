package copernikus
import play.api.libs.json._

trait TelescopeServiceTrait:
  def deploySunshade(t: Telescope): Telescope
  def deployPrimaryMirror(t: Telescope): Telescope
end TelescopeServiceTrait

trait AlignmentOpticalSystemsTrait:
  def align(
      t: Option[PrimaryMirror],
      alignments: List[(Int, Int)]
  ): PrimaryMirror
end AlignmentOpticalSystemsTrait

trait InfraredImageCaptureTrait:
  def capture(p: InfraredImage): Unit
end InfraredImageCaptureTrait

trait ModuleTrait:
  def initialize(): Unit
end ModuleTrait

object AlignmentOpticalSystems extends AlignmentOpticalSystemsTrait {
  def align(
      t: Option[PrimaryMirror],
      alignments: List[(Int, Int)]
  ): PrimaryMirror = {
    t match {
      case Some(t) =>
        val segments = t.copy().segments
        val newHexagonalSegment = alignments.map((i, degrees) => {
          println(s"Aligning segment ${i} to ${degrees} degrees")
          HexagonalSegment(i, segments(i).degrees + degrees)
        })
        t.copy(segments = newHexagonalSegment)
      case None =>
        throw new Exception("No Primary Mirror initilized")
    }

  }
}

object TelescopeService extends TelescopeServiceTrait {
  def deploySunshade(t: Telescope): Telescope = {
    println("Desplegando Parasol...")
    println("Parasol desplegado: Proteccion contra la luz y el calor del Sol")
    println("Inicializando Modulos...")
    Bus.initialize()
    t.copy(activeSunshade = true)
  }

  def deployPrimaryMirror(t: Telescope): Telescope = {
    println("Desplegando Espejo Primario...")

    val hexagonalSegmentList =
      (0 until totalHexagonalSegment).toList.map(i => HexagonalSegment(i, 90))
    val primaryMirror = PrimaryMirror(1, hexagonalSegmentList)

    println(
      s"Espejo Primario con $totalHexagonalSegment segmentos hexagonales desplegados"
    )

    t.copy(primaryMirror = Some(primaryMirror))
  }
}

object Bus {
  val computerModule = new ComputerModule
  val comunicaterModule = new ComunicaterModule
  val memoryModule = new MemoryModule
  val propulserModule = new PropulserModule
  val iaModule = new IAModule

  def getModule(e: Module) = {
    e match {
      case Module.Computer    => computerModule
      case Module.Comunicater => comunicaterModule
      case Module.Memory      => memoryModule
      case Module.Propulser   => propulserModule
      case Module.IA          => iaModule
    }
  }
  def initialize(): Unit = {
    computerModule.initialize()
    comunicaterModule.initialize()
    memoryModule.initialize()
    propulserModule.initialize()
    iaModule.initialize()
  }
}

class ComputerModule extends ModuleTrait {
  override def initialize(): Unit = println("Computer Module inicializado")
}

class ComunicaterModule extends ModuleTrait {
  override def initialize(): Unit = println("Comunicater Module inicializado")

  def sendInfraredImage(image: InfraredImage): Unit = {
    // Convertimos a JSON y enviamos
    val jsonImage = Json.toJs
  }

}

class MemoryModule extends ModuleTrait {
  override def initialize(): Unit = println("Memory Module inicializado")

  def saveTelescope(t: Telescope): Unit = {
    TelescopeDao.save(t)
  }

  def getTelescope(id: Int): Telescope = {
    TelescopeDao.get(id)
  }
}

class PropulserModule extends ModuleTrait {
  override def initialize(): Unit = println("Propulser Module inicializado")
}

class IAModule extends ModuleTrait {
  override def initialize(): Unit = println("IA Module inicializado")

  def clean(image: InfraredImage): InfraredImage = {
    val fakeImage = image.copy().matrix
    val matrix = fakeImage.map(list =>
      list.map(element => cleanElectromagneticNoise(element))
    )
    image.copy(matrix = matrix)
  }
}
