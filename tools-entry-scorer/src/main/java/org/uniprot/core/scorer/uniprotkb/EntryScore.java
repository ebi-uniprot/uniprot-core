package org.uniprot.core.scorer.uniprotkb;

import java.text.DecimalFormat;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 17-Mar-2010 Time: 15:17:12 To change this template use File | Settings
 * | File Templates.
 */
public class EntryScore {

    String accession;
    int taxId;
    double descriptionScore;
    double geneScore;
    double commentScore;
    double xrefScore;
    double goScore;
    double keywordScore;
    double featureScore;
    double citiationScore;
    double totalScore;

    public EntryScore(String accession) {
        this.accession = accession;
    }

    public String getAccession() {
        return accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public double getDescriptionScore() {
        return descriptionScore;
    }

    public void setDescriptionScore(double descriptionScore) {
        this.descriptionScore = descriptionScore;
    }

    public double getGeneScore() {
        return geneScore;
    }

    public void setGeneScore(double geneScore) {
        this.geneScore = geneScore;
    }

    public double getCommentScore() {
        return commentScore;
    }

    public void setCommentScore(double commentScore) {
        this.commentScore = commentScore;
    }

    public double getXrefScore() {
        return xrefScore;
    }

    public void setXrefScore(double xrefScore) {
        this.xrefScore = xrefScore;
    }

    public double getKeywordScore() {
        return keywordScore;
    }

    public void setKeywordScore(double keywordScore) {
        this.keywordScore = keywordScore;
    }

    public double getFeatureScore() {
        return featureScore;
    }

    public void setFeatureScore(double featureScore) {
        this.featureScore = featureScore;
    }

    public double getCitiationScore() {
        return citiationScore;
    }

    public void setCitiationScore(double citiationScore) {
        this.citiationScore = citiationScore;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public double getGoScore() {
        return goScore;
    }

    public void setGoScore(double goScore) {
        this.goScore = goScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public int getTaxId() {
        return taxId;
    }

    public void setTaxId(int taxId) {
        this.taxId = taxId;
    }

    public String toStringWithTaxId() {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return accession +
                ", " + taxId +
                ", " + decimalFormat.format(descriptionScore) +
                ", " + decimalFormat.format(geneScore) +
                ", " + decimalFormat.format(commentScore) +
                ", " + decimalFormat.format(xrefScore) +
                ", " + decimalFormat.format(goScore) +
                ", " + decimalFormat.format(keywordScore) +
                ", " + decimalFormat.format(featureScore) +
                ", " + decimalFormat.format(citiationScore) +
                ", " + decimalFormat.format(totalScore);
    }

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return accession +
                ", " + decimalFormat.format(descriptionScore) +
                ", " + decimalFormat.format(geneScore) +
                ", " + decimalFormat.format(commentScore) +
                ", " + decimalFormat.format(xrefScore) +
                ", " + decimalFormat.format(goScore) +
                ", " + decimalFormat.format(keywordScore) +
                ", " + decimalFormat.format(featureScore) +
                ", " + decimalFormat.format(citiationScore) +
                ", " + decimalFormat.format(totalScore);
    }
}
