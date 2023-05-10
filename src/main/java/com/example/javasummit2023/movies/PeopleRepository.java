package com.example.javasummit2023.movies;

import org.neo4j.ogm.session.SessionFactory;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author Michael J. Simons
 */
@ApplicationScoped
public class PeopleRepository {

	private final SessionFactory sessionFactory;

	public PeopleRepository(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	Person save(Person person) {

		var session = sessionFactory.openSession();
		session.save(person);
		return person;
	}
}
