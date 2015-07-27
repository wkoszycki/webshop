package pl.koszycki.samples.webshop.domain.dto;

import pl.koszycki.samples.webshop.domain.Product;

/**
 *
 * @author Wojciech Koszycki
 */
public class ProductDto {

    public final long availableQuantity;
    public final Product product;

    public ProductDto(Product product, long availableQuantity) {
        this.product = product;
        this.availableQuantity = availableQuantity;
    }

}
