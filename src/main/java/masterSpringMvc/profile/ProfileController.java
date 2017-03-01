package masterSpringMvc.profile;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import masterSpringMvc.date.USLocalDateFormatter;

@Controller
public class ProfileController {
	private UserProfileSession userProfileSession;

	@Autowired
	public ProfileController(UserProfileSession userProfileSession) {
		this.userProfileSession = userProfileSession;
	}

	@ModelAttribute
	public ProfileForm getProfileForm() {
		return userProfileSession.toForm();
	}

	@ModelAttribute("dateFormat")
	public String localFormat(Locale locale) {
		return USLocalDateFormatter.getPattern(locale);
	}
	
	@RequestMapping("/profile")
	public String displayProfile() {
		return "/profile/profilePage";
	}

	@RequestMapping(value = "/profile", params = { "save" }, method = RequestMethod.POST)
	public String saveProfile(@Valid ProfileForm profileForm, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "profile/profilePage";
		}
		userProfileSession.saveForm(profileForm);
		redirectAttributes.addAttribute("tweets", profileForm.getTastes());
		return "redirect:/search/mixed;keywords={tweets}";
	}

	@RequestMapping(value = "/profile", params = { "addTaste" })
	public String addRow(ProfileForm profileForm) {
		profileForm.getTastes().add(null);
		return "profile/profilePage";
	}

	@RequestMapping(value = "/profile", params = { "removeTaste" })
	public String removeRow(ProfileForm profileForm, HttpServletRequest req) {
		Integer rowId = Integer.valueOf(req.getParameter("removeTaste"));
		profileForm.getTastes().remove(rowId.intValue());
		return "profile/profilePage";
	}

}
