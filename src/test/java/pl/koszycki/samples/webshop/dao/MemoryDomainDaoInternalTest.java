package pl.koszycki.samples.webshop.dao;

import pl.koszycki.samples.webshop.domain.BaseEntity;

import java.util.Set;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Wojciech Koszycki
 */
public class MemoryDomainDaoInternalTest {

    MemoryDomainDao memoryDomainDao;

    @Before
    public void cleanUpBefore() {
        memoryDomainDao = new MemoryDomainDao();
        memoryDomainDao.createSchema();
    }

    @Test
    public void shouldCreateSchema() {
        assertNotNull("Schema shouldn't be null", memoryDomainDao.schema);
    }

    @Test
    public void shouldCreateEntitiesTables() {
        memoryDomainDao.createEntitiesTables();
        assertTrue(memoryDomainDao.schema.size() > 1);
    }

    @Test
    public void shouldReturnEntitiesClasses() {
        Set<Class<? extends BaseEntity>> entitiesClasses = memoryDomainDao.getEntitiesClasses();
        assertNotNull(entitiesClasses);
        assertTrue(entitiesClasses.size() > 1);
    }

}
