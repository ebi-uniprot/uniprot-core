package org.uniprot.core.xml.proteome;

import org.uniprot.core.proteome.GenomeAssembly;
import org.uniprot.core.proteome.GenomeAssemblyLevel;
import org.uniprot.core.proteome.GenomeAssemblySource;
import org.uniprot.core.proteome.impl.GenomeAssemblyBuilder;
import org.uniprot.core.util.Utils;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.proteome.GenomeAssemblyType;
import org.uniprot.core.xml.jaxb.proteome.ObjectFactory;

import java.util.Objects;

/**
 * @author lgonzales
 * @since 15/04/2020
 */
public class GenomeAssemblyConverter implements Converter<GenomeAssemblyType, GenomeAssembly> {

    private final ObjectFactory xmlFactory;

    public GenomeAssemblyConverter() {
        this(new ObjectFactory());
    }

    public GenomeAssemblyConverter(ObjectFactory xmlFactory) {
        this.xmlFactory = xmlFactory;
    }

    @Override
    public GenomeAssembly fromXml(GenomeAssemblyType xmlObj) {
        GenomeAssembly result = null;
        if (Utils.notNull(xmlObj)) {
            GenomeAssemblyBuilder builder =
                    new GenomeAssemblyBuilder()
                            .assemblyId(xmlObj.getGenomeAssembly())
                            .genomeAssemblyUrl(xmlObj.getGenomeAssemblyUrl())
                            .source(
                                    GenomeAssemblySource.fromValue(
                                            xmlObj.getGenomeAssemblySource()));

            if(Objects.nonNull(xmlObj.getGenomeRepresentation())){
                builder.level(GenomeAssemblyLevel.fromValue(xmlObj.getGenomeRepresentation()));
            }

            result = builder.build();
        }
        return result;
    }

    @Override
    public GenomeAssemblyType toXml(GenomeAssembly uniObj) {
        GenomeAssemblyType xmlObj = xmlFactory.createGenomeAssemblyType();
        if (Utils.notNullNotEmpty(uniObj.getAssemblyId())) {
            xmlObj.setGenomeAssembly(uniObj.getAssemblyId());
        }
        if (Utils.notNullNotEmpty(uniObj.getGenomeAssemblyUrl())) {
            xmlObj.setGenomeAssemblyUrl(uniObj.getGenomeAssemblyUrl());
        }
        if (Utils.notNull(uniObj.getSource())) {
            xmlObj.setGenomeAssemblySource(uniObj.getSource().getDisplayName());
        }
        if (Utils.notNull(uniObj.getLevel())) {
            xmlObj.setGenomeRepresentation(uniObj.getLevel().getDisplayName());
        }
        return xmlObj;
    }
}
