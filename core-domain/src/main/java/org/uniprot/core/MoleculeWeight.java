package org.uniprot.core;

import org.uniprot.core.util.EnumDisplay;

import javax.annotation.Nonnull;

public enum MoleculeWeight implements EnumDisplay {
    A(710788),
    B(1146532),
    C(1031388),
    D(1150886),
    E(1291155),
    F(1471766),
    G(570519),
    H(1371411),
    I(1131594),
    K(1281741),
    L(1131594),
    M(1311926),
    N(1141038),
    O(2373000),
    P(971167),
    Q(1281307),
    R(1561875),
    S(870782),
    T(1011051),
    U(1500400),
    V(991326),
    W(1862132),
    X(1113306),
    Y(1631760),
    Z(1287473),

    h2o(180153);

    private int weight;

    MoleculeWeight(int weight) {
        this.weight = weight;
    }

    private static int getElementWeight(char c) {
        try {
            return MoleculeWeight.valueOf(String.valueOf(c)).weight;
        } catch (Exception e) {

        }
        return 0;
    }

    public static int calcMolecularWeight(@Nonnull String sequence) {

        long weight = 0;
        for (char c : sequence.toCharArray()) {
            int val = getElementWeight(c);
            weight += val;
        }
        weight += h2o.weight;
        weight += 5000;
        weight = weight / 10000;
        return (int) weight;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public @Nonnull String getDisplayName() {
        return this.name();
    }

    public @Nonnull String getName() {
        return String.valueOf(weight);
    }
}
