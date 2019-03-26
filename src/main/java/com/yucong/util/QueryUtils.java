package com.yucong.util;

import javax.persistence.EntityManager;

import org.hibernate.Session;

public class QueryUtils {

	public static void aaa(EntityManager em, String sqlString) {
		Session session = em.unwrap(Session.class);
	}

}
