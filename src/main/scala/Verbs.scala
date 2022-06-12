package copernikus

trait TelescopeServiceTrait:
  def deploySunshade(t: Telescope): Telescope
end TelescopeServiceTrait

trait AlignmentOpticalSystemsTrait:
  def align(t: PrimaryMirror): PrimaryMirror
end AlignmentOpticalSystemsTrait

trait InfraredImageCaptureTrait:
  def capture(p: InfraredImage): Unit
end InfraredImageCaptureTrait

trait ModuleTrait:
  def initialize(): Unit
end ModuleTrait

object TelescopeService extends TelescopeServiceTrait {
  def deploySunshade(t: Telescope): Telescope = {
    println("Desplegando Parasol...")
    println("Parasol desplegado: Proteccion contra la luz y el calor del Sol")
    println("Inicializando Modulos...")
    Bus.initialize()
    t.copy(activeSunshade = true)
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
      case Module.Computer => computerModule 
      case Module.Comunicater => comunicaterModule
      case Module.Memory => memoryModule
      case Module.Propulser => propulserModule
      case Module.IA => iaModule
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

}

class MemoryModule extends ModuleTrait {
  override def initialize(): Unit = println("Memory Module inicializado")
  def saveTelescope(t: Telescope): Unit = {
    TelescopeDao.save(t)
  }
}

class PropulserModule extends ModuleTrait {
  override def initialize(): Unit = println("Propulser Module inicializado")
}

class IAModule extends ModuleTrait {
  override def initialize(): Unit = println("IA Module inicializado")
}
