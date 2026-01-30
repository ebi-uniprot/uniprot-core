parser grammar UniprotAAParser;

options { tokenVocab=UniprotAALexer; superClass=org.uniprot.core.flatfile.antlr.AbstractUniProtParser;}


ff: entry+;

entry:
        ac
        de?
        gn?
        cc?
        kw?
        ft?
        END_OF_ENTRY
        NEW_LINE?
        ;


ac: AC_LINE+;
de: DE_LINE+;
gn: GN_LINE+;
cc: CC_LINE+;
kw: KW_LINE+;
ft: FT_LINE+;



