package pl.koszycki.samples.webshop.service;

import pl.koszycki.samples.webshop.dao.MemoryDomainDao;
import pl.koszycki.samples.webshop.domain.Order;
import pl.koszycki.samples.webshop.domain.dto.OrderDto;
import pl.koszycki.samples.webshop.domain.dto.OrderItemDto;
import pl.koszycki.samples.webshop.domain.dto.ProductDto;

import java.util.Arrays;
import java.util.Collection;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Wojciech Koszycki
 */
public class ShopServiceTest {

    ShopService shopService;

    @Before
    public void setUp() {
        this.shopService = new ShopService();
        MemoryDomainDao memoryDomainDao = new MemoryDomainDao();
        memoryDomainDao.initialize();
        shopService.domainDao = memoryDomainDao;
        StockServiceMock mockedStockService = new StockServiceMock();
        mockedStockService.domainDao = memoryDomainDao;
        shopService.stockService = mockedStockService;
    }

    @Test
    public void shouldProcessOrder() {
        Order processedOrder = shopService.processOrder(new OrderDto(Arrays.asList(new OrderItemDto(2, 5))));
        assertNotNull(processedOrder.id);
        assertNotNull(processedOrder.created);
    }

    @Test
    public void shouldReturnOrder() {
        Order processedOrder = shopService.processOrder(new OrderDto(Arrays.asList(new OrderItemDto(2, 5))));
        Order retrievedOrder = shopService.getOrder(processedOrder.id);
        assertEquals(processedOrder.id, retrievedOrder.id);
        assertEquals(processedOrder.items, retrievedOrder.items);
    }

    @Test
    public void shouldRetunProductsWithQuantity() {
        Collection<ProductDto> products = shopService.getProductsWithQuantity();
        assertNotNull(products);
        assertTrue(products.size() > 0);
    }

}
