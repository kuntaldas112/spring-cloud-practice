package com.bittu.springcloud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bittu.springcloud.feign.CouponClient;
import com.bittu.springcloud.model.Coupon;
import com.bittu.springcloud.model.Product;
import com.bittu.springcloud.repos.ProductRepo;

import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/productapi")
@RefreshScope
public class ProductController {
	@Autowired
	ProductRepo productRepo;
	@Autowired
	CouponClient client;
	@Value("${com.bittu.springcloud.prop}")
	private String prop;
	
	@PostMapping("/products")
	@Retry(name="product-api",fallbackMethod = "handleError")
	public Product create(@RequestBody Product product) {
		Coupon coupon = client.getCoupon(product.getCouponCode());
		product.setPrice(product.getPrice().subtract(coupon.getDiscount()));
		return productRepo.save(product);
	}
	
	public Product handleError(Product product,Exception exception) {
		System.out.println("inside handle error");
		return product;
	}
	@GetMapping("/prop")
	public String getProp() {
		return prop;
	}
}
