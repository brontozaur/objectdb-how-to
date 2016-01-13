package com.encode.borg.beans;

import javax.persistence.*;

@Cacheable
@Entity
@Table(name = "person_job")
public class PersonJob {

	public final static String DEFAULT_CURRENCY = "EUR";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PersonJob_seq")
	@SequenceGenerator(name = "PersonJob_seq", initialValue = 1, allocationSize = 1)
	private long idPersonJob;

	@Column(name = "numeJob", nullable = false)
	private String numeJob;

	@Column(name = "salary", precision = 2)
	private int salary;

	@Column(name = "currency", precision = 2)
	private String currency;

	@ManyToOne
	@JoinColumn(name = "personID", nullable = false)
	private Person personx;

	public String getNumeJob() {
		return numeJob;
	}

	public void setNumeJob(String numeJob) {
		this.numeJob = numeJob;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Person getPerson() {
		return personx;
	}

	public long getIdPersonJob() {
		return idPersonJob;
	}

	public void setPerson(Person person) {
		this.personx = person;
	}
}
