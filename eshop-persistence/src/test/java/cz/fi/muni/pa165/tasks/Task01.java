package cz.fi.muni.pa165.tasks;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.entity.Category;
import org.testng.Assert;

@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class Task01 extends AbstractTestNGSpringContextTests{

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Test
    public void categoryTest() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Category cat = new Category();
        cat.setName("Test");
        em.persist(cat);
        em.getTransaction().commit();
        em.close();
        //TODO under this line: create a second entity manager in categoryTest, use find method to find the category and assert its name.
        
        EntityManager em1 = emf.createEntityManager();
        Category cat1 = em1.find(Category.class, cat.getId());
        Assert.assertEquals(cat1.getName(), cat.getName());
        em1.getTransaction().begin();
        em1.close();
    }

}
