package site.metacoding.white.util;

import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
class Product {
    private Integer id;
    private String name;
    private Integer price;
    private Integer qty;
    private String mcp; // 제조사
}

@Getter
@Setter
class ProductDto {
    private String name;
    private Integer price;
    private Integer qty;

    // Product -> ProductDto 로 변경

    // @Builder
    // public ProductDto(String name, Integer price, Integer qty) {
    // this.name = name;
    // this.price = price;
    // this.qty = qty;
    // }
    public ProductDto(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();
        this.qty = product.getQty();
    }

    public Product toEntity() {
        return Product.builder()
                .name(name).price(price).qty(qty).build();
    }
}

public class DtoTest {

    @Test
    public void 고급매핑하기() {
        // toEntity, toDto 만들어서 매핑하면 편하지 않을까? (재활용)
    }

    @Test
    public void 매핑하기1() {
        // 1. Product 객체생성 (디폴트) // 2. 값 넣기
        // Product product = new Product(1, "바나나", 1000, 100, "삼성");

        // 1-1 & 2-1. Builder로 Product 객체생성 (디폴트)
        Product productB = Product.builder()
                .id(1).name("애플").price(1000).qty(888).mcp("샘성").build();

        // 3. ProductDto 객체생성 (디폴트) // 4. Product -> ProductDto로 옮기기
        ProductDto productDto = new ProductDto(productB);

        // 5. ProductDto -> product 변경
        // Product product2 = new Product(null, productDto.getName(),
        // =productDto.getPrice(), productDto.getQty(), null);

        // 5-1. Builder 매핑으로 ProductDto -> product 변경
        Product product2 = productDto.toEntity();

    }
}