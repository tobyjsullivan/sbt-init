import sbt._
import java.io._

object SbtInit extends Plugin {
  override lazy val settings = Seq(Keys.commands += initCommand)

  private lazy val initCommand = Command.command("init") { (state: State) =>
    val projectPath = state.baseDir
    println(projectPath)

    print("Project name [empty-project]: ")
    val projName = readLine() match {
      case "" => "empty-project"
      case s: String => s
    }

    print("Project version [1.0]: ")
    val projVer = readLine() match {
      case "" => "1.0"
      case s: String => s
    }

    println(s"Creating $projName v$projVer")

    createBuildSbt(projectPath, projName, projVer)

    createSrcDirectories(projectPath)

    state
  }

  private def createBuildSbt(path: File, projName: String, projVer: String) {
    val buildSbtPath = "build.sbt"

    val f = new File(path, buildSbtPath)
    if(f.exists()) println("build.sbt already exists. Skipping")
    else {
      val content = "name := \""+projName+"\"\n\nversion := \""+projVer+"\"\n\nscalaVersion := \"2.10.3\"\n"

      f.createNewFile()
      val fw = new FileWriter(f.getAbsoluteFile())
      val bw = new BufferedWriter(fw)
      bw.write(content)
      bw.close()
    }
  }

  private def createSrcDirectories(path: File) {
    val mainPath = "src/main/scala/"
    val testPath = "src/test/scala/"

    (new File(path, mainPath)).mkdirs()
    (new File(path, testPath)).mkdirs()
  }
}
