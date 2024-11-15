package com.test.sqlimage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.test.sqlimage.mapper.thingMapper;
import com.test.sqlimage.service.CloudinaryService;
import com.test.sqlimage.vo.cartVo;
import com.test.sqlimage.vo.thingVo;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class thingController {

    @Autowired
    private thingMapper dao;
    
    @Autowired
    private CloudinaryService cloudinaryService;

    private static final String IMAGE_SAVE_PATH = "C:/images/";

    @GetMapping("/products/list")
    public List<thingVo> getAllProducts() {
    	
        return dao.getAllInfo();
    }

    @GetMapping("/products/detail")
    public ResponseEntity<thingVo> getProductDetail(@RequestParam("thing_id") String thing_id) {
        thingVo product = dao.getInfo(thing_id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @RequestMapping(value = "/products/detail", method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> handleOptions() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/products/cart")
    public ResponseEntity<?> inputCart(@RequestBody cartVo cartRequest) {
        String customer_id = cartRequest.getCustomer_id();
        String thing_id = cartRequest.getThing_id();
        int price1 = cartRequest.getPrice();
        int num1 = cartRequest.getNum();
        String name = cartRequest.getname(); // name 가져오기
        String image_path = cartRequest.getImage_path();
       
        int num;
        int cart;
        

        cartVo count = dao.selectcart(customer_id, thing_id);
        System.out.println("나와라"+cartRequest.getThing_id());

        if (count == null) {
            num = num1;
            cart = dao.inputcart(customer_id, thing_id, num, price1, name, image_path);
        } else {
            num = count.getNum() + num1;
            int price = count.getPrice() + price1;
            cart = dao.updatecart(customer_id, thing_id, num, price);
        }

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @GetMapping("/products/cartlist")
    public ResponseEntity<List<cartVo>> cartlist(@RequestParam String customer_id) {
        List<cartVo> cartList = dao.getcartlist(customer_id); // CartList와 ThingVo의 세부 정보를 함께 가져옵니다.

        if (cartList == null || cartList.isEmpty()) {     
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(cartList); // CartVo의 리스트를 반환합니다.
    }

  



    
    

    @PostMapping("/products/cartnum")
    public ResponseEntity<?> cartnum(@RequestBody cartVo cartRequest) {
        String customer_id = cartRequest.getCustomer_id();
        String thing_id = cartRequest.getThing_id();
        String name = cartRequest.getname();
        int num = cartRequest.getNum();
        int price = cartRequest.getPrice();
        int bid = cartRequest.getBid();

        if (bid == 1) {
            if (num > 1) {
                price = (price / num) * (num - 1);
                num -= 1;
            }
        } else {
            price = (price / num) * (num + 1);
            num += 1;
        }

        int cart = dao.updatecart(customer_id, thing_id, num, price);

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping("/products/deletecart")
    public ResponseEntity<?> deletecart(@RequestBody cartVo cartRequest) {
        String customer_id = cartRequest.getCustomer_id();
        String thing_id = cartRequest.getThing_id();

        int result = dao.deletecart(customer_id, thing_id);

        if (result > 0) {
            return new ResponseEntity<>("삭제 성공", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("삭제 실패", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/products/deletecarts")
    public ResponseEntity<?> deletecarts(@RequestBody cartVo cartRequest) {
        String customer_id = cartRequest.getCustomer_id();
        List<String> thing_ids = cartRequest.getThing_ids();
        
        int tot = 0;
        System.out.println(customer_id);
        for (String thing_id : thing_ids) {
        	thing_id = thing_id.trim();
        	System.out.println(thing_id);
            tot += dao.deletecart(customer_id, thing_id);
        	System.out.println(tot);

        }
        if (tot > 0) {
            return new ResponseEntity<>("삭제 성공", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("삭제 실패", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/things/register")
    public Map<String, Object> registerThing(@RequestParam("company_id") String company_id,
                                             @RequestParam("name") String name,
                                             @RequestParam("price") int price,
                                             @RequestParam("num") int num,
                                             @RequestParam("explain") String explain,
                                             @RequestParam("uploadFile") MultipartFile uploadFile) {
        Map<String, Object> response = new HashMap<>();
        
     // Cloudinary 설정
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dhuybxduy",
            "api_key", "324727897453721",
            "api_secret", "rJaNQVsUL-80csaqHFinKdKUii0"
        ));

        String image_path = null;
        try {
            // 파일을 임시로 저장
            File tempFile = File.createTempFile("upload-", uploadFile.getOriginalFilename());
            uploadFile.transferTo(tempFile);
            
            // 임시 파일이 생성되었는지 확인
            if (tempFile.exists()) {
                System.out.println("임시 파일 생성 성공: " + tempFile.getAbsolutePath());
            } else {
                System.out.println("임시 파일 생성 실패");
            }
            
            // Cloudinary에 이미지 업로드
            Map uploadResult = cloudinary.uploader().upload(tempFile, ObjectUtils.emptyMap());
            image_path = (String) uploadResult.get("url");

            System.out.println("이미지 URL: " + image_path);

            // 업로드된 URL을 thingVO에 설정
            thingVo thingVO = new thingVo();
            thingVO.setthing_id("TH" + System.currentTimeMillis());
            thingVO.setcompany_id(company_id);
            thingVO.setName(name);
            thingVO.setPrice(price);
            thingVO.setNum(num);
            thingVO.setExplain(explain);
            thingVO.setImage_path(image_path); // 변환된 이미지 URL 설정

            // 데이터베이스에 저장
            dao.insertThing(thingVO);
            dao.insertChart(thingVO);
            response.put("success", true);
            response.put("message", "상품이 성공적으로 등록되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "상품 등록에 실패했습니다.");
        }
        return response;
    }
    
    
    @GetMapping("/api/shop_buy_details")
    public ResponseEntity<?> getShopBuyDetails(
            @RequestParam String thing_id,
            @RequestParam String name,
            @RequestParam String image_path,
            @RequestParam int price,
            @RequestParam int quantity) {
        
        // 로그로 확인
        System.out.println("Thing ID: " + thing_id);
        System.out.println("Name: " + name);
        System.out.println("Image Path: " + image_path);
        System.out.println("Price: " + price);
        System.out.println("Quantity: " + quantity);

        // 현재는 단순히 요청받은 정보를 그대로 반환
        Map<String, Object> response = new HashMap<>();
        response.put("thing_id", thing_id);
        response.put("name", name);
        response.put("image_path", image_path);
        response.put("price", price);
        response.put("quantity", quantity);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    
    @PostMapping("/uploadImage")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // MultipartFile을 File 객체로 변환
            File tempFile = File.createTempFile("upload-", file.getOriginalFilename());
            file.transferTo(tempFile);

            // Cloudinary에 이미지 업로드
            String image_path = cloudinaryService.uploadImage(tempFile);

            // 업로드된 이미지 URL을 반환
            Map<String, String> response = new HashMap<>();
            response.put("image_path", image_path);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("업로드 실패: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/products/shop_buy")
    public ResponseEntity<List<cartVo>> getShopBuy(
            @RequestParam("thing_id") String thing_id1,
            @RequestParam("customer_id") String customer_id) {
        
        List<String> thingIdList = Arrays.asList(thing_id1.split(","));
        System.out.println("Received thing IDs: " + thingIdList);

        List<cartVo> products = new ArrayList<>();

        for (String thing_id : thingIdList) {
            cartVo product = dao.selectcart(customer_id, thing_id);
            if (product != null) {
                products.add(product);
                System.out.println("Fetched product for thing_id " + thing_id + ": " + product);
            } else {
                System.out.println("No product found for thing_id " + thing_id);
            }
        }

        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    
    @GetMapping("/products/cartbuy")
    public ResponseEntity<thingVo> getcartbuy(
           @RequestParam("thing_id") String thing_id) {

            thingVo product = dao.getInfo(thing_id);
            
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
    
    


}
