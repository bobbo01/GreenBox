package com.kh.Final_Project;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.kh.Final_Project.customer.service.CustomerServiceImp;
import com.kh.Final_Project.customer.service.KakaoService;
import com.kh.Final_Project.customer.service.NaverService;
import com.kh.Final_Project.customer.vo.CustomerForm;
import com.kh.Final_Project.product.entity.Product;
import com.kh.Final_Project.product.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class IndexController {

	@Autowired
	private KakaoService kakaoService;

	@Autowired
	private CustomerServiceImp customerService;

	@Autowired
	private NaverService naverService;
	
	@Autowired
	private ProductService productService;

	@GetMapping("/")
	public String index(Model model) {
		List<Product> vegetables = productService.findByCategory(Product.Category.Vegetable);
		List<Product> meats = productService.findByCategory(Product.Category.Meat);
		List<Product> seafood = productService.findByCategory(Product.Category.Seafood);
		List<Product> fruit = productService.findByCategory(Product.Category.Fruit);
		List<Product> mealkit = productService.findByCategory(Product.Category.MealKit);
		model.addAttribute("vegetablesList", vegetables);
		model.addAttribute("meatsList", meats);
		model.addAttribute("seafoodList", seafood);
		model.addAttribute("fruitList", fruit);
		model.addAttribute("mealkitList", mealkit);
		return "index";
	}

	@GetMapping("/auto")
	public String kakao(String code, HttpServletRequest request) {
		
		CustomerForm customerForm = kakaoService.getKakaoInfo(code);
		if (customerService.kakaoSignUp(customerForm, request) == null) {
			return "redirect:/";
		}
		
		return "index";
	}

	@GetMapping("/naver/auto")
	public String naver(String code,  HttpServletRequest request) {

		// 인가코드를 이용해서 토큰 발급하기!
		CustomerForm customerForm = naverService.getNaverInfo(code);
		
		if (customerService.naverSignUp(customerForm, request) == null) {
			return "redirect:/";
		}
		
		return "index";
	}

}
