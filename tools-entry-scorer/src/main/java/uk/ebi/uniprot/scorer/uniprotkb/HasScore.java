package uk.ebi.uniprot.scorer.uniprotkb;

/**
 * Created by IntelliJ IDEA.
 * User: spatient
 * Date: 01-Mar-2010
 * Time: 13:32:32
 * To change this template use File | Settings | File Templates.
 */
public interface HasScore {
    public double score();
    public Consensus consensus();
}
