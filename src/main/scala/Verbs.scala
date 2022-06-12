package copernikus

trait TelescopeServiceTrait:
  def deploy(t: Telescope): Telescope
end TelescopeServiceTrait

trait AlignmentOpticalSystemsTrait:
  def align(t: Telescope): Telescope
end AlignmentOpticalSystemsTrait

trait InfraredImageCaptureTrait:
  def capture(p: InfraredImage): Unit
end InfraredImageCaptureTrait

object ComputerModule {}
object ComunicaterModule {}
object MemoryModule {}
object PropulserModule {}
object IAModule {}
