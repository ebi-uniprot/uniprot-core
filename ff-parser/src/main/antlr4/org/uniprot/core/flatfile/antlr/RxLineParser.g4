/*
RX   Bibliographic_db=IDENTIFIER[; Bibliographic_db=IDENTIFIER...];

RX   MEDLINE=83283433; PubMed=6688356;
RX   PubMed=15626370; DOI=10.1016/j.toxicon.2004.10.011;
RX   MEDLINE=22709107; PubMed=12788972; DOI=10.1073/pnas.1130426100;
RX   AGRICOLA=IND20551642; DOI=10.1007/BF00224104;
*/

parser grammar RxLineParser;

options { tokenVocab=RxLineLexer; superClass=org.uniprot.core.flatfile.antlr.AbstractUniProtParser;}

rx_rx : RX_HEADER rx ((SPACE|CHANGE_OF_LINE) rx)* NEW_LINE;

rx : (pubmed|doi|agri|medline) SEMICOLON;

pubmed: PUBMED VALUE;
doi: DOI VALUE;
agri: AGRICOLA VALUE;

//medline is not used anymore, the model listener will simply ignore it.
medline: MEDLINE VALUE;


