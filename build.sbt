import com.typesafe.sbt.packager.docker.ExecCmd

name := "learn-sbt"

ThisBuild / version := "1.0"

scalaVersion := "2.12.8"

ThisBuild / licenses ++= Seq(("MIT", url("http://opensource.org/licenses/MIT")))

publish/skip := true

lazy val calculators = project
  .dependsOn(api)
  .enablePlugins(JavaAppPackaging)
  .enablePlugins(DockerPlugin)
  .settings(
    libraryDependencies ++= Dependencies.calculatorDependencies,
    dockerCommands := dockerCommands.value.filterNot {
      case ExecCmd("ENTRYPOINT", _) => true
      case _ => false
    },
    dockerCommands ++= Seq(ExecCmd("ENTRYPOINT", "/opt/docker/bin/net-worth"))
  )

lazy val api = project
  .enablePlugins(JavaAppPackaging)
  .settings(
    libraryDependencies ++= Dependencies.apiDependencies
  )

lazy val word = settingKey[String]("How is things")
word := "pretty good"

val randomInt = taskKey[Int]("give me a random number")
randomInt := scala.util.Random.nextInt
//resolvers += Resolver.JCenterRepository
//lazy val test = project.settings(
//  libraryDependencies += ("calculators" % "calculators_2.12" % "1.0")
//)
