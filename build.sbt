version := "0.0.1"

organization := "devinsideyou"

scalaVersion := "2.12.4"

triggeredMessage := Watched.clearWhenTriggered

resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"

libraryDependencies ++=
  Seq(
     "org.scalatest" %%  "scalatest" %  "3.0.5" % "test", // http://www.scalatest.org/
    "org.scalacheck" %% "scalacheck" % "1.14.0" % "test",
       "org.pegdown"  %    "pegdown" %  "1.6.0" % "test"  // https://github.com/sirthias/pegdown/
  )

testOptions in Test ++=
  Seq(
    Tests.Argument(TestFrameworks.ScalaTest, "-oSD"),
    Tests.Argument(TestFrameworks.ScalaTest, "-h", "target/test-reports")
  )

addCommandAlias("testc", ";clean;coverage;test;coverageReport")

coverageExcludedPackages := "Main"
