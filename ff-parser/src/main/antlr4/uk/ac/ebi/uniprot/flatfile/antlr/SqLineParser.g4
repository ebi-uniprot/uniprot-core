/*
SQ   SEQUENCE   256 AA;  29735 MW;  B4840739BF7D4121 CRC64;
     MAFSAEDVLK EYDRRRRMEA LLLSLYYPND RKLLDYKEWS PPRVQVECPK APVEWNNPPS
     EKGLIVGHFS GIKYKGEKAQ ASEVDVNKMC CWVSKFKDAM RRYQGIQTCK IPGKVLSDLD
     AKIKAYNLTV EGVEGFVRYS RVTKQHVAAF LKELRHSKQY ENVNLIHYIL TDKRVDIQHL
     EKDLVKDFKA LVESAHRMRQ GHMINVKYIL YQLLKKHGHG PDGPDILTVK TGSKGVLYDD
     SFRKIYTDLG WKFTPL
*/

parser grammar SqLineParser;

options { tokenVocab=SqLineLexer; superClass=uk.ac.ebi.uniprot.flatfile.antlr.AbstractUniProtParser;}

sq_sq: sq_head sq_block;

sq_head: SQ SPACE3 SEQUENCE SPACE3 sq_length SPACE1 AA SPACE2
        molecular_weight SPACE1 MW SPACE2 crc SPACE1 CRC64 NEWLINE;

sq_length: INT;
molecular_weight: INT;

sq_block: sq_line* sq_line_last;

sq_line: SPACE5 sq_letter_block6 NEWLINE;
sq_line_last: SPACE5 (sq_letter_blocks SPACE1)? sq_letters NEWLINE;

sq_letter_blocks: (sq_letter_10|sq_letter_block2|sq_letter_block3|sq_letter_block4|sq_letter_block5);

sq_letter_block6 : sq_letter_10 SPACE1 sq_letter_10 SPACE1 sq_letter_10 SPACE1 sq_letter_10 SPACE1 sq_letter_10 SPACE1 sq_letter_10;
sq_letter_block5 : sq_letter_10 SPACE1 sq_letter_10 SPACE1 sq_letter_10 SPACE1 sq_letter_10 SPACE1 sq_letter_10;
sq_letter_block4 : sq_letter_10 SPACE1 sq_letter_10 SPACE1 sq_letter_10 SPACE1 sq_letter_10;
sq_letter_block3 : sq_letter_10 SPACE1 sq_letter_10 SPACE1 sq_letter_10;
sq_letter_block2 : sq_letter_10 SPACE1 sq_letter_10;

sq_letter_10: SQLETTER10;
sq_letters : SQLETTER|SQLETTER2|SQLETTER3|SQLETTER4|SQLETTER5|SQLETTER6|SQLETTER7|SQLETTER8|SQLETTER9|SQLETTER10;

crc: CRCLETTERS|INT;
