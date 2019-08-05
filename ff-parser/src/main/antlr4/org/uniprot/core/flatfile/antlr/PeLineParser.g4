parser grammar PeLineParser;


options { tokenVocab=PeLineLexer; superClass=org.uniprot.core.flatfile.antlr.AbstractUniProtParser; }

pe_pe: PE_HEADER  pe_level SEMICOLON NEWLINE;

pe_level : LEVEL1 | LEVEL2 | LEVEL3| LEVEL4| LEVEL5;