package org.uniprot.core.uniprotkb.comment;

import java.util.List;

/**
 * Description of A cofactor: any non-protein substance required for an enzyme to be catalytically
 * active
 *
 * <p>
 *
 * <p>The CC COFACTOR annotation has following format:
 *
 * <ul>
 *   <li>CC -!- COFACTOR:( <molecule>:)?
 *   <li>(CC Name=<cofactor>; Xref=<database>:<identifier>;( Evidence={<evidence>};)?)+
 *   <li>(CC Note=<free text>;)?
 * </ul>
 *
 * <p>
 *
 * <p>Here are some examples of cofactor comment in flatfile format
 *
 * <p><i> CC -!- COFACTOR: CC Name=Mg(2+); Xref=ChEBI:CHEBI:18420;
 * Evidence={ECO:0000255|HAMAP-Rule:MF_00086}; CC Name=Co(2+); Xref=ChEBI:CHEBI:48828;
 * Evidence={ECO:0000255|HAMAP-Rule:MF_00086}; CC Note=Binds 2 divalent ions per subunit (magnesium
 * or cobalt). CC {ECO:0000255|HAMAP-Rule:MF_00086}; </i>
 *
 * <p><i> CC -!- COFACTOR: Isoform 1: CC Name=Zn(2+); Xref=ChEBI:CHEBI:29105;
 * Evidence={ECO:0000269|PubMed:16683188}; CC Note=Binds 3 zinc ions. {ECO:0000269|PubMed:16683188};
 * </i>
 *
 * <p><i> CC -!- COFACTOR: Serine protease NS3: CC Name=Zn(2+); Xref=ChEBI:CHEBI:29105;
 * Evidence={ECO:0000269|PubMed:9060645}; CC Note=Binds 1 zinc ion per NS3 protease domain.; </i>
 *
 * @author jieluo
 * @version 1.0
 * @see Comment
 */
public interface CofactorComment extends Comment, HasMolecule {

    /** @return list of cofactor */
    List<Cofactor> getCofactors();

    /** @return cofactor note */
    Note getNote();

    boolean hasCofactors();

    boolean hasNote();

    boolean isValid();
}
