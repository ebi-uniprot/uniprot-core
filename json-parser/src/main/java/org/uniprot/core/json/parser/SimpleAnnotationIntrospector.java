package org.uniprot.core.json.parser;

import java.util.Arrays;

import org.uniprot.core.util.EnumDisplay;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.cfg.PackageVersion;
import com.fasterxml.jackson.databind.util.ClassUtil;

public class SimpleAnnotationIntrospector extends AnnotationIntrospector {

	private static final long serialVersionUID = 7321338666065917109L;

	@Override
    public Version version() {
        return PackageVersion.VERSION;
    }

    public String[] findEnumValues(Class<?> enumType, Enum<?>[] enumValues, String[] names) {
        return Arrays.stream(enumValues).map(en -> {
            EnumDisplay<?> jsonEnum = (EnumDisplay<?>) en;
            return jsonEnum.toDisplayName();
        }).toArray(String[]::new);
    }

    public Enum<?> findDefaultEnumValue(Class<Enum<?>> enumCls) {
        return ClassUtil.findFirstAnnotatedEnumValue(enumCls, JsonEnumDefaultValue.class);
    }

}