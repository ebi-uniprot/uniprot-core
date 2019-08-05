/*
RG   The mouse genome sequencing consortium;
*/

parser grammar RgLineParser;

options { tokenVocab=RgLineLexer; superClass=org.uniprot.core.flatfile.antlr.AbstractUniProtParser;}

rg_rg: rg_line+;

rg_line: RG_HEADER rg_value RG_END;

rg_value: WORD ((SPACE|CHANGE_OF_LINE) WORD)*;