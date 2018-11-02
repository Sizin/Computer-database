package com.excilys.cdb.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.model.Computer;

@Repository
public class HibernateComputerDao implements HibernateComputerDaoInterface {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Computer findById(long id) {
		return sessionFactory.getCurrentSession().get(Computer.class, id);
	}

	@Override
	public List<Computer> findAll() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Computer";
		Query query = session.createQuery(hql);
		return query.list();

	}

	@Override
	public List<Computer> findAll(int offset, int range) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Computer";
		Query query = session.createQuery(hql);
		if(offset != 0 && range != 0 ) {
			int page = (offset == 1) ? 0 : offset * range;
			query.setFirstResult(page).setMaxResults(range);
		}
		return query.list();
	}
	

	@Override
	public List<Computer> findAll(int offset, int range, String search) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Computer ";
		Query query = session.createQuery(hql + "WHERE name LIKE :search" ).setParameter("search", search);
		if(offset != 0 && range != 0 ) {
			int page = (offset == 1) ? 0 : offset * range;
			query.setFirstResult(page).setMaxResults(range);
		}
		return query.list();
	}

	
	@Override
	public int findCount() {
		Session session = sessionFactory.getCurrentSession();
//		String hql = "select count(*) FROM Computer";
//		Query query = session.createQuery(hql);
//		return query.getFirstResult().intValue();
		return ((Long)session.createQuery("select count(*) from Computer").uniqueResult()).intValue();
	}

	@Override
	public int findCount(String search) {
		Session session = sessionFactory.getCurrentSession();
		return ((Long)session.createQuery("select count(*) from Computer WHERE name LIKE :search ", Long.class).setParameter("search", search).uniqueResult()).intValue();
	
	
	}

	@Override
	public void add(Computer computer) {
		Session session = sessionFactory.getCurrentSession();
		session.persist(computer);
	}

	@Override
	public void update(Computer computer) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(computer);
	}	

}
