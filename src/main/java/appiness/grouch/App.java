package appiness.grouch;

import java.util.UUID;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import appiness.grouch.model.Council;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("defaultPersistenceUnit");
    	Council cl = new Council(UUID.randomUUID(), "");
        System.out.println( "Hello World!" );
    }
}
