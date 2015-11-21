lazy val root = (project in file(".")).
  settings(
    name := "sysadmincraft",
    version := "0.1.0",
    scalaVersion := "2.11.7"
  )

assemblyJarName in assembly := "SysAdmincraft.jar"

resolvers += "Bukkit" at "http://repo.bukkit.org/content/groups/public/"

libraryDependencies ++= Seq(
  "org.bukkit" % "bukkit" % "1.6.4-R2.0" % "provided",
  "org.specs2" %% "specs2-core" % "3.6.5" % "test"
)

scalacOptions in Test += "-Yrangepos"

// META-INF discarding
assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
