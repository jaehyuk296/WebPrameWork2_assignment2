package kr.ac.hansung.controller;

import kr.ac.hansung.dto.ProductDto;
import kr.ac.hansung.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import kr.ac.hansung.entity.Product;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;    

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String list(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id"));
        Page<Product> productPage;
        
        if (keyword != null && !keyword.isBlank()) {
            productPage = productService.searchProducts(keyword, pageRequest);
        } else {
            productPage = productService.getProducts(pageRequest);
        }
        
        model.addAttribute("productPage", productPage);
        model.addAttribute("keyword", keyword);
        return "products/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "products/detail";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("product", new ProductDto());
        return "products/add";
    }

    @PostMapping
    public String save(@ModelAttribute ProductDto dto) {
        productService.save(dto);
        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);
        ProductDto dto = new ProductDto();
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        dto.setStock(product.getStock());
        model.addAttribute("product", dto);
        return "products/edit";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id, 
                         @ModelAttribute("product") @Valid ProductDto dto, 
                         BindingResult result) {
        if (result.hasErrors()) {
            return "products/edit";
        }
        productService.updateProduct(id, dto);
        return "redirect:/products/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/products";
    }
}
