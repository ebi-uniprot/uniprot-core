package org.uniprot.core.xml.uniprot;

import java.util.Optional;

import org.uniprot.core.uniprotkb.description.Flag;
import org.uniprot.core.uniprotkb.description.FlagType;
import org.uniprot.core.uniprotkb.description.ProteinDescription;
import org.uniprot.core.uniprotkb.description.impl.ProteinDescriptionBuilder;
import org.uniprot.core.xml.Updater;
import org.uniprot.core.xml.jaxb.uniprot.SequenceType;

public class FlagUpdater implements Updater<SequenceType, ProteinDescription> {

    private static final String MULTIPLE = "multiple";
    private static final String SINGLE = "single";

    @Override
    public ProteinDescription fromXml(ProteinDescription modelObject, SequenceType xmlObject) {
        ProteinDescriptionBuilder result = ProteinDescriptionBuilder.from(modelObject);
        if (xmlObject != null) {
            FlagType fType =
                    Optional.ofNullable(modelObject.getFlag()).map(Flag::getType).orElse(null);
            String frag = xmlObject.getFragment();
            if (xmlObject.isPrecursor() != null && xmlObject.isPrecursor()) {
                if (SINGLE.equals(frag)) {
                    fType = FlagType.FRAGMENT_PRECURSOR;
                } else if (MULTIPLE.equals(frag)) {
                    fType = FlagType.FRAGMENTS_PRECURSOR;
                } else {
                    fType = FlagType.PRECURSOR;
                }
            } else if (SINGLE.equals(frag)) {
                fType = FlagType.FRAGMENT;
            } else if (MULTIPLE.equals(frag)) {
                fType = FlagType.FRAGMENTS;
            }
            result.flag(fType);
        }
        return result.build();
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
