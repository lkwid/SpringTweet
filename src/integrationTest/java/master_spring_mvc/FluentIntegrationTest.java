package master_spring_mvc;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.fluentlenium.adapter.junit.FluentTest;
import org.fluentlenium.core.annotation.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import master_spring_mvc.search.StubTwitterSearchConfig;
import pages.LoginPage;
import pages.ProfilePage;
import pages.SearchResultsPage;;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes= {
		MasterSpringMvcApplication.class,
		StubTwitterSearchConfig.class
})
public class FluentIntegrationTest extends FluentTest {
	
	@Page
	private LoginPage loginPage;
	@Page
	private ProfilePage profilePage;
	@Page
	private SearchResultsPage searchResultsPage;
	
	@Value("${local.server.port}")
	private int serverPort;		
	
	@Override
	public String getBaseUrl() {
		return "http://localhost:" + serverPort;
	}

	@Override
	public WebDriver newWebDriver() {
		File file = new File("./operadriver.exe");
		System.setProperty("webdriver.opera.driver", file.getAbsolutePath());
		return new OperaDriver();
	}
	
	@Test
	public void hasPageTitle() {
		goTo(getBaseUrl());
		assertThat(window().title()).isEqualTo("Zaloguj");
	}
	
	@Test
	public void shouldBeRedirectedAfterFillingForm() {
		goTo(getBaseUrl());
		loginPage.isAt();
		loginPage.login();
		profilePage.isAt();
		profilePage.fillInfos("programista", "programista@adrespoczty.pl", "1987-03-19");
		profilePage.addTaste("spring");
		profilePage.saveProfile();
		takeScreenShot();
		searchResultsPage.isAt();
		assertThat(searchResultsPage.getNumberOfResults()).isEqualTo(2);
	}	
	
	/*	@Test
	public void shouldBeRedirectedAfterFillingForm() {
		goTo("http://localhost:" + serverPort);
		assertThat(find("h2").first().text()).isEqualTo("Logowanie");
		$("button", withName("twitterSignin")).submit();
		assertThat(find("h2").first().text()).isEqualTo("Twój profil");
		$("#twitterHandle").fill().with("programista");
		$("#email").fill().with("programista@adrespoczty.pl");
		$("#birthDate").fill().with("1987-03-19");
		$("button", withName("addTaste")).click();		
		$("#tastes0").fill().with("opera");
		$("button", withName("save")).click();		
		takeScreenShot("test.jpg");
		assertThat(find("h2").first().text()).isEqualTo("Wyniki wyszukiwania opera");
		assertThat(find("ul.collection").find("li")).hasSize(2);		
	} */	

}
