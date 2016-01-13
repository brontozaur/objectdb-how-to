package com.encode.borg;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

import com.encode.borg.beans.*;
import com.encode.borg.persistence.BorgPersistence;

public class Main {

	public static void main(String[] args) {
		emptyDatabase();
		// createEntries();
		// printData();
		// deletePersonsUsingDeleteQuery();
		// deletePersonsUsingPersistence();
		printAllJobs();
		printAllRelatives();
	}

	private static void deletePersonsUsingPersistence() {
		EntityManager em = null;
		try {
			em = BorgPersistence.getEntityManager();
			em.getTransaction().begin();
			List<Person> persons = getPersons(em);
			for (Person p : persons) {
				printJobsForPerson(p);
				printRelativesForPerson(p);
				System.err.println("removing person : " + p.getNume() + " " + p.getPrenume() + " using persistence remove() method");
				em.remove(p);
			}
			em.getTransaction().commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		finally {
			em.close();
		}
	}

	private static void deletePersonsUsingDeleteQuery() {
		EntityManager em = null;
		try {
			em = BorgPersistence.getEntityManager();
			em.getTransaction().begin();
			Query query = em.createQuery("DELETE FROM " + Person.class.getName());
			query.executeUpdate();
			em.getTransaction().commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		finally {
			em.close();
		}
	}

	private static void printJobsForPerson(Person p) {
		List<PersonJob> jobs = p.getJobs();
		System.err.println("--------------------------JOBS size for " + (p.getNume() + " " + p.getPrenume()) + ": " + jobs.size());
		for (PersonJob pj : jobs) {
			System.err.println(pj.getNumeJob());
		}
	}

	private static void printRelativesForPerson(Person p) {
		List<PersonRelative> relatives = p.getRelatives();
		System.err.println("--------------------------RELATIVES size for " + (p.getNume() + " " + p.getPrenume()) + ": " + relatives.size());
		for (PersonRelative pr : relatives) {
			System.err.println(pr.getNumeGradRudenie());
		}
	}

	private static void printAllJobs() {
		List<PersonJob> jobs = getPersonJobs();
		System.err.println("JOBS size: " + jobs.size());
		for (PersonJob pj : jobs) {
			System.err.println(pj.getPerson().getNume() + " " + pj.getPerson().getPrenume() + " WORKS AS: " + pj.getNumeJob());
		}
	}

	private static void printAllRelatives() {
		List<PersonRelative> relatives = getPersonRelatives();
		System.err.println("RELATIVES size: " + relatives.size());
		for (PersonRelative pj : relatives) {
			System.err.println(pj.getPerson().getNume() + " " + pj.getPerson().getPrenume() + " has relative: " + pj.getNumeGradRudenie());
		}
	}

	private static void printData() {
		EntityManager em = null;
		try {
			em = BorgPersistence.getEntityManager();
			List<Person> persons = getPersons(em);
		for (Person person : persons) {
			int i = 0;
			for (PersonJob pj : person.getJobs()) {
				System.err.println(person.getNume() + " job#" + (++i) + " " + pj.getNumeJob());
			}
			i = 0;
			for (PersonRelative pr : person.getRelatives()) {
				System.err.println(person.getNume() + " relative#" + (++i) + " " + pr.getNumeGradRudenie());
			}
		}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			em.close();
		}
	}

	private static void createEntries() {
		EntityManager em = BorgPersistence.getEntityManager();
		em.getTransaction().begin();
		Person p = createPerson("Lord", "Hypercube", "Boston");
		em.persist(p);

		// creating jobs and relative for person#1
		PersonJob pj = createPersonJob("fabricant de conserve", p);
		em.persist(pj);

		pj = createPersonJob("vanzator la alimentara", p);
		em.persist(pj);

		PersonRelative pr = createPersonRelative("var", p);
		em.persist(pr);

		pr = createPersonRelative("nepot", p);
		em.persist(pr);

		p = createPerson("Sir", "Scarface", "Atlanta");
		em.persist(p);

		// creating jobs and relative for person#2
		pj = createPersonJob("bagabont", p);
		em.persist(pj);

		pr = createPersonRelative("unchi", p);
		em.persist(pr);

		em.getTransaction().commit();
		em.close();
		BorgPersistence.closeFactory();
	}

	private static PersonJob createPersonJob(String numeJob, Person p) {
		PersonJob pj = new PersonJob();
		pj.setCurrency("EUR");
		pj.setNumeJob(numeJob);
		pj.setPerson(p);
		pj.setSalary(100);
		return pj;
	}

	private static PersonRelative createPersonRelative(String numeGradRudenie, Person p) {
		PersonRelative pr = new PersonRelative();
		pr.setNumeGradRudenie(numeGradRudenie);
		pr.setPerson(p);
		return pr;
	}

	private static Person createPerson(String nume, String prenume, String oras) {
		Person p = new Person();
		p.setAdresa(oras);
		p.setNume(nume);
		p.setPrenume(prenume);
		p.setServerTimestamp(new Timestamp(System.currentTimeMillis()));
		return p;
	}

	private static List<Person> getPersons(EntityManager em) {
		try {
			TypedQuery<Person> query = em.createQuery("SELECT ci FROM Person ci ORDER BY ci.nume", Person.class);
			return query.getResultList();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static List<PersonJob> getPersonJobs() {
		EntityManager em = null;
		try {
			em = BorgPersistence.getEntityManager();
			TypedQuery<PersonJob> query = em.createQuery("SELECT ci FROM PersonJob ci ORDER BY ci.personx.idUser", PersonJob.class);
			return query.getResultList();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally {
			em.close();
		}
	}

	private static List<PersonRelative> getPersonRelatives() {
		EntityManager em = null;
		try {
			em = BorgPersistence.getEntityManager();
			TypedQuery<PersonRelative> query = em.createQuery("SELECT ci FROM PersonRelative ci ORDER by ci.person.idUser", PersonRelative.class);
			return query.getResultList();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally {
			em.close();
		}
	}

	private static void emptyDatabase() {
		EntityManager em = null;
		try {
			em = BorgPersistence.getEntityManager();
			em.getTransaction().begin();
			Query query = em.createQuery("DELETE FROM " + Person.class.getName());
			query.executeUpdate();
			// query = em.createQuery("DELETE FROM " + PersonJob.class.getName());
			// query.executeUpdate();
			// query = em.createQuery("DELETE FROM " + PersonRelative.class.getName());
			// query.executeUpdate();
			em.getTransaction().commit();
		}
		catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		finally {
			em.close();
		}
	}

}
