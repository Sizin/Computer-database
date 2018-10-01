package com.excilys.cdb.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.junit.Test;
import org.omg.Messaging.SyncScopeHelper;

import com.excilys.cdb.model.Computer;


public class ComputerCRUDTest extends JPAHibernateTest{

	
	
//	@Test
//	public void testGetAll() {
//		EntityTransaction tx = em.getTransaction();
//		tx.begin();
//		Query query = em.createQuery("SELECT count(*) FROM Computer");
//		long nbComputers = (long) query.getSingleResult();
//		ComputerDao computerDao = ComputerDao.getTestInstance();
//		int nbGet  = computerDao.getComputerCount();
//    	assertEquals(nbComputers, nbGet);
//	}
}
