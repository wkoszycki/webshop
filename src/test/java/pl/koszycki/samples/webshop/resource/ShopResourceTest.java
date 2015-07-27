package pl.koszycki.samples.webshop.resource;

import pl.koszycki.samples.webshop.dao.MemoryDomainDao;
import pl.koszycki.samples.webshop.domain.Order;
import pl.koszycki.samples.webshop.domain.dto.OrderDto;
import pl.koszycki.samples.webshop.domain.dto.OrderItemDto;
import pl.koszycki.samples.webshop.domain.dto.ProductDto;
import pl.koszycki.samples.webshop.exception.InvalidQuantityException;
import pl.koszycki.samples.webshop.exception.ProductNotFoundException;
import pl.koszycki.samples.webshop.service.ShopServiceMock;
import pl.koszycki.samples.webshop.service.StockServiceMock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import javax.ws.rs.core.Response;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Wojciech Koszycki <wojciech.koszycki@gmail.com>
 */
public class ShopResourceTest {

    ShopResource shopResource;
    OrderDto orderDto;

    public ShopResourceTest() {
    }

    @Before
    public void setUp() {
        this.shopResource = new ShopResource();
        MemoryDomainDao memoryDomainDao = new MemoryDomainDao();
        memoryDomainDao.initialize();
        ShopServiceMock shopService = new ShopServiceMock();
        shopService.mockDomainDao(memoryDomainDao);
        StockServiceMock stockServiceMock = new StockServiceMock();
        stockServiceMock.mockDomainDao(memoryDomainDao);
        shopService.mockStockService(stockServiceMock);
        shopResource.shopService = shopService;

    }

    @Test
    public void shouldCreateOrder() {
        Response resourceResponse = createSampleOrder();
        assertEquals(201, resourceResponse.getStatus());
        Order order = (Order) resourceResponse.getEntity();
        assertNotNull("Order shouldn't be null", order);
        assertNotNull("Order id shouldn't be null", order.id);
        assertEquals(5, new ArrayList<>(order.items).get(0).quantity);
    }

    private Response createSampleOrder() {
        orderDto = new OrderDto(Arrays.asList(new OrderItemDto(2, 5)));
        return shopResource.createOrder(orderDto);
    }

    @Test
    public void shouldGetOrder() {
        Response resourceResponse = createSampleOrder();
        Order sampleOrder = (Order) resourceResponse.getEntity();
        Order retrievedOrder = (Order) shopResource.getOrder(sampleOrder.id).getEntity();
        assertEquals(sampleOrder, retrievedOrder);
    }

    @Test(expected = ProductNotFoundException.class)
    public void shouldThrowProductNotFoundException() {
        orderDto = new OrderDto(Arrays.asList(new OrderItemDto(9999, 5)));
        Response response = shopResource.createOrder(orderDto);
        assertEquals(400, response.getStatus());
    }

    @Test(expected = InvalidQuantityException.class)
    public void shouldThrowInvalidQuantityExceptionQuantityToHigh() {
        orderDto = new OrderDto(Arrays.asList(new OrderItemDto(2, 1001)));
        Response response = shopResource.createOrder(orderDto);
        assertEquals(400, response.getStatus());
    }

    @Test(expected = InvalidQuantityException.class)
    public void shouldThrowInvalidQuantityExceptionQuantityLess() {
        orderDto = new OrderDto(Arrays.asList(new OrderItemDto(3, 0)));
        Response response = shopResource.createOrder(orderDto);
        assertEquals(400, response.getStatus());
    }

    @Test
    public void shouldReturnProductsWithIds() {
        orderDto = new OrderDto(Arrays.asList(new OrderItemDto(1, 0)));
        Response response = shopResource.getProducts();
        assertEquals(200, response.getStatus());
        Collection<ProductDto> products = (Collection<ProductDto>) response.getEntity();
        assertEquals(2, products.size());
        products.stream().forEach((productDto) -> {
            assertNotNull("Product " + productDto.product.name + " should have id ", productDto.product.id);
        });
    }

}
