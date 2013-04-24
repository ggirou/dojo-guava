package m6.dojos.guava;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import m6.dojos.guava.referential.Author;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class AuthorDatabase {
	Set<Author> authorDatabase = new LinkedHashSet<Author>();

	static final Function<Author, String> AUTHOR_TO_LOGIN = new Function<Author, String>() {
		public String apply(Author input) {
			return input.getLogin();
		}
	
	};
	public Author getByLogin(final String login) {
		Predicate<Author> predicate = new Predicate<Author>() {
			public boolean apply(Author input) {
				return Objects.equal(input.getLogin(), login);
			}
		};
		return Iterables.find(authorDatabase, predicate, null);
	}

	/**
	 * @return la Map des auteurs rangés par login
	 */
	public Map<String, Author> getAuthorsByLogin() {
		return Maps.uniqueIndex(authorDatabase, AUTHOR_TO_LOGIN);
	}


	/**
	 * @return La liste des logins des différents auteurs
	 */
	public Iterable<String> getLogins() {
		return Lists.newArrayList(Iterables.transform(authorDatabase, AUTHOR_TO_LOGIN));
	}
}
