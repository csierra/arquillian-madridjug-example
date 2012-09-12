package com.bizonos.test.rest;

import static com.bizonos.test.TestUtils.addJPA;
import static com.bizonos.test.TestUtils.basicArchive;

import java.net.MalformedURLException;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class RestTest {

	@Deployment(testable = false)
	public static Archive<?> createTestArchive() {
			return addJPA(basicArchive())
				.addPackages(true, "com.bizonos.rest")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	public String augment(URL contextPath, String path)
			 {
		try {
			if (contextPath == null) {
				return null;
			}
			if (path == null || "".equals(path.trim())) {
				return contextPath.toExternalForm();
			}
			if (path.startsWith("/")) {
				throw new MalformedURLException("Provided path must be relative");
			}
			String externalForm = contextPath.toExternalForm();
			return new URL(externalForm + path).toExternalForm();
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

}
