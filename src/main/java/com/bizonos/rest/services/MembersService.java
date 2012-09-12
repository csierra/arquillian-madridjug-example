package com.bizonos.rest.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.bizonos.rest.model.Member;

@Stateless
public class MembersService {
	
	@PersistenceContext
	EntityManager em;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void storeMember(Member member) {
		em.persist(member);
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<Member> findAll() {
		Query query = em.createNamedQuery("findAllOrderById", Member.class);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<Member> findAllByName() {
		Query query = em.createNamedQuery("findAllOrderByName", Member.class);
		return query.getResultList();
	}
}
