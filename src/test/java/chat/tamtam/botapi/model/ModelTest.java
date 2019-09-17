package chat.tamtam.botapi.model;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jparams.verifier.tostring.NameStyle;
import com.jparams.verifier.tostring.ToStringVerifier;
import com.jparams.verifier.tostring.preset.IntelliJPreset;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * @author alexandrchuprin
 */
public class ModelTest {
    @Test
    public void testAllModels() throws Throwable {
        for (Class aClass : getClasses("chat.tamtam.botapi.model")) {
            if (aClass.getName().endsWith("Test")) {
                continue;
            }

            if (aClass.isInterface()) {
                continue;
            }

            if (aClass.isAnonymousClass()) {
                continue;
            }

            String classPath = aClass.getProtectionDomain().getCodeSource().getLocation().getPath();
            if (classPath.contains("test-classes"))
                continue;

            if (aClass.isEnum()) {
                testEnum((Class<? extends Enum<?>>) aClass);
            }

            Annotation typeInfo = aClass.getAnnotation(JsonTypeInfo.class);
            if (typeInfo != null) {
                testPolymorphicType(aClass);
            }

            if (!aClass.isEnum()) {
                ToStringVerifier.forClass(aClass)
                        .withClassName(NameStyle.SIMPLE_NAME)
                        .withPreset(new IntelliJPreset())
                        .verify();
            }

            EqualsVerifier.forClass(aClass)
                    .suppress(Warning.NONFINAL_FIELDS, Warning.INHERITED_DIRECTLY_FROM_OBJECT)
                    .usingGetClass()
                    .verify();
        }
    }

    private void testPolymorphicType(Class aClass) throws Throwable {
        Field typesField = aClass.getField("TYPES");
        Set<String> typesValue = (Set<String>) typesField.get(null);
        JsonSubTypes subTypesAnn = (JsonSubTypes) aClass.getAnnotation(JsonSubTypes.class);
        JsonSubTypes.Type[] types = subTypesAnn.value();
        for (JsonSubTypes.Type type : types) {
            assertThat(typesValue, hasItem(type.name()));
            Object subObject = instantiate(type.value());
            Method getTypeMethod = type.value().getMethod("getType");
            assertThat(getTypeMethod.invoke(subObject), is(type.name()));
        }

        Object thisObject = instantiate(aClass);
        Method getTypeMethod = aClass.getMethod("getType");
        try {
            getTypeMethod.invoke(thisObject);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            throw e;
        } catch (InvocationTargetException e) {
            try {
                throw e.getCause();
            } catch (UnsupportedOperationException throwable) {
                // expected
            }
        }
    }

    private void testEnum(Class<? extends Enum<?>> anEnum) {
        Enum<?>[] enumConstants = anEnum.getEnumConstants();
        for (Enum<?> enumConstant : enumConstants) {
            if (enumConstant instanceof TamTamEnum) {
                assertThat(TamTamEnum.create(enumConstant.getDeclaringClass(), ((TamTamEnum) enumConstant).getValue()), is(enumConstant));
            }
        }
    }

    private static Class[] getClasses(String packageName) throws IOException, ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }

        ArrayList<Class> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }

        return classes.toArray(new Class[0]);
    }

    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }

        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.getName().endsWith(".class")) {
                classes.add(
                        Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }

        return classes;
    }

    private static <T> T instantiate(Class<T> cls) throws Exception {
        Constructor<T> constr = (Constructor<T>) cls.getConstructors()[0];
        List<Object> params = new ArrayList<>();
        for (Class<?> pType : constr.getParameterTypes()) {
            params.add((pType.isPrimitive()) ? 0 : null);
        }

        return constr.newInstance(params.toArray());
    }
}