package com.encode.borg.persistence;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.*;

public class BorgPersistence {

	private static EntityManagerFactory emf;
	private static Logger logger = Logger.getLogger(BorgPersistence.class.getName());

	static {
		createEntityManagerFactory();
	}

	private static void createEntityManagerFactory() {
		try {
			if (emf == null || !emf.isOpen()) {
				emf = Persistence.createEntityManagerFactory("borg.odb");
			}
		}
		catch (Exception ex) {
			logger.log(Level.SEVERE, "error connecting to the database", ex);
		}
	}

	public static EntityManager getEntityManager() {
		createEntityManagerFactory();
		return emf.createEntityManager();
	}

	public static void closeFactory() {
		emf.getCache().evictAll();
		emf.close();
	}
}
