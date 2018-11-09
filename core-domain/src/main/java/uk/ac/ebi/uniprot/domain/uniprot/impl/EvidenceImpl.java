package uk.ac.ebi.uniprot.domain.uniprot.impl;


import com.fasterxml.jackson.annotation.JsonIgnore;

import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.xdb.DBCrossReference;
import uk.ac.ebi.uniprot.domain.xdb.impl.DBCrossReferenceImpl;

public class EvidenceImpl implements Evidence {
	private static final String PIPE = "|";
	private static final String COLON = ":";
	private EvidenceCode evidenceCode;
	private DBCrossReference source;
	
	
	static final String REFERENCE ="Reference";
	static final String REF_PREFIX ="Ref.";
	
    public static Evidence parseEvidenceLine(String val) {
        String[] token = val.split("\\|");
        String code = token[0];
        DBCrossReference xref =null;
        if (token.length == 2) {
            String[] tokens2 = token[1].split(":");
            if (tokens2.length == 2) {
            	xref = new DBCrossReferenceImpl(tokens2[0], tokens2[1]);
            } else if(tokens2[0].startsWith(REF_PREFIX)) {
            	xref = new DBCrossReferenceImpl(REFERENCE, tokens2[0]);        
            }else {
            	throw new IllegalArgumentException(val + " is not valid evidence string");
            }
        }
        EvidenceCode evidenceCode = EvidenceCode.codeOf(code);
        return new EvidenceImpl(evidenceCode, xref);
    }


	public EvidenceImpl() {

	}
	public EvidenceImpl(EvidenceCode evidenceCode, String databaseName, String dbId) {
		this.evidenceCode = evidenceCode;
		this.source = new DBCrossReferenceImpl(databaseName, dbId);
	}

	public EvidenceImpl(EvidenceCode evidenceCode, DBCrossReference source) {
		this.evidenceCode = evidenceCode;
		this.source = source;
	}

	@Override
	public EvidenceCode getEvidenceCode() {
		return evidenceCode;
	}


	public void setEvidenceCode(EvidenceCode evidenceCode) {
		this.evidenceCode = evidenceCode;
	}

	@Override
	public DBCrossReference getSource() {
		return source;
	}

	
	public void setSource(DBCrossReference source) {
		this.source = source;
	}

	@Override
	public int compareTo(Evidence o) {
		return this.getValue().compareTo(o.getValue());
	}

	@Override
	public String toString() {
		return getValue();
	}
	@JsonIgnore
	@Override
	public String getValue() {

		StringBuilder sb = new StringBuilder();
		sb.append(evidenceCode.getCode());
		if (source != null) {
			sb.append(PIPE);
			if (source.getDatabaseName().equals(REFERENCE)) {
				sb.append(source.getId());
			} else {
				sb.append(source.getDatabaseName()).append(COLON).append(source.getId());
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
