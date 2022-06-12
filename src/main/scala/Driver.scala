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
  None
)

val createFakeAlignments = (total: Int) =>
  (0 until total).toList.map(i => (i, randomInt(90)))

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

    // Use Case 3: Capture Infrared Image
    val telescope4 = memoryModule.getTelescope(telescopeId)
  }
}
