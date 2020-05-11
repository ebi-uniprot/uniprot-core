package org.uniprot.core.json.parser;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;

import org.uniprot.core.citation.Citation;
import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.unirule.RuleExceptionAnnotation;

public class CustomAnnotationIntrospector extends SimpleAnnotationIntrospector {
    private static final long serialVersionUID = 3724944589060382231L;

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
