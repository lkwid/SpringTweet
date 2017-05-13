package geb.pages

import geb.Page

class LoginPage extends Page {
	static url = '/login?lang=pl'	
	static at = { $('h2', 0).text() == 'Logowanie' }
	static content = {
		twitterSignin { $('button', name: 'twitterSignin') }
	}		
	void loginWithTwitter() {
		twitterSignin.click()
	} 

}
