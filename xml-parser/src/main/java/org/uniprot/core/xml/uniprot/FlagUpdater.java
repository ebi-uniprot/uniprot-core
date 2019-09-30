package org.uniprot.core.xml.uniprot;

import org.uniprot.core.uniprot.description.Flag;
import org.uniprot.core.uniprot.description.FlagType;
import org.uniprot.core.uniprot.description.ProteinDescription;
import org.uniprot.core.uniprot.description.builder.FlagBuilder;
import org.uniprot.core.xml.Updater;
import org.uniprot.core.xml.jaxb.uniprot.SequenceType;

public class FlagUpdater implements Updater<SequenceType, ProteinDescription> {

    private static final String MULTIPLE = "multiple";
    private static final String SINGLE = "single";

    @Override
    public ProteinDescription fromXml(ProteinDescription modelObject, SequenceType xmlObject) {
        Flag flag = null;
        String frag = xmlObject.getFragment();
        if (xmlObject.isPrecursor() != null && xmlObject.isPrecursor()) {

            if (SINGLE.equals(frag)) {
                flag = new FlagBuilder(FlagType.FRAGMENT_PRECURSOR).build();
            } else if (MULTIPLE.equals(frag)) {
                flag = new FlagBuilder(FlagType.FRAGMENTS_PRECURSOR).build();
            } else {
                flag = new FlagBuilder(FlagType.PRECURSOR).build();
            }
        } else if (SINGLE.equals(frag)) {
            flag = new FlagBuilder(FlagType.FRAGMENT).build();
        } else if (MULTIPLE.equals(frag)) {
            flag = new FlagBuilder(FlagType.FRAGMENTS).build();
        }
        if (flag != null) {
            modelObject.setFlag(flag);
        }
        return modelObject;
    }

    @Override
    public void toXml(SequenceType xmlObject, ProteinDescription modelObject) {
        Flag flag = modelObject.getFlag();
        if (flag != null) {
            FlagType type = flag.getType();
            switch (type) {
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
