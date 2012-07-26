package edu.mayo.cts2.framework.plugin.service.nlm.model

trait VersionedNameParser extends NameParser {

  def toVersionedName(versionedName: String): VersionedName = {
    val split = versionedName.split('-')

    new VersionedName(split(0), split(1))

  }
}

class VersionedName(name: String, version: String) {

  def getName():String = { name }
  def getVersion():String = { version }

}
