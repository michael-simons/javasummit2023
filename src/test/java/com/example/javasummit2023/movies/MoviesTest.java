package com.example.javasummit2023.movies;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.neo4j.driver.Driver;
import org.neo4j.driver.types.TypeSystem;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MoviesTest {

	@Inject
	private MovieRepository movieRepository;

	@Inject
	Driver driver;

	@Test
	void shouldCreateMovie() {

		var movie = movieRepository.save(new Movie("Bullet Train", "A movie about vengeance."));
		assertThat(movie.getId()).isNotNull();

		var idProperty = driver.executableQuery("MATCH (n:Movie {title: $title}) RETURN n.id AS id")
			.withParameters(Map.of("title", movie.getTitle()))
			.execute()
			.records().get(0).get("id");
		assertThat(TypeSystem.getDefault().NULL().isTypeOf(idProperty)).isFalse();
	}

	@Test
	void findByTitleShouldWork() {
		var title = "The Matrix";

		assertThat(movieRepository.findByTitle(title))
			.isPresent()
			.hasValueSatisfying(movie -> {
				assertThat(movie.getTitle()).isEqualTo(title);
				assertThat(movie.getId()).isNotNull();
			});
	}
}
