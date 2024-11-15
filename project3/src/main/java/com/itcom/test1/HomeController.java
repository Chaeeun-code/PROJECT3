package com.itcom.test1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
   
   @RequestMapping("/")
   public String index() {
      return "index";
   }
   
   @GetMapping("/index")
   public String redirectToindex() {
       return "index"; 
   }
   
   @GetMapping("/shop_main")
   public String redirectToShop() {
       return "shop_main"; 
   }
   
   @GetMapping("/shop_buy")
   public String redirectToShopBuy() {
       return "shop_buy";
   }
   
   @GetMapping("/shop_detail")
   public String redirectToShopDetail() {
       return "shop_detail";
   }
   
   @GetMapping("/shop_cart")
   public String redirectToShopCart() {
       return "shop_cart"; 
   }
   
   @GetMapping("/manage")
   public String redirectToManageMode() {
       return "manage"; 
   }
   
   @GetMapping("/manage_product")
   public String redirectToManageProduct() {
       return "manage_product";
   }
   
   @GetMapping("/manage_certification")
   public String redirectTocertification() {
       return "manage_certification";
   }
   
   @GetMapping("/mypage")
   public String redirectToMypage() {
       return "mypage"; 
   }
   
   @GetMapping("/mypage_modify")
   public String redirectToMypageModify() {
       return "mypage_modify";
   }
   
   
   @GetMapping("/campaign_main")
   public String redirectToCampaign() {
       return "campaign_main"; 
   }
   
   @GetMapping("/campaign_detail")
   public String redirectTodetail() {
       return "campaign_detail"; 
   }
   
   @GetMapping("/company")
   public String redirectToCompany() {
       return "/company";
   }
   
   @GetMapping("/login")
   public String login() {
       return "/login";
   }
   
   @GetMapping("/manage_credit")
   public String manage_credit() {
	   return "/manage_credit";
   }
   

}



