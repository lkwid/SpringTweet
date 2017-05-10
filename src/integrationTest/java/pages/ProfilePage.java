package pages;

import static org.assertj.core.api.Assertions.assertThat;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

public class ProfilePage extends FluentPage {
	
	@FindBy(name = "addTaste")
	private FluentWebElement addTasteButton;	
	@FindBy(name = "save")
	private FluentWebElement saveButton;
	
	public String getUrl() {
		return "/profile";
	}
	
	public void isAt() {
		assertThat(find("h2").first().text()).isEqualTo("Twój profil");
	}
	
	public void fillInfos(String twitterHandle, String email, String birthDate) {
		$("#twitterHandle").fill().with(twitterHandle);
		$("#email").fill().with(email);
		$("#birthDate").fill().with(birthDate);
	}
	
	public void addTaste(String taste) {
		addTasteButton.click();
		$("#tastes0").fill().with(taste);
	}
	
	public void saveProfile() {
		saveButton.click();
	}
}
