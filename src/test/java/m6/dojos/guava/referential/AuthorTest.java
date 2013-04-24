package m6.dojos.guava.referential;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class AuthorTest {

	private Author monsieurDark;
	private Author monsieurGirou;
	private Author monsieurFraneois;
	private Author madameDark;
	private Author monsieurDarkTwin;

	@Before
	public void setUp() throws Exception {
		monsieurDark = new Author.Builder().firstName("vincent").lastName("bostoen").email("vincent.bostoen@m6.com").login("vbostoen").build();
		madameDark = new Author.Builder().firstName("madame").lastName("bostoen").email("madame.bostoen@m6.com").login("mbostoen").build();
		monsieurGirou = new Author.Builder().firstName("guillaume").lastName("girou").email("guillaume.girou@m6.com").login("ggirou").build();
		monsieurFraneois = new Author.Builder().firstName("nicolas").lastName("franéois").email("nicolas.franéois@m6.com").login("nfranéois").build();
		monsieurDarkTwin = new Author.Builder().firstName("vincent").lastName("bostoen").email("vincent.bostoen@m6.com").login("vbostoen").build();
	}

	@Test
	public void testHashCode() {
		assertThat(monsieurDark.hashCode()).isEqualTo(monsieurDarkTwin.hashCode());
		assertThat(monsieurDark.hashCode()).isNotEqualTo(monsieurGirou.hashCode());
	}

	@Test
	public void testEqualsObject() {
		assertThat(monsieurDark.equals(monsieurDarkTwin)).isTrue();
		assertThat(monsieurDark.equals(monsieurGirou)).isFalse();
		assertThat(monsieurDark.equals(null)).isFalse();
	}

	@Test
	public void testCompareTo() {
		// Egalité
		assertThat(monsieurDark.compareTo(monsieurDarkTwin)).isEqualTo(0);

		// Comparaison discriminante sur le lastname
		assertThat(monsieurDark.compareTo(monsieurGirou)).isEqualTo(-1);
		assertThat(monsieurFraneois.compareTo(monsieurGirou)).isEqualTo(-1);

		// Comparaison discriminante sur le firstname
		assertThat(monsieurDark.compareTo(madameDark)).isEqualTo(1);
		assertThat(madameDark.compareTo(monsieurDark)).isEqualTo(-1);
	}

	@Test(expected = NullPointerException.class)
	public void CompareToNull() {
		monsieurDark.compareTo(null);
	}
}
