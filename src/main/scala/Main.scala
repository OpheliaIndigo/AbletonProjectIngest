package opheliaindigo.abletonproject.ingest;

import java.nio.file.Files
import java.io.File

@main def hello(): Unit =
  var projects = new ProjectCrawler(List("Backup")).crawlPath("../../../../../OneDrive/Music/")
  println(projects.length)
  var tempDir = Files.createTempDirectory("abletonCrawler").toFile()
  outputProjects(projects, tempDir)