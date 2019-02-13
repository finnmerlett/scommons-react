
[![Build Status](https://travis-ci.org/scommons/scommons-react.svg?branch=master)](https://travis-ci.org/scommons/scommons-react)
[![Coverage Status](https://coveralls.io/repos/github/scommons/scommons-react/badge.svg?branch=master)](https://coveralls.io/github/scommons/scommons-react?branch=master)
[![Scala.js](https://www.scala-js.org/assets/badges/scalajs-0.6.17.svg)](https://www.scala-js.org)

## Scala Commons React
Scala.js facades for common React utilities and components.

It uses excellent [scalajs-reactjs](https://github.com/shogowada/scalajs-reactjs) binding/facade library.


### How to add it to your project

Current version is under active development, but you already can try it:
```scala
val scommonsReactVer = "0.1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "org.scommons.react" %%% "scommons-react-core" % scommonsReactVer,
  
  "org.scommons.react" %%% "scommons-react-test" % scommonsReactVer % "test",
  "org.scommons.react" %%% "scommons-react-test-dom" % scommonsReactVer % "test"
)
```

Latest `SNAPSHOT` version is published to [Sonatype Repo](https://oss.sonatype.org/content/repositories/snapshots/org/scommons/), just make sure you added
the proper dependency resolver to your `build.sbt` settings:
```scala
resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
```

### Supported Features

* React Hooks:
  * [useState](showcase/src/main/scala/scommons/react/showcase/UseStateDemo.scala) => [tests](showcase/src/test/scala/scommons/react/showcase/UseStateDemoSpec.scala)

### How to Build

To build and run all the tests use the following command:
```bash
sbt clean test
```

## Documentation

You can find more documentation [here](https://scommons.org)
