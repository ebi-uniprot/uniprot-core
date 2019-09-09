package org.uniprot.core.uniprot.comment.builder;

import org.junit.jupiter.api.Test;
import org.uniprot.core.TestHelper;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.WebResourceComment;

import static org.junit.jupiter.api.Assertions.*;


public class WebResourceCommentBuilderTest {

    @Test
    public void testSetDatabaseName() {
        WebResourceCommentBuilder builder = new WebResourceCommentBuilder();
        String databaseName = "someDbName";
        WebResourceComment comment = builder.resourceName(databaseName)
                .build();
        assertEquals(CommentType.WEBRESOURCE, comment.getCommentType());
        assertEquals(databaseName, comment.getResourceName());
        assertEquals("", comment.getResourceUrl());
        assertFalse(comment.isFtp());
        assertEquals("", comment.getNote());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testSetDatabaseUrl() {
        WebResourceCommentBuilder builder = new WebResourceCommentBuilder();
        String databaseName = "someDbName";
        String databaseUrl = "some url";
        WebResourceComment comment = builder.resourceName(databaseName)
                .resourceUrl(databaseUrl)
                .build();
        assertEquals(CommentType.WEBRESOURCE, comment.getCommentType());
        assertEquals(databaseName, comment.getResourceName());
        assertEquals(databaseUrl, comment.getResourceUrl());
        assertFalse(comment.isFtp());
        assertEquals("", comment.getNote());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testSetDatabaseFtp() {
        WebResourceCommentBuilder builder = new WebResourceCommentBuilder();
        String databaseName = "someDbName";
        String databaseFtp = "some ftp";
        WebResourceComment comment = builder.resourceName(databaseName)
                .resourceUrl(databaseFtp)
                .isFtp(true)
                .build();
        assertEquals(CommentType.WEBRESOURCE, comment.getCommentType());
        assertEquals(databaseName, comment.getResourceName());
        assertEquals(databaseFtp, comment.getResourceUrl());
        assertTrue(comment.isFtp());
        assertEquals("", comment.getNote());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testSetNote() {
        WebResourceCommentBuilder builder = new WebResourceCommentBuilder();
        String databaseName = "someDbName";
        String databaseUrl = "some url";
        String note = "some note";
        WebResourceComment comment = builder.resourceName(databaseName)
                .resourceUrl(databaseUrl)
                .note(note)
                .build();
        assertEquals(CommentType.WEBRESOURCE, comment.getCommentType());
        assertEquals(databaseName, comment.getResourceName());
        assertEquals(databaseUrl, comment.getResourceUrl());
        assertFalse(comment.isFtp());
        assertEquals(note, comment.getNote());
        TestHelper.verifyJson(comment);
    }
}
