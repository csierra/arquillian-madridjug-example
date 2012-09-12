package com.bizonos.rest.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.bizonos.rest.model.Member;
import com.bizonos.rest.services.MembersService;

/**
 * JAX-RS Example
 * 
 * This class produces a RESTful service to read the contents of the members table.
 */
@Path("/members")
@RequestScoped
public class MemberResourceRESTService {
   @Inject
   private EntityManager em;
   
   @Inject
   private MembersService mService;

   @GET
   @Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
   public List<Member> listAllMembers() {
      return mService.findAll();
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
   public Member lookupMemberById(@PathParam("id") long id) {
      return em.find(Member.class, id);
   }
   
   @PUT
   @Path("/")
   @Consumes({MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
   public void storeNewMember(Member newMember) {
	   try {
		mService.storeMember(newMember);
	} catch (Exception e) {
			throw new WebApplicationException(Response
					.status(Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
					.entity(ExceptionUtils.getRootCause(e).getMessage()).build());
	}
   }
   
   
}
