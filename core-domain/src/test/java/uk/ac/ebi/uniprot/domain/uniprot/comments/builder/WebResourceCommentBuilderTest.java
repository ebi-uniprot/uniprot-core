package uk.ac.ebi.uniprot.domain.uniprot.comments.builder;

import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.WebResourceComment;

import org.junit.Test;

import static org.junit.Assert.*;

public class WebResourceCommentBuilderTest {

    @Test
    public void testSetDatabaseName() {
        WebResourceCommentBuilder builder =  WebResourceCommentBuilder.newInstance();
        String databaseName ="someDbName";
        WebResourceComment comment = builder.databaseName(databaseName)
                .build();
        assertEquals(CommentType.WEBRESOURCE, comment.getCommentType());
        assertEquals(databaseName, comment.getDatabaseName());
        assertEquals(null, comment.getDatabaseURL());
        assertEquals(null, comment.getDatabaseFTP());
        assertEquals(null, comment.getDatabaseNote());
    }

    @Test
    public void testSetDatabaseUrl() {
        WebResourceCommentBuilder builder =  WebResourceCommentBuilder.newInstance();
        String databaseName ="someDbName";
        String databaseUrl ="some url";
        WebResourceComment comment = builder.databaseName(databaseName)
                .databaseUrl(databaseUrl)
                .build();
        assertEquals(CommentType.WEBRESOURCE, comment.getCommentType());
        assertEquals(databaseName, comment.getDatabaseName());
        assertEquals(databaseUrl, comment.getDatabaseURL());
        assertEquals(null, comment.getDatabaseFTP());
        assertEquals(null, comment.getDatabaseNote());
    }

    @Test
    public void testSetDatabaseFtp() {
        WebResourceCommentBuilder builder =  WebResourceCommentBuilder.newInstance();
        String databaseName ="someDbName";
        String databaseFtp ="some ftp";
        WebResourceComment comment = builder.databaseName(databaseName)
                .databaseFtp(databaseFtp)
                .build();
        assertEquals(CommentType.WEBRESOURCE, comment.getCommentType());
        assertEquals(databaseName, comment.getDatabaseName());
        assertEquals(null, comment.getDatabaseURL());
        assertEquals(databaseFtp, comment.getDatabaseFTP());
        assertEquals(null, comment.getDatabaseNote());
    }

    @Test
    public void testSetNote() {
        WebResourceCommentBuilder builder =  WebResourceCommentBuilder.newInstance();
        String databaseName ="someDbName";
        String databaseUrl ="some url";
        String note ="some note";
        WebResourceComment comment = builder.databaseName(databaseName)
                .databaseUrl(databaseUrl)
                .note(note)
                .build();
        assertEquals(CommentType.WEBRESOURCE, comment.getCommentType());
        assertEquals(databaseName, comment.getDatabaseName());
        assertEquals(databaseUrl, comment.getDatabaseURL());
        assertEquals(null, comment.getDatabaseFTP());
        assertEquals(note, comment.getDatabaseNote());
    }

}
