package com.encode.borg.beans;

import javax.persistence.*;

@Cacheable
@Entity
@Table(name = "person_relative")
public class PersonRelative {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PersonRelative_seq")
	@SequenceGenerator(name = "PersonRelative_seq", initialValue = 1, allocationSize = 1)
	private long id;

	@Column(name = "numeGradRudenie", nullable = false)
	private String numeGradRudenie;

	@ManyToOne
	@JoinColumn(name = "personID", nullable = false)
	private Person person;

	public String getNumeGradRudenie() {
		return numeGradRudenie;
	}

	public void setNumeGradRudenie(String numeGradRudenie) {
		this.numeGradRudenie = numeGradRudenie;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public long getId() {
		return id;
	}
}
