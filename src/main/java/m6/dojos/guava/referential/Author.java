package m6.dojos.guava.referential;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

public class Author implements Comparable<Author>{
	private String firstName;
	private String lastName;
	private String login;
	private String email;

	public static class Builder {
		private String login;
		private String email;
		private String firstName;
		private String lastName;

		public Builder login(String login) {
			this.login = login;
			return this;
		}

		public Builder email(String email) {
			this.email = email;
			return this;
		}

		public Builder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public Builder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public Author build() {
			return new Author(this);
		}
	}

	private Author(Builder builder) {
		this.login = builder.login;
		this.email = builder.email;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
	}

	public Author(String firstName, String lastName, String login, String mail) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.login = login;
		this.email = mail;
	}
	
	
	public int compareTo(Author o) {
		return ComparisonChain.start().compare(getLastName(), o.getLastName())
				.compare(getFirstName(), o.getFirstName()).result();
	}
	

	@Override
	public int hashCode() {
		return Objects.hashCode(login);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Author) {
			Author that = (Author) object;
			return Objects.equal(this.login, that.login);
		}
		return false;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	
}
