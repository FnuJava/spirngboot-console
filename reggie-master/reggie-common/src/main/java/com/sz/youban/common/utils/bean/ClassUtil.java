package com.sz.youban.common.utils.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 反射工具
 *
 * @author One_Eleven Oct 4, 2014 10:50:21 AM
 */
public abstract class ClassUtil {

    /**
     * 数组类名的后缀: []
     */
    public static final String ARRAY_SUFFIX = "[]";
    /**
     * 多维数组类名的前缀: [L
     */
    public static final String INTERNAL_ARRAY_PREFIX = "[L";
    /**
     * 包名分隔符: .
     */
    public static final char PACKAGE_SEPARATOR = '.';
    /**
     * 内部类分隔符: $
     */
    public static final char INNER_CLASS_SEPARATOR = '$';
    /**
     * CGLIB 代理类的分隔符 : $$
     */
    public static final String CGLIB_CLASS_SEPARATOR = "$$";
    private static final Map<String, Class<?>> primitives = new HashMap<String, Class<?>>(8);
    private static final Map<String, String> transforms = new HashMap<String, String>(8);
    private static final String ADVISED_FIELD_NAME = "advised";
    private static final String CLASS_JDK_DYNAMIC_AOP_PROXY = "org.springframework.aop.framework.JdkDynamicAopProxy";
    private static final Map<String, Class<?>> classCache = new WeakHashMap<String, Class<?>>();
    private static final ClassLoader defaultLoader = ClassUtil.class.getClassLoader();
    private static final String[] defaultPackages = {"java.lang", "java.util"};
    private static Logger logger = LoggerFactory.getLogger(ClassUtil.class);

    static {
        primitives.put("byte", byte.class);
        primitives.put("char", char.class);
        primitives.put("double", double.class);
        primitives.put("float", float.class);
        primitives.put("int", int.class);
        primitives.put("long", long.class);
        primitives.put("short", short.class);
        primitives.put("boolean", boolean.class);

        transforms.put("byte", "B");
        transforms.put("char", "C");
        transforms.put("double", "D");
        transforms.put("float", "F");
        transforms.put("int", "I");
        transforms.put("long", "J");
        transforms.put("short", "S");
        transforms.put("boolean", "Z");

    }

    /**
     * 强行设置Field可访问.
     */
    protected static void makeAccessible(final Field field) {
        if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
            field.setAccessible(true);
        }
    }


    /**
     * 通过反射, 获得Class定义中声明的父类的泛型参数的类型. 如无法找到, 返回Object.class. eg. public UserDao
     * extends HibernateDao<User>
     *
     * @param clazz The class to introspect
     * @return the first generic declaration, or Object.class if cannot be determined
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getSuperClassGenricType(final Class<?> clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 通过反射, 获得定义Class时声明的父类的泛型参数的类型. 如无法找到, 返回Object.class.
     *
     * 如public UserDao extends HibernateDao<User,Long>
     */
    @SuppressWarnings({"rawtypes"})
    public static Class getSuperClassGenricType(final Class<?> cls, final int index) {
        Type genType = cls.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            logger.warn(cls.getSimpleName() + "'s superclass not ParameterizedType");
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            logger.warn("Index: " + index + ", Size of " + cls.getSimpleName() + "'s Parameterized Type: " + params.length);
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            logger.warn(cls.getSimpleName() + " not set the actual class on superclass generic parameter");
            return Object.class;
        }

        return (Class) params[index];
    }


    /**
     * 将反射时的checked exception转换为unchecked exception.
     */
    public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
        return convertReflectionExceptionToUnchecked(null, e);
    }

    public static RuntimeException convertReflectionExceptionToUnchecked(String desc, Exception e) {
        desc = (desc == null) ? "Unexpected Checked Exception." : desc;
        if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException || e instanceof NoSuchMethodException) {
            return new IllegalArgumentException(desc, e);
        } else if (e instanceof InvocationTargetException) {
            return new RuntimeException(desc, ((InvocationTargetException) e).getTargetException());
        } else if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        return new RuntimeException(desc, e);
    }

    /**
     * 通过反射创建实例
     */
    public static <T> T getNewInstance(Class<T> cls) {
        try {
            return cls.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取得obj所属Class
     */
    @SuppressWarnings("rawtypes")
    public static Class getTargetClass(Object obj) {
        if (obj == null) {
            return null;
        }
        Class cls = obj.getClass();
        if (isCglibProxy(obj)) {
            cls = cls.getSuperclass();
        }
        return cls;
    }


    /**
     * 判断是否属于cglib代理对象
     */
    @SuppressWarnings("rawtypes")
    public static boolean isCglibProxy(Object obj) {
        Class cls = obj.getClass();
        return cls.getName().indexOf(CGLIB_CLASS_SEPARATOR) > 0;
    }

    /**
     * 判断是否属于jdk动态代理对象
     */
    @SuppressWarnings("rawtypes")
    public static boolean isJdkDynamicProxy(Object obj) {
        return false;
    }


    /**
     * 获取数组元素类型
     */
    public static Class<?> findArrayElementType(Class<?> arrayClass) {
        if (!arrayClass.isArray()) {
            return arrayClass;
        }
        String className = arrayClass.getName();
        int beginIndex = className.lastIndexOf('[');
        int endIndex = className.indexOf(';');
        if (beginIndex == -1) {
            return arrayClass;
        }
        int bracketsIndex = className.indexOf('[');

        // 检查是否还有 '[', 如果有，说明是多维数组
        if (bracketsIndex != beginIndex) {
            className = className.substring(bracketsIndex + 1); // 去掉[
        } else {
            if (endIndex == -1 && className.indexOf(INTERNAL_ARRAY_PREFIX) == -1) { // primitive,
                // 形如
                // [I
                String transform = className.substring(bracketsIndex + 1);
                Iterator<Map.Entry<String, String>> iter = transforms.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    if (entry.getValue().equals(transform)) {
                        className = entry.getKey();
                        break;
                    }
                }
            } else { // 对象类型, 形如 [Ljava.lang.String;
                className = className.substring(bracketsIndex + 2, endIndex);
            }
        }
        return findClass(className);
    }

    /**
     * 搜索指定类名的类，将其转化为Class对象返回
     *
     * @param className 指定的类名
     * @return Class对象
     */
    public static Class<?> findClass(String className) {
        Class<?> cached = classCache.get(className);
        if (cached != null) {
            return cached;
        }
        cached = findClass(className, defaultPackages, defaultLoader);
        classCache.put(className, cached);
        return cached;
    }

    /**
     * 将指定的一组类名转化为Class数组
     *
     * @param classNames 指定的一组类名
     * @return Class数组
     */
    @SuppressWarnings("rawtypes")
    public static Class[] findClass(String[] classNames) {
        Class[] classArray = new Class[classNames.length];
        for (int i = 0, len = classNames.length; i < len; i++) {
            classArray[i] = findClass(classNames[i]);
        }
        return classArray;
    }

    /**
     * 使用指定的ClassLoader、在指定的包下，搜索指定类名的类，将其转化为Class对象
     *
     * @param className 指定的类名
     * @param packages  指定的包
     * @param loader    指定的ClassLoader
     * @return Class对象
     */
    public static Class<?> findClass(String className, String[] packages, ClassLoader loader) {
        String save = className;
        int dimensions = 0;
        int index = 0;
        while ((index = className.indexOf(ARRAY_SUFFIX, index) + 1) > 0) {
            dimensions++;
        }
        StringBuilder brackets = new StringBuilder(className.length() - dimensions);
        for (int i = 0; i < dimensions; i++) {
            brackets.append('[');
        }
        className = className.substring(0, className.length() - 2 * dimensions);

        String prefix = (dimensions > 0) ? brackets + "L" : "";
        String suffix = (dimensions > 0) ? ";" : "";

        try {
            return Class.forName(prefix + className + suffix, false, loader);
        } catch (ClassNotFoundException ignore) {
        }
        for (int i = 0; i < packages.length; i++) {
            try {
                return Class.forName(prefix + packages[i] + PACKAGE_SEPARATOR + className + suffix, false, loader);
            } catch (ClassNotFoundException ignore) {
            }
        }
        if (dimensions == 0) {
            Class<?> c = primitives.get(className);
            if (c != null) {
                return c;
            }
        } else {
            String transform = transforms.get(className);
            if (transform != null) {
                try {
                    return Class.forName(brackets + transform, false, loader);
                } catch (ClassNotFoundException ignore) {
                }
            }
        }
        throw new RuntimeException("className " + save + "  not found !");
    }

    /**
     * 在指定的类上，根据方法名称和参数类型，查找方法
     *
     * @param targetClass    指定的类
     * @param methodName     方法名称
     * @param parameterTypes 参数类型
     * @return Method 方法对象
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Method findMethod(Class targetClass, String methodName, Class... parameterTypes) {
        try {
            return targetClass.getDeclaredMethod(methodName, parameterTypes);
        } catch (Exception e1) {
            try {
                return targetClass.getMethod(methodName, parameterTypes);
            } catch (Exception e2) {
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("can not find method[").append(methodName).append("(");
        for (int i = 0, len = parameterTypes.length; i < len; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(parameterTypes[i].getName());
        }
        sb.append(")] for class[").append(targetClass.getName()).append("]");
        throw new RuntimeException(sb.toString());
    }

    /**
     * 由指定的包括类名、方法名、参数列表的字符串，查找方法.<br/>
     * <code>fullMethodNameWithParams</code>形如：java.lang.String.substring(int,
     * int)
     *
     * @param fullMethodNameWithParams 包括类名、方法名、参数列表的字符串
     * @return 方法对象
     */
    @SuppressWarnings("rawtypes")
    public static Method findMethod(String fullMethodNameWithParams) {
        int lparen = fullMethodNameWithParams.indexOf('(');
        int dot = fullMethodNameWithParams.lastIndexOf('.', lparen);
        int rparen = fullMethodNameWithParams.indexOf(')', lparen);
        String className = fullMethodNameWithParams.substring(0, dot).trim();
        String methodName = fullMethodNameWithParams.substring(dot + 1, lparen).trim();

        Class targetClass = findClass(className);
        List<String> paramList = new ArrayList<String>();
        int start = lparen + 1;
        for (; ; ) {
            int comma = fullMethodNameWithParams.indexOf(',', start);
            if (comma < 0) {
                break;
            }
            paramList.add(fullMethodNameWithParams.substring(start, comma).trim());
            start = comma + 1;
        }
        if (start < rparen) {
            paramList.add(fullMethodNameWithParams.substring(start, rparen).trim());
        }
        String[] paramArray = paramList.toArray(new String[paramList.size()]);
        Class[] paramTypes = findClass(paramArray);
        return findMethod(targetClass, methodName, paramTypes);
    }

    /**
     * 获取类加载器
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
