package m6.dojos.guava;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.Set;

import m6.dojos.guava.referential.Author;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class AuthorDatabaseTest {
	private AuthorDatabase authorDatabase;
	private Author monsieurFraneois;
	private Author monsieurDark;
	private Author monsieurGirou;
	private Author mademoiselleChalouli;

	@Before
	public void setUp() {
		authorDatabase = new AuthorDatabase();

		Set<Author> authorSet = authorDatabase.authorDatabase;
		authorSet.add(monsieurDark = new Author.Builder().email("vincent.bostoen@m6.com").login("vbostoen").build());
		authorSet.add(monsieurGirou = new Author.Builder().email("guillaume.girou@m6.com").login("ggirou").build());
		authorSet.add(monsieurFraneois = new Author.Builder().email("nicolas.franéois@m6.com").login("nfranéois").build());
		authorSet.add(mademoiselleChalouli = new Author.Builder().email("leila.chalouli@m6.com").login("lchalouli").build());

	}

	@Test
	public void should_return_author_by_login() {
		// Given

		// When
		Author author = authorDatabase.getByLogin(monsieurFraneois.getLogin());

		// Then
		assertThat(author).isSameAs(monsieurFraneois);
	}

	@Test
	public void should_return_null_if_not_found_by_login() {
		// Given
		String unknownLogin = "un inconnu";

		// When
		Author author = authorDatabase.getByLogin(unknownLogin);

		// Then
		assertThat(author).isNull();
	}

	@Test
	public void should_return_authors_by_login() {
		// GIVEN
		ImmutableMap<String, Author> expected = ImmutableMap.of(monsieurDark.getLogin(), monsieurDark, monsieurGirou.getLogin(), monsieurGirou,
				monsieurFraneois.getLogin(), monsieurFraneois, mademoiselleChalouli.getLogin(), mademoiselleChalouli);

		// WHEN
		Map<String, Author> result = authorDatabase.getAuthorsByLogin();

		// THEN
		assertThat(result).isEqualTo(expected);
	}

	@Test
	public void should_return_login_list_from_authors() {
		// Given
		Iterable<String> expected = Lists.newArrayList("vbostoen", "ggirou", "nfranéois", "lchalouli");

		// When
		Iterable<String> result = authorDatabase.getLogins();
		List<String> resultList = Lists.newArrayList(result);

		authorDatabase.authorDatabase.add(new Author.Builder().login("coucou").build());

		// Then
		assertThat(Iterables.elementsEqual(resultList, expected)).isTrue();
		assertThat(Iterables.elementsEqual(result, expected)).isTrue();
	}
}
