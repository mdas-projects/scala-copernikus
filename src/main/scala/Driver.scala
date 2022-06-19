package copernikus

val telescopeId = 1
val telescopeInitial = Telescope(
  telescopeId,
  "Copernicus",
  100,
  randomDouble(),
  randomDouble(),
  randomDouble(),
  false,
  None,
  None
)

package tripTelescope {
  @main def deploy: Unit = {
    // Inicialización de memoryModule
    val memoryModule: MemoryModule =
      Bus.getModule(Module.Memory).asInstanceOf[MemoryModule]

    val telescopeService = new TelescopeService
    val alignmentOpticalSystems = new AlignmentOpticalSystems

    // Use Case 1: Deploy Sunshade
    // Desplegamos el telescopio y inicializamos los modulos y guardamos telescopio en memoria
    val telescope1 = memoryModule.saveTelescope(
      telescopeService.deploySunshade(telescopeInitial)
    )

    // Use Case 2: Deploy Primary Mirror
    val telescope2 = memoryModule.saveTelescope(
      telescopeService.deployPrimaryMirror(telescope1)
    )

    // Use Case 3: Align Telescope
    val telescope3 = memoryModule.saveTelescope(
      telescope2.copy(primaryMirror =
        Some(
          alignmentOpticalSystems.align(
            telescope2.primaryMirror,
            createFakeAlignments(totalHexagonalSegment)
          )
        )
      )
    )

    // Use Case 4: Capture Infrared Image

    // Capture Infrate ligth and store information using anonymous class
    val fakeNoisyInfraredImage = new FakeImage {
      override def create(): InfraredImage = {
        InfraredImage(1, 1, "2022-06-12T20:53:34.743Z", createFakeImage(20, 20))
      }
    }

    val telescope4 = memoryModule.saveTelescope(
      telescope3.copy(images = Some(List(fakeNoisyInfraredImage.create())))
    )

    // Use case 4.1: Electromagnetic Noise cleaning
    val iaModule: IAModule =
      Bus.getModule(Module.IA).asInstanceOf[IAModule]

    val telescope5 = memoryModule.saveTelescope(
      telescope4.copy(images =
        Some(
          telescope4.images.dropRight(1).toList.flatten ++ List(
            iaModule.clean(
              fakeNoisyInfraredImage.create(),
              cleanElectromagneticNoise
            )
          )
        )
      )
    )

    // Use case 4.2: Send image to Earth, publish discovers
    val comunicaterModule: ComunicaterModule =
      Bus.getModule(Module.Comunicater).asInstanceOf[ComunicaterModule]
    comunicaterModule.sendInfraredImage(telescope5.images.toList.flatten.last)
  }
}
