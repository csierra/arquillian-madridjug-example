package com.bizonos.rest.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.bizonos.rest.model.Member;
import com.bizonos.rest.services.MembersService;

@RequestScoped
public class MemberListProducer {
   @Inject
   MembersService mService;

   private List<Member> members;

   // @Named provides access the return value via the EL variable name "members" in the UI (e.g.,
   // Facelets or JSP view)
   @Produces
   @Named
   public List<Member> getMembers() {
      return members;
   }

   public void onMemberListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Member member) {
      retrieveAllMembersOrderedByName();
   }

   @PostConstruct
   public void retrieveAllMembersOrderedByName() {
      members = mService.findAllByName();
   }
}
