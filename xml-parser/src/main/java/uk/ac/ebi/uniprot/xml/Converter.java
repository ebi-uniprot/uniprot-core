package uk.ac.ebi.uniprot.xml;

public interface Converter<F, T> {
	T fromXml(F xmlObj);
	F toXml(T uniObj);
	
}
