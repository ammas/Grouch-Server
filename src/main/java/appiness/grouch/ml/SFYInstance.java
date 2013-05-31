package appiness.grouch.ml;


public class SFYInstance {

	private SFYDocument document;

	private Integer[] features;
	private SFYTokenCollection contentTokens;

	public SFYInstance(SFYDocument document) {
		setDocument(document);
		setContentTokens(new SFYTokenCollection());
	}

	public SFYDocument getDocument() {
		return document;
	}

	public void setDocument(SFYDocument document) {
		this.document = document;
	}

	public Integer[] getFeatures() {
		return features;
	}

	public void setFeatures(Integer[] features) {
		this.features = features;
	}

	public SFYTokenCollection getContentTokens() {
		return contentTokens;
	}

	public void setContentTokens(SFYTokenCollection contentTokens) {
		this.contentTokens = contentTokens;
	}

	public void mergeTokensWith(SFYInstance mergeWith) {
		getContentTokens().merge(mergeWith.getContentTokens());
	}

}
