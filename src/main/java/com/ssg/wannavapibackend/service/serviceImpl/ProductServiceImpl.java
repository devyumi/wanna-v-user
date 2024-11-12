package com.ssg.wannavapibackend.service.serviceImpl;

import com.ssg.wannavapibackend.domain.Product;
import com.ssg.wannavapibackend.dto.response.ProductResponseDTO;
import com.ssg.wannavapibackend.repository.ProductRepository;
import com.ssg.wannavapibackend.service.ProductService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;


    @Override
    public List<ProductResponseDTO> getProductList() {
        List<Product> products = productRepository.findAll();

        return products.stream()
            .map(product -> new ProductResponseDTO(product.getId(), product.getName(),
                product.getImage(), product.getSellingPrice(), product.getDiscountRate(),
                product.getFinalPrice()))
            .collect(Collectors.toList());
    }
}
