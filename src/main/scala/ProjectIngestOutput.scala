package opheliaindigo.abletonproject.ingest;

import java.io.File
import opheliaindigo.abletonproject.ingest.UnzipHandler


def outputProjects(projectProperties: Array[(File, List[String])], outputDirectory: File) = 
  projectProperties.foreach((file, parents) => outputProject(file, parents, outputDirectory))

def outputProject(projectFile: File, projectParents: List[String], outputDirectory: File) =
  var fileName = projectParents.:+(projectFile.getName()).fold("")((x, y) => x + y)
  UnzipHandler.unZip(projectFile.getAbsolutePath(), outputDirectory.getAbsolutePath()+"/"+fileName+".xml")
