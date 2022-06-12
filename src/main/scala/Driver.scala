package copernikus

@main def initContext: Unit =
  val telescope = Telescope(
    1,
    "Copernicus",
    100,
    randomDouble(),
    randomDouble(),
    randomDouble(),
    false
  )

package deployTelescope {
  @main def deploy: Unit =
    println("Hello world!")

}

package alignTelescope {
  @main def align: Unit =
    println("Hello world!")

}

package infraredImageCapture {
  @main def align: Unit =
    println("Hello world!")

}
