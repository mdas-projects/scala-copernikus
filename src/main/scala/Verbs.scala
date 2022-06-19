package copernikus

// Definimos las interfaces de los services
trait FakeImage {
  def create(): InfraredImage
}

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

trait ComunicaterInfraredImageTrait:
  def sendInfraredImage(image: InfraredImage): Unit
end ComunicaterInfraredImageTrait

// Abstract class para que pueda albergar logica futura para las modulos que lo implemetan
abstract class ModuleTrait {
  def initialize(): Unit
}

// Class ya que sera inicializado en el driver antes de ejecutar el caso de uso requerido
class AlignmentOpticalSystems extends AlignmentOpticalSystemsTrait {
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

// Class ya que sera inicializado en el driver antes de ejecutar el caso de uso requerido
class TelescopeService extends TelescopeServiceTrait {
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

// Creamos singleton para el Bus ya que necesitamos que siempre este disponible desde antes de todos los casos de uso requeridos
object Bus {
  val computerModule = new ComputerModule
  val comunicaterModule = new ComunicaterModule
  val memoryModule = new MemoryModule(new TelescopeDao)
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

// Class ya que sera inicializado por el bus
class ComputerModule extends ModuleTrait {
  override def initialize(): Unit = println("Computer Module inicializado")
}

// Class ya que sera inicializado por el bus
class ComunicaterModule extends ModuleTrait with ComunicaterInfraredImageTrait {
  override def initialize(): Unit = println("Comunicater Module inicializado")

  def sendInfraredImage(image: InfraredImage): Unit = {
    print(
      s"Enviando imagen a tierra... ${image.matrix} con orden ${image.order}"
    )
  }
}

// Class ya que sera inicializado por el bus
class MemoryModule(telescopeDao: TelescopeDao) extends ModuleTrait {
  override def initialize(): Unit = println("Memory Module inicializado")

  def saveTelescope(t: Telescope): Telescope = {
    this.telescopeDao.save(t)
  }

  def getTelescope(id: Int): Telescope = {
    this.telescopeDao.get(id)
  }
}

// Class ya que sera inicializado por el bus
class PropulserModule extends ModuleTrait {
  override def initialize(): Unit = println("Propulser Module inicializado")
}

// Class ya que sera inicializado por el bus
class IAModule extends ModuleTrait {
  override def initialize(): Unit = println("IA Module inicializado")

  def clean(
      image: InfraredImage,
      cleanAlgorithmFunction: (pixel: Int) => Int
  ): InfraredImage = {
    val fakeImage = image.copy().matrix
    val matrix = fakeImage.map(list =>
      list.map(element => cleanAlgorithmFunction(element))
    )
    image.copy(matrix = matrix)
  }
}
