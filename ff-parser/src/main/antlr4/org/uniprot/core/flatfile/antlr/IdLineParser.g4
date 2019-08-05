/*
ID   EntryName Status; SequenceLength.
*/
parser grammar IdLineParser;

options { tokenVocab=IdLineLexer; superClass=org.uniprot.core.flatfile.antlr.AbstractUniProtParser;}

id_id: ID_HEAD entry_name SPACE+ review_status SPACE+ length SPACE AA DOT_NEWLINE;

review_status: REVIEW_STATUS_REVIEWED | REVIEW_STATUS_UNREVIEWED;
               
length: INT;

entry_name: ENTRY_NAME;
