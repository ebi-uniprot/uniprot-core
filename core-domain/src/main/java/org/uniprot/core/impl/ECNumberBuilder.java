package org.uniprot.core.impl;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.ECNumber;

public class ECNumberBuilder implements Builder<ECNumber> {
    private String number;

    public ECNumberBuilder(String number) {
        this.number = number;
    }

    @Override
    public @Nonnull ECNumber build() {
        return new ECNumberImpl(number);
    }

    public static @Nonnull ECNumberBuilder from(@Nonnull ECNumber instance) {
        return new ECNumberBuilder(instance.getValue());
    }
}
