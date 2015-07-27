package pl.koszycki.samples.webshop.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author Wojciech Koszycki
 */
public class Order extends BaseEntity {

    public Date created;
    public Date sendDate;
    public OrderStatus status;
    public Collection<OrderItem> items;

    public Order() {
        this.items = new ArrayList<>();
        this.status = OrderStatus.NEW;
    }

}
