import sbt._
import sbt.complete.Parsers._
import java.io._

object SbtInit extends Plugin {
  override lazy val settings = Seq(Keys.commands += initCommand)

  private lazy val initCommand = Command.args("init", "args") { (state: State, args: Seq[String]) =>
    val projectPath = state.baseDir

    val projName = args match {
      case x :: xs => x
      case _ => {
        print("Project name [empty-project]: ")
        readLine() match {
          case "" => "empty-project"
          case s: String => s
        }
      }
    }

    println(s"Creating project...")

    createBuildSbt(projectPath, projName)

    createSrcDirectories(projectPath)

    state
  }

  private def createBuildSbt(path: File, projName: String) {
    val buildSbtPath = "build.sbt"

    val f = new File(path, buildSbtPath)
    if(f.exists()) println("build.sbt already exists. Skipping")
    else {
      val content = "name := \""+projName+"\"\n\nversion := \"1.0-SNAPSHOT\"\n\nscalaVersion := \"2.10.3\"\n"

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
