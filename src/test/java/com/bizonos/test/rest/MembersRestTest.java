package com.bizonos.test.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URL;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Test;

import com.bizonos.rest.model.Member;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;


public class MembersRestTest extends RestTest {
   
   @ArquillianResource URL contextPath;
   Client client = Client.create();
   
   @Test
   @RunAsClient
   @InSequence(0)
   public void testInicial() throws Exception {
		WebResource resource = client.resource(augment(contextPath,
				"rest/members/0"));
	   Member member = resource.accept(MediaType.TEXT_XML).get(Member.class);
	   assertEquals("John Smith", member.getName());
   }
   
   @Test
   @RunAsClient
   @InSequence(1)
   public void testInsercion() {
	   WebResource resource = client.resource(augment(contextPath, "rest/members"));
	   Member member = new Member();
	   member.setName("peperolo");
	   member.setEmail("peperolo@atitican.com");
	   member.setPhoneNumber("55533224411");
	   resource.type(MediaType.TEXT_XML).put(member);
		Member result = client.resource(augment(contextPath, "rest/members/1"))
				.accept(MediaType.TEXT_XML).get(Member.class);
	   assertEquals(1, (long)result.getId());
   }
   
   @Test
   @RunAsClient
   @InSequence(2)
   public void testRecuperacion() {
		Member result = client.resource(augment(contextPath, "rest/members/1"))
				.accept(MediaType.APPLICATION_JSON).get(Member.class);
		assertEquals(1, (long)result.getId());
   }
   
   @Test
   @RunAsClient
   @InSequence(3)
   public void testRecuperacionCruda() {
		String raw = client.resource(augment(contextPath, "rest/members/1"))
				.accept(MediaType.APPLICATION_JSON).get(String.class);
		assertEquals(
				"{\"id\":1,\"name\":\"peperolo\",\"email\":\"peperolo@atitican.com\",\"phoneNumber\":\"55533224411\"}",
				raw);
   }
   
   @RunAsClient
   @InSequence(4)
   @Test
   public void testValidation() {
	   WebResource resource = client.resource(augment(contextPath, "rest/members"));
	   Member member = new Member();
	   try {
		   resource.type(MediaType.TEXT_XML).put(member);
		   fail("Should have raised Validation Exception");
	   } catch (UniformInterfaceException e) {
		   ClientResponse response = e.getResponse();
		   assertEquals(Status.BAD_REQUEST, response.getClientResponseStatus());
		   assertTrue(response.getEntity(String.class).startsWith("Validation failed"));
	   }
   }
   
   @RunAsClient
   @InSequence(5)
   @Test
   public void testListMembers() {
	   WebResource resource = client.resource(augment(contextPath, "rest/members"));
	   List<Member> list = resource.get(new GenericType<List<Member>>(){});
	   assertEquals(2, list.size());
   }
   
   @RunAsClient
   @InSequence(6)
   @Test
   public void testListMembersAreInOrder() {
	   WebResource resource = client.resource(augment(contextPath, "rest/members"));
	   Member member = new Member();
	   member.setName("aaaa");
	   member.setEmail("aaa@atitican.com");
	   member.setPhoneNumber("55533224411");
	   resource.type(MediaType.TEXT_XML).put(member);
	   List<Member> list = resource.get(new GenericType<List<Member>>(){});
	   assertEquals(3, list.size());
	   assertTrue(list.get(0).getName().startsWith("John"));
	   assertTrue(list.get(1).getName().startsWith("peperolo"));
	   assertTrue(list.get(2).getName().startsWith("a"));
   }
}
