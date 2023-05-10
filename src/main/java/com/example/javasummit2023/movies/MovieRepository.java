/*
 * Copyright (c) 2002-2022 "Neo4j,"
 * Neo4j Sweden AB [http://neo4j.com]
 *
 * This file is part of Neo4j.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.javasummit2023.movies;

import java.util.Collection;
import java.util.Optional;

import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.session.SessionFactory;

import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author Michael J. Simons
 */
@ApplicationScoped
public class MovieRepository {

	private final SessionFactory sessionFactory;

	MovieRepository(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Collection<Movie> findAll() {

		return sessionFactory.openSession().loadAll(Movie.class);
	}

	public Optional<Movie> findByTitle(String title) {
		var movies = sessionFactory.openSession().loadAll(
			Movie.class,
			new Filter("title", ComparisonOperator.EQUALS, title)
		);
		return movies.isEmpty() ? Optional.empty() : Optional.of(movies.iterator().next());
	}

	public Movie save(Movie movie) {
		this.sessionFactory.openSession().save(movie);
		return movie;
	}
}
