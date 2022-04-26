package com.shopme.admin.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.FileUploadUtil;
import com.shopme.admin.security.shopmeUserDetails;
import com.shopme.admin.service.UserService;
import com.shopme.common.entity.User;

@Controller
public class AccountController {
	
	@Autowired
	private UserService userServices;
	
	@GetMapping("/account")
	public String updateAccount(Model model, @AuthenticationPrincipal shopmeUserDetails loggedUser) {
		String email = loggedUser.getUsername();;
		
		User user = userServices.getUserByEmail(email);
		model.addAttribute(user);
		return "account_form";
	}

	@PostMapping("/account/update")
	public String saveUser(User user, @AuthenticationPrincipal shopmeUserDetails loggedUser, RedirectAttributes redirectAttributes, @RequestParam("image") MultipartFile multipartFile) throws IOException {
		redirectAttributes.addFlashAttribute("message", "Your account saved successfully");
		
		if(!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhotos(fileName);
			User saved = userServices.updateAccount(user);
			String uploadDir = "user-photos/" + saved.getId();
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} else {
			if(user.getPhotos().isEmpty()) user.setPhotos(null);
			userServices.updateAccount(user);
		}
		
		loggedUser.setFirstName(user.getFirstName());
		loggedUser.setLastName(user.getLastName());

		return "redirect:/account";
	}

}
