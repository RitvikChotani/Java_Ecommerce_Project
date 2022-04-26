package com.shopme.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.FileUploadUtil;
import com.shopme.admin.exception.UserNotFoundException;
import com.shopme.admin.export.UserCsvExporter;
import com.shopme.admin.export.UserExcelExporter;
import com.shopme.admin.export.UserPDFExporter;
import com.shopme.admin.service.UserService;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public String listFirstPage(Model model) {
		
		return listAllByPage(1, model, "firstName", "asc", null);

	}
	
	
	@GetMapping("/users/page/{pagenum}")
	public String listAllByPage(@PathVariable(name="pagenum") int pagenum, Model model, @Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword) {
		Page<User> page = userService.listAllByPage(pagenum, sortField, sortDir, keyword);
		List<User> users = page.getContent();

		long startCount = (pagenum - 1) * userService.USER_PER_PAGE + 1;
		long endCount = startCount + userService.USER_PER_PAGE - 1;
		endCount = endCount > page.getTotalPages() ? page.getTotalElements() : endCount;

		String reverseSortDir =  sortDir.equals("asc") ? "des" : "asc";
		
		model.addAttribute("users", users);
		model.addAttribute("totalElements",page.getTotalElements());
		model.addAttribute("currentPage", pagenum);
		model.addAttribute("endCount", endCount);
		model.addAttribute("startCount",startCount);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("keyword", keyword);
		
		return "users";
	}
	
	@GetMapping("/users/new")
	public String newUser(Model model) {
		List<Role> listRole = userService.listAllRole();
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("listRole", listRole);
		model.addAttribute("title", "Create new user - Admin Control Panel");
		return "user_form";
	}
	
	@PostMapping("/users/save")
	public String saveUser(User user, RedirectAttributes redirectAttributes, @RequestParam("image") MultipartFile multipartFile) throws IOException {
		redirectAttributes.addFlashAttribute("message", "User saved successfully");
		
		if(!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhotos(fileName);
			User saved = userService.saveUser(user);
			String uploadDir = "user-photos/" + saved.getId();
			FileUploadUtil.cleanDir(uploadDir);
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		} else {
			if(user.getPhotos().isEmpty()) user.setPhotos(null);
			userService.saveUser(user);
		}

		return "redirect:/users/page/1?sortField=id&sortDir=asc&keyword=" + user.getEmail();
	}
	
	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable(name="id") Integer id, RedirectAttributes redirectAttributes,
			Model model) {
		try {
			User user = userService.getById(id);
			model.addAttribute("user",user);
			List<Role> listRole = userService.listAllRole();
			model.addAttribute("listRole", listRole);
			model.addAttribute("title", "Edit user ID(" + id +") - Admin Control Panel");
			redirectAttributes.addFlashAttribute("message", "User with ID(" +id +") has been updated successfully");
			return "user_form";
		} catch (UserNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return "redirect:/users";
		}
	}
	
	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable(name="id") Integer id, RedirectAttributes redirectAttributes,
			Model model) {
		try {
			userService.deleteUser(id);
			redirectAttributes.addFlashAttribute("message", "User with ID " +id +" has been deleted successfully");
		} catch (UserNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/users";
	}
	
	@GetMapping("/users/{id}/enabled/{status}")
	public String updateUserEnabledStatus(@PathVariable("id") Integer id, 
			@PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
		userService.enableUser(id, enabled);
		redirectAttributes.addFlashAttribute("message", "User with ID " +id +" has been " + (enabled ? "enabled" : "disabled") + " successfully");
		return "redirect:/users";
	}
	
	@GetMapping("/users/exportCSV")
	public void exportToCSV(HttpServletResponse response) throws IOException {
		List<User> user = userService.listAll();
		UserCsvExporter exporter = new UserCsvExporter();
		exporter.export(user, response);
		
	}
	
	@GetMapping("/users/exportXLXS")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		List<User> user = userService.listAll();
		UserExcelExporter exporter = new UserExcelExporter();
		exporter.export(user, response);
		
	}
	
	@GetMapping("/users/exportPDF")
	public void exportToPDF(HttpServletResponse response) throws IOException {
		List<User> user = userService.listAll();
		UserPDFExporter exporter = new UserPDFExporter();
		exporter.export(user, response);
		
	}
	
	
}
