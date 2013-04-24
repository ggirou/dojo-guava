package m6.dojos.guava.referential;

import static org.fest.assertions.Assertions.assertThat;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

public class TweetTest {

	private Tweet firstTweet;
	private Tweet secondTweet;
	private Tweet sameFirstTweet;
	private Author monsieurFraneois;
	private Author monsieurDark;
	private Tweet firstTweetDifferentAuthor;
	private Tweet firstTweetDifferentDate;
	private Tweet firstTweetDifferentTweet;

	@Before
	public void setUp() {
		monsieurFraneois = new Author.Builder().email("nicolas.franéois@m6.com").login("nfranéois").build();
		monsieurDark = new Author.Builder().email("vincent.bostoen@m6.com").login("vbostoen").build();

		firstTweet = new Tweet.Builder().author(monsieurFraneois).date(new DateTime(2011, 11, 23, 14, 23, 45)).tweet("toto").build();
		sameFirstTweet = new Tweet.Builder().author(monsieurFraneois).date(new DateTime(2011, 11, 23, 14, 23, 45)).tweet("toto").build();
		firstTweetDifferentAuthor = new Tweet.Builder().author(monsieurDark).date(new DateTime(2011, 11, 23, 14, 23, 45)).tweet("toto").build();
		firstTweetDifferentDate = new Tweet.Builder().author(monsieurFraneois).date(new DateTime(2011, 11, 23, 14, 22, 15)).tweet("toto").build();
		firstTweetDifferentTweet = new Tweet.Builder().author(monsieurFraneois).date(new DateTime(2011, 11, 23, 14, 23, 45)).tweet("toto yeah").build();

		secondTweet = new Tweet.Builder().author(monsieurFraneois).date(new DateTime(2011, 11, 23, 14, 22, 15)).tweet("tata").build();
	}

	@Test
	public void testHashCode() {
		assertThat(firstTweet.hashCode()).isEqualTo(sameFirstTweet.hashCode());
		assertThat(firstTweet.hashCode()).isNotEqualTo(firstTweetDifferentAuthor.hashCode());
		assertThat(firstTweet.hashCode()).isNotEqualTo(firstTweetDifferentDate.hashCode());
		assertThat(firstTweet.hashCode()).isNotEqualTo(firstTweetDifferentTweet.hashCode());
	}

	@Test
	public void testEqualsObject() {
		assertThat(firstTweet.equals(sameFirstTweet)).isTrue();
		assertThat(firstTweet.equals(firstTweetDifferentAuthor)).isFalse();
		assertThat(firstTweet.equals(firstTweetDifferentDate)).isFalse();
		assertThat(firstTweet.equals(firstTweetDifferentTweet)).isFalse();
		assertThat(firstTweet.equals(null)).isFalse();
	}

	@Test
	public void testCompareTo() {
		assertThat(firstTweet.compareTo(secondTweet)).isEqualTo(1);
		assertThat(secondTweet.compareTo(firstTweet)).isEqualTo(-1);
		assertThat(firstTweet.compareTo(sameFirstTweet)).isEqualTo(0);
	}
	
	@Test(expected=NullPointerException.class)
	public void testCompareToNull(){
		assertThat(firstTweet.compareTo(null)).isEqualTo(-1);
	}

}
