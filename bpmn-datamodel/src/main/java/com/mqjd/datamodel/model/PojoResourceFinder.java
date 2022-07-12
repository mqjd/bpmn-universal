package com.mqjd.datamodel.model;

import freemarker.template.Template;
import org.codehaus.commons.compiler.util.resource.LazyMultiResourceFinder;
import org.codehaus.commons.compiler.util.resource.MapResourceFinder;
import org.codehaus.commons.compiler.util.resource.ResourceFinder;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class PojoResourceFinder extends LazyMultiResourceFinder {

    public PojoResourceFinder(List<Pojo> pojos, Template template) {
        super(createPojoResourceFinder(pojos, template));
    }

    private static Iterator<ResourceFinder> createPojoResourceFinder(
            List<Pojo> pojos, Template template) {
        MapResourceFinder resourceFinder = new MapResourceFinder();
        pojos.stream().map(v -> new PojoResource(template, v)).forEach(resourceFinder::addResource);
        return Stream.of(resourceFinder).map(v -> (ResourceFinder) v).iterator();
    }
}
