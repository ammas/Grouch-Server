package appiness.grouch;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import appiness.grouch.query.NaiveQuery;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("defaultPersistenceUnit");
    
	    NaiveQuery query = new NaiveQuery(emf);	
    	query.query("coke");
	    
	    
        System.out.println( "Hello World!" );
    }
}
