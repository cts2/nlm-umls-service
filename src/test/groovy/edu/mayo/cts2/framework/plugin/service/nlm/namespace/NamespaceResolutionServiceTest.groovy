package edu.mayo.cts2.framework.plugin.service.nlm.namespace

import static org.junit.Assert.*

import org.junit.Test

import clojure.lang.RT


public class NamespaceResolutionServiceTest {
	
	@Test
	void TestGetUri() {
		def svc = new NamespaceResolutionService()
		svc.namespaceServiceUrl = "http://informatics.mayo.edu/cts2/services/bioportal-rdf"
		
		assertEquals "http://schema.omg.org/spec/CTS2/1.0/", svc.prefixToUri("cts2")
	}
	
	@Test
	void TestGetPrefix() {
		def svc = new NamespaceResolutionService()
		svc.namespaceServiceUrl = "http://informatics.mayo.edu/cts2/services/bioportal-rdf"
		
		assertEquals "cts2", svc.uriToPrefix("http://schema.omg.org/spec/CTS2/1.0/")
	}
	
}
