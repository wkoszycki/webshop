package pl.koszycki.samples.webshop.dao;

import pl.koszycki.samples.webshop.domain.BaseEntity;
import pl.koszycki.samples.webshop.domain.Order;
import pl.koszycki.samples.webshop.domain.Product;
import pl.koszycki.samples.webshop.domain.Stock;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Wojciech Koszycki
 */
public class MemoryDomainDaoTest {

    DomainDao domainDao;
    Product sampleProduct;

    @Before
    public void setUpBeforeTest() {
        MemoryDomainDao memoryDomainDao = new MemoryDomainDao();
        memoryDomainDao.initialize();
        domainDao = memoryDomainDao;
        sampleProduct = storeEntity(createSampleProduct()).get();
        addStockToSampleProduct();
    }

    private <T extends BaseEntity> Optional<T> storeEntity(T entity) {
        return domainDao.store(entity);
    }

    private Product createSampleProduct() {
        return new Product("test", "some desc", 1000);
    }

    private void addStockToSampleProduct() {
        Stock stock = new Stock();
        stock.currentStock = 5;
        stock.product = sampleProduct;
        storeEntity(stock);
    }

    @Test
    public void shouldFindEntity() {
        Product retreivedProduct = domainDao.find(Product.class, sampleProduct.id).get();
        assertEquals("Retrieved Product should be the same", sampleProduct, retreivedProduct);
    }

    @Test
    public void shouldNotFindEntity() {
        assertFalse(domainDao.find(Product.class, 150210055102L).isPresent());
    }

    @Test
    public void shouldStoreEntity() {
        Product product = createSampleProduct();
        Product storedProduct = storeEntity(product).get();
        assertEquals(storedProduct.name, product.name);
    }

    @Test
    public void shouldNotStoreDuplicateEntity() {
        Product product = createSampleProduct();
        Product storedProduct = storeEntity(product).get();
        assertTrue(!Objects.equals(storedProduct.id, sampleProduct.id));
    }

    @Test
    public void shouldUpdateEntity() {
        String newProductName = "different name";
        sampleProduct.name = newProductName;
        Product updatedProduct = domainDao.update(sampleProduct).get();
        assertEquals(newProductName, updatedProduct.name);
    }

    @Test
    public void shouldNotUpdateEntity() {
        assertFalse(domainDao.update(new Product("flying product", null, 600)).isPresent());
    }

    @Test
    public void shouldFindAllEntities() {
        Collection<Product> products = domainDao.findAll(Product.class);
        assertNotNull(products);
        assertEquals(3, products.size());
    }

    @Test
    public void shouldNotFindAllEntities() {
        Collection<Order> orders = domainDao.findAll(Order.class);
        assertNotNull(orders);
        assertEquals(0, orders.size());
    }

    @Test
    public void shouldFindStock() {
        Collection<Stock> stocks = this.domainDao.findStock(sampleProduct.id);
        assertEquals(stocks.size(), 1);
    }

    @Test
    public void shouldNotFindStock() {
        Collection<Stock> stocks = this.domainDao.findStock(150210064012L);
        assertEquals(0, stocks.size());
    }

}
