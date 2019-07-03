/*
OH   NCBI_TaxID=TaxID; HostName.

The length of an OH line may exceed 75 characters.
*/

parser grammar OhLineParser;

options { tokenVocab=OhLineLexer; superClass=uk.ac.ebi.uniprot.flatfile.antlr.AbstractUniProtParser;}

oh_oh: oh_line+;
oh_line: OH_HEADER NCBI_TAX tax  SEPARATOR hostname  LINE_END;

tax: TAX;
hostname: HOST_WORD DOT? (SPACE HOST_WORD DOT?)*;



