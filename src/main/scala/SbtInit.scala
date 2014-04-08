import sbt._

object SbtInit extends Plugin {
  override lazy val settings = Seq(Keys.commands += initCommand)

  private lazy val initCommand = Command.command("init") { (state: State) =>
    println("TODO")
    state
  }
}
