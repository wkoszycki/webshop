package pl.koszycki.samples.webshop.dao;

import org.reflections.Reflections;

import pl.koszycki.samples.webshop.domain.BaseEntity;
import pl.koszycki.samples.webshop.domain.Product;
import pl.koszycki.samples.webshop.domain.Stock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

/**
 * @author Wojciech Koszycki
 */
@ApplicationScoped
public class MemoryDomainDao implements DomainDao {

  private long idSequence = 1;
  Map<Class, Map<Long, Object>> schema;

  @PostConstruct
  public void initialize() {
    createSchema();
    createEntitiesTables();
    addSampleProducts();
  }

  protected void createSchema() {
    schema = new HashMap<>(10);
  }

  protected void createEntitiesTables() {
    Set<Class<? extends BaseEntity>> entities = getEntitiesClasses();
    entities.stream().forEach((entityClass) -> {
      schema.put(entityClass, new HashMap<>(20));
    });
  }

  private void addSampleProducts() {
    Product productA = store(new Product("A", "Best product from A group", 1050)).get();
    Product productB = store(new Product("B", "Best product from B group", 200)).get();
    addProductStock(productA, 60L);
    addProductStock(productB, 80L);
  }

  private void addProductStock(Product product, Long quantity) {
    Stock stock = new Stock();
    stock.currentStock = quantity;
    stock.product = product;
    store(stock);
  }

  protected Set<Class<? extends BaseEntity>> getEntitiesClasses() {
    Reflections reflections = new Reflections("pl.koszycki.internal.webshop.domain");
    return reflections.getSubTypesOf(BaseEntity.class);
  }

  @Override
  public <T extends BaseEntity> Optional<T> find(Class<T> clazz, long id) {
    Map<Long, Object> entities = schema.get(clazz);
    return Optional.ofNullable((T) entities.get(id));
  }

  @Override
  public <T extends BaseEntity> Optional<T> store(T entity) {
    Map<Long, Object> entities = schema.get(entity.getClass());
    entity.id = ++idSequence;
    entities.put(idSequence, entity);
    return Optional.ofNullable((T) entities.get(idSequence));
  }

  @Override
  public <T extends BaseEntity> Optional<T> update(T entity) {
    Map<Long, Object> entities = schema.get(entity.getClass());
    if (entity.id != null && entities.containsKey(entity.id)) {
      entities.put(entity.id, entity);
    }
    return Optional.ofNullable((T) entities.get(entity.id));
  }

  @Override
  public <T extends BaseEntity> Collection<T> findAll(Class<T> entityClass) {
    Collection entities = schema.get(entityClass).values();
    return new HashSet<>(entities);
  }

  @Override
  public <T extends BaseEntity> Collection<Stock> findStock(long productId) {
    Map<Long, Object> stockResult = schema.get(Stock.class);
    Collection values = stockResult.values();
    Collection<Stock> stocks = new ArrayList<>(values);
    Collection<Stock> productStocks = new ArrayList();
    stocks.stream().filter((stock) -> (stock.product.id == productId)).forEach((stock) -> {
      productStocks.add(stock);
    });
    return productStocks;
  }

  @Override
  public void removeStock(Stock stock) {
    Map<Long, Object> stockTable = schema.get(Stock.class);
    if (stockTable.containsKey(stock.id)) {
      stockTable.remove(stock.id);
    }
  }

}
