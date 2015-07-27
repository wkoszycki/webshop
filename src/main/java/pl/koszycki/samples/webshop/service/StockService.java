package pl.koszycki.samples.webshop.service;

import pl.koszycki.samples.webshop.dao.DomainDao;
import pl.koszycki.samples.webshop.domain.Order;
import pl.koszycki.samples.webshop.domain.OrderItem;
import pl.koszycki.samples.webshop.domain.Stock;
import java.io.Serializable;
import java.util.Collection;
import java.util.TreeSet;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

/**
 *
 * @author Wojciech Koszycki
 */
@Default
public class StockService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    DomainDao domainDao;

    long getAvailableStock(Long productId) {
        Collection<Stock> stocks = domainDao.findStock(productId);
        long totalAvailableStock = 0;
        return stocks.stream()
            .map((stock) -> stock.currentStock)
            .reduce(totalAvailableStock, (accumulator, _item) -> accumulator + _item);
    }

    void removeAvailableStock(Order order) {
        for (OrderItem item : order.items) {
            Collection<Stock> stocks = domainDao.findStock(item.product.id);
            TreeSet<Stock> sortedStocks = sortStock(stocks);
            long quantityToBeDeducted = item.quantity;
            for (Stock stock : sortedStocks) {
                if (stockleft(stock, quantityToBeDeducted)) {
                    deductQuantityFromStock(stock, quantityToBeDeducted);
                    domainDao.update(stock);
                    break;
                }
                quantityToBeDeducted -= stock.currentStock;
                domainDao.removeStock(stock);
            }
        }
    }

    private static long deductQuantityFromStock(Stock stock, long quantityToBeDeducted) {
        return stock.currentStock -= quantityToBeDeducted;
    }

    private boolean stockleft(Stock stock, long quantityToBeDeducted) {
        return (stock.currentStock - quantityToBeDeducted) > 0;

    }

    TreeSet<Stock> sortStock(Collection<Stock> stocks) {
        TreeSet<Stock> sortedStocks = new TreeSet<>((Stock o1, Stock o2) -> (int) (o2.currentStock - o1.currentStock));
        sortedStocks.addAll(stocks);
        return sortedStocks;
    }

}
