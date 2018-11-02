package com.excilys.cdb.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

@Repository
public class HibernateCompanyDao implements HibernateCompanyDaoInterface{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Computer findById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Company> findAll() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Company";
		Query query = session.createQuery(hql);
		return query.list();
	}

}
