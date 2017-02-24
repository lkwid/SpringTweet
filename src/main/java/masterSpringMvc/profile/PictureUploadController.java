package masterSpringMvc.profile;

import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import masterSpringMvc.config.PictureUploadProperties;

@Controller
@SessionAttributes("picturePath")
public class PictureUploadController {
	private final Resource picturesDir;
	private final Resource anonymousPicture;

	@Autowired
	public PictureUploadController(PictureUploadProperties uploadProperties) {
		picturesDir = uploadProperties.getUploadPath();
		anonymousPicture = uploadProperties.getAnonymousPicture();
	}

	@ModelAttribute("picturePath")
	public Resource picturePath() {
		return anonymousPicture;
	}

	@RequestMapping("upload")
	public String uploadPage() {
		return "profile/uploadPage";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String onUpload(MultipartFile file, RedirectAttributes redirectAttrs, Model model) throws IOException {
		if (file.isEmpty() || !isImage(file)) {
			redirectAttrs.addFlashAttribute("error", "Niewłaściwy plik. Załaduj plik z obrazem");
			return "redirect:/upload";
		}
		Resource picturePath = copyFileToPicture(file);
		model.addAttribute("picturePath", picturePath);
		return "profile/uploadPage";
	}

	@RequestMapping(value = "/uploadedPicture")
	public void getUploadedPicture(HttpServletResponse response, @ModelAttribute("picturePath") Resource picturePath)
			throws IOException {
		response.setHeader("Content-Type", URLConnection.guessContentTypeFromName(picturePath.toString()));
		Path path = Paths.get(picturePath.getURI());
		Files.copy(path, response.getOutputStream());
	}

	private Resource copyFileToPicture(MultipartFile file) throws IOException {
		String fileExtension = getFileExtension(file.getOriginalFilename());
		File tempFile = File.createTempFile("pic", fileExtension, picturesDir.getFile());
		try (InputStream in = file.getInputStream(); OutputStream out = new FileOutputStream(tempFile)) {
			IOUtils.copy(in, out);
		}
		return new FileSystemResource(tempFile);
	}

	private boolean isImage(MultipartFile file) {
		return file.getContentType().startsWith("image");
	}

	private static String getFileExtension(String name) {
		return name.substring(name.lastIndexOf("."));
	}

}
