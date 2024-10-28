package com.example.ex03.controllers.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import jakarta.validation.Valid;

import com.example.ex03.entities.Category;
import com.example.ex03.models.CategoryModel;
import com.example.ex03.services.ICategoryService;



@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
	@Autowired
	ICategoryService cateService;
	
	@GetMapping("")
	public String list(Model model) {
		List<Category> listcate = cateService.findAll();
		// chuyển dữ liệu từ model vào biến categories để truyền lên view
		model.addAttribute("categories",listcate);
		return "admin/categories/list";
	}
	
	@GetMapping("add")
	public String add(ModelMap model) {
		CategoryModel cateModel = new CategoryModel();
		cateModel.setIsEdit(false);
		model.addAttribute("category", cateModel);
		return "admin/category/addOrEdit";
	}
	
	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("category") CategoryModel cateModel, BindingResult result) {
		System.out.println(cateModel);
		if(result.hasErrors()) {
			return new ModelAndView("admin/category/addOrEdit");
		}
		Category entity = new Category();
		// copy từ model sang entity
		BeanUtils.copyProperties(cateModel, entity);
		cateService.save(entity);
		
		String message = "";
		if(cateModel.getIsEdit() == true) {
			message = "Category is edited";
		}else {
			message = "Category is saved";
		}
		model.addAttribute("message", message);
		return new ModelAndView("forward:/admin/categories",model);
	}
	
	@GetMapping("edit/{id}")
	public ModelAndView edit(ModelMap model, @PathVariable Integer id) {
		Optional<Category> optCategory = cateService.findById(id);
		CategoryModel cateModel = new CategoryModel();
		// kiểm tra sự tồn tại của category
		if(optCategory.isPresent()) {
			Category entity = optCategory.get();
			// copy từ entity sang cateModel
			BeanUtils.copyProperties(entity, cateModel);
			cateModel.setIsEdit(true);
			// đẩy dữ liệu ra view
			model.addAttribute("category", cateModel);
			
			return new ModelAndView("admin/category/addOrEdit", model);
		}
		model.addAttribute("message", "Category is not existed!!!!");
		return new ModelAndView("forward:/admin/categories", model);
	}
}
