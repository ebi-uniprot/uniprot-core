package org.uniprot.core.proteome;

import java.io.Serializable;

/**
 * @author jluo
 * @date: 1 Apr 2020
 */
public interface BuscoReport extends Serializable {

    int getComplete();

    int getCompleteSingle();

    int getCompleteDuplicated();

    int getFragmented();

    int getMissing();

    int getTotal();

    String getLineageDb();

    default int getScore() {
        int result = 0;
        if (getTotal() > 0) {
            result = (getComplete() + getFragmented()) * 100 / getTotal();
        }
        return result;
    }

    /**
     * for data: 3775(C) 1639(CS) 2136(CD) 64(F) 111(M) 3950(T)
     *
     * @return The summary: C:95.6%[S:41.5%,D:54.1%],F:1.6%,M:2.8%,n:3950
     */
    default String calculateSummary() {
        StringBuilder sb = new StringBuilder();
        if (getTotal() > 0) {
            float c = getComplete() * 100f / getTotal();
            sb.append("C:").append(String.format("%.1f", c)).append("%[");

            float cs = getCompleteSingle() * 100f / getTotal();
            sb.append("S:").append(String.format("%.1f", cs)).append("%,");

            float cd = getCompleteDuplicated() * 100f / getTotal();
            sb.append("D:").append(String.format("%.1f", cd)).append("%],");

            float f = getFragmented() * 100f / getTotal();
            sb.append("F:").append(String.format("%.1f", f)).append("%,");

            float m = getMissing() * 100f / getTotal();
            sb.append("M:").append(String.format("%.1f", m)).append("%,");
        }
        sb.append("n:").append(getTotal());
        return sb.toString();
    }
}
