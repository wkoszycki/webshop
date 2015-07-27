package pl.koszycki.samples.webshop.service;

import pl.koszycki.samples.webshop.dao.DomainDao;

/**
 *
 * @author Wojciech Koszycki
 */
public class ShopServiceMock extends ShopService {

    public void mockDomainDao(DomainDao domainDao) {
        this.domainDao = domainDao;
    }

    public void mockStockService(StockService stockService) {
        this.stockService = stockService;
    }

}
