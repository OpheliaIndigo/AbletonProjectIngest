package opheliaindigo.abletonproject.ingest;

import java.io.File

class ProjectCrawler(excl: List[String]=List.empty):
  def crawlPath(source: String): Array[(File, List[String])] =
    if (new File(source).exists)
      crawl(new File(source), List())
    else
      Array()

  def crawl(source: File, parents: List[String]): Array[(File, List[String])] =
    // return crawl of all contained folders
    // return all ALS files
    var files = gatherFiles(source).map(file => (file, parents))
    var newFiles = files ++ gatherFolders(source).flatMap(file =>
      crawl(file, parents.:+(file.getName()))
    )
    return newFiles

  def gatherFiles(folder: File): Array[File] =
    return folder.listFiles
      .filter(_.getName.endsWith(".als"))

  def gatherFolders(folder: File): Array[File] =
    return folder
      .listFiles()
      .filter(_.isDirectory())
      .filter(f => !this.excl.contains(f.getName()))
