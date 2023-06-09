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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import org.neo4j.ogm.id.UuidStrategy;
import org.neo4j.ogm.typeconversion.UuidStringConverter;

/**
 * @author Michael J. Simons
 */
@NodeEntity
public final class Movie {

	@Id @GeneratedValue(strategy = UuidStrategy.class)
	@Convert(UuidStringConverter.class)
	private UUID id;

	private String title;

	@Property("tagline")
	private String description;

	private Integer released;

	@Relationship(value = "ACTED_IN", direction = Relationship.Direction.INCOMING)
	private List<Actor> actors = new ArrayList<>();

	@Relationship(value = "DIRECTED", direction = Relationship.Direction.INCOMING)
	private List<Person> directors = new ArrayList<>();

	public Movie(String title, String description) {
		this.title = title;
		this.description = description;
	}

	/**
	 * Make OGM happy.
	 */
	Movie() {
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public List<Actor> getActors() {
		return Collections.unmodifiableList(this.actors);
	}

	public List<Person> getDirectors() {
		return Collections.unmodifiableList(this.directors);
	}

	public Integer getReleased() {
		return released;
	}

	public void setReleased(Integer released) {
		this.released = released;
	}

	public Movie addActors(Collection<Actor> actors) {
		this.actors.addAll(actors);
		return this;
	}

	public Movie addDirectors(Collection<Person> directors) {
		this.directors.addAll(directors);
		return this;
	}

	public UUID getId() {
		return id;
	}
}
