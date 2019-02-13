package uk.ac.ebi.uniprot.flatfile.parser.impl.id;

/**
 * Created with IntelliJ IDEA. User: wudong Date: 08/08/13 Time: 11:50 To change
 * this template use File | Settings | File Templates.
 */
public class IdLineObject {
	private Boolean reviewed;
	private String entryName;
	private  int sequenceLength;
	public IdLineObject() {
		
	}
	public IdLineObject(String entryName, boolean reviewed, int sequenceLength) {
		this.reviewed = reviewed;
		this.entryName = entryName;
		this.sequenceLength = sequenceLength;
	}

	public Boolean getReviewed() {
		return reviewed;
	}

	public String getEntryName() {
		return entryName;
	}

	public int getSequenceLength() {
		return sequenceLength;
	}

	public void setReviewed(Boolean reviewed) {
		this.reviewed = reviewed;
	}

	public void setEntryName(String entryName) {
		this.entryName = entryName;
	}

	public void setSequenceLength(int sequenceLength) {
		this.sequenceLength = sequenceLength;
	}
	
}
