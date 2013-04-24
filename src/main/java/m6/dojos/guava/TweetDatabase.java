package m6.dojos.guava;

import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import m6.dojos.guava.referential.Author;
import m6.dojos.guava.referential.Tweet;

import org.joda.time.LocalDate;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Ordering;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;

public class TweetDatabase {
	private final class DatePredicate implements Predicate<Tweet> {
		private final LocalDate tweetDate;

		private DatePredicate(LocalDate tweetDate) {
			this.tweetDate = tweetDate;
		}

		public boolean apply(Tweet input) {
			// TODO Auto-generated method stub
			return input.getDate().toLocalDate().equals(tweetDate);
		}
	}

	/**
	 * Fonction permettant de récupérer l'auteur d'un tweet
	 */
	private static final Function<Tweet, Author> tweetToAuthor = new Function<Tweet, Author>() {
		public Author apply(Tweet input) {
			return input.getAuthor();
		}
	};

	/**
	 * La base de données des tweets
	 */
	Set<Tweet> tweets = Sets.newHashSet();

	/**
	 * Utilise le Set des Tweets et applique une transformation pour les rangés par leurs auteurs On utilise ensuite un
	 * TreeMultiMap pour que les tweets soient triés
	 * 
	 * @return les tweets triés rangés par auteurs
	 */
	public SortedSetMultimap<Author, Tweet> groupByAuthor() {
		return TreeMultimap.create(Multimaps.index(tweets, tweetToAuthor));
	}

	/**
	 * Récupère le premier tweet d'un auteur pour une date
	 * 
	 * @param author
	 * @param tweetDate
	 * @return le premier tweet
	 */
	public Tweet getFirstAuthorTweet(final Author author, final LocalDate tweetDate) {
		// Pas d'implémentation d'un nouveau prédicat pour rechercher l'auteur
		Predicate<Tweet> predicates = Predicates.compose(Predicates.equalTo(author), tweetToAuthor);
		if (tweetDate != null) {
			predicates = Predicates.and(predicates, new DatePredicate(tweetDate));
		}

		Iterable<Tweet> filteredTweets = Iterables.filter(tweets, predicates);

		try {
			// Utilisation du ordering imposée
			return Ordering.natural().min(filteredTweets);
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	/**
	 * Recherche parmis les tweets s'il en existe au moins un qui contient le texte text
	 * 
	 * @param text
	 *            le texte recherché
	 * @return true si le texte a été trouvé et devinez quoi si ce n'est pas le cas
	 */
	public boolean hasAnyTweetWith(String text) {
		// Pas d'implémentation d'un nouveau prédicat pour ça !!!
		return false;
	}

	/**
	 * Vérifie si tous les tweets postés à une date, l'ont été après midi.
	 * 
	 * @param tweetDate
	 *            la date à vérifier
	 * @return true si tous les tweets ont été postés après midi et devinez quoi si ce n'est pas le cas
	 */
	public boolean areAllTweetsPostedAfterNoon(LocalDate tweetDate) {
		// Implémenter deux prédicats
		return false;
	}

	/**
	 * Cherche les auteurs fous ! qui ont twitté au moins deux fois pour la date donnée.
	 * 
	 * @param tweetDate
	 *            la date de tweet recherchée
	 * @return les auteurs associés à leurs tweets trouvés
	 */
	public SetMultimap<Author, Tweet> findMadAuthors(final LocalDate tweetDate) {
		// On récupère les tweets pour la date donnée

		// On créé une structure pour compter le nombre de tweets par auteur

		// Filtrer les auteurs ayant au moins deux tweets

		// On transforme le tout en une implémentation de SetMultimap pour ranger les tweets par auteur de manière triée
		return null;
	}

	/**
	 * Solution old school (pas si mal)
	 * 
	 * @param tweetDate
	 * @return
	 */
	public Map<Author, Set<Tweet>> findMadAuthorsWithoutGuava(LocalDate tweetDate) {
		Map<Author, Set<Tweet>> workedMap = new TreeMap<Author, Set<Tweet>>();
		Map<Author, Set<Tweet>> result = new TreeMap<Author, Set<Tweet>>();

		for (Tweet tweet : tweets) {
			if (tweet.getDate().toLocalDate().equals(tweetDate)) {
				Author author = tweet.getAuthor();
				if (!workedMap.containsKey(author)) {
					workedMap.put(author, new TreeSet<Tweet>());
				}
				Set<Tweet> authorTweets = workedMap.get(author);
				authorTweets.add(tweet);
			}
		}

		for (Entry<Author, Set<Tweet>> entry : workedMap.entrySet()) {
			if (entry.getValue().size() >= 2) {
				result.put(entry.getKey(), entry.getValue());
			}
		}

		return result;
	}
}
