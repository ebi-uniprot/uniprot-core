package org.uniprot.core.xml.unirule;

import org.uniprot.core.unirule.Rule;
import org.uniprot.core.xml.Converter;
import org.uniprot.core.xml.jaxb.unirule.MainType;

public interface TypeConverter<F extends MainType, T extends Rule> extends Converter<F, T> {}
