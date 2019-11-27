package org.uniprot.core.scorer.uniprotkb;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.DefaultUniProtParser;
import org.uniprot.core.flatfile.parser.impl.SupportingDataMapImpl;
import org.uniprot.core.uniprot.UniProtEntry;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 08-Mar-2010 Time: 13:47:47 To change this template
 * use File | Settings | File Templates.
 */
class Q04756ScoredTest {

    private String entry =
            "ID   HGFA_HUMAN              Reviewed;         655 AA.\n"
                    + "AC   Q04756; Q14726; Q2M1W7; Q53X47;\n"
                    + "DT   01-JUN-1994, integrated into UniProtKB/Swiss-Prot.\n"
                    + "DT   01-JUN-1994, sequence version 1.\n"
                    + "DT   11-DEC-2019, entry version 186.\n"
                    + "DE   RecName: Full=Hepatocyte growth factor activator;\n"
                    + "DE            Short=HGF activator;\n"
                    + "DE            Short=HGFA;\n"
                    + "DE            EC=3.4.21.-;\n"
                    + "DE   Contains:\n"
                    + "DE     RecName: Full=Hepatocyte growth factor activator short chain;\n"
                    + "DE   Contains:\n"
                    + "DE     RecName: Full=Hepatocyte growth factor activator long chain;\n"
                    + "DE   Flags: Precursor;\n"
                    + "GN   Name=HGFAC;\n"
                    + "OS   Homo sapiens (Human).\n"
                    + "OC   Eukaryota; Metazoa; Chordata; Craniata; Vertebrata; Euteleostomi; Mammalia;\n"
                    + "OC   Eutheria; Euarchontoglires; Primates; Haplorrhini; Catarrhini; Hominidae;\n"
                    + "OC   Homo.\n"
                    + "OX   NCBI_TaxID=9606;\n"
                    + "RN   [1]\n"
                    + "RP   NUCLEOTIDE SEQUENCE [MRNA], AND PARTIAL PROTEIN SEQUENCE.\n"
                    + "RC   TISSUE=Liver, and Serum;\n"
                    + "RX   PubMed=7683665;\n"
                    + "RA   Miyazawa K., Shimomura T., Kitamura A., Kondo J., Morimoto Y., Kitamura N.;\n"
                    + "RT   \"Molecular cloning and sequence analysis of the cDNA for a human serine\n"
                    + "RT   protease responsible for activation of hepatocyte growth factor. Structural\n"
                    + "RT   similarity of the protease precursor to blood coagulation factor XII.\";\n"
                    + "RL   J. Biol. Chem. 268:10024-10028(1993).\n"
                    + "RN   [2]\n"
                    + "RP   NUCLEOTIDE SEQUENCE [GENOMIC DNA].\n"
                    + "RX   PubMed=9874200; DOI=10.1046/j.1432-1327.1998.2580355.x;\n"
                    + "RA   Miyazawa K., Wang Y., Minoshima S., Shimizu N., Kitamura N.;\n"
                    + "RT   \"Structural organization and chromosomal localization of the human\n"
                    + "RT   hepatocyte growth factor activator gene -- phylogenetic and functional\n"
                    + "RT   relationship with blood coagulation factor XII, urokinase, and tissue-type\n"
                    + "RT   plasminogen activator.\";\n"
                    + "RL   Eur. J. Biochem. 258:355-361(1998).\n"
                    + "RN   [3]\n"
                    + "RP   NUCLEOTIDE SEQUENCE [LARGE SCALE GENOMIC DNA].\n"
                    + "RX   PubMed=15815621; DOI=10.1038/nature03466;\n"
                    + "RA   Hillier L.W., Graves T.A., Fulton R.S., Fulton L.A., Pepin K.H., Minx P.,\n"
                    + "RA   Wagner-McPherson C., Layman D., Wylie K., Sekhon M., Becker M.C.,\n"
                    + "RA   Fewell G.A., Delehaunty K.D., Miner T.L., Nash W.E., Kremitzki C., Oddy L.,\n"
                    + "RA   Du H., Sun H., Bradshaw-Cordum H., Ali J., Carter J., Cordes M., Harris A.,\n"
                    + "RA   Isak A., van Brunt A., Nguyen C., Du F., Courtney L., Kalicki J.,\n"
                    + "RA   Ozersky P., Abbott S., Armstrong J., Belter E.A., Caruso L., Cedroni M.,\n"
                    + "RA   Cotton M., Davidson T., Desai A., Elliott G., Erb T., Fronick C., Gaige T.,\n"
                    + "RA   Haakenson W., Haglund K., Holmes A., Harkins R., Kim K., Kruchowski S.S.,\n"
                    + "RA   Strong C.M., Grewal N., Goyea E., Hou S., Levy A., Martinka S., Mead K.,\n"
                    + "RA   McLellan M.D., Meyer R., Randall-Maher J., Tomlinson C.,\n"
                    + "RA   Dauphin-Kohlberg S., Kozlowicz-Reilly A., Shah N., Swearengen-Shahid S.,\n"
                    + "RA   Snider J., Strong J.T., Thompson J., Yoakum M., Leonard S., Pearman C.,\n"
                    + "RA   Trani L., Radionenko M., Waligorski J.E., Wang C., Rock S.M.,\n"
                    + "RA   Tin-Wollam A.-M., Maupin R., Latreille P., Wendl M.C., Yang S.-P., Pohl C.,\n"
                    + "RA   Wallis J.W., Spieth J., Bieri T.A., Berkowicz N., Nelson J.O., Osborne J.,\n"
                    + "RA   Ding L., Meyer R., Sabo A., Shotland Y., Sinha P., Wohldmann P.E.,\n"
                    + "RA   Cook L.L., Hickenbotham M.T., Eldred J., Williams D., Jones T.A., She X.,\n"
                    + "RA   Ciccarelli F.D., Izaurralde E., Taylor J., Schmutz J., Myers R.M.,\n"
                    + "RA   Cox D.R., Huang X., McPherson J.D., Mardis E.R., Clifton S.W., Warren W.C.,\n"
                    + "RA   Chinwalla A.T., Eddy S.R., Marra M.A., Ovcharenko I., Furey T.S.,\n"
                    + "RA   Miller W., Eichler E.E., Bork P., Suyama M., Torrents D., Waterston R.H.,\n"
                    + "RA   Wilson R.K.;\n"
                    + "RT   \"Generation and annotation of the DNA sequences of human chromosomes 2 and\n"
                    + "RT   4.\";\n"
                    + "RL   Nature 434:724-731(2005).\n"
                    + "RN   [4]\n"
                    + "RP   NUCLEOTIDE SEQUENCE [LARGE SCALE GENOMIC DNA].\n"
                    + "RA   Mural R.J., Istrail S., Sutton G.G., Florea L., Halpern A.L., Mobarry C.M.,\n"
                    + "RA   Lippert R., Walenz B., Shatkay H., Dew I., Miller J.R., Flanigan M.J.,\n"
                    + "RA   Edwards N.J., Bolanos R., Fasulo D., Halldorsson B.V., Hannenhalli S.,\n"
                    + "RA   Turner R., Yooseph S., Lu F., Nusskern D.R., Shue B.C., Zheng X.H.,\n"
                    + "RA   Zhong F., Delcher A.L., Huson D.H., Kravitz S.A., Mouchard L., Reinert K.,\n"
                    + "RA   Remington K.A., Clark A.G., Waterman M.S., Eichler E.E., Adams M.D.,\n"
                    + "RA   Hunkapiller M.W., Myers E.W., Venter J.C.;\n"
                    + "RL   Submitted (SEP-2005) to the EMBL/GenBank/DDBJ databases.\n"
                    + "RN   [5]\n"
                    + "RP   NUCLEOTIDE SEQUENCE [LARGE SCALE MRNA], AND VARIANT LEU-231.\n"
                    + "RC   TISSUE=Liver;\n"
                    + "RX   PubMed=15489334; DOI=10.1101/gr.2596504;\n"
                    + "RG   The MGC Project Team;\n"
                    + "RA   Gerhard D.S., Wagner L., Feingold E.A., Shenmen C.M., Grouse L.H.,\n"
                    + "RA   Schuler G., Klein S.L., Old S., Rasooly R., Good P., Guyer M., Peck A.M.,\n"
                    + "RA   Derge J.G., Lipman D., Collins F.S., Jang W., Sherry S., Feolo M.,\n"
                    + "RA   Misquitta L., Lee E., Rotmistrovsky K., Greenhut S.F., Schaefer C.F.,\n"
                    + "RA   Buetow K., Bonner T.I., Haussler D., Kent J., Kiekhaus M., Furey T.,\n"
                    + "RA   Brent M., Prange C., Schreiber K., Shapiro N., Bhat N.K., Hopkins R.F.,\n"
                    + "RA   Hsie F., Driscoll T., Soares M.B., Casavant T.L., Scheetz T.E.,\n"
                    + "RA   Brown-stein M.J., Usdin T.B., Toshiyuki S., Carninci P., Piao Y.,\n"
                    + "RA   Dudekula D.B., Ko M.S., Kawakami K., Suzuki Y., Sugano S., Gruber C.E.,\n"
                    + "RA   Smith M.R., Simmons B., Moore T., Waterman R., Johnson S.L., Ruan Y.,\n"
                    + "RA   Wei C.L., Mathavan S., Gunaratne P.H., Wu J., Garcia A.M., Hulyk S.W.,\n"
                    + "RA   Fuh E., Yuan Y., Sneed A., Kowis C., Hodgson A., Muzny D.M., McPherson J.,\n"
                    + "RA   Gibbs R.A., Fahey J., Helton E., Ketteman M., Madan A., Rodrigues S.,\n"
                    + "RA   Sanchez A., Whiting M., Madari A., Young A.C., Wetherby K.D., Granite S.J.,\n"
                    + "RA   Kwong P.N., Brinkley C.P., Pearson R.L., Bouffard G.G., Blakesly R.W.,\n"
                    + "RA   Green E.D., Dickson M.C., Rodriguez A.C., Grimwood J., Schmutz J.,\n"
                    + "RA   Myers R.M., Butterfield Y.S., Griffith M., Griffith O.L., Krzywinski M.I.,\n"
                    + "RA   Liao N., Morin R., Morrin R., Palmquist D., Petrescu A.S., Skalska U.,\n"
                    + "RA   Smailus D.E., Stott J.M., Schnerch A., Schein J.E., Jones S.J., Holt R.A.,\n"
                    + "RA   Baross A., Marra M.A., Clifton S., Makowski K.A., Bosak S., Malek J.;\n"
                    + "RT   \"The status, quality, and expansion of the NIH full-length cDNA project:\n"
                    + "RT   the Mammalian Gene Collection (MGC).\";\n"
                    + "RL   Genome Res. 14:2121-2127(2004).\n"
                    + "RN   [6]\n"
                    + "RP   PROTEIN SEQUENCE OF 36-50.\n"
                    + "RX   PubMed=15340161; DOI=10.1110/ps.04682504;\n"
                    + "RA   Zhang Z., Henzel W.J.;\n"
                    + "RT   \"Signal peptide prediction based on analysis of experimentally verified\n"
                    + "RT   cleavage sites.\";\n"
                    + "RL   Protein Sci. 13:2819-2824(2004).\n"
                    + "RN   [7]\n"
                    + "RP   GLYCOSYLATION [LARGE SCALE ANALYSIS] AT ASN-468.\n"
                    + "RC   TISSUE=Plasma;\n"
                    + "RX   PubMed=16335952; DOI=10.1021/pr0502065;\n"
                    + "RA   Liu T., Qian W.-J., Gritsenko M.A., Camp D.G. II, Monroe M.E., Moore R.J.,\n"
                    + "RA   Smith R.D.;\n"
                    + "RT   \"Human plasma N-glycoproteome analysis by immunoaffinity subtraction,\n"
                    + "RT   hydrazide chemistry, and mass spectrometry.\";\n"
                    + "RL   J. Proteome Res. 4:2070-2080(2005).\n"
                    + "RN   [8]\n"
                    + "RP   GLYCOSYLATION [LARGE SCALE ANALYSIS] AT ASN-468.\n"
                    + "RC   TISSUE=Liver;\n"
                    + "RX   PubMed=19159218; DOI=10.1021/pr8008012;\n"
                    + "RA   Chen R., Jiang X., Sun D., Han G., Wang F., Ye M., Wang L., Zou H.;\n"
                    + "RT   \"Glycoproteomics analysis of human liver tissue by combination of multiple\n"
                    + "RT   enzyme digestion and hydrazide chemistry.\";\n"
                    + "RL   J. Proteome Res. 8:651-661(2009).\n"
                    + "RN   [9]\n"
                    + "RP   X-RAY CRYSTALLOGRAPHY (2.6 ANGSTROMS) OF 373-655 ALONE AND IN COMPLEX WITH\n"
                    + "RP   INHIBITOR HAI1B, ACTIVE SITE, AND DISULFIDE BONDS.\n"
                    + "RX   PubMed=15713485; DOI=10.1016/j.jmb.2004.12.048;\n"
                    + "RA   Shia S., Stamos J., Kirchhofer D., Fan B., Wu J., Corpuz R.T., Santell L.,\n"
                    + "RA   Lazarus R.A., Eigenbrot C.;\n"
                    + "RT   \"Conformational lability in serine protease active sites: structures of\n"
                    + "RT   hepatocyte growth factor activator (HGFA) alone and with the inhibitory\n"
                    + "RT   domain from HGFA inhibitor-1B.\";\n"
                    + "RL   J. Mol. Biol. 346:1335-1349(2005).\n"
                    + "RN   [10]\n"
                    + "RP   X-RAY CRYSTALLOGRAPHY (2.2 ANGSTROMS) OF 373-655 IN COMPLEX WITH ANTIBODY,\n"
                    + "RP   GLYCOSYLATION AT ASN-468, AND DISULFIDE BONDS.\n"
                    + "RX   PubMed=18077410; DOI=10.1073/pnas.0708251104;\n"
                    + "RA   Wu Y., Eigenbrot C., Liang W.C., Stawicki S., Shia S., Fan B., Ganesan R.,\n"
                    + "RA   Lipari M.T., Kirchhofer D.;\n"
                    + "RT   \"Structural insight into distinct mechanisms of protease inhibition by\n"
                    + "RT   antibodies.\";\n"
                    + "RL   Proc. Natl. Acad. Sci. U.S.A. 104:19784-19789(2007).\n"
                    + "CC   -!- FUNCTION: Activates hepatocyte growth factor (HGF) by converting it\n"
                    + "CC       from a single chain to a heterodimeric form.\n"
                    + "CC   -!- SUBUNIT: Heterodimer of a short chain and a long chain linked by a\n"
                    + "CC       disulfide bond. {ECO:0000269|PubMed:15713485,\n"
                    + "CC       ECO:0000269|PubMed:18077410}.\n"
                    + "CC   -!- SUBCELLULAR LOCATION: Secreted. Note=Secreted as an inactive single-\n"
                    + "CC       chain precursor and is then activated to a heterodimeric form.\n"
                    + "CC   -!- TISSUE SPECIFICITY: Liver.\n"
                    + "CC   -!- SIMILARITY: Belongs to the peptidase S1 family. {ECO:0000255|PROSITE-\n"
                    + "CC       ProRule:PRU00274}.\n"
                    + "CC   -!- CAUTION: It is uncertain whether Met-1 is the initiator. {ECO:0000305}.\n"
                    + "CC   ---------------------------------------------------------------------------\n"
                    + "CC   Copyrighted by the UniProt Consortium, see https://www.uniprot.org/terms\n"
                    + "CC   Distributed under the Creative Commons Attribution (CC BY 4.0) License\n"
                    + "CC   ---------------------------------------------------------------------------\n"
                    + "DR   EMBL; D14012; BAA03113.1; -; mRNA.\n"
                    + "DR   EMBL; BC112190; AAI12191.1; -; mRNA.\n"
                    + "DR   EMBL; D50030; BAA74450.1; -; Genomic_DNA.\n"
                    + "DR   EMBL; AL590235; -; NOT_ANNOTATED_CDS; Genomic_DNA.\n"
                    + "DR   EMBL; CH471131; EAW82464.1; -; Genomic_DNA.\n"
                    + "DR   EMBL; BC112192; AAI12193.1; -; mRNA.\n"
                    + "DR   CCDS; CCDS3369.1; -.\n"
                    + "DR   PIR; A46688; A46688.\n"
                    + "DR   RefSeq; NP_001284368.1; NM_001297439.1.\n"
                    + "DR   RefSeq; NP_001519.1; NM_001528.3.\n"
                    + "DR   PDB; 1YBW; X-ray; 2.70 A; A/B=373-655.\n"
                    + "DR   PDB; 1YC0; X-ray; 2.60 A; A=373-655.\n"
                    + "DR   PDB; 2R0K; X-ray; 3.51 A; A=373-655.\n"
                    + "DR   PDB; 2R0L; X-ray; 2.20 A; A=408-655, B=373-407.\n"
                    + "DR   PDB; 2WUB; X-ray; 2.90 A; A/C=408-655, B/D=373-407.\n"
                    + "DR   PDB; 2WUC; X-ray; 2.70 A; A=408-654, B=373-407.\n"
                    + "DR   PDB; 3K2U; X-ray; 2.35 A; A=408-655, B=373-407.\n"
                    + "DR   PDBsum; 1YBW; -.\n"
                    + "DR   PDBsum; 1YC0; -.\n"
                    + "DR   PDBsum; 2R0K; -.\n"
                    + "DR   PDBsum; 2R0L; -.\n"
                    + "DR   PDBsum; 2WUB; -.\n"
                    + "DR   PDBsum; 2WUC; -.\n"
                    + "DR   PDBsum; 3K2U; -.\n"
                    + "DR   SMR; Q04756; -.\n"
                    + "DR   BioGrid; 109331; 12.\n"
                    + "DR   DIP; DIP-6022N; -.\n"
                    + "DR   IntAct; Q04756; 13.\n"
                    + "DR   STRING; 9606.ENSP00000421801; -.\n"
                    + "DR   BindingDB; Q04756; -.\n"
                    + "DR   ChEMBL; CHEMBL3351190; -.\n"
                    + "DR   DrugCentral; Q04756; -.\n"
                    + "DR   MEROPS; S01.228; -.\n"
                    + "DR   GlyConnect; 1309; -.\n"
                    + "DR   iPTMnet; Q04756; -.\n"
                    + "DR   PhosphoSitePlus; Q04756; -.\n"
                    + "DR   BioMuta; HGFAC; -.\n"
                    + "DR   DMDM; 547643; -.\n"
                    + "DR   jPOST; Q04756; -.\n"
                    + "DR   MassIVE; Q04756; -.\n"
                    + "DR   MaxQB; Q04756; -.\n"
                    + "DR   PaxDb; Q04756; -.\n"
                    + "DR   PeptideAtlas; Q04756; -.\n"
                    + "DR   PRIDE; Q04756; -.\n"
                    + "DR   ProteomicsDB; 58278; -.\n"
                    + "DR   ABCD; Q04756; -.\n"
                    + "DR   Ensembl; ENST00000382774; ENSP00000372224; ENSG00000109758.\n"
                    + "DR   GeneID; 3083; -.\n"
                    + "DR   KEGG; hsa:3083; -.\n"
                    + "DR   UCSC; uc003ghc.4; human.\n"
                    + "DR   CTD; 3083; -.\n"
                    + "DR   DisGeNET; 3083; -.\n"
                    + "DR   EuPathDB; HostDB:ENSG00000109758.8; -.\n"
                    + "DR   GeneCards; HGFAC; -.\n"
                    + "DR   HGNC; HGNC:4894; HGFAC.\n"
                    + "DR   HPA; HPA058279; -.\n"
                    + "DR   HPA; HPA059076; -.\n"
                    + "DR   MIM; 604552; gene.\n"
                    + "DR   neXtProt; NX_Q04756; -.\n"
                    + "DR   OpenTargets; ENSG00000109758; -.\n"
                    + "DR   PharmGKB; PA29270; -.\n"
                    + "DR   eggNOG; KOG1217; Eukaryota.\n"
                    + "DR   eggNOG; KOG3627; Eukaryota.\n"
                    + "DR   eggNOG; COG5640; LUCA.\n"
                    + "DR   GeneTree; ENSGT00940000159778; -.\n"
                    + "DR   HOGENOM; HOG000237314; -.\n"
                    + "DR   InParanoid; Q04756; -.\n"
                    + "DR   KO; K09631; -.\n"
                    + "DR   OrthoDB; 1314811at2759; -.\n"
                    + "DR   PhylomeDB; Q04756; -.\n"
                    + "DR   TreeFam; TF329901; -.\n"
                    + "DR   Reactome; R-HSA-6806942; MET Receptor Activation.\n"
                    + "DR   ChiTaRS; HGFAC; human.\n"
                    + "DR   EvolutionaryTrace; Q04756; -.\n"
                    + "DR   GeneWiki; HGFAC; -.\n"
                    + "DR   GenomeRNAi; 3083; -.\n"
                    + "DR   Pharos; Q04756; Tchem.\n"
                    + "DR   PRO; PR:Q04756; -.\n"
                    + "DR   Proteomes; UP000005640; Chromosome 4.\n"
                    + "DR   RNAct; Q04756; protein.\n"
                    + "DR   Bgee; ENSG00000109758; Expressed in 75 organ(s), highest expression level in right lobe of liver.\n"
                    + "DR   ExpressionAtlas; Q04756; baseline and differential.\n"
                    + "DR   Genevisible; Q04756; HS.\n"
                    + "DR   GO; GO:0005829; C:cytosol; TAS:Reactome.\n"
                    + "DR   GO; GO:0005576; C:extracellular region; TAS:ProtInc.\n"
                    + "DR   GO; GO:0005615; C:extracellular space; IBA:GO_Central.\n"
                    + "DR   GO; GO:0005791; C:rough endoplasmic reticulum; IBA:GO_Central.\n"
                    + "DR   GO; GO:0004252; F:serine-type endopeptidase activity; IBA:GO_Central.\n"
                    + "DR   GO; GO:0008236; F:serine-type peptidase activity; TAS:ProtInc.\n"
                    + "DR   GO; GO:0006508; P:proteolysis; IBA:GO_Central.\n"
                    + "DR   CDD; cd00061; FN1; 1.\n"
                    + "DR   CDD; cd00062; FN2; 1.\n"
                    + "DR   CDD; cd00108; KR; 1.\n"
                    + "DR   CDD; cd00190; Tryp_SPc; 1.\n"
                    + "DR   Gene3D; 2.10.10.10; -; 1.\n"
                    + "DR   Gene3D; 2.40.20.10; -; 1.\n"
                    + "DR   InterPro; IPR014394; Coagulation_fac_XII/HGFA.\n"
                    + "DR   InterPro; IPR013032; EGF-like_CS.\n"
                    + "DR   InterPro; IPR000742; EGF-like_dom.\n"
                    + "DR   InterPro; IPR000083; Fibronectin_type1.\n"
                    + "DR   InterPro; IPR000562; FN_type2_dom.\n"
                    + "DR   InterPro; IPR036943; FN_type2_sf.\n"
                    + "DR   InterPro; IPR000001; Kringle.\n"
                    + "DR   InterPro; IPR013806; Kringle-like.\n"
                    + "DR   InterPro; IPR018056; Kringle_CS.\n"
                    + "DR   InterPro; IPR038178; Kringle_sf.\n"
                    + "DR   InterPro; IPR009003; Peptidase_S1_PA.\n"
                    + "DR   InterPro; IPR001314; Peptidase_S1A.\n"
                    + "DR   InterPro; IPR001254; Trypsin_dom.\n"
                    + "DR   InterPro; IPR018114; TRYPSIN_HIS.\n"
                    + "DR   InterPro; IPR033116; TRYPSIN_SER.\n"
                    + "DR   Pfam; PF00008; EGF; 1.\n"
                    + "DR   Pfam; PF00039; fn1; 1.\n"
                    + "DR   Pfam; PF00040; fn2; 1.\n"
                    + "DR   Pfam; PF00051; Kringle; 1.\n"
                    + "DR   Pfam; PF00089; Trypsin; 1.\n"
                    + "DR   PIRSF; PIRSF001146; Factor_XII_HGFA; 1.\n"
                    + "DR   PRINTS; PR00722; CHYMOTRYPSIN.\n"
                    + "DR   SMART; SM00181; EGF; 2.\n"
                    + "DR   SMART; SM00058; FN1; 1.\n"
                    + "DR   SMART; SM00059; FN2; 1.\n"
                    + "DR   SMART; SM00130; KR; 1.\n"
                    + "DR   SMART; SM00020; Tryp_SPc; 1.\n"
                    + "DR   SUPFAM; SSF50494; SSF50494; 1.\n"
                    + "DR   SUPFAM; SSF57440; SSF57440; 2.\n"
                    + "DR   PROSITE; PS00022; EGF_1; 2.\n"
                    + "DR   PROSITE; PS01186; EGF_2; 1.\n"
                    + "DR   PROSITE; PS50026; EGF_3; 2.\n"
                    + "DR   PROSITE; PS01253; FN1_1; 1.\n"
                    + "DR   PROSITE; PS51091; FN1_2; 1.\n"
                    + "DR   PROSITE; PS00023; FN2_1; 1.\n"
                    + "DR   PROSITE; PS51092; FN2_2; 1.\n"
                    + "DR   PROSITE; PS00021; KRINGLE_1; 1.\n"
                    + "DR   PROSITE; PS50070; KRINGLE_2; 1.\n"
                    + "DR   PROSITE; PS50240; TRYPSIN_DOM; 1.\n"
                    + "DR   PROSITE; PS00134; TRYPSIN_HIS; 1.\n"
                    + "DR   PROSITE; PS00135; TRYPSIN_SER; 1.\n"
                    + "PE   1: Evidence at protein level;\n"
                    + "KW   3D-structure; Direct protein sequencing; Disulfide bond; EGF-like domain;\n"
                    + "KW   Glycoprotein; Hydrolase; Kringle; Polymorphism; Protease;\n"
                    + "KW   Reference proteome; Repeat; Secreted; Serine protease; Signal; Zymogen.\n"
                    + "FT   SIGNAL          1..35\n"
                    + "FT                   /evidence=\"ECO:0000269|PubMed:15340161\"\n"
                    + "FT   PROPEP          36..372\n"
                    + "FT                   /note=\"Removed in mature form\"\n"
                    + "FT                   /id=\"PRO_0000027911\"\n"
                    + "FT   CHAIN           373..407\n"
                    + "FT                   /note=\"Hepatocyte growth factor activator short chain\"\n"
                    + "FT                   /id=\"PRO_0000027912\"\n"
                    + "FT   CHAIN           408..655\n"
                    + "FT                   /note=\"Hepatocyte growth factor activator long chain\"\n"
                    + "FT                   /id=\"PRO_0000027913\"\n"
                    + "FT   DOMAIN          103..150\n"
                    + "FT                   /note=\"Fibronectin type-II\"\n"
                    + "FT                   /evidence=\"ECO:0000255|PROSITE-ProRule:PRU00478,\n"
                    + "FT                   ECO:0000255|PROSITE-ProRule:PRU00479\"\n"
                    + "FT   DOMAIN          160..198\n"
                    + "FT                   /note=\"EGF-like 1\"\n"
                    + "FT                   /evidence=\"ECO:0000255|PROSITE-ProRule:PRU00076\"\n"
                    + "FT   DOMAIN          200..240\n"
                    + "FT                   /note=\"Fibronectin type-I\"\n"
                    + "FT                   /evidence=\"ECO:0000255|PROSITE-ProRule:PRU00478\"\n"
                    + "FT   DOMAIN          241..279\n"
                    + "FT                   /note=\"EGF-like 2\"\n"
                    + "FT                   /evidence=\"ECO:0000255|PROSITE-ProRule:PRU00076\"\n"
                    + "FT   DOMAIN          286..367\n"
                    + "FT                   /note=\"Kringle\"\n"
                    + "FT                   /evidence=\"ECO:0000255|PROSITE-ProRule:PRU00121\"\n"
                    + "FT   DOMAIN          408..646\n"
                    + "FT                   /note=\"Peptidase S1\"\n"
                    + "FT                   /evidence=\"ECO:0000255|PROSITE-ProRule:PRU00274\"\n"
                    + "FT   ACT_SITE        447\n"
                    + "FT                   /note=\"Charge relay system\"\n"
                    + "FT                   /evidence=\"ECO:0000269|PubMed:15713485\"\n"
                    + "FT   ACT_SITE        497\n"
                    + "FT                   /note=\"Charge relay system\"\n"
                    + "FT                   /evidence=\"ECO:0000269|PubMed:15713485\"\n"
                    + "FT   ACT_SITE        598\n"
                    + "FT                   /note=\"Charge relay system\"\n"
                    + "FT                   /evidence=\"ECO:0000269|PubMed:15713485\"\n"
                    + "FT   CARBOHYD        48\n"
                    + "FT                   /note=\"N-linked (GlcNAc...) asparagine\"\n"
                    + "FT                   /evidence=\"ECO:0000255\"\n"
                    + "FT   CARBOHYD        290\n"
                    + "FT                   /note=\"N-linked (GlcNAc...) asparagine\"\n"
                    + "FT                   /evidence=\"ECO:0000255\"\n"
                    + "FT   CARBOHYD        468\n"
                    + "FT                   /note=\"N-linked (GlcNAc...) asparagine\"\n"
                    + "FT                   /evidence=\"ECO:0000269|PubMed:16335952,\n"
                    + "FT                   ECO:0000269|PubMed:18077410, ECO:0000269|PubMed:19159218\"\n"
                    + "FT   CARBOHYD        546\n"
                    + "FT                   /note=\"N-linked (GlcNAc...) asparagine\"\n"
                    + "FT                   /evidence=\"ECO:0000255\"\n"
                    + "FT   DISULFID        108..133\n"
                    + "FT                   /evidence=\"ECO:0000250\"\n"
                    + "FT   DISULFID        122..148\n"
                    + "FT                   /evidence=\"ECO:0000250\"\n"
                    + "FT   DISULFID        164..175\n"
                    + "FT                   /evidence=\"ECO:0000250\"\n"
                    + "FT   DISULFID        169..186\n"
                    + "FT                   /evidence=\"ECO:0000250\"\n"
                    + "FT   DISULFID        188..197\n"
                    + "FT                   /evidence=\"ECO:0000250\"\n"
                    + "FT   DISULFID        202..230\n"
                    + "FT                   /evidence=\"ECO:0000250\"\n"
                    + "FT   DISULFID        228..237\n"
                    + "FT                   /evidence=\"ECO:0000250\"\n"
                    + "FT   DISULFID        245..256\n"
                    + "FT                   /evidence=\"ECO:0000250\"\n"
                    + "FT   DISULFID        250..267\n"
                    + "FT                   /evidence=\"ECO:0000250\"\n"
                    + "FT   DISULFID        269..278\n"
                    + "FT                   /evidence=\"ECO:0000250\"\n"
                    + "FT   DISULFID        286..367\n"
                    + "FT                   /evidence=\"ECO:0000250\"\n"
                    + "FT   DISULFID        307..349\n"
                    + "FT                   /evidence=\"ECO:0000250\"\n"
                    + "FT   DISULFID        338..362\n"
                    + "FT                   /evidence=\"ECO:0000250\"\n"
                    + "FT   DISULFID        394..521\n"
                    + "FT                   /note=\"Interchain (between short and long chains)\"\n"
                    + "FT   DISULFID        432..448\n"
                    + "FT   DISULFID        440..510\n"
                    + "FT   DISULFID        535..604\n"
                    + "FT   DISULFID        567..583\n"
                    + "FT   DISULFID        594..622\n"
                    + "FT   VARIANT         218\n"
                    + "FT                   /note=\"A -> S (in dbSNP:rs3748034)\"\n"
                    + "FT                   /id=\"VAR_051851\"\n"
                    + "FT   VARIANT         225\n"
                    + "FT                   /note=\"V -> M (in dbSNP:rs16844370)\"\n"
                    + "FT                   /id=\"VAR_033651\"\n"
                    + "FT   VARIANT         231\n"
                    + "FT                   /note=\"F -> L (in dbSNP:rs1987546)\"\n"
                    + "FT                   /evidence=\"ECO:0000269|PubMed:15489334\"\n"
                    + "FT                   /id=\"VAR_033652\"\n"
                    + "FT   VARIANT         509\n"
                    + "FT                   /note=\"R -> H (in dbSNP:rs16844401)\"\n"
                    + "FT                   /id=\"VAR_024294\"\n"
                    + "FT   VARIANT         644\n"
                    + "FT                   /note=\"R -> Q (in dbSNP:rs2498323)\"\n"
                    + "FT                   /id=\"VAR_024295\"\n"
                    + "FT   STRAND          422..427\n"
                    + "FT                   /evidence=\"ECO:0000244|PDB:2R0L\"\n"
                    + "FT   STRAND          430..438\n"
                    + "FT                   /evidence=\"ECO:0000244|PDB:2R0L\"\n"
                    + "FT   STRAND          441..444\n"
                    + "FT                   /evidence=\"ECO:0000244|PDB:2R0L\"\n"
                    + "FT   HELIX           446..449\n"
                    + "FT                   /evidence=\"ECO:0000244|PDB:2R0L\"\n"
                    + "FT   HELIX           455..457\n"
                    + "FT                   /evidence=\"ECO:0000244|PDB:2R0L\"\n"
                    + "FT   STRAND          458..463\n"
                    + "FT                   /evidence=\"ECO:0000244|PDB:2R0L\"\n"
                    + "FT   STRAND          475..477\n"
                    + "FT                   /evidence=\"ECO:0000244|PDB:2R0L\"\n"
                    + "FT   STRAND          479..484\n"
                    + "FT                   /evidence=\"ECO:0000244|PDB:2R0L\"\n"
                    + "FT   STRAND          490..492\n"
                    + "FT                   /evidence=\"ECO:0000244|PDB:3K2U\"\n"
                    + "FT   TURN            493..496\n"
                    + "FT                   /evidence=\"ECO:0000244|PDB:2R0L\"\n"
                    + "FT   STRAND          499..503\n"
                    + "FT                   /evidence=\"ECO:0000244|PDB:2R0L\"\n"
                    + "FT   STRAND          506..508\n"
                    + "FT                   /evidence=\"ECO:0000244|PDB:2R0L\"\n"
                    + "FT   STRAND          509..511\n"
                    + "FT                   /evidence=\"ECO:0000244|PDB:1YC0\"\n"
                    + "FT   STRAND          514..516\n"
                    + "FT                   /evidence=\"ECO:0000244|PDB:1YBW\"\n"
                    + "FT   STRAND          534..540\n"
                    + "FT                   /evidence=\"ECO:0000244|PDB:2R0L\"\n"
                    + "FT   STRAND          543..546\n"
                    + "FT                   /evidence=\"ECO:0000244|PDB:2R0L\"\n"
                    + "FT   STRAND          555..561\n"
                    + "FT                   /evidence=\"ECO:0000244|PDB:2R0L\"\n"
                    + "FT   HELIX           564..567\n"
                    + "FT                   /evidence=\"ECO:0000244|PDB:2R0L\"\n"
                    + "FT   TURN            570..573\n"
                    + "FT                   /evidence=\"ECO:0000244|PDB:2R0L\"\n"
                    + "FT   HELIX           574..576\n"
                    + "FT                   /evidence=\"ECO:0000244|PDB:2R0L\"\n"
                    + "FT   STRAND          581..585\n"
                    + "FT                   /evidence=\"ECO:0000244|PDB:2R0L\"\n"
                    + "FT   STRAND          587..589\n"
                    + "FT                   /evidence=\"ECO:0000244|PDB:2R0L\"\n"
                    + "FT   TURN            595..599\n"
                    + "FT                   /evidence=\"ECO:0000244|PDB:1YBW\"\n"
                    + "FT   STRAND          601..606\n"
                    + "FT                   /evidence=\"ECO:0000244|PDB:2R0L\"\n"
                    + "FT   STRAND          609..618\n"
                    + "FT                   /evidence=\"ECO:0000244|PDB:2R0L\"\n"
                    + "FT   TURN            621..623\n"
                    + "FT                   /evidence=\"ECO:0000244|PDB:2R0L\"\n"
                    + "FT   STRAND          625..627\n"
                    + "FT                   /evidence=\"ECO:0000244|PDB:1YBW\"\n"
                    + "FT   STRAND          629..633\n"
                    + "FT                   /evidence=\"ECO:0000244|PDB:2R0L\"\n"
                    + "FT   HELIX           634..637\n"
                    + "FT                   /evidence=\"ECO:0000244|PDB:2R0L\"\n"
                    + "FT   HELIX           638..645\n"
                    + "FT                   /evidence=\"ECO:0000244|PDB:2R0L\"\n"
                    + "SQ   SEQUENCE   655 AA;  70682 MW;  2CF72F1E1B862ED7 CRC64;\n"
                    + "     MGRWAWVPSP WPPPGLGPFL LLLLLLLLLP RGFQPQPGGN RTESPEPNAT ATPAIPTILV\n"
                    + "     TSVTSETPAT SAPEAEGPQS GGLPPPPRAV PSSSSPQAQA LTEDGRPCRF PFRYGGRMLH\n"
                    + "     ACTSEGSAHR KWCATTHNYD RDRAWGYCVE ATPPPGGPAA LDPCASGPCL NGGSCSNTQD\n"
                    + "     PQSYHCSCPR AFTGKDCGTE KCFDETRYEY LEGGDRWARV RQGHVEQCEC FGGRTWCEGT\n"
                    + "     RHTACLSSPC LNGGTCHLIV ATGTTVCACP PGFAGRLCNI EPDERCFLGN GTGYRGVAST\n"
                    + "     SASGLSCLAW NSDLLYQELH VDSVGAAALL GLGPHAYCRN PDNDERPWCY VVKDSALSWE\n"
                    + "     YCRLEACESL TRVQLSPDLL ATLPEPASPG RQACGRRHKK RTFLRPRIIG GSSSLPGSHP\n"
                    + "     WLAAIYIGDS FCAGSLVHTC WVVSAAHCFS HSPPRDSVSV VLGQHFFNRT TDVTQTFGIE\n"
                    + "     KYIPYTLYSV FNPSDHDLVL IRLKKKGDRC ATRSQFVQPI CLPEPGSTFP AGHKCQIAGW\n"
                    + "     GHLDENVSGY SSSLREALVP LVADHKCSSP EVYGADISPN MLCAGYFDCK SDACQGDSGG\n"
                    + "     PLACEKNGVA YLYGIISWGD GCGRLHKPGV YTRVANYVDW INDRIRPPRR LVAPS\n"
                    + "//\n";

    @Test
    void test() {
        UniProtEntry entry =
                new DefaultUniProtParser(new SupportingDataMapImpl(), true).parse(this.entry);
        UniProtEntryScored scored = new UniProtEntryScored(entry);
        System.out.println(scored.score());
        assertTrue(scored.score() > 0);
    }
}
