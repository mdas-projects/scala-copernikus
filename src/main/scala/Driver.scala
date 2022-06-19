package copernikus

val telescopeId = 1
val telescopeInitial = Telescope(
  telescopeId,
  "Copernicus",
  100,
  randomHOF(RandomType.Double)(),
  randomHOF(RandomType.Double)(),
  randomHOF(RandomType.Double)(),
  false,
  None,
  None
)

val createFakeAlignments = (total: Int) =>
  (0 until total).toList.map(i => (i, randomHOF(RandomType.Int)(90)))

val createFakeImage = (rows: Int, columns: Int) => {
  (0 until rows).toList.map(_ =>
    (0 until columns).toList.map(_ => randomInt(255))
  )
}

package tripTelescope {
  @main def deploy: Unit = {
    // Inicialización de memoryModule
    val memoryModule: MemoryModule =
      Bus.getModule(Module.Memory).asInstanceOf[MemoryModule]

    // Use Case 1: Deploy Sunshade
    // Desplegamos el telescopio y inicializamos los modulos
    // Guardamos telescopio en memoria
    val telescope1 = memoryModule.saveTelescope(
      TelescopeService.deploySunshade(telescopeInitial)
    )

    // Use Case 2: Deploy Primary Mirror
    val telescope2 = memoryModule.saveTelescope(
      TelescopeService.deployPrimaryMirror(telescope1)
    )

    // Use Case 3: Align Telescope
    val telescope3 = memoryModule.saveTelescope(
      telescope2.copy(primaryMirror =
        Some(
          AlignmentOpticalSystems.align(
            telescope2.primaryMirror,
            createFakeAlignments(totalHexagonalSegment)
          )
        )
      )
    )

    // Use Case 4: Capture Infrared Image
    val list = createFakeImage(20, 20)
    println(s"List: $list")

    // Capture Infrate ligth and store information
    val fakeNoisyInfraredImage =
      InfraredImage(1, 1, "2022-06-12T20:53:34.743Z", list)

    val telescope4 = memoryModule.saveTelescope(
      telescope3.copy(images = Some(List(fakeNoisyInfraredImage)))
    )

    // Use case 4.1: Electromagnetic Noise cleaning
    val iaModule: IAModule =
      Bus.getModule(Module.IA).asInstanceOf[IAModule]

    val telescope5 = memoryModule.saveTelescope(
      telescope4.copy(images = Some(telescope4.images.dropRight(1).toList.flatten ++ List(
        iaModule.clean(fakeNoisyInfraredImage)
      )))
    )

    // Use case 4.2: Send image to Earth, publish discovers
    val comunicaterModule: ComunicaterModule =
      Bus.getModule(Module.Comunicater).asInstanceOf[ComunicaterModule]
    comunicaterModule.sendInfraredImage(telescope5.images.toList.flatten.last)
  }
}
