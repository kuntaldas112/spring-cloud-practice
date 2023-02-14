package com.bittu.springcloud.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bittu.springcloud.model.Coupon;

@FeignClient(name="API-GATEWAY-SERVICE")
public interface CouponClient {
	@GetMapping("/couponapi/coupons/{code}")
	public Coupon getCoupon(@PathVariable String code);
}
