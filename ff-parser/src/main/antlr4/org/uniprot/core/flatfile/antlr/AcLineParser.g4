parser grammar AcLineParser;


options { tokenVocab=AcLineLexer; superClass=org.uniprot.core.flatfile.antlr.AbstractUniProtParser; }

ac_ac: ac_line+;

ac_line: AC_HEAD (accession SPACE1)* (accession NEWLINE);

accession : ac SEMICOLON;

ac: ACCESSION (DASH INTEGER)?;

           