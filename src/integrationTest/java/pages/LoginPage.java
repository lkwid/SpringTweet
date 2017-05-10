package pages;

import static org.assertj.core.api.Assertions.assertThat;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends FluentPage {
	
	@FindBy(name = "twitterSignin")
	private FluentWebElement signinButton;

	public String getUrl() {
		return "/login";
	}
	
	public void isAt() {
		assertThat(find("h2").first().text()).isEqualTo("Logowanie");
	}
	
	public void login() {
		signinButton.click();
	}
	
}
