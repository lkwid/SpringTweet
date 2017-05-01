package master_spring_mvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import master_spring_mvc.profile.UserProfileSession;

@Controller
public class HomeController {
	private UserProfileSession userProfileSession;

	@Autowired
	public HomeController(UserProfileSession userProfileSession) {
		this.userProfileSession = userProfileSession;
	}

	@RequestMapping("/")
	public String home(RedirectAttributes redirectAttributes) {
		List<String> tastes = userProfileSession.toForm().getTastes();
		if (tastes.isEmpty())
			return "redirect:/profile";
		redirectAttributes.addAttribute("tweets", tastes);
		return "redirect:/search/mixed;keywords={tweets}";
	}

}
