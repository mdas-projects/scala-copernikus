package copernikus

package deployTelescope {
  @main def deploy: Unit = {
    val telescopeInitial = Telescope(
        1,
        "Copernicus",
        100,
        randomDouble(),
        randomDouble(),
        randomDouble(),
        false
      )

    //Caso de uso: Desplegar Parasol

    //Desplegamos el telescopio y inicializamos los modulos
    val telescope = TelescopeService.deploySunshade(telescopeInitial)

    //Guardamos telescopio en memoria  
    val memoryModule: MemoryModule = Bus.getModule(Module.Memory).asInstanceOf[MemoryModule] 
    memoryModule.saveTelescope(telescope) 
  }
}

package deployPrimaryMirror {
  @main def deploy: Unit = println("Hello world!")

} 


package alignTelescope {
  @main def align: Unit =    println("Hello world!")


    //Caso de uso: Alinear Telescopio
    
    //Despligue de los 18 segmentos
    
    //Intrucciones desde Tierra

    //Operacion de alineacion
}


package infraredImageCapture {
  @main def align: Unit =
    println("Hello world!")

}
