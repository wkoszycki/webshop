package pl.koszycki.samples.webshop.domain.dto;

import java.util.Collection;

/**
 *
 * @author Wojciech Koszycki
 */
public class OrderDto {

    public Collection<OrderItemDto> items;

    public OrderDto() {
    }

    public OrderDto(Collection<OrderItemDto> items) {
        this.items = items;
    }

}
