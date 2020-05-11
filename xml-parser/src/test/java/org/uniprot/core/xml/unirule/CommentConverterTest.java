package org.uniprot.core.xml.unirule;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;

public class CommentConverterTest extends AbstractConverterTest {

    public static CommentType createObject() {
        CommentType commentType = objectCreator.createLoremIpsumObject(CommentType.class);
        commentType.setType("disease");
        commentType.getDisease().getDbReference().setType("MIM");
        return commentType;
    }

    public static class CommentTypeList extends ArrayList<CommentType> {}

    public static List<CommentType> createObjects() {
        return objectCreator.createLoremIpsumObject(CommentTypeList.class);
    }
}
