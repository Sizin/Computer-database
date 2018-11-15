package com.excilys.cdb.persistence.hibernatePersistence;


import com.excilys.cdb.core.User;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.core.Computer;
import com.excilys.cdb.core.Role;

@Repository
public class HibernateUserDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public User getUserName(String username) {
		return sessionFactory.getCurrentSession().get(User.class, username);
	
	}

	
}
