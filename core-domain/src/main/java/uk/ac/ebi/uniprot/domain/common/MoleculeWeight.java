package uk.ac.ebi.uniprot.domain.common;


public enum MoleculeWeight {

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

    public int getWeight() {
        return weight;
    }


    public static int calcMolecularWeight(String sequence) {

        long weight = 0;
        for (char c : sequence.toCharArray()) {
            int val = MoleculeWeight.valueOf(String.valueOf(c)).weight;
            weight += val;
        }
        weight += h2o.weight;
        weight += 5000;
        weight = weight / 10000;
        return (int) weight;
    }
}
