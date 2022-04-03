package com.mqjd.web.util;

import com.mqjd.web.asm.ClassReader;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class ClassPathScanner {
    private static final String URL_PROTOCOL_JAR = "jar";
    private static final String FILE_URL_PREFIX = "file";

    public static List<? extends Class<? extends Handler<RoutingContext>>> scanAllHandlers(
            String basePackage, AnnotationFilter filter) {
        ClassLoader cl = ClassPathScanner.class.getClassLoader();
        URL baseResource = cl.getResource(convertPackageToResourcePath(basePackage));
        String protocol = Objects.requireNonNull(baseResource).getProtocol();

        try {
            List<URL> urls;
            if (URL_PROTOCOL_JAR.equals(protocol)) {
                urls = readAllClass((JarURLConnection) baseResource.openConnection());
            } else if (FILE_URL_PREFIX.equals(protocol)) {
                urls = readAllClass(new File(Objects.requireNonNull(baseResource).toURI()));
            } else {
                throw new IllegalStateException("not support protocol:" + protocol);
            }
            return urls.stream()
                    .map(file -> loadClassByFile(file, cl))
                    .filter(filter)
                    .collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            throw new IllegalArgumentException("error when scan package :" + basePackage, e);
        }
    }

    private static Class<? extends Handler<RoutingContext>> loadClassByFile(
            URL url, ClassLoader classLoader) {
        try {
            String className = new ClassReader(url.openStream()).getClassName();
            //noinspection unchecked
            return (Class<? extends Handler<RoutingContext>>)
                    classLoader.loadClass(convertResourcePathToClassName(className));
        } catch (ClassNotFoundException | IOException e) {
            throw new IllegalArgumentException(
                    "load class failed,maybe is not a Handler<RoutingContext> class", e);
        }
    }

    private static List<URL> readAllClass(File dir) throws MalformedURLException {
        List<URL> result = new ArrayList<>();
        if (dir.isDirectory()) {
            File[] listFiles = dir.listFiles();
            if (listFiles != null) {
                for (File file : listFiles) {
                    if (file.isFile()) {
                        result.add(file.toURI().toURL());
                    } else {
                        readAllClass(file);
                    }
                }
            }
        }
        return result;
    }

    public static List<URL> readAllClass(JarURLConnection jarURLConnection) {
        try {
            List<URL> urls = new ArrayList<>();
            JarFile jarFile = jarURLConnection.getJarFile();
            JarEntry jarEntry = jarURLConnection.getJarEntry();
            String rootEntryPath = (jarEntry != null ? jarEntry.getName() : "");
            for (Enumeration<JarEntry> entries = jarFile.entries(); entries.hasMoreElements(); ) {
                JarEntry entry = entries.nextElement();
                String entryPath = entry.getName();
                if (entryPath.startsWith(rootEntryPath) && !entry.isDirectory()) {
                    urls.add(new URL(jarURLConnection.getURL(), "/" + entryPath));
                }
            }
            return urls;
        } catch (IOException e) {
            throw new IllegalArgumentException("scan class failed jar: " + jarURLConnection, e);
        }
    }

    private static String convertResourcePathToClassName(String resourcePath) {
        return resourcePath.replace('/', '.');
    }

    private static String convertPackageToResourcePath(String packagePath) {
        return packagePath.replace('.', '/');
    }
}
