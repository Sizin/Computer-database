package com.excilys.cdb.persistence.hibernatePersistence;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.core.Computer;

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
		return session.createQuery(SELECT_ALL).list();
	}

	@Override
	public List<Computer> findAll(int offset, int range) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(SELECT_ALL);
		if(offset != 0 && range != 0 ) {
			int page = (offset == 1) ? 0 : offset * range;
			query.setFirstResult(page).setMaxResults(range);
		}
		return query.list();
	}
	

	@Override
	public List<Computer> findAll(int offset, int range, String search) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(SELECT_WHERE_NAME).setParameter("search", '%'+search+'%');
		if(offset != 0 && range != 0 ) {
			int page = (offset == 1) ? 0 : offset * range;
			query.setFirstResult(page).setMaxResults(range);
		}
		return query.list();
	}

	@Override
	public int findCount() {
		Session session = sessionFactory.getCurrentSession();
		return ((Long)session.createQuery(COUNT_ALL).uniqueResult()).intValue();
	}

	@Override
	public int findCount(String search) {
		Session session = sessionFactory.getCurrentSession();
		return ((Long)session.createQuery(COUNT_WHERE_NAME).setParameter("search", '%'+search+'%').uniqueResult()).intValue();	
	}

	@Override
	public void add(Computer computer) {
		Session session = sessionFactory.getCurrentSession();
		session.save(computer);
	}

	@Override
	public void update(Computer computer) {
		Session session = sessionFactory.getCurrentSession();
		session.update(computer);
	}	

	@Override
	public void delete(Computer computer) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(computer);
	}

}
