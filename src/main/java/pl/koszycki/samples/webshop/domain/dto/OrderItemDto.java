package pl.koszycki.samples.webshop.domain.dto;

/**
 *
 * @author Wojciech Koszycki
 */
public class OrderItemDto {

    public long productId;
    public long quantity;

    public OrderItemDto() {
    }

    public OrderItemDto(long productId, long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

}
