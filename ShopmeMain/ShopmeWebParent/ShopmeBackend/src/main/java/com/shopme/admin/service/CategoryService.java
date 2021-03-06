package com.shopme.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.admin.repository.CategoryRepository;
import com.shopme.common.entity.Category;


@Service
public class CategoryService {
	
	@Autowired
	CategoryRepository cateRepo;
	
	public List<Category> listAll() {
		return (List<Category>) cateRepo.findAll();
	}
	
	public List<Category> listAllForm() {
		List<Category> catForm = new ArrayList<>();
		
		Iterable<Category> catList = cateRepo.findAll();
		
		for(Category cat : catList) {
			if(cat.getParent() == null) {
				catForm.add(new Category(cat.getName()));
			}
			
			Set<Category> children =  cat.getChild();
			
			for(Category subCat : children) {
				String name = "--" + subCat.getName();
				catForm.add(new Category(name));
				listChildren(catForm, subCat, 1);
			}
		}
		
		return (List<Category>) catForm;
	}
	
	public void listChildren(List<Category> catForm, Category cat, int level) {
		int newLevel = ++level;
		Set<Category> children = cat.getChild();
		
		for(Category subCat : children) {
			String name="";
			for(int i = 0; i < newLevel; i++) {
				name += "--";
			}
			name+=subCat;
			catForm.add(new Category(name));
			listChildren(catForm, subCat, 1);
		}
		
	}
	
	public Category save(Category cat) {
		return cateRepo.save(cat);
	}
}
