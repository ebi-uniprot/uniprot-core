parser grammar PeLineParser;


options { tokenVocab=PeLineLexer; superClass=uk.ac.ebi.uniprot.flatfile.antlr.AbstractUniProtParser; }

pe_pe: PE_HEADER  pe_level SEMICOLON NEWLINE;

pe_level : LEVEL1 | LEVEL2 | LEVEL3| LEVEL4| LEVEL5;