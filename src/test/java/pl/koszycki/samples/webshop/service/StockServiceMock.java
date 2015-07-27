package pl.koszycki.samples.webshop.service;

import pl.koszycki.samples.webshop.dao.DomainDao;

/**
 *
 * @author Wojciech Koszycki
 */
public class StockServiceMock extends StockService {

    public void mockDomainDao(DomainDao domainDao) {
        this.domainDao = domainDao;
    }

}
