package org.uniprot.core.json.parser;

import org.uniprot.core.citation.Citation;
import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.unirule.RuleExceptionAnnotation;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.cfg.PackageVersion;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import com.fasterxml.jackson.databind.util.ClassUtil;

class FullAnnotationIntrospector extends AnnotationIntrospector {

    @Override
    public Version version() {
        return PackageVersion.VERSION;
    }

    @Override
    public Enum<?> findDefaultEnumValue(Class<Enum<?>> enumCls) {
        return ClassUtil.findFirstAnnotatedEnumValue(enumCls, JsonEnumDefaultValue.class);
    }

    @Override
    public TypeResolverBuilder<?> findTypeResolver(
            MapperConfig<?> config, AnnotatedClass ac, JavaType baseType) {
        if (baseType.isTypeOrSubTypeOf(Comment.class)
                || baseType.isTypeOrSubTypeOf(Citation.class)
                || baseType.isTypeOrSubTypeOf(RuleExceptionAnnotation.class)) {
            StdTypeResolverBuilder typeResolverBuilder = new StdTypeResolverBuilder();
            typeResolverBuilder.init(JsonTypeInfo.Id.NAME, null);
            typeResolverBuilder.typeProperty("type");
            typeResolverBuilder.inclusion(JsonTypeInfo.As.PROPERTY);
            return typeResolverBuilder;
        }
        return super.findTypeResolver(config, ac, baseType);
    }
}
