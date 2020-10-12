package org.uniprot.core.flatfile.tool.ca;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Created by Hermann Zellner on 28/08/18. Contains the mapping for one Rhea entry or one old
 * catalytic activity reaction text without mapping to Rhea
 */
public final class CatalyticActivity implements Serializable {

    private static final long serialVersionUID = -7148573214522550219L;

    private final String rheaUn;
    private final String text;
    private final List<String> ecs;
    private final List<String> reactantIds;
    private final String rheaLr;
    private final String rheaRl;

    /**
     * @param rheaUn Rhea-ID in the format e.g. RHEA:10000, null in case of old catalytic activity
     *     reaction text
     * @param text Catalytic Activity reaction text
     * @param reactantIds Valid Reactants for this Rhea-ID, null in case of old catalytic activity
     *     reaction text
     * @param ecs Valid EC numbers for this Rhea entry or old catalytic activity reaction text
     * @param rheaLr Valid Rhea-ID for physiological left-to-right reaction
     * @param rheaRl Valid Rhea-ID for physiological right-to-left reaction
     */
    public CatalyticActivity(
            String rheaUn,
            String text,
            List<String> reactantIds,
            List<String> ecs,
            String rheaLr,
            String rheaRl) {
        this.rheaUn = rheaUn;
        this.text = text;
        this.ecs = ecs;
        this.reactantIds = reactantIds;
        this.rheaLr = rheaLr;
        this.rheaRl = rheaRl;
    }

    /**
     * The Rhea-ID of these Rhea data in the format e.g. RHEA:10000
     *
     * @return
     */
    public String getRheaUn() {
        return rheaUn;
    }

    /**
     * The Rhea reaction text
     *
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     * The Rhea-ID of the physiological left-to-right-reaction in the format e.g. RHEA:10000
     *
     * @return
     */
    public String getRheaLr() {
        return rheaLr;
    }

    /**
     * The Rhea-ID of the physiological right-to-left-reaction in the format e.g. RHEA:10000
     *
     * @return
     */
    public String getRheaRl() {
        return rheaRl;
    }

    /**
     * The Set of valid EC numbers for this Rhea entry as String, e.g. "1.1.1.1"
     *
     * @return
     */
    public List<String> getEcs() {
        return ecs;
    }

    public List<String> getReactantIds() {
        return reactantIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CatalyticActivity rheaData = (CatalyticActivity) o;
        return Objects.equals(rheaUn, rheaData.rheaUn)
                && Objects.equals(text, rheaData.text)
                && Objects.equals(ecs, rheaData.ecs)
                && Objects.equals(reactantIds, rheaData.reactantIds)
                && Objects.equals(rheaLr, rheaData.rheaLr)
                && Objects.equals(rheaRl, rheaData.rheaRl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rheaUn, text, ecs, reactantIds, rheaLr, rheaRl);
    }
}
