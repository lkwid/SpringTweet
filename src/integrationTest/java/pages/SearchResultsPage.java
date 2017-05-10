package pages;

import static org.assertj.core.api.Assertions.assertThat;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import com.google.common.base.Joiner;

public class SearchResultsPage extends FluentPage {
	
	@FindBy(css = "ul.collection")
	private FluentWebElement resultList;
	
	public void isAt(String ... keywords) {
		assertThat(find("h2").first().text()).isEqualTo("Wyniki wyszukiwania " + Joiner.on(",").join(keywords));
	}
	
	public int getNumberOfResults() {
		return resultList.find("li").size();
	}

}
