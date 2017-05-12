package master_spring_mvc.search;

import static org.junit.Assert.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import static org.hamcrest.Matchers.*;

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.setup.MockMvcBuilders

import master_spring_mvc.MasterSpringMvcApplication
import spock.lang.Specification

@ContextConfiguration (
	classes = MasterSpringMvcApplication
	)

@SpringBootTest
class SearchControllerMockSpec extends Specification {		
	def twitterSearch = Mock(TwitterSearch)
	def searchController = new SearchController(twitterSearch)
	def mockMvc = MockMvcBuilders.standaloneSetup(searchController).setRemoveSemicolonContent(false).build()
	def "Po wyszukaniu słowa spring powinna pojawić się strona z wynikami"() {
		when: "Szukam słowa spring"
		def response = mockMvc.perform(get("/search/mixed;keywords=spring"))
		then: "Usługa wyszukująca jest wywoływana tylko raz"		
		1* twitterSearch.search(_, _) >> [new LightTweet('Treść tweeta')]
		and: "Pojawiła się strona z wynikami"
		response
			.andExpect(status().isOk())
			.andExpect(view().name("resultPage"))			
		and: "Model zawiera znalezione tweety"
		response
			.andExpect(model().attribute("tweets", everyItem(hasProperty("text", is("Treść tweeta")))))
	}
}