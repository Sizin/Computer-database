package com.excilys.cdb.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Test;

import com.excilys.cdb.model.Computer;

public class ComputerCRUDTest extends JPAHibernateTest{

    @Test
    public void testPrint() {

    	EntityTransaction tx = em.getTransaction();
    	
    	tx.begin();
    	
    	Computer computer = new Computer();
    	computer.setId(12);
    	computer.setName("Rajesh3000");
    	
//    	em.persist(computer);   
    	tx.commit();
    	
    	long myId = computer.getId();

    	// Asserts that an object isn't null, if it is an 'AssertionError' is thrown with the given message
//    	assertNotNull("Id not null", myId);

    	Computer myRetrievedComputer = em.find(Computer.class, myId);
    	
    	assertNotNull("Computer Retrieved from DB", myRetrievedComputer);
    	
    	
    	
//    	assertEquals(computer.getId(), myRetrievedComputer.getName());
    	
//    	em.getTransaction().commit();
//    	em.close();
    	
    }
    
}
