package pl.koszycki.samples.webshop.domain;

/**
 *
 * @author Wojciech Koszycki
 */
public class Product extends BaseEntity {

    public String name;
    public String description;
    public long price;

    public Product() {
    }

    public Product(String name, String description, long price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

}
