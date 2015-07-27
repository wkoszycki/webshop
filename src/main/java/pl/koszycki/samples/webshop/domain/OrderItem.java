package pl.koszycki.samples.webshop.domain;

/**
 *
 * @author Wojciech Koszycki
 */
public class OrderItem extends BaseEntity {

    public Product product;
    public long quantity;

    public OrderItem(Product product, long quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public OrderItem() {
    }

}
