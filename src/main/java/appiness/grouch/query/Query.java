package appiness.grouch.query;

import java.util.List;



public interface Query {

	List<QueryResult> query(String query);
	
	
}
