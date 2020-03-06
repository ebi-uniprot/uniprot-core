package org.uniprot.core.xml.uniprot;

import org.uniprot.core.Sequence;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.jaxb.uniprot.SequenceType;

public class SequenceConverter implements Converter<SequenceType, Sequence> {
    private final ObjectFactory xmlUniprotFactory;

    public SequenceConverter() {
        this(new ObjectFactory());
    }

    public SequenceConverter(ObjectFactory xmlUniprotFactory) {
        this.xmlUniprotFactory = xmlUniprotFactory;
    }

    @Override
    public Sequence fromXml(SequenceType xmlObj) {
        String sequence = xmlObj.getValue();
        //	sequence = sequence.replaceAll(" ", "");
        return new SequenceBuilder(sequence).build();
    }

    @Override
    public SequenceType toXml(Sequence uniObj) {
        SequenceType xmlObj = xmlUniprotFactory.createSequenceType();
        xmlObj.setChecksum(uniObj.getCrc64());
        xmlObj.setLength(uniObj.getValue().length());
        xmlObj.setMass(uniObj.getMolWeight());
        String sequence = uniObj.getValue();
        //		StringBuilder sb = new StringBuilder();
        //
        //		int count = 0;
        //		int size = 60;
        //		for (int i = 0; i < sequence.length(); i = i + size) {
        //			if (count > 0) {
        //				sb.append(" ");
        //			}
        //			if (sequence.length() > i + size) {
        //
        //				sb.append(sequence.substring(i, i + size));
        //				count++;
        //			} else {
        //				sb.append(sequence.substring(i));
        //			}
        //		}

        xmlObj.setValue(sequence);

        return xmlObj;
    }
}
