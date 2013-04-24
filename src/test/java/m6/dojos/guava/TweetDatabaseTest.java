package m6.dojos.guava;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Set;

import m6.dojos.guava.referential.Author;
import m6.dojos.guava.referential.Tweet;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.SortedSetMultimap;

public class TweetDatabaseTest {

	private TweetDatabase tweetDatabase;
	private Author monsieurFraneois;
	private Author monsieurDark;
	private Author monsieurGirou;
	private Tweet monsieurFraneoisSecondTweet;
	private Tweet monsieurFraneoisFirstTweet;
	private Tweet monsieurDarkThirdTweet;
	private Tweet monsieurDarkFirstTweet;
	private Tweet monsieurGirouUniqueTweet;
	private Tweet monsieurDarkSecondTweet;
	private Author mademoiselleChalouli;
	private Tweet mademoiselleChalouliUniqueTweet;
	private Author monsieurHugoBoss;

	@Before
	public void setUp() {
		tweetDatabase = new TweetDatabase();
		Set<Tweet> tweets = tweetDatabase.tweets;

		monsieurDark = new Author.Builder().firstName("vincent").lastName("bostoen").email("vincent.bostoen@m6.com").login("vbostoen").build();
		monsieurGirou = new Author.Builder().firstName("guillaume").lastName("girou").email("guillaume.girou@m6.com").login("ggirou").build();
		monsieurFraneois = new Author.Builder().firstName("nicolas").lastName("franéois").email("nicolas.franéois@m6.com").login("nfranéois").build();
		mademoiselleChalouli = new Author.Builder().firstName("leila").lastName("chalouli").email("leila.chalouli@m6.com").login("lchalouli").build();
		monsieurHugoBoss = new Author.Builder().firstName("steve").lastName("bienéois").email("steve.bienaime@m6.com").login("sbienaime").build();

		tweets.add(monsieurFraneoisSecondTweet = new Tweet.Builder().author(monsieurFraneois).date(new DateTime(2011, 11, 23, 14, 23, 45))
				.tweet("Je regarde les tweets de Dr Jones ! Trop de la balle !").build());
		tweets.add(monsieurFraneoisFirstTweet = new Tweet.Builder().author(monsieurFraneois).date(new DateTime(2011, 11, 23, 14, 22, 15))
				.tweet("Je binôme avec Leila !! Je kiffe !").build());
		tweets.add(monsieurDarkFirstTweet = new Tweet.Builder().author(monsieurDark).date(new DateTime(2011, 7, 14, 10, 22, 15))
				.tweet("Le discours de Nicolas était génial !").build());
		tweets.add(monsieurGirouUniqueTweet = new Tweet.Builder().author(monsieurGirou).date(new DateTime(2011, 1, 1, 0, 0, 0))
				.tweet("Bonne année et bonne Guinness !").build());
		tweets.add(monsieurDarkThirdTweet = new Tweet.Builder().author(monsieurDark).date(new DateTime(2011, 11, 23, 23, 59, 15))
				.tweet("Apache Mahout, c'est moins bon que les duchesses !").build());
		tweets.add(monsieurDarkSecondTweet = new Tweet.Builder().author(monsieurDark).date(new DateTime(2011, 11, 23, 14, 18, 27))
				.tweet("Agnès, sans regrets !").build());
		tweets.add(mademoiselleChalouliUniqueTweet = new Tweet.Builder().author(mademoiselleChalouli).date(new DateTime(2011, 11, 23, 12, 18, 27))
				.tweet("Haaaa !! Steve est trop mignon !!").build());
	}

	@Test
	public void should_group_tweets_by_author() {
		// Given

		// When
		SortedSetMultimap<Author, Tweet> result = tweetDatabase.groupByAuthor();

		// Then
		assertThat(result).isNotNull();
		assertThat(Iterables.elementsEqual(result.keySet(), Lists.newArrayList(monsieurDark, mademoiselleChalouli, monsieurFraneois, monsieurGirou))).isTrue();
		assertThat(Iterables.elementsEqual(result.get(monsieurFraneois), Lists.newArrayList(monsieurFraneoisFirstTweet, monsieurFraneoisSecondTweet))).isTrue();
		assertThat(result.get(monsieurGirou)).containsOnly(monsieurGirouUniqueTweet);
		assertThat(
				Iterables.elementsEqual(result.get(monsieurDark), Lists.newArrayList(monsieurDarkFirstTweet, monsieurDarkSecondTweet, monsieurDarkThirdTweet)))
				.isTrue();
		assertThat(result.get(mademoiselleChalouli)).containsOnly(mademoiselleChalouliUniqueTweet);
	}

	@Test
	public void should_return_first_tweet_for_an_author() {
		// Given
		LocalDate tweetDate = new LocalDate(2011, 11, 23);

		// When
		Tweet result = tweetDatabase.getFirstAuthorTweet(monsieurFraneois, tweetDate);

		// Then
		assertThat(result).isNotNull().isSameAs(monsieurFraneoisFirstTweet);
	}

	@Test
	public void should_return_null_when_author_has_no_tweet_for_a_date() {
		// Given
		LocalDate tweetDate = new LocalDate(1910, 11, 23);

		// When
		Tweet result = tweetDatabase.getFirstAuthorTweet(monsieurFraneois, tweetDate);

		// Then
		assertThat(result).isNull();
	}

	@Test
	public void should_return_null_when_author_has_no_tweet_at_all() {
		// Given
		LocalDate tweetDate = new LocalDate(1910, 11, 23);

		// When
		Tweet result = tweetDatabase.getFirstAuthorTweet(monsieurHugoBoss, tweetDate);

		// Then
		assertThat(result).isNull();
	}

	@Test
	public void should_return_first_of_the_first_tweet_when_date_is_null() {
		// Given

		// When
		Tweet result = tweetDatabase.getFirstAuthorTweet(monsieurDark, null);

		// Then
		assertThat(result).isNotNull().isSameAs(monsieurDarkFirstTweet);
	}

	@Test
	public void should_check_if_a_tweet_with_Steve_exists() {
		// Given

		// When
		boolean result = tweetDatabase.hasAnyTweetWith("Steve");

		// Then
		assertThat(result).isTrue();
	}

	@Test
	public void should_check_if_a_tweet_with_Carla_does_not_exists() {
		// Given

		// When
		boolean result = tweetDatabase.hasAnyTweetWith("Carla");

		// Then
		assertThat(result).isFalse();
	}

	@Test
	public void should_check_if_all_tweets_has_been_posted_after_noon_for_given_date() {
		// Given
		LocalDate tweetDate = new LocalDate(2011, 11, 23);

		// When
		boolean result = tweetDatabase.areAllTweetsPostedAfterNoon(tweetDate);

		// Then
		assertThat(result).isTrue();
	}

	@Test
	public void should_check_if_all_tweets_has_been_posted_after_noon_for_given_date_is_false() {
		// Given
		LocalDate tweetDate = new LocalDate(2011, 1, 1);

		// When
		boolean result = tweetDatabase.areAllTweetsPostedAfterNoon(tweetDate);

		// Then
		assertThat(result).isFalse();
	}

	@Test
	public void should_return_authors_that_have_more_than_one_tweet_for_a_date() {
		// Given
		LocalDate tweetDate = new LocalDate(2011, 11, 23);

		// When
		Multimap<Author, Tweet> madAuthorsTweets = tweetDatabase.findMadAuthors(tweetDate);

		// then
		assertThat(madAuthorsTweets).isNotNull();
		assertThat(Iterables.elementsEqual(madAuthorsTweets.keySet(), Lists.newArrayList(monsieurDark, monsieurFraneois))).isTrue();
		assertThat(Iterables.elementsEqual(madAuthorsTweets.get(monsieurDark), Lists.newArrayList(monsieurDarkSecondTweet, monsieurDarkThirdTweet))).isTrue();
		assertThat(Iterables.elementsEqual(madAuthorsTweets.get(monsieurFraneois), Lists.newArrayList(monsieurFraneoisFirstTweet, monsieurFraneoisSecondTweet)))
				.isTrue();
	}
}
