parser grammar TextHelperParser;

options { tokenVocab=TextHelperLexer;}

text_containing_change_of_line:
    REMOVE_CHANGE_OF_LINE_WORD (REMOVE_CHANGE_OF_LINE_SPACE|REMOVE_CHANGE_OF_LINE_CHANGE_OF_LINE REMOVE_CHANGE_OF_LINE_WORD)*;

text_cc_disease_abbr_mim:
   CC_DISEASE_ABBR_MIM_LEFT_BRACKET
   p_text_cc_disease_abbr_mim_abbr
   CC_DISEASE_ABBR_MIM_RIGHT_BRACKET
   (CC_DISEASE_ABBR_MIM_SPACE|CC_DISEASE_ABBR_MIM_CHANGE_LINE)
   CC_DISEASE_ABBR_MIM_LEFT_BRACE
   p_text_cc_disease_abbr_mim_mim
   CC_DISEASE_ABBR_MIM_RIGHT_BRACE
   CC_DISEASE_ABBR_MIM_COLON;

p_text_cc_disease_abbr_mim_abbr:
   p_text_cc_disease_abbr_mim_abbr_word+ ((CC_DISEASE_ABBR_MIM_SPACE|CC_DISEASE_ABBR_MIM_CHANGE_LINE) p_text_cc_disease_abbr_mim_abbr_word+)*;
p_text_cc_disease_abbr_mim_mim:
   CC_DISEASE_ABBR_MIM_MIM CC_DISEASE_ABBR_MIM_VALUE;
p_text_cc_disease_abbr_mim_abbr_word: CC_DISEASE_ABBR_MIM_WORD
           | (CC_DISEASE_ABBR_MIM_LEFT_BRACKET p_text_cc_disease_abbr_mim_abbr_word CC_DISEASE_ABBR_MIM_RIGHT_BRACKET);


text_cc_disease_pubmed :
    CC_DISEASE_PUBMED_LEFT_BRACKET
        CC_DISEASE_PUBMED_ID
            (CC_DISEASE_PUBMED_COMA ( CC_DISEASE_PUBMED_CHANGE_LINE|CC_DISEASE_PUBMED_SPACE ) CC_DISEASE_PUBMED_ID)*
    CC_DISEASE_PUBMED_RIGHT_BRACKET;

text_volume_page:
    VOLUME_PAGE_WORD VOLUME_PAGE_COLON VOLUME_PAGE_WORD VOLUME_PAGE_DASH VOLUME_PAGE_WORD;