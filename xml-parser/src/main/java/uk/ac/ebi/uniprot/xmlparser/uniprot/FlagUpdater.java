package uk.ac.ebi.uniprot.xmlparser.uniprot;


import uk.ac.ebi.uniprot.domain.uniprot.description.Flag;
import uk.ac.ebi.uniprot.domain.uniprot.description.FlagType;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.factory.ProteinDescriptionFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.SequenceType;
import uk.ac.ebi.uniprot.xmlparser.Updater;

public class FlagUpdater implements Updater<SequenceType, ProteinDescription> {

	private static final String MULTIPLE = "multiple";
	private static final String SINGLE = "single";

	@Override
	public ProteinDescription fromXml(ProteinDescription modelObject, SequenceType xmlObject) {
		Flag flag = null;
		String frag = xmlObject.getFragment();
		if(xmlObject.isPrecursor() !=null && xmlObject.isPrecursor()){
			
			if(SINGLE.equals(frag)){
				flag = ProteinDescriptionFactory.INSTANCE.createFlag(FlagType.FRAGMENT_PRECURSOR);
			}else if (MULTIPLE.equals(frag)){
				flag = ProteinDescriptionFactory.INSTANCE.createFlag(FlagType.FRAGMENTS_PRECURSOR);
			}else {
				flag = ProteinDescriptionFactory.INSTANCE.createFlag(FlagType.PRECURSOR);
			}		
		}else if (SINGLE.equals(frag)){
			flag = ProteinDescriptionFactory.INSTANCE.createFlag(FlagType.FRAGMENT);
		}else if (MULTIPLE.equals(frag)){
			flag = ProteinDescriptionFactory.INSTANCE.createFlag(FlagType.FRAGMENTS);
		}
		if(flag !=null) {
			modelObject.setFlag(flag);
		}
		return modelObject;
	}

	@Override
	public void toXml(SequenceType xmlObject, ProteinDescription modelObject) {
		Flag flag =modelObject.getFlag();
		if(flag !=null) {
			FlagType type = flag.getType();
			switch(type) {
			case FRAGMENT:
				xmlObject.setFragment(SINGLE);
				break;
			case FRAGMENTS:
				xmlObject.setFragment(MULTIPLE);
				break;
			case PRECURSOR:
				xmlObject.setPrecursor(true);
				break;
			case FRAGMENT_PRECURSOR:
				xmlObject.setFragment(SINGLE);
				xmlObject.setPrecursor(true);
				break;
			case FRAGMENTS_PRECURSOR:
				xmlObject.setFragment(MULTIPLE);
				xmlObject.setPrecursor(true);
				break;
			
			}
		}
	
	}

}
