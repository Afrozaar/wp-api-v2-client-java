package com.afrozaar.wordpress.wpapi.v2.util;

import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.model.RenderableField;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author johan
 */
public class FieldExtractor {

    private FieldExtractor() {
    }

    // extract the optional value of the renderable field
    public static BiFunction<Function<Post, RenderableField>, Post, Optional<String>> renderableField = (f, p) -> Optional
            .ofNullable(f.apply(p))
            .map(RenderableField::getRendered);

    public static <T> Optional<String> extractField(Function<T, RenderableField> func,T source) {
        return Optional.ofNullable(func.apply(source))
                .map(RenderableField::getRendered);
    }
}
