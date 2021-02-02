package org.uniprot.core.json.parser;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.cfg.PackageVersion;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.github.bohnman.squiggly.filter.SquigglyPropertyFilter;
import org.uniprot.core.util.EnumDisplay;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

class SimpleAnnotationIntrospector extends AnnotationIntrospector {
    private static final long serialVersionUID = 7321338666065917109L;

    private final Set<Class<?>> ignorableTypes;

    SimpleAnnotationIntrospector() {
        this(Collections.emptySet());
    }

    SimpleAnnotationIntrospector(Set<Class<?>> ignorableTypes) {
        this.ignorableTypes = ignorableTypes;
    }

    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }

    @Override
    public String[] findEnumValues(Class<?> enumType, Enum<?>[] enumValues, String[] names) {
        return Arrays.stream(enumValues)
                .map(
                        en -> {
                            EnumDisplay jsonEnum = (EnumDisplay) en;
                            return jsonEnum.getDisplayName();
                        })
                .toArray(String[]::new);
    }

    @Override
    public Enum<?> findDefaultEnumValue(Class<Enum<?>> enumCls) {
        return ClassUtil.findFirstAnnotatedEnumValue(enumCls, JsonEnumDefaultValue.class);
    }

    @Override
    public Object findFilterId(Annotated ann) {
        return SquigglyPropertyFilter.FILTER_ID;
    }

    @Override
    public Boolean isIgnorableType(AnnotatedClass ac) {
        if (ignorableTypes.contains(ac.getRawType())) {
            return true;
        }
        return super.isIgnorableType(ac);
    }
}
