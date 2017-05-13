package geb.pages

import geb.Page

class ProfilePage extends Page {
	static url = '/profile?lang=pl'
	static at = { $('h2', 0).text() == 'Twój profil' }
	static content = {
		addTasteButton { $('button', name: 'addTaste') }
		saveButton { $('button', name: 'save') }			
		}
	
	void fillInfos(String twitterHandle, String email, String birthDate) {
		$('#twitterHandle') << twitterHandle
		$('#email') << email
		$('#birthDate') << birthDate		
	}
	
	void addTaste(String taste) {
		addTasteButton.click()
		$('#tastes0') << taste
	}
	
	void saveProfile() {
		saveButton.click()
	}

}
