package uk.ebi.uniprot.scorer.uniprotkb;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniProtParser;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 08-Mar-2010 Time: 13:47:47 To change this template use File | Settings
 * | File Templates.
 */
public class Q04756ScoredTest {

    private String entry = "ID   HGFA_HUMAN              Reviewed;         655 AA.\n" +
            "AC   Q04756; Q14726; Q2M1W7; Q53X47;\n" +
            "DT   01-JUN-1994, integrated into UniProtKB/Swiss-Prot.\n" +
            "DT   01-JUN-1994, sequence version 1.\n" +
            "DT   02-MAR-2010, entry version 107.\n" +
            "DE   RecName: Full=Hepatocyte growth factor activator;\n" +
            "DE            Short=HGF activator;\n" +
            "DE            Short=HGFA;\n" +
            "DE            EC=3.4.21.-;\n" +
            "DE   Contains:\n" +
            "DE     RecName: Full=Hepatocyte growth factor activator short chain;\n" +
            "DE   Contains:\n" +
            "DE     RecName: Full=Hepatocyte growth factor activator long chain;\n" +
            "DE   Flags: Precursor;\n" +
            "GN   Name=HGFAC;\n" +
            "OS   Homo sapiens (Human).\n" +
            "OC   Eukaryota; Metazoa; Chordata; Craniata; Vertebrata; Euteleostomi;\n" +
            "OC   Mammalia; Eutheria; Euarchontoglires; Primates; Haplorrhini;\n" +
            "OC   Catarrhini; Hominidae; Homo.\n" +
            "OX   NCBI_TaxID=9606;\n" +
            "RN   [1]\n" +
            "RP   NUCLEOTIDE SEQUENCE [MRNA], AND PARTIAL PROTEIN SEQUENCE.\n" +
            "RC   TISSUE=Liver, and Serum;\n" +
            "RX   PubMed=7683665;\n" +
            "RA   Miyazawa K., Shimomura T., Kitamura A., Kondo J., Morimoto Y.,\n" +
            "RA   Kitamura N.;\n" +
            "RT   \"Molecular cloning and sequence analysis of the cDNA for a human\n" +
            "RT   serine protease reponsible for activation of hepatocyte growth factor.\n" +
            "RT   Structural similarity of the protease precursor to blood coagulation\n" +
            "RT   factor XII.\";\n" +
            "RL   J. Biol. Chem. 268:10024-10028(1993).\n" +
            "RN   [2]\n" +
            "RP   NUCLEOTIDE SEQUENCE [GENOMIC DNA].\n" +
            "RX   PubMed=9874200;\n" +
            "RX   DOI=10.1046/j.1432-1327.1998.2580355.x;\n" +
            "RA   Miyazawa K., Wang Y., Minoshima S., Shimizu N., Kitamura N.;\n" +
            "RT   \"Structural organization and chromosomal localization of the human\n" +
            "RT   hepatocyte growth factor activator gene -- phylogenetic and functional\n" +
            "RT   relationship with blood coagulation factor XII, urokinase, and tissue-\n" +
            "RT   type plasminogen activator.\";\n" +
            "RL   Eur. J. Biochem. 258:355-361(1998).\n" +
            "RN   [3]\n" +
            "RP   NUCLEOTIDE SEQUENCE [LARGE SCALE GENOMIC DNA].\n" +
            "RX   PubMed=15815621; DOI=10.1038/nature03466;\n" +
            "RA   Hillier L.W., Graves T.A., Fulton R.S., Fulton L.A., Pepin K.H.,\n" +
            "RA   Minx P., Wagner-McPherson C., Layman D., Wylie K., Sekhon M.,\n" +
            "RA   Becker M.C., Fewell G.A., Delehaunty K.D., Miner T.L., Nash W.E.,\n" +
            "RA   Kremitzki C., Oddy L., Du H., Sun H., Bradshaw-Cordum H., Ali J.,\n" +
            "RA   Carter J., Cordes M., Harris A., Isak A., van Brunt A., Nguyen C.,\n" +
            "RA   Du F., Courtney L., Kalicki J., Ozersky P., Abbott S., Armstrong J.,\n" +
            "RA   Belter E.A., Caruso L., Cedroni M., Cotton M., Davidson T., Desai A.,\n" +
            "RA   Elliott G., Erb T., Fronick C., Gaige T., Haakenson W., Haglund K.,\n" +
            "RA   Holmes A., Harkins R., Kim K., Kruchowski S.S., Strong C.M.,\n" +
            "RA   Grewal N., Goyea E., Hou S., Levy A., Martinka S., Mead K.,\n" +
            "RA   McLellan M.D., Meyer R., Randall-Maher J., Tomlinson C.,\n" +
            "RA   Dauphin-Kohlberg S., Kozlowicz-Reilly A., Shah N.,\n" +
            "RA   Swearengen-Shahid S., Snider J., Strong J.T., Thompson J., Yoakum M.,\n" +
            "RA   Leonard S., Pearman C., Trani L., Radionenko M., Waligorski J.E.,\n" +
            "RA   Wang C., Rock S.M., Tin-Wollam A.-M., Maupin R., Latreille P.,\n" +
            "RA   Wendl M.C., Yang S.-P., Pohl C., Wallis J.W., Spieth J., Bieri T.A.,\n" +
            "RA   Berkowicz N., Nelson J.O., Osborne J., Ding L., Meyer R., Sabo A.,\n" +
            "RA   Shotland Y., Sinha P., Wohldmann P.E., Cook L.L., Hickenbotham M.T.,\n" +
            "RA   Eldred J., Williams D., Jones T.A., She X., Ciccarelli F.D.,\n" +
            "RA   Izaurralde E., Taylor J., Schmutz J., Myers R.M., Cox D.R., Huang X.,\n" +
            "RA   McPherson J.D., Mardis E.R., Clifton S.W., Warren W.C.,\n" +
            "RA   Chinwalla A.T., Eddy S.R., Marra M.A., Ovcharenko I., Furey T.S.,\n" +
            "RA   Miller W., Eichler E.E., Bork P., Suyama M., Torrents D.,\n" +
            "RA   Waterston R.H., Wilson R.K.;\n" +
            "RT   \"Generation and annotation of the DNA sequences of human chromosomes 2\n" +
            "RT   and 4.\";\n" +
            "RL   Nature 434:724-731(2005).\n" +
            "RN   [4]\n" +
            "RP   NUCLEOTIDE SEQUENCE [LARGE SCALE GENOMIC DNA].\n" +
            "RA   Mural R.J., Istrail S., Sutton G.G., Florea L., Halpern A.L.,\n" +
            "RA   Mobarry C.M., Lippert R., Walenz B., Shatkay H., Dew I., Miller J.R.,\n" +
            "RA   Flanigan M.J., Edwards N.J., Bolanos R., Fasulo D., Halldorsson B.V.,\n" +
            "RA   Hannenhalli S., Turner R., Yooseph S., Lu F., Nusskern D.R.,\n" +
            "RA   Shue B.C., Zheng X.H., Zhong F., Delcher A.L., Huson D.H.,\n" +
            "RA   Kravitz S.A., Mouchard L., Reinert K., Remington K.A., Clark A.G.,\n" +
            "RA   Waterman M.S., Eichler E.E., Adams M.D., Hunkapiller M.W., Myers E.W.,\n" +
            "RA   Venter J.C.;\n" +
            "RL   Submitted (SEP-2005) to the EMBL/GenBank/DDBJ databases.\n" +
            "RN   [5]\n" +
            "RP   NUCLEOTIDE SEQUENCE [LARGE SCALE MRNA], AND VARIANT LEU-231.\n" +
            "RC   TISSUE=Liver;\n" +
            "RX   PubMed=15489334; DOI=10.1101/gr.2596504;\n" +
            "RG   The MGC Project Team;\n" +
            "RT   \"The status, quality, and expansion of the NIH full-length cDNA\n" +
            "RT   project: the Mammalian Gene Collection (MGC).\";\n" +
            "RL   Genome Res. 14:2121-2127(2004).\n" +
            "RN   [6]\n" +
            "RP   PROTEIN SEQUENCE OF 36-50.\n" +
            "RX   PubMed=15340161; DOI=10.1110/ps.04682504;\n" +
            "RA   Zhang Z., Henzel W.J.;\n" +
            "RT   \"Signal peptide prediction based on analysis of experimentally\n" +
            "RT   verified cleavage sites.\";\n" +
            "RL   Protein Sci. 13:2819-2824(2004).\n" +
            "RN   [7]\n" +
            "RP   GLYCOSYLATION [LARGE SCALE ANALYSIS] AT ASN-468, AND MASS\n" +
            "RP   SPECTROMETRY.\n" +
            "RC   TISSUE=Plasma;\n" +
            "RX   PubMed=16335952; DOI=10.1021/pr0502065;\n" +
            "RA   Liu T., Qian W.-J., Gritsenko M.A., Camp D.G. II, Monroe M.E.,\n" +
            "RA   Moore R.J., Smith R.D.;\n" +
            "RT   \"Human plasma N-glycoproteome analysis by immunoaffinity subtraction,\n" +
            "RT   hydrazide chemistry, and mass spectrometry.\";\n" +
            "RL   J. Proteome Res. 4:2070-2080(2005).\n" +
            "RN   [8]\n" +
            "RP   GLYCOSYLATION [LARGE SCALE ANALYSIS] AT ASN-468, AND MASS\n" +
            "RP   SPECTROMETRY.\n" +
            "RC   TISSUE=Liver;\n" +
            "RX   PubMed=19159218; DOI=10.1021/pr8008012;\n" +
            "RA   Chen R., Jiang X., Sun D., Han G., Wang F., Ye M., Wang L., Zou H.;\n" +
            "RT   \"Glycoproteomics analysis of human liver tissue by combination of\n" +
            "RT   multiple enzyme digestion and hydrazide chemistry.\";\n" +
            "RL   J. Proteome Res. 8:651-661(2009).\n" +
            "RN   [9]\n" +
            "RP   X-RAY CRYSTALLOGRAPHY (2.6 ANGSTROMS) OF 373-655 ALONE AND IN COMPLEX\n" +
            "RP   WITH INHIBITOR HAI1B, ACTIVE SITE, AND DISULFIDE BONDS.\n" +
            "RX   PubMed=15713485; DOI=10.1016/j.jmb.2004.12.048;\n" +
            "RA   Shia S., Stamos J., Kirchhofer D., Fan B., Wu J., Corpuz R.T.,\n" +
            "RA   Santell L., Lazarus R.A., Eigenbrot C.;\n" +
            "RT   \"Conformational lability in serine protease active sites: structures\n" +
            "RT   of hepatocyte growth factor activator (HGFA) alone and with the\n" +
            "RT   inhibitory domain from HGFA inhibitor-1B.\";\n" +
            "RL   J. Mol. Biol. 346:1335-1349(2005).\n" +
            "RN   [10]\n" +
            "RP   X-RAY CRYSTALLOGRAPHY (2.2 ANGSTROMS) OF 373-655 IN COMPLEX WITH\n" +
            "RP   ANTIBODY, GLYCOSYLATION AT ASN-468, AND DISULFIDE BONDS.\n" +
            "RX   PubMed=18077410; DOI=10.1073/pnas.0708251104;\n" +
            "RA   Wu Y., Eigenbrot C., Liang W.C., Stawicki S., Shia S., Fan B.,\n" +
            "RA   Ganesan R., Lipari M.T., Kirchhofer D.;\n" +
            "RT   \"Structural insight into distinct mechanisms of protease inhibition by\n" +
            "RT   antibodies.\";\n" +
            "RL   Proc. Natl. Acad. Sci. U.S.A. 104:19784-19789(2007).\n" +
            "CC   -!- FUNCTION: Activates hepatocyte growth factor (HGF) by converting\n" +
            "CC       it from a single chain to a heterodimeric form.\n" +
            "CC   -!- SUBUNIT: Heterodimer of a short chain and a long chain linked by a\n" +
            "CC       disulfide bond.\n" +
            "CC   -!- INTERACTION:\n" +
            "CC       O43278:SPINT1; NbExp=1; IntAct=EBI-1041722, EBI-953990;\n" +
            "CC   -!- SUBCELLULAR LOCATION: Secreted. Note=Secreted as an inactive\n" +
            "CC       single-chain precursor and is then activated to a heterodimeric\n" +
            "CC       form.\n" +
            "CC   -!- TISSUE SPECIFICITY: Liver.\n" +
            "CC   -!- SIMILARITY: Belongs to the peptidase S1 family.\n" +
            "CC   -!- SIMILARITY: Contains 2 EGF-like domains.\n" +
            "CC   -!- SIMILARITY: Contains 1 fibronectin type-I domain.\n" +
            "CC   -!- SIMILARITY: Contains 1 fibronectin type-II domain.\n" +
            "CC   -!- SIMILARITY: Contains 1 kringle domain.\n" +
            "CC   -!- SIMILARITY: Contains 1 peptidase S1 domain.\n" +
            "CC   -!- CAUTION: It is uncertain whether Met-1 is the initiator.\n" +
            "CC   -----------------------------------------------------------------------\n" +
            "CC   Copyrighted by the UniProt Consortium, see https://www.uniprot.org/terms\n" +
            "CC   Distributed under the Creative Commons Attribution-NoDerivs License\n" +
            "CC   -----------------------------------------------------------------------\n" +
            "DR   EMBL; D14012; BAA03113.1; -; mRNA.\n" +
            "DR   EMBL; BC112190; AAI12191.1; -; mRNA.\n" +
            "DR   EMBL; D50030; BAA74450.1; -; Genomic_DNA.\n" +
            "DR   EMBL; AL590235; CAM21456.1; -; Genomic_DNA.\n" +
            "DR   EMBL; CH471131; EAW82464.1; -; Genomic_DNA.\n" +
            "DR   EMBL; BC112192; AAI12193.1; -; mRNA.\n" +
            "DR   PIR; A46688; A46688.\n" +
            "DR   RefSeq; NP_001519.1; -.\n" +
            "DR   UniGene; Hs.104; -.\n" +
            "DR   PDB; 1YBW; X-ray; 2.70 A; A/B=373-655.\n" +
            "DR   PDB; 1YC0; X-ray; 2.60 A; A=373-655.\n" +
            "DR   PDB; 2R0K; X-ray; 3.51 A; A=373-655.\n" +
            "DR   PDB; 2R0L; X-ray; 2.20 A; A=408-655, B=373-407.\n" +
            "DR   PDB; 2WUB; X-ray; 2.90 A; A/C=408-655, B/D=373-407.\n" +
            "DR   PDB; 2WUC; X-ray; 2.70 A; A=408-655, B=373-407.\n" +
            "DR   PDB; 3K2U; X-ray; 2.35 A; A=408-655, B=373-407.\n" +
            "DR   PDBsum; 1YBW; -.\n" +
            "DR   PDBsum; 1YC0; -.\n" +
            "DR   PDBsum; 2R0K; -.\n" +
            "DR   PDBsum; 2R0L; -.\n" +
            "DR   PDBsum; 2WUB; -.\n" +
            "DR   PDBsum; 2WUC; -.\n" +
            "DR   PDBsum; 3K2U; -.\n" +
            "DR   SMR; Q04756; 102-148, 199-284.\n" +
            "DR   DIP; DIP-6022N; -.\n" +
            "DR   IntAct; Q04756; 1.\n" +
            "DR   STRING; Q04756; -.\n" +
            "DR   MEROPS; S01.228; -.\n" +
            "DR   PhosphoSitePlus; Q04756; -.\n" +
            "DR   PeptideAtlas; Q04756; -.\n" +
            "DR   PRIDE; Q04756; -.\n" +
            "DR   Ensembl; ENST00000382774; ENSP00000372224; Homo sapiens.\n" +
            "DR   GeneID; 3083; -.\n" +
            "DR   KEGG; hsa:3083; -.\n" +
            "DR   UCSC; uc003ghc.1; human.\n" +
            "DR   CTD; 3083; -.\n" +
            "DR   GeneCards; GC04P003413; -.\n" +
            "DR   H-InvDB; HIX0031501; -.\n" +
            "DR   HGNC; HGNC:4894; HGFAC.\n" +
            "DR   HPA; CAB005215; -.\n" +
            "DR   MIM; 604552; gene.\n" +
            "DR   PharmGKB; PA29270; -.\n" +
            "DR   eggNOG; prNOG15730; -.\n" +
            "DR   HOGENOM; HBG755338; -.\n" +
            "DR   HOVERGEN; HBG004345; -.\n" +
            "DR   InParanoid; Q04756; -.\n" +
            "DR   OMA; RDRAWGY; -.\n" +
            "DR   OrthoDB; EOG9VX4R3; -.\n" +
            "DR   PhylomeDB; Q04756; -.\n" +
            "DR   PMAP-CutDB; Q04756; -.\n" +
            "DR   Bgee; Q04756; -.\n" +
            "DR   CleanEx; HS_HGFAC; -.\n" +
            "DR   GO; GO:0005615; C:extracellular space; IEA:InterPro.\n" +
            "DR   GO; GO:0005515; F:protein binding; IPI:IntAct.\n" +
            "DR   GO; GO:0004252; F:serine-type endopeptidase activity; TAS:ProtInc.\n" +
            "DR   GO; GO:0006508; P:proteolysis; TAS:ProtInc.\n" +
            "DR   InterPro; IPR014394; Coagulation_fac_XII_Hep-GF-Act.\n" +
            "DR   InterPro; IPR006210; EGF-like.\n" +
            "DR   InterPro; IPR013032; EGF-like_reg_CS.\n" +
            "DR   InterPro; IPR000742; EGF_3.\n" +
            "DR   InterPro; IPR000083; Fibrnctn1.\n" +
            "DR   InterPro; IPR000562; FN_type2_col_bd.\n" +
            "DR   InterPro; IPR000001; Kringle.\n" +
            "DR   InterPro; IPR013806; Kringle-like.\n" +
            "DR   InterPro; IPR018056; Kringle_CS.\n" +
            "DR   InterPro; IPR018059; Kringle_sub.\n" +
            "DR   InterPro; IPR018114; Peptidase_S1/S6_AS.\n" +
            "DR   InterPro; IPR001254; Peptidase_S1_S6.\n" +
            "DR   InterPro; IPR001314; Peptidase_S1A.\n" +
            "DR   InterPro; IPR009003; Ser/Cys_Pept_Trypsin-like.\n" +
            "DR   Gene3D; G3DSA:2.40.20.10; Kringle; 1.\n" +
            "DR   Pfam; PF00039; fn1; 1.\n" +
            "DR   Pfam; PF00040; fn2; 1.\n" +
            "DR   Pfam; PF00051; Kringle; 1.\n" +
            "DR   Pfam; PF00089; Trypsin; 1.\n" +
            "DR   PIRSF; PIRSF001146; Factor_XII_HGFA; 1.\n" +
            "DR   PRINTS; PR00722; CHYMOTRYPSIN.\n" +
            "DR   PRINTS; PR00013; FNTYPEII.\n" +
            "DR   PRINTS; PR00018; KRINGLE.\n" +
            "DR   SMART; SM00181; EGF; 2.\n" +
            "DR   SMART; SM00058; FN1; 1.\n" +
            "DR   SMART; SM00059; FN2; 1.\n" +
            "DR   SMART; SM00130; KR; 1.\n" +
            "DR   SMART; SM00020; Tryp_SPc; 1.\n" +
            "DR   SUPFAM; SSF57440; Kringle-like; 2.\n" +
            "DR   SUPFAM; SSF50494; Pept_Ser_Cys; 1.\n" +
            "DR   PROSITE; PS00022; EGF_1; 2.\n" +
            "DR   PROSITE; PS01186; EGF_2; 1.\n" +
            "DR   PROSITE; PS50026; EGF_3; 2.\n" +
            "DR   PROSITE; PS01253; FN1_1; 1.\n" +
            "DR   PROSITE; PS51091; FN1_2; 1.\n" +
            "DR   PROSITE; PS00023; FN2_1; 1.\n" +
            "DR   PROSITE; PS51092; FN2_2; 1.\n" +
            "DR   PROSITE; PS00021; KRINGLE_1; 1.\n" +
            "DR   PROSITE; PS50070; KRINGLE_2; 1.\n" +
            "DR   PROSITE; PS50240; TRYPSIN_DOM; 1.\n" +
            "DR   PROSITE; PS00134; TRYPSIN_HIS; 1.\n" +
            "DR   PROSITE; PS00135; TRYPSIN_SER; 1.\n" +
            "PE   1: Evidence at protein level;\n" +
            "KW   3D-structure; Complete proteome; Direct protein sequencing;\n" +
            "KW   Disulfide bond; EGF-like domain; Glycoprotein; Hydrolase; Kringle;\n" +
            "KW   Polymorphism; Protease; Repeat; Secreted; Serine protease; Signal;\n" +
            "KW   Zymogen.\n" +
            "FT   SIGNAL        1     35\n" +
            "FT   PROPEP       36    372       Removed in mature form.\n" +
            "FT                                /FTId=PRO_0000027911.\n" +
            "FT   CHAIN       373    407       Hepatocyte growth factor activator short\n" +
            "FT                                chain.\n" +
            "FT                                /FTId=PRO_0000027912.\n" +
            "FT   CHAIN       408    655       Hepatocyte growth factor activator long\n" +
            "FT                                chain.\n" +
            "FT                                /FTId=PRO_0000027913.\n" +
            "FT   DOMAIN      103    150       Fibronectin type-II.\n" +
            "FT   DOMAIN      160    198       EGF-like 1.\n" +
            "FT   DOMAIN      200    240       Fibronectin type-I.\n" +
            "FT   DOMAIN      241    279       EGF-like 2.\n" +
            "FT   DOMAIN      286    367       Kringle.\n" +
            "FT   DOMAIN      408    646       Peptidase S1.\n" +
            "FT   ACT_SITE    447    447       Charge relay system.\n" +
            "FT   ACT_SITE    497    497       Charge relay system.\n" +
            "FT   ACT_SITE    598    598       Charge relay system.\n" +
            "FT   CARBOHYD     48     48       N-linked (GlcNAc...) (Potential).\n" +
            "FT   CARBOHYD    290    290       N-linked (GlcNAc...) (Potential).\n" +
            "FT   CARBOHYD    468    468       N-linked (GlcNAc...).\n" +
            "FT   CARBOHYD    492    492       N-linked (GlcNAc...) (Potential).\n" +
            "FT   CARBOHYD    546    546       N-linked (GlcNAc...) (Potential).\n" +
            "FT   DISULFID    108    133       By similarity.\n" +
            "FT   DISULFID    122    148       By similarity.\n" +
            "FT   DISULFID    164    175       By similarity.\n" +
            "FT   DISULFID    169    186       By similarity.\n" +
            "FT   DISULFID    188    197       By similarity.\n" +
            "FT   DISULFID    202    230       By similarity.\n" +
            "FT   DISULFID    228    237       By similarity.\n" +
            "FT   DISULFID    245    256       By similarity.\n" +
            "FT   DISULFID    250    267       By similarity.\n" +
            "FT   DISULFID    269    278       By similarity.\n" +
            "FT   DISULFID    286    367       By similarity.\n" +
            "FT   DISULFID    307    349       By similarity.\n" +
            "FT   DISULFID    338    362       By similarity.\n" +
            "FT   DISULFID    394    521       Interchain (between short and long\n" +
            "FT                                chains).\n" +
            "FT   DISULFID    432    448\n" +
            "FT   DISULFID    440    510\n" +
            "FT   DISULFID    535    604\n" +
            "FT   DISULFID    567    583\n" +
            "FT   DISULFID    594    622\n" +
            "FT   VARIANT     218    218       A -> S (in dbSNP:rs3748034).\n" +
            "FT                                /FTId=VAR_051851.\n" +
            "FT   VARIANT     225    225       V -> M (in dbSNP:rs16844370).\n" +
            "FT                                /FTId=VAR_033651.\n" +
            "FT   VARIANT     231    231       F -> L (in dbSNP:rs1987546).\n" +
            "FT                                /FTId=VAR_033652.\n" +
            "FT   VARIANT     509    509       R -> H (in dbSNP:rs16844401).\n" +
            "FT                                /FTId=VAR_024294.\n" +
            "FT   VARIANT     644    644       R -> Q (in dbSNP:rs2498323).\n" +
            "FT                                /FTId=VAR_024295.\n" +
            "FT   STRAND      422    427\n" +
            "FT   STRAND      430    438\n" +
            "FT   STRAND      441    444\n" +
            "FT   HELIX       446    449\n" +
            "FT   HELIX       455    457\n" +
            "FT   STRAND      458    463\n" +
            "FT   STRAND      475    477\n" +
            "FT   STRAND      479    484\n" +
            "FT   TURN        493    496\n" +
            "FT   STRAND      499    503\n" +
            "FT   STRAND      506    508\n" +
            "FT   STRAND      534    540\n" +
            "FT   STRAND      543    546\n" +
            "FT   STRAND      555    561\n" +
            "FT   HELIX       564    567\n" +
            "FT   TURN        570    573\n" +
            "FT   HELIX       574    576\n" +
            "FT   STRAND      581    585\n" +
            "FT   STRAND      587    589\n" +
            "FT   STRAND      601    606\n" +
            "FT   STRAND      609    618\n" +
            "FT   STRAND      629    633\n" +
            "FT   HELIX       634    637\n" +
            "FT   HELIX       638    645\n" +
            "SQ   SEQUENCE   655 AA;  70682 MW;  2CF72F1E1B862ED7 CRC64;\n" +
            "     MGRWAWVPSP WPPPGLGPFL LLLLLLLLLP RGFQPQPGGN RTESPEPNAT ATPAIPTILV\n" +
            "     TSVTSETPAT SAPEAEGPQS GGLPPPPRAV PSSSSPQAQA LTEDGRPCRF PFRYGGRMLH\n" +
            "     ACTSEGSAHR KWCATTHNYD RDRAWGYCVE ATPPPGGPAA LDPCASGPCL NGGSCSNTQD\n" +
            "     PQSYHCSCPR AFTGKDCGTE KCFDETRYEY LEGGDRWARV RQGHVEQCEC FGGRTWCEGT\n" +
            "     RHTACLSSPC LNGGTCHLIV ATGTTVCACP PGFAGRLCNI EPDERCFLGN GTGYRGVAST\n" +
            "     SASGLSCLAW NSDLLYQELH VDSVGAAALL GLGPHAYCRN PDNDERPWCY VVKDSALSWE\n" +
            "     YCRLEACESL TRVQLSPDLL ATLPEPASPG RQACGRRHKK RTFLRPRIIG GSSSLPGSHP\n" +
            "     WLAAIYIGDS FCAGSLVHTC WVVSAAHCFS HSPPRDSVSV VLGQHFFNRT TDVTQTFGIE\n" +
            "     KYIPYTLYSV FNPSDHDLVL IRLKKKGDRC ATRSQFVQPI CLPEPGSTFP AGHKCQIAGW\n" +
            "     GHLDENVSGY SSSLREALVP LVADHKCSSP EVYGADISPN MLCAGYFDCK SDACQGDSGG\n" +
            "     PLACEKNGVA YLYGIISWGD GCGRLHKPGV YTRVANYVDW INDRIRPPRR LVAPS\n" +
            "//\n";

    @Test
    public void test() {
        UniProtEntry entry = new DefaultUniProtParser("", "", "", true)
                .parse(this.entry);
        UniProtEntryScored scored = new UniProtEntryScored(entry);
        System.out.println(scored.score());
        assertTrue(scored.score() > 0);
    }
}
