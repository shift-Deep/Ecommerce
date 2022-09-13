package com.sheryians.major.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sheryians.major.dto.ProductDto;
import com.sheryians.major.model.Category;
import com.sheryians.major.model.Product;
import com.sheryians.major.service.CategoryService;
import com.sheryians.major.service.ProductService;

@Controller
public class AdminController {
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	
	public static String uploadDir = System.getProperty("user.dir")+"/src/main/resources/static/productImages";
	
	@GetMapping("/admin")
	public String getAdminPanel() {
		return "adminHome";
	}
	
	@GetMapping("/admin/categories")
	public String getCatg(Model model) {
		model.addAttribute("categories",categoryService.getAllCategory());
		return "categories";
	}
	
	@GetMapping("/admin/categories/add")
	public String getCatgAdd(Model model) {
		model.addAttribute("category", new Category());
		return "categoriesAdd";
	}
	
	@PostMapping("/admin/categories/add")
	public String postCatgAdd(@ModelAttribute("category") Category category, Model model) {
		categoryService.addCategory(category);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCat(@PathVariable int id) {
		
		categoryService.deleteCat(id);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/update/{id}")
	public String updateCat(@PathVariable int id, Model model) {
		
		Optional<Category> category = categoryService.getCatById(id);
		if(category.isPresent()) {
			model.addAttribute("category",category.get());
			return "categoriesAdd";
		}else {
			return "404";
		}
		
	}
	
	//****************************************************************************
	@GetMapping("/admin/products")
	public String getAllProduct(Model model) {
		
		model.addAttribute("products",productService.getAllProduct());
		return "products";
	}
	
	@GetMapping("/admin/products/add")
	public String addProduct(Model model) {
		
		model.addAttribute("productDTO",new ProductDto());
		model.addAttribute("categories",categoryService.getAllCategory());
		return "productsAdd";
	}
	
	@PostMapping("/admin/products/add")
	public String addProductPost(@ModelAttribute("productDTO") ProductDto product,@RequestParam("productImage") MultipartFile file,@RequestParam("imgName") String imgName,Model model) throws IOException {
		Product p = new Product();
		p.setName(product.getName());
		p.setPrice(product.getPrice());
		p.setDescription(product.getDescription());
		p.setWeight(product.getWeight());
		p.setId(product.getId());
		p.setCategory(categoryService.getCatById(product.getCategoryId()).get());
		String imageUUID;
		if(!file.isEmpty()) {
			imageUUID = file.getOriginalFilename();
			Path fileNameAndPath = Paths.get(uploadDir,imageUUID);
			Files.write(fileNameAndPath, file.getBytes());
		}else {
			imageUUID = imgName;
		}
		p.setImageName(imageUUID); 
		productService.addProduct(p);
		
		return "redirect:/admin/products";
	}
	
	@GetMapping("/admin/product/delete/{id}")
	public String deleteProduct(@PathVariable long id) {
		productService.removeProduct(id);
		return "redirect:/admin/products";
	}
	
	@GetMapping("/admin/product/update/{id}")
	public String updateProduct(@PathVariable long id, Model model) {
		Product product = productService.getProductById(id).get();
		
		ProductDto p = new ProductDto();
		p.setId(product.getId());
		p.setName(product.getName());
		p.setPrice(product.getPrice());
		p.setDescription(product.getDescription());
		p.setWeight(product.getWeight());
		p.setCategoryId(product.getCategory().getId());
		p.setId(product.getId());
		p.setImageName(product.getImageName());
		model.addAttribute("categories",categoryService.getAllCategory());
		model.addAttribute("productDTO",p);
		return "productsAdd";
	}

}
