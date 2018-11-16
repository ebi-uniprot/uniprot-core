package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.WebResourceComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.WebResourceCommentBuilder;

import static org.junit.Assert.*;

public class WebResourceCommentBuilderTest {

    @Test
    public void testSetDatabaseName() {
        WebResourceCommentBuilder builder =  WebResourceCommentBuilder.newInstance();
        String databaseName ="someDbName";
        WebResourceComment comment = builder.resourceName(databaseName)
                .build();
        assertEquals(CommentType.WEBRESOURCE, comment.getCommentType());
        assertEquals(databaseName, comment.getResourceName());
        assertFalse(comment.getResourceUrl().isPresent());
        assertFalse(comment.isFtp());
        assertFalse(null, comment.getNote().isPresent());
    }

    @Test
    public void testSetDatabaseUrl() {
        WebResourceCommentBuilder builder =  WebResourceCommentBuilder.newInstance();
        String databaseName ="someDbName";
        String databaseUrl ="some url";
        WebResourceComment comment = builder.resourceName(databaseName)
                .resourceUrl(databaseUrl)
                .build();
        assertEquals(CommentType.WEBRESOURCE, comment.getCommentType());
        assertEquals(databaseName, comment.getResourceName());
        assertEquals(databaseUrl, comment.getResourceUrl().get());
        assertFalse( comment.isFtp());
        assertFalse(comment.getNote().isPresent());
    }

    @Test
    public void testSetDatabaseFtp() {
        WebResourceCommentBuilder builder =  WebResourceCommentBuilder.newInstance();
        String databaseName ="someDbName";
        String databaseFtp ="some ftp";
        WebResourceComment comment = builder.resourceName(databaseName)
                .resourceUrl(databaseFtp)
                .isFtp(true)
                .build();
        assertEquals(CommentType.WEBRESOURCE, comment.getCommentType());
        assertEquals(databaseName, comment.getResourceName());
        assertEquals(databaseFtp, comment.getResourceUrl().get());
        assertTrue( comment.isFtp());
        assertFalse(comment.getNote().isPresent());
    }

    @Test
    public void testSetNote() {
        WebResourceCommentBuilder builder =  WebResourceCommentBuilder.newInstance();
        String databaseName ="someDbName";
        String databaseUrl ="some url";
        String note ="some note";
        WebResourceComment comment = builder.resourceName(databaseName)
                .resourceUrl(databaseUrl)
                .note(note)
                .build();
        assertEquals(CommentType.WEBRESOURCE, comment.getCommentType());
        assertEquals(databaseName, comment.getResourceName());
        assertEquals(databaseUrl, comment.getResourceUrl().get());
        assertFalse( comment.isFtp());
        assertEquals(note, comment.getNote().get());
    }

}
