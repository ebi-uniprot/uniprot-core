package org.uniprot.core.xml.uniprot.comment;

import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;

import java.util.regex.Pattern;

public class SCConflictConverter implements Converter<CommentType.Conflict, String> {
    private static final Pattern TARGET_A = Pattern.compile("[A-Z]{1}\\d{5}");
    private static final Pattern TARGET_B = Pattern.compile("[A-Z]{4}\\d{8,9}");
    private static final Pattern TARGET_C = Pattern.compile("[A-Z]{2}\\d{6}");
    private static final Pattern TARGET_D = Pattern.compile("[A-Z]{3}\\d{5}\\.\\d");
    private static final Pattern TARGET_E = Pattern.compile("Ref\\.\\d+");
    private final ObjectFactory xmlUniprotFactory;

    public SCConflictConverter() {
        this(new ObjectFactory());
    }

    public SCConflictConverter(ObjectFactory xmlUniprotFactory) {
        this.xmlUniprotFactory = xmlUniprotFactory;
    }

    @Override
    public String fromXml(CommentType.Conflict conflict) {
        String sequence;
        if (conflict != null && conflict.getSequence() != null) {

            CommentType.Conflict.Sequence sequenceType = conflict.getSequence();

            if (sequenceType.getResource().equals("EMBL")) {
                sequence = sequenceType.getId();
            } else if (sequenceType.getResource().equals("EMBL-CDS")) {
                sequence = sequenceType.getId() + "." + sequenceType.getVersion().toString();
            } else {
                sequence = "Ref." + conflict.getRef();
            }

            return sequence;
        } else if (conflict != null
                && conflict.getSequence() == null
                && conflict.getRef() != null) {
            sequence = "Ref." + conflict.getRef();
            return sequence;
        } else return null;
    }

    @Override
    public CommentType.Conflict toXml(String sequenceId) {
        if (sequenceId != null && !sequenceId.trim().isEmpty()) {
            CommentType.Conflict conflict = xmlUniprotFactory.createCommentTypeConflict();
            CommentType.Conflict.Sequence sequence =
                    xmlUniprotFactory.createCommentTypeConflictSequence();

            if (TARGET_A.matcher(sequenceId).matches()
                    || TARGET_B.matcher(sequenceId).matches()
                    || TARGET_C.matcher(sequenceId).matches()) {
                sequence.setId(sequenceId);
                sequence.setResource("EMBL");

                conflict.setSequence(sequence);
            } else if (TARGET_D.matcher(sequenceId).matches()) {
                sequence.setId(sequenceId.substring(0, sequenceId.indexOf('.')));
                sequence.setResource("EMBL-CDS");

                String version = sequenceId.substring(sequenceId.indexOf('.') + 1);
                sequence.setVersion(new Integer(version));

                conflict.setSequence(sequence);
            } else if (TARGET_E.matcher(sequenceId).matches()) {
                String ref = sequenceId.substring(sequenceId.indexOf('.') + 1, sequenceId.length());
                conflict.setRef(ref);
                conflict.setSequence(null);
            }

            return conflict;
        } else return null;
    }
}
