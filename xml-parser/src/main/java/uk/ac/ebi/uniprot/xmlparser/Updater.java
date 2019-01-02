package uk.ac.ebi.uniprot.xmlparser;

public interface Updater <F, T> {
	 T fromXml(T modelObject, F xmlObject);

	 void toXml(F xmlObject, T modelObject);
}
