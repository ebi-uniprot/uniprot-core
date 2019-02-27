package uk.ebi.uniprot.scorer.uniprotkb;

import java.text.DecimalFormat;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 18-Mar-2010 Time: 11:48:41 To change this template use File | Settings
 * | File Templates.
 */
public class SetScore {

    public enum Type {
        DESCRIPTION_SCORE,
        GENE_SCORE,
        COMMENT_SCORE,
        XREF_SCORE,
        GO_SCORE,
        KEYWORD_SCORE,
        FEATURE_SCORE,
        CITATION_SCORE,
        TOTAL_SCORE;
    }

    private int count; // Number of numbers that have been entered.
    private Type type;
    private double sum; // The sum of all the items that have been entered.
    private double squareSum; // The sum of the squares of all the items.
    private double max = Double.NEGATIVE_INFINITY; // Largest item seen.
    private double min = Double.POSITIVE_INFINITY; // Smallest item seen.

    public SetScore(Type type) {
        this.type = type;
    }

    public void addScore(double num) {
        // Add the number to the dataset.
        count++;
        sum += num;
        squareSum += num * num;
        if (num > max)
            max = num;
        if (num < min)
            min = num;
    }

    public Type getType() {
        return type;
    }

    public int getCount() {
        // Return number of items that have been entered.
        return count;
    }

    public double getSum() {
        // Return the sum of all the items that have been entered.
        return sum;
    }

    public double getMean() {
        // Return average of all the items that have been entered.
        // Value is Double.NaN if count == 0.
        return sum / count;
    }

    public double getStandardDeviation() {
        // Return standard deviation of all the items that have been entered.
        // Value will be Double.NaN if count == 0.
        double mean = getMean();
        return Math.sqrt(squareSum / count - mean * mean);
    }

    public double getMin() {
        // Return the smallest item that has been entered.
        // Value will be infinity if no items have been entered.
        return min;
    }

    public double getMax() {
        // Return the largest item that has been entered.
        // Value will be -infinity if no items have been entered.
        return max;
    }

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return type +
                ", " + count +
                ", " + decimalFormat.format(sum) +
                ", " + decimalFormat.format(getMean()) +
                ", " + decimalFormat.format(getStandardDeviation()) +
                ", " + decimalFormat.format(max) +
                ", " + decimalFormat.format(min);
    }
}
