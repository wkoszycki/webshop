package pl.koszycki.samples.webshop.dao;

import pl.koszycki.samples.webshop.domain.BaseEntity;
import pl.koszycki.samples.webshop.domain.Stock;
import java.util.Collection;
import java.util.Optional;

/**
 *
 * @author Wojciech Koszycki
 */
public interface DomainDao {

    <T extends BaseEntity> Optional<T> find(Class<T> clazz, long id);

    <T extends BaseEntity> Optional<T> store(T entity);

    <T extends BaseEntity> Optional<T> update(T entity);

    <T extends BaseEntity> Collection<T> findAll(Class<T> entityClass);

    <T extends BaseEntity> Collection<Stock> findStock(long productId);

    void removeStock(Stock stock);

}
