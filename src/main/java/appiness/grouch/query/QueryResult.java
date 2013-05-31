package appiness.grouch.query;

public class QueryResult {

	private final String productId;
	private final String materialId;

	public String getProductId() {
		return productId;
	}

	public String getMaterialId() {
		return materialId;
	}

	public QueryResult(String productId,String materialId) {
		super();
		this.productId = productId;
		this.materialId = materialId;

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "productId:" + productId + "\n" + "\n" + "materialId:"
				+ materialId + "\n";
	}

}
