package org.uniprot.core.xml.uniparc;

import org.uniprot.core.Sequence;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniparc.ObjectFactory;

/**
 * @author jluo
 * @date: 23 May 2019
 */
public class SequenceConverter
        implements Converter<org.uniprot.core.xml.jaxb.uniparc.Sequence, Sequence> {
    private final ObjectFactory xmlFactory;

    public SequenceConverter() {
        this(new ObjectFactory());
    }

    public SequenceConverter(ObjectFactory xmlFactory) {
        this.xmlFactory = xmlFactory;
    }

    @Override
    public Sequence fromXml(org.uniprot.core.xml.jaxb.uniparc.Sequence xmlObj) {
        String sequence = xmlObj.getContent();
        // sequence = sequence.replaceAll(" ", "");
        return new SequenceBuilder(sequence).build();
    }

    @Override
    public org.uniprot.core.xml.jaxb.uniparc.Sequence toXml(Sequence uniObj) {
        org.uniprot.core.xml.jaxb.uniparc.Sequence xmlObj = xmlFactory.createSequence();
        xmlObj.setContent(uniObj.getValue());
        xmlObj.setChecksum(uniObj.getCrc64());
        xmlObj.setLength(uniObj.getLength());
        return xmlObj;
    }
    //	private String convertSeq(String sequence) {
    //		StringBuilder sb = new StringBuilder();
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
    //		return sb.toString();
    //	}
}
