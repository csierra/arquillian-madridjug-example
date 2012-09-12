package com.bizonos.test.ejb;

import static com.bizonos.test.TestUtils.addJPA;
import static com.bizonos.test.TestUtils.basicArchive;
import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.bizonos.rest.model.Member;
import com.bizonos.rest.services.MembersService;

@RunWith(Arquillian.class)
public class ListTest {

	@Deployment
	public static WebArchive deployment() {
		return addJPA(basicArchive()).addClasses(MembersService.class, Member.class)
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@PersistenceContext
	EntityManager em;
	
	@Inject
	UserTransaction utx;
	
	@Inject
	MembersService mService;
	
	@Test
	public void testListByIdHasItsOwnTransaction() throws Exception {
		//Comenzamos una transacción
		utx.begin();
		em.joinTransaction();
		
		List<Member> all = mService.findAll();
		int before = all.size();
		
		Member member = new Member();
		member.setName("a");
		member.setEmail("a@a");
		member.setPhoneNumber("55512345678");
		mService.storeMember(member);
		
		List<Member> allAfter = mService.findAll();
		assertEquals("No debería reflejarse el contenido de la transacción en curso en la lista", before, allAfter.size());
		
		utx.commit();
		
		List<Member> afterCommit = mService.findAll();
		assertEquals(before+1, afterCommit.size());
	}
}
