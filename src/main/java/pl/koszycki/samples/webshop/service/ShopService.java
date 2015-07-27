package pl.koszycki.samples.webshop.service;

import pl.koszycki.samples.webshop.dao.DomainDao;
import pl.koszycki.samples.webshop.domain.Order;
import pl.koszycki.samples.webshop.domain.OrderItem;
import pl.koszycki.samples.webshop.domain.Product;
import pl.koszycki.samples.webshop.domain.dto.OrderDto;
import pl.koszycki.samples.webshop.domain.dto.OrderItemDto;
import pl.koszycki.samples.webshop.domain.dto.ProductDto;
import pl.koszycki.samples.webshop.exception.InvalidQuantityException;
import pl.koszycki.samples.webshop.exception.OrderNotFoundException;
import pl.koszycki.samples.webshop.exception.ProductNotFoundException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

/**
 *
 * @author Wojciech Koszycki <wojciech.koszycki@gmail.com>
 */
@Default
public class ShopService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    DomainDao domainDao;

    @Inject
    StockService stockService;

    public Order processOrder(OrderDto orderDto) throws ProductNotFoundException,
                                                        InvalidQuantityException {
        Order order = new Order();
        if (orderDto.items == null || orderDto.items.isEmpty()) {
            throw new InvalidQuantityException("No items found");
        }
        for (OrderItemDto item : orderDto.items) {
            Product product = domainDao.find(Product.class, item.productId).orElseThrow(() -> new ProductNotFoundException("Product with given id doesn't exist"));
            isValidQuantity(item);
            order.items.add(new OrderItem(product, item.quantity));
        }
        stockService.removeAvailableStock(order);
        order.created = new Date();
        return domainDao.store(order).orElseThrow(() -> new RuntimeException());
    }

    private void isValidQuantity(OrderItemDto item) throws InvalidQuantityException {
        if (item.quantity <= 0 || item.quantity > stockService.getAvailableStock(item.productId)) {
            throw new InvalidQuantityException("Quantity" + item.quantity + " is invalid");
        }
    }

    public Collection<ProductDto> getProductsWithQuantity() {
        Collection<Product> products = this.domainDao.findAll(Product.class);
        Collection<ProductDto> productsWithQuantity = new ArrayList<>(products.size());
        products.stream().forEach((product) -> {
            productsWithQuantity.add(new ProductDto(product, stockService.getAvailableStock(product.id)));
        });
        return productsWithQuantity;
    }

    public Order getOrder(long id) throws OrderNotFoundException {
        Optional<Order> order = this.domainDao.find(Order.class, id);
        return order.orElseThrow(() -> new OrderNotFoundException("Order with given id not exist"));
    }

}
