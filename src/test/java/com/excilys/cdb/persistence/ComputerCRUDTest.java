package com.excilys.cdb.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.omg.Messaging.SyncScopeHelper;

import com.excilys.cdb.app.TestRunner;
import com.excilys.cdb.model.Computer;


public class ComputerCRUDTest extends JPAHibernateTest{

	private static Logger logger = Logger.getLogger(ComputerCRUDTest.class);
	
	
	@Test
	public void testGetAll() {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Query query = em.createQuery("SELECT count(*) FROM Computer");
		
		ComputerDao computerDao = ComputerDao.getTestInstance();
		
		long nbComputers = (long) query.getSingleResult();
		
		int nb = computerDao.getComputerCount();
		System.out.println(nb);
		
//		ComputerDao computerDao = ComputerDao.getTestInstance();
//		int nbGet  = computerDao.getComputerCount();
//    	assertEquals(nbComputers, nbGet);
	}
}
