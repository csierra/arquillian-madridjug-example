package com.bizonos.test.ejb;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ValidationException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.bizonos.rest.controller.MemberRegistration;
import com.bizonos.rest.model.Member;
import com.bizonos.rest.util.Resources;
import static com.bizonos.test.TestUtils.*;

@RunWith(Arquillian.class)
public class MemberManagerTest {
	
	@PersistenceContext
	EntityManager em;
	
	@Deployment
	public static Archive<?> deployment() {
		
		return addJPA(basicArchive())
				.addClass(MemberRegistration.class)
				.addClass(Member.class)
				.addClass(Resources.class)
				.addAsResource(EmptyAsset.INSTANCE, "META-INF/beans.xml");
	}
	
	@EJB
	MemberRegistration registration;
	
	@Test
	public void testRegistration() throws Exception {
		Member newMember = registration.getNewMember();
		assertNull(newMember.getId());
		newMember.setName("peperolo");
		newMember.setEmail("peperolo@atitican.com");
		newMember.setPhoneNumber("12345678901");
		registration.register();
		assertNotNull(newMember.getId());
		assertNull(registration.getNewMember().getId());
	}
	
	@Test
	public void testValidation() throws Exception {
		registration.getNewMember();
		try {
			registration.register();
			fail("Should throw ValidationException");
		} catch (EJBTransactionRolledbackException e) {
			assertTrue(ExceptionUtils.getRootCause(e) instanceof ValidationException);
		}
	}
}
