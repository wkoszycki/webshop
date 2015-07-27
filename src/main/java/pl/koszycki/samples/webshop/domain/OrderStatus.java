package pl.koszycki.samples.webshop.domain;

/**
 *
 * @author Wojciech Koszycki
 */
public enum OrderStatus {

    NEW("new"), PAID("paid"), DELIVERED("send");

    private final String label;

    private OrderStatus(String name) {
        this.label = name;
    }

    public String getLabel() {
        return label;
    }

}
