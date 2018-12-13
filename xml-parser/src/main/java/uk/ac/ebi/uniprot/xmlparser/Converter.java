package uk.ac.ebi.uniprot.xmlparser;

public interface Converter<F, T> {
	T fromXml(F xmlObj);
	F toXml(T uniObj);
	
}
