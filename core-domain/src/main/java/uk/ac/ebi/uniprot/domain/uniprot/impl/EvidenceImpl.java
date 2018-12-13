package uk.ac.ebi.uniprot.domain.uniprot.impl;


import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceType;

public class EvidenceImpl implements Evidence {
	private static final String PIPE = "|";
	private static final String COLON = ":";
	private EvidenceCode evidenceCode;
	private DBCrossReference<EvidenceType> source;
	
	
	static final EvidenceType REFERENCE =new EvidenceType("Reference");
	static final String REF_PREFIX ="Ref.";

    public static Evidence parseEvidenceLine(String val) {
        String[] token = val.split("\\|");
        String code = token[0];
        DBCrossReference<EvidenceType> xref =null;
        if (token.length == 2) {
        	int index = token[1].indexOf(':');
        	if(index ==-1) {
        		if (token[1].startsWith(REF_PREFIX)) {
                	xref = new DBCrossReferenceImpl<>(REFERENCE, token[1]);      
        		}else {
        			throw new IllegalArgumentException(val + " is not valid evidence string");
        		}
        	}else {
        		String type = token[1].substring(0, index);
        		String id = token[1].substring(index+1);
        		xref = new DBCrossReferenceImpl<>(new EvidenceType(type), id);
        	}
        }
        EvidenceCode evidenceCode = EvidenceCode.codeOf(code);
        return new EvidenceImpl(evidenceCode, xref);
    }


	
	public EvidenceImpl( EvidenceCode evidenceCode,
			 String databaseName, String dbId) {
		this(evidenceCode, new DBCrossReferenceImpl<>(new EvidenceType(databaseName), dbId));
	
	}
	private EvidenceImpl(){

	}

	private EvidenceImpl(String value){
		System.out.println("LEO LEO LEO LEO");
	}

	public EvidenceImpl(EvidenceCode evidenceCode, DBCrossReference<EvidenceType> source) {
		this.evidenceCode = evidenceCode;
		this.source = source;
	}

	@Override
	public EvidenceCode getEvidenceCode() {
		return evidenceCode;
	}

	@Override
	public DBCrossReference<EvidenceType> getSource() {
		return source;
	}

//	
//	public void setSource(DBCrossReference source) {
//		this.source = source;
//	}

	@Override
	public int compareTo(Evidence o) {
		return this.getValue().compareTo(o.getValue());
	}

	@Override
	public String toString() {
		return getValue();
	}

	@Override
	public String getValue() {

		StringBuilder sb = new StringBuilder();
		sb.append(evidenceCode.getCode());
		if (source != null) {
			sb.append(PIPE);
			if (source.getDatabaseType().equals(REFERENCE)) {
				sb.append(source.getId());
			} else {
				if(source.getDatabaseType().getDetail() !=null)
					sb.append(source.getDatabaseType().getDetail().getDisplayName()).append(COLON).append(source.getId());
				else
					sb.append(source.getDatabaseType().getName()).append(COLON).append(source.getId());
			}
		}

		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((evidenceCode == null) ? 0 : evidenceCode.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EvidenceImpl other = (EvidenceImpl) obj;
		if (evidenceCode != other.evidenceCode)
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		return true;
	}

	
}
