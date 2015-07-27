package pl.koszycki.samples.webshop.service;

import pl.koszycki.samples.webshop.dao.MemoryDomainDao;
import pl.koszycki.samples.webshop.domain.Order;
import pl.koszycki.samples.webshop.domain.OrderItem;
import pl.koszycki.samples.webshop.domain.Product;
import pl.koszycki.samples.webshop.domain.Stock;

import java.util.Arrays;
import java.util.Collection;
import java.util.TreeSet;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Wojciech Koszycki
 */
public class StockServiceTest {

    StockService stockService;

    @Before
    public void setUp() {
        this.stockService = new StockService();
        MemoryDomainDao memoryDomainDao = new MemoryDomainDao();
        memoryDomainDao.initialize();
        stockService.domainDao = memoryDomainDao;
    }

    @Test
    public void shouldReturnAvailableStock() {
        assertEquals(60l, stockService.getAvailableStock(2L));
    }

    @Test
    public void shouldSortFromBiggerToSmallerStock() {
        Stock smallerStock = new Stock();
        smallerStock.currentStock = 10;
        Stock biggerStock = new Stock();
        biggerStock.currentStock = 20;
        Collection<Stock> stocks = Arrays.asList(smallerStock, biggerStock);
        TreeSet<Stock> sortedStock = stockService.sortStock(stocks);
        assertEquals(biggerStock.currentStock, sortedStock.first().currentStock);
        assertEquals(smallerStock.currentStock, sortedStock.last().currentStock);
    }

    @Test
    public void shouldReturnSameOrder() {
        Stock smallerStock = new Stock();
        smallerStock.currentStock = 10;
        Stock biggerStock = new Stock();
        biggerStock.currentStock = 20;
        Collection<Stock> stocks = Arrays.asList(biggerStock, smallerStock);
        TreeSet<Stock> sortedStock = stockService.sortStock(stocks);
        assertEquals(biggerStock.currentStock, sortedStock.first().currentStock);
        assertEquals(smallerStock.currentStock, sortedStock.last().currentStock);
    }

    @Test
    public void shouldReturnRemoveAvailableStock() {
        Product productA = stockService.domainDao.find(Product.class, 2L).get();
        Product productB = stockService.domainDao.find(Product.class, 3L).get();
        long avaiableStockOfproductA = stockService.getAvailableStock(productA.id);
        long avaiableStockOfproductB = stockService.getAvailableStock(productB.id);
        Order order = new Order();
        order.items = Arrays.asList(new OrderItem(productA, 10l), new OrderItem(productB, 10l));
        stockService.removeAvailableStock(order);
        assertEquals(avaiableStockOfproductA - 10, stockService.getAvailableStock(productA.id));
        assertEquals(avaiableStockOfproductB - 10, stockService.getAvailableStock(productB.id));
    }

}
