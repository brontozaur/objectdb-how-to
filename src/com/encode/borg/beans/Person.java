package com.encode.borg.beans;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

@Cacheable
@Entity
@Table(name = "person")
@NamedQueries({
	@NamedQuery(name = "Person.findAll", query = "from Person") })
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Person_seq")
	@SequenceGenerator(name = "Person_seq", initialValue = 1, allocationSize = 1)
	private long idUser = 0;

	@Column(name = "nume", nullable = false)
	private String nume;

	@Column(name = "prenume")
	private String prenume;

	@Column(name = "adresa")
	private String adresa;

	@Column(name = "data")
	private Date data;

	@Column(name = "serverTimestamp")
	private Timestamp serverTimestamp;

	@Lob
	@Column(name = "image", length = 10_000_000)
	private byte[] image;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "personx", fetch = FetchType.EAGER, orphanRemoval = true)
	List<PersonJob> jobs;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "person", fetch = FetchType.EAGER, orphanRemoval = true)
	List<PersonRelative> relatives;

	public String getAdresa() {
		return this.adresa;
	}

	public void setAdresa(final String adresa) {
		this.adresa = adresa;
	}

	public final String getNume() {
		return this.nume;
	}

	public final void setNume(final String nume) {
		this.nume = nume;
	}

	public final String getPrenume() {
		return this.prenume;
	}

	public final void setPrenume(final String prenume) {
		this.prenume = prenume;
	}

	public void setData(final Date data) {
		this.data = data;
	}

	public Date getData() {
		return this.data;
	}

	public final Timestamp getServerTimestamp() {
		return this.serverTimestamp;
	}

	public final void setServerTimestamp(final Timestamp serverTimestamp) {
		this.serverTimestamp = serverTimestamp;
	}

	public final long getIdUser() {
		return this.idUser;
	}

	public final void setIdUser(final long idUser) {
		this.idUser = idUser;
	}

	public List<PersonJob> getJobs() {
		return jobs;
	}

	public List<PersonRelative> getRelatives() {
		return relatives;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
}
