/*
RG   The mouse genome sequencing consortium;
*/

parser grammar RgLineParser;

options { tokenVocab=RgLineLexer; }

rg_rg: rg_line+;

rg_line: RG_HEADER rg_value RG_END;

rg_value: WORD ((SPACE|CHANGE_OF_LINE) WORD)*;