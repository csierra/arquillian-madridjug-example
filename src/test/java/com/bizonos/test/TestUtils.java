package com.bizonos.test;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;


public class TestUtils {
	
	public static WebArchive basicArchive() {
		MavenDependencyResolver resolver = DependencyResolvers.use(
				MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");
		return ShrinkWrap.create(WebArchive.class)
			.addAsLibraries(resolver.artifact("org.apache.commons:commons-lang3")
				.resolveAsFiles());
				
	}
	
	public static WebArchive addJPA(WebArchive archive) {
		return archive.addAsResource("META-INF/persistence.xml")
				.addAsResource("import.sql");
	}
}
