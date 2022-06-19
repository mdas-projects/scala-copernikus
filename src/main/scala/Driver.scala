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

val createFakeAlignments = (total: Int) =>
  (0 until total).toList.map(i => (i, randomInt(90)))

val createFakeImage = (rows: Int, columns: Int) => {
  (0 until rows).toList.map(_ =>
    (0 until columns).toList.map(_ => randomInt(255))
  )
}

package tripTelescope {
  @main def deploy: Unit = {
    // Use Case 1: Deploy Sunshade
    // Desplegamos el telescopio y inicializamos los modulos
    val telescope = TelescopeService.deploySunshade(telescopeInitial)

    // Guardamos telescopio en memoria
    val memoryModule: MemoryModule =
      Bus.getModule(Module.Memory).asInstanceOf[MemoryModule]
    memoryModule.saveTelescope(telescope)

    // Use Case 2: Deploy Primary Mirror
    val telescope2 = memoryModule.getTelescope(telescopeId)
    memoryModule.saveTelescope(TelescopeService.deployPrimaryMirror(telescope2))

    // Use Case 3: Align Telescope
    val telescope3 = memoryModule.getTelescope(telescopeId)
    val primaryMirror =
      AlignmentOpticalSystems.align(
        telescope3.primaryMirror,
        createFakeAlignments(totalHexagonalSegment)
      )
    memoryModule.saveTelescope(
      telescope3.copy(primaryMirror = Some(primaryMirror))
    )

    // Use Case 4: Capture Infrared Image
    val telescope4 = memoryModule.getTelescope(telescopeId)

    val list = createFakeImage(20, 20)
    println(s"List: $list")

    // Capture Infrate ligth and store information
    val fakeNoisyInfraredImage =
      InfraredImage(1, 1, "2022-06-12T20:53:34.743Z", list)

    memoryModule.saveTelescope(
      telescope4.copy(images = Some(List(fakeNoisyInfraredImage)))
    )

    // Electromagnetic Noise cleaning
    val telescope5 = memoryModule.getTelescope(telescopeId)
    val iaModule: IAModule =
      Bus.getModule(Module.IA).asInstanceOf[IAModule]

    val cleanedInfraredImage = iaModule.clean(fakeNoisyInfraredImage)

    val newImageList: List[InfraredImage] =
      telescope5.images.dropRight(1).toList.flatten ++ List(
        cleanedInfraredImage
      )

    memoryModule.saveTelescope(
      telescope5.copy(images = Some(newImageList))
    )

    // Send image to Earth, publish discovers

  }
}
