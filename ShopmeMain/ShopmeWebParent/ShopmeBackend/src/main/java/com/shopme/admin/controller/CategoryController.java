package com.shopme.admin.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.FileUploadUtil;
import com.shopme.admin.repository.CategoryRepository;
import com.shopme.admin.service.CategoryService;
import com.shopme.common.entity.Category;

@Controller
public class CategoryController {

	@Autowired
	CategoryService catServices;
	
	@GetMapping("/categories")
	public String showCategory(Model model) {
	
		List<Category> category = catServices.listAll();
		model.addAttribute("categories", category);
		
		return "category";
	}
	
	
	@GetMapping("/categories/new")
	public String newCategory(Model model) {
		List<Category> categoryList = catServices.listAllForm();
		model.addAttribute("category", new Category());
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("titie", "Create new Category");
		return "category_form";
	}
	
	@PostMapping("/categories/save")
	public String saveCategory(RedirectAttributes redirect,Category cat,@RequestParam("fileImage") MultipartFile multiFile) throws IOException {
		String fileName = StringUtils.cleanPath(multiFile.getOriginalFilename());
		cat.setImage(fileName);
		
		Category savedCat = catServices.save(cat);
		
		String uploadDir = "/category-images/" + savedCat.getId();
		FileUploadUtil.saveFile(uploadDir, fileName, multiFile);
		redirect.addFlashAttribute("message", "The Category has been saved");
		return "redirect:/categories";		
	}
	
}