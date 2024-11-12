package com.ssg.wannavapibackend.controller.web;

import com.ssg.wannavapibackend.dto.response.ProductResponseDTO;
import com.ssg.wannavapibackend.service.ProductService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/list")
    public String getProductList(Model model) {
        List<ProductResponseDTO> products = productService.getProductList();
        model.addAttribute("products", products);
        return "product/index";
    }

    @GetMapping()
    public String getProductDetail(@RequestParam("id") Long id, Model model) {
        log.info(" 🎉🎉🎉🎉 Product Detail 🎉🎉🎉🎉\n ID: " + id + "\n Model: " + model);
        model.addAttribute("id", id);
        return "product/detail";
    }

//    private List<ProductResponseDTO> getDummyListData() {
//        List<ProductResponseDTO> products = new ArrayList<>();
//        products.add(new ProductResponseDTO(1, "풀무원 식물성 유니 짜장면 풀무원 식물성 유니 짜장면 풀무원 식물성 유니 짜장면 풀무원 식물성 유니 짜장면 ", Arrays.asList("https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/retail/images/950531282389413-5a75db2d-00d6-4e81-8caf-9a0085b15921.jpg"), 22000.0, 15, 18700.0));
//        products.add(new ProductResponseDTO(2, "풀무원 식물성 유니 간장비빔면", Arrays.asList("https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/retail/images/950531282389413-5a75db2d-00d6-4e81-8caf-9a0085b15921.jpg"), 22000.0, 12, 18800.0));
//        products.add(new ProductResponseDTO(3, "풀무원 식물성 유니 김치찌개", Arrays.asList("https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/retail/images/950531282389413-5a75db2d-00d6-4e81-8caf-9a0085b15921.jpg"), 35000.0, 10, 31500.0));
//        products.add(new ProductResponseDTO(4, "풀무원 식물성 유니 불고기", Arrays.asList("https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/retail/images/950531282389413-5a75db2d-00d6-4e81-8caf-9a0085b15921.jpg"), 29000.0, 20, 23100.0));
//        products.add(new ProductResponseDTO(5, "풀무원 식물성 유니 된장찌개", Arrays.asList("https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/retail/images/950531282389413-5a75db2d-00d6-4e81-8caf-9a0085b15921.jpg"), 28000.0, 18, 23000.0));
//        products.add(new ProductResponseDTO(6, "풀무원 식물성 유니 미역국", Arrays.asList("https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/retail/images/950531282389413-5a75db2d-00d6-4e81-8caf-9a0085b15921.jpg"), 25000.0, 10, 22500.0));
//        products.add(new ProductResponseDTO(7, "풀무원 식물성 유니 순두부찌개", Arrays.asList("https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/retail/images/950531282389413-5a75db2d-00d6-4e81-8caf-9a0085b15921.jpg"), 24000.0, 12, 21600.0));
//        products.add(new ProductResponseDTO(8, "풀무원 식물성 유니 떡볶이", Arrays.asList("https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/retail/images/950531282389413-5a75db2d-00d6-4e81-8caf-9a0085b15921.jpg"), 23000.0, 14, 20200.0));
//        products.add(new ProductResponseDTO(9, "풀무원 식물성 유니 부대찌개", Arrays.asList("https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/retail/images/950531282389413-5a75db2d-00d6-4e81-8caf-9a0085b15921.jpg"), 33000.0, 16, 29400.0));
//        products.add(new ProductResponseDTO(10, "풀무원 식물성 유니 잡채", Arrays.asList("https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/retail/images/950531282389413-5a75db2d-00d6-4e81-8caf-9a0085b15921.jpg"), 24000.0, 22, 19200.0));
//        products.add(new ProductResponseDTO(11, "풀무원 식물성 유니 떡국", Arrays.asList("https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/retail/images/950531282389413-5a75db2d-00d6-4e81-8caf-9a0085b15921.jpg"), 20000.0, 11, 18000.0));
//        products.add(new ProductResponseDTO(12, "풀무원 식물성 유니 비빔밥", Arrays.asList("https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/retail/images/950531282389413-5a75db2d-00d6-4e81-8caf-9a0085b15921.jpg"), 26000.0, 13, 23400.0));
//        products.add(new ProductResponseDTO(13, "풀무원 식물성 유니 두부구이", Arrays.asList("https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/retail/images/950531282389413-5a75db2d-00d6-4e81-8caf-9a0085b15921.jpg"), 18000.0, 19, 16200.0));
//        products.add(new ProductResponseDTO(14, "풀무원 식물성 유니 소고기장조림", Arrays.asList("https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/retail/images/950531282389413-5a75db2d-00d6-4e81-8caf-9a0085b15921.jpg"), 28000.0, 14, 25200.0));
//        products.add(new ProductResponseDTO(15, "풀무원 식물성 유니 바지락칼국수", Arrays.asList("https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/retail/images/950531282389413-5a75db2d-00d6-4e81-8caf-9a0085b15921.jpg"), 32000.0, 10, 28800.0));
//        products.add(new ProductResponseDTO(16, "풀무원 식물성 유니 오징어볶음", Arrays.asList("https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/retail/images/950531282389413-5a75db2d-00d6-4e81-8caf-9a0085b15921.jpg"), 27000.0, 15, 24300.0));
//        products.add(new ProductResponseDTO(17, "풀무원 식물성 유니 감자탕", Arrays.asList("https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/retail/images/950531282389413-5a75db2d-00d6-4e81-8caf-9a0085b15921.jpg"), 35000.0, 25, 31500.0));
//        products.add(new ProductResponseDTO(18, "풀무원 식물성 유니 김밥", Arrays.asList("https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/retail/images/950531282389413-5a75db2d-00d6-4e81-8caf-9a0085b15921.jpg"), 20000.0, 16, 18000.0));
//        products.add(new ProductResponseDTO(19, "풀무원 식물성 유니 갈비찜", Arrays.asList("https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/retail/images/950531282389413-5a75db2d-00d6-4e81-8caf-9a0085b15921.jpg"), 42000.0, 8, 37800.0));
//        products.add(new ProductResponseDTO(20, "풀무원 식물성 유니 샤브샤브", Arrays.asList("https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/retail/images/950531282389413-5a75db2d-00d6-4e81-8caf-9a0085b15921.jpg"), 37000.0, 10, 33300.0));
//        products.add(new ProductResponseDTO(21, "풀무원 식물성 유니 짬뽕", Arrays.asList("https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/retail/images/950531282389413-5a75db2d-00d6-4e81-8caf-9a0085b15921.jpg"), 26000.0, 13, 23400.0));
//        products.add(new ProductResponseDTO(22, "풀무원 식물성 유니 탕수육", Arrays.asList("https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/retail/images/950531282389413-5a75db2d-00d6-4e81-8caf-9a0085b15921.jpg"), 30000.0, 10, 27000.0));
//        products.add(new ProductResponseDTO(23, "풀무원 식물성 유니 오므라이스", Arrays.asList("https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/retail/images/950531282389413-5a75db2d-00d6-4e81-8caf-9a0085b15921.jpg"), 22000.0, 22, 19800.0));
//        products.add(new ProductResponseDTO(24, "풀무원 식물성 유니 치즈스틱", Arrays.asList("https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/retail/images/950531282389413-5a75db2d-00d6-4e81-8caf-9a0085b15921.jpg"), 18000.0, 12, 16200.0));
//        products.add(new ProductResponseDTO(25, "풀무원 식물성 유니 된장국", Arrays.asList("https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/retail/images/950531282389413-5a75db2d-00d6-4e81-8caf-9a0085b15921.jpg"), 20000.0, 18, 18000.0));
//        products.add(new ProductResponseDTO(26, "풀무원 식물성 유니 갈비탕", Arrays.asList("https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/retail/images/950531282389413-5a75db2d-00d6-4e81-8caf-9a0085b15921.jpg"), 35000.0, 10, 31500.0));
//        products.add(new ProductResponseDTO(27, "풀무원 식물성 유니 칼국수", Arrays.asList("https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/retail/images/950531282389413-5a75db2d-00d6-4e81-8caf-9a0085b15921.jpg"), 25000.0, 15, 22500.0));
//        products.add(new ProductResponseDTO(28, "풀무원 식물성 유니 불고기비빔밥", Arrays.asList("https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/retail/images/950531282389413-5a75db2d-00d6-4e81-8caf-9a0085b15921.jpg"), 27000.0, 10, 24300.0));
//        products.add(new ProductResponseDTO(29, "풀무원 식물성 유니 짜장밥", Arrays.asList("https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/retail/images/950531282389413-5a75db2d-00d6-4e81-8caf-9a0085b15921.jpg"), 21000.0, 20, 18900.0));
//        products.add(new ProductResponseDTO(30, "풀무원 식물성 유니 볶음밥", Arrays.asList("https://thumbnail7.coupangcdn.com/thumbnails/remote/492x492ex/image/retail/images/950531282389413-5a75db2d-00d6-4e81-8caf-9a0085b15921.jpg"), 22000.0, 14, 19800.0));
//
//        return products;
//    }
}
