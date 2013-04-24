package m6.dojos.guava.referential;

import org.joda.time.DateTime;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ComparisonChain;

public class Tweet implements Comparable<Tweet> {
	private Author author;
	private String tweet;
	private DateTime date;

	public static class Builder {
		private Author author;
		private String tweet;
		private DateTime date;

		public Builder author(Author author) {
			this.author = author;
			return this;
		}

		public Builder tweet(String tweet) {
			this.tweet = tweet;
			return this;
		}

		public Builder date(DateTime date) {
			this.date = date;
			return this;
		}

		public Tweet build() {
			return new Tweet(this);
		}
	}

	private Tweet(Builder builder) {
		this.author = builder.author;
		this.tweet = builder.tweet;
		this.date = builder.date;
	}

	public Tweet(Author author, String tweet, DateTime date) {
		this.author = author;
		this.tweet = tweet;
		this.date = date;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(author, tweet, date);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Tweet) {
			Tweet that = (Tweet) object;
			return Objects.equal(this.author, that.author) && Objects.equal(this.tweet, that.tweet) && Objects.equal(this.date, that.date);
		}
		return false;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public String getTweet() {
		return tweet;
	}

	public void setTweet(String tweet) {
		this.tweet = tweet;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	public int compareTo(Tweet secondTweet) {
		Preconditions.checkNotNull(secondTweet);
		return ComparisonChain.start().compare(this.date, secondTweet.date).result();
	}
}
