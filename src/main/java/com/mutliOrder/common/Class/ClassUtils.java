package com.mutliOrder.common.Class;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassUtils {
	/**
	 * 从包package中获取所有的Class
	 * 
	 * @param pack
	 * @return
	 * @throws IOException
	 */
	public static Set<Class<?>> getClasses(String pack) throws IOException {

		// 第一个class类的集合
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		// 是否循环迭代
		boolean recursive = true;
		// 获取包的名字 并进行替换
		String packageName = pack;
		String packageDirName = packageName.replace('.', '/');
		// 定义一个枚举的集合 并进行循环来处理这个目录下的things
		Enumeration<URL> dirs;

		dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
		// 循环迭代下去
		while (dirs.hasMoreElements()) {
			// 获取下一个元素
			URL url = dirs.nextElement();
			// 得到协议的名称
			String protocol = url.getProtocol();
			// 如果是以文件的形式保存在服务器上
			if ("file".equals(protocol)) {
				// 获取包的物理路径
				String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
				// 以文件的方式扫描整个包下的文件 并添加到集合中
				findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
			} else if ("jar".equals(protocol)) {
				// 如果是jar包文件
				// 定义一个JarFile
				JarFile jar;

				// 获取jar
				jar = ((JarURLConnection) url.openConnection()).getJarFile();
				// 从此jar包 得到一个枚举类
				Enumeration<JarEntry> entries = jar.entries();
				// 同样的进行循环迭代
				while (entries.hasMoreElements()) {
					// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
					JarEntry entry = entries.nextElement();
					String name = entry.getName();
					// 如果是以/开头的
					if (name.charAt(0) == '/') {
						// 获取后面的字符串
						name = name.substring(1);
					}
					// 如果前半部分和定义的包名相同
					if (name.startsWith(packageDirName)) {
						int idx = name.lastIndexOf('/');
						// 如果以"/"结尾 是一个包
						if (idx != -1) {
							// 获取包名 把"/"替换成"."
							packageName = name.substring(0, idx).replace('/', '.');
						}
						// 如果可以迭代下去 并且是一个包
						if ((idx != -1) || recursive) {
							// 如果是一个.class文件 而且不是目录
							if (name.endsWith(".class") && !entry.isDirectory()) {
								// 去掉后面的".class" 获取真正的类名
								String className = name.substring(packageName.length() + 1, name.length() - 6);
								try {
									// 添加到classes
									classes.add(Class.forName(packageName + '.' + className));
								} catch (ClassNotFoundException e) {
									// log
									// .error("添加用户自定义视图类错误
									// 找不到此类的.class文件");
									e.printStackTrace();
								}
							}
						}
					}
				}

			}
		}

		return classes;
	}

	/**
	 * 以文件的形式来获取包下的所有Class
	 * 
	 * @param packageName
	 * @param packagePath
	 * @param recursive
	 * @param classes
	 */
	public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive,
			Set<Class<?>> classes) {
		// 获取此包的目录 建立一个File
		File dir = new File(packagePath);
		// 如果不存在或者 也不是目录就直接返回
		if (!dir.exists() || !dir.isDirectory()) {
			// log.warn("用户定义包名 " + packageName + " 下没有任何文件");
			return;
		}
		// 如果存在 就获取包下的所有文件 包括目录
		File[] dirfiles = dir.listFiles(new FileFilter() {
			// 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
			public boolean accept(File file) {
				return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
			}
		});
		// 循环所有文件
		for (File file : dirfiles) {
			// 如果是目录 则继续扫描
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive,
						classes);
			} else {
				// 如果是java类文件 去掉后面的.class 只留下类名
				String className = file.getName().substring(0, file.getName().length() - 6);
				try {
					// 添加到集合中去
					// classes.add(Class.forName(packageName + '.' +
					// className));
					// 经过回复同学的提醒，这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净
					classes.add(
							Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
				} catch (ClassNotFoundException e) {
					// log.error("添加用户自定义视图类错误 找不到此类的.class文件");
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 生成指定包下所有class，并做成<simplename,class>
	 * 
	 * @param packageName
	 * @return
	 * @throws IOException
	 */
	public static Map<String, Class<?>> getClassBySimpleName(String packageName) throws IOException {
		Map<String, Class<?>> res = new HashMap<String, Class<?>>();
		Set<Class<?>> classes = getClasses(packageName);
		for (Class<?> clas : classes) {
			if (clas.getSimpleName() != null && !("").equals(clas.getSimpleName())) {
				res.put(clas.getSimpleName(), clas);
			}
		}
		return res;

	}

	/**
	 * 获取继承指定类的指定包下所有class，并做成map<simpleName,class>
	 * 
	 * @param packageName
	 * @param extendsClass
	 * @return
	 * @throws IOException
	 */
	public static Map<String, Class<?>> getClassesExtends(String packageName, Class<?> extendsClass)
			throws IOException {
		Map<String, Class<?>> res = new HashMap<String, Class<?>>();
		Set<Class<?>> classes = getClasses(packageName);
		for (Class<?> clas : classes) {
			if (clas.getSuperclass().equals(extendsClass) && clas.getSimpleName() != null
					&& !("").equals(clas.getSimpleName())) {
				res.put(clas.getSimpleName(), clas.asSubclass(extendsClass));
			}
		}
		return res;
	}

	/**
	 * 获取继承指定类的指定包下所有class，并做成set
	 * 
	 * @param packageName
	 * @param T
	 * @return
	 * @throws IOException
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("unchecked")
	public static <T> Set<T> getInterfaceInstanceSet(
			String packageName, Class<?> T) throws IOException, InstantiationException, IllegalAccessException {
		Set<T> res = new HashSet<T>();
		Set<Class<?>> classes = getClasses(packageName);
		for (Class<?> clas : classes) {
			Class<?>[] interfaces = clas.getInterfaces();
			int len = interfaces.length;
			for(int i = 0;i<len;i++) {
				if(T.equals(interfaces[i])) {
					res.add((T) clas.newInstance());
					break;
				}
			}
		}
		return res;
	}

	/**
	 * 找到给定包下实现了指定接口的类，再运行给定方法名和参数类型的方法，参数为指定参数。
	 * 
	 * @param packagePath
	 * @param interfaceClass
	 * @param methodName
	 * @param argsClass
	 * @param param
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws IOException
	 */
	public static HashMap<String, Object> invokeAll(String packagePath, Class<?> interfaceClass, String methodName,
			Class<?>[] argsClass, Object[] param)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, InstantiationException, IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Set<Class<?>> classes = ClassUtils.getClasses(packagePath);
		for (Class<?> cls : classes) {
			Class<?>[] interfaces = cls.getInterfaces();
			for (Class<?> interfac : interfaces) {
				if (null != interfac && interfac.equals(interfaceClass)) {
					Method method = cls.getMethod(methodName, argsClass);
					Object obj = method.invoke(cls.newInstance(), param);
					map.put(cls.getName(), obj);
				}
			}
		}
		return map;
	}

	/**
	 * 找到给定包下实现了指定接口的类，再运行给定方法名和参数类型的方法，参数为指定参数,参数类型为参数类型。
	 * 
	 * @param packagePath
	 * @param interfaceClass
	 * @param methodName
	 * @param param
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static HashMap<String, Object> invokeAllByinterface(String packagePath, Class<?> interfaceClass,
			String methodName, Object[] param)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, InstantiationException, IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Set<Class<?>> classes = ClassUtils.getClasses(packagePath);
		int len = param.length;
		Class<?>[] argsClass = new Class[len];
		for (int i = 0; i < len; i++) {
			argsClass[i] = param[i].getClass();
		}
		for (Class<?> cls : classes) {
			Class<?>[] interfaces = cls.getInterfaces();
			for (Class<?> interfac : interfaces) {
				if (null != interfac && interfac.equals(interfaceClass)) {
					Object obj = invoke(cls.newInstance(), methodName, argsClass, param);
					map.put(cls.getName(), obj);
				}
			}
		}
		return map;
	}

	/**
	 * 找到给定包下继承了指定接口的类，再运行给定方法名和参数类型的方法，参数为指定参数,参数类型为参数类型。
	 * 
	 * @param packagePath
	 * @param subClass
	 * @param methodName
	 * @param param
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static HashMap<String, Object> invokeAllBySubClass(String packagePath, Class<?> subClass, String methodName,
			Object[] param)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, InstantiationException, IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Set<Class<?>> classes = ClassUtils.getClasses(packagePath);
		int len = param.length;
		Class<?>[] argsClass = new Class[len];
		for (int i = 0; i < len; i++) {
			argsClass[i] = param[i].getClass();
		}
		for (Class<?> cls : classes) {
			Class<?> subcla = cls.getSuperclass();
			if (null != subcla && subcla.equals(subClass)) {
				Object obj = invoke(cls.newInstance(), methodName, argsClass, param);
				map.put(cls.getName(), obj);
			}
		}
		return map;
	}

	/**
	 * 传入对象的实例，方法名，参数，参数类型为参数的类型
	 * 
	 * @param instance
	 * @param methodName
	 * @param param
	 * @return
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 */
	public static Object invoke(Object instance, String methodName, Object[] param)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, InstantiationException {
		Class<?> cls = instance.getClass();
		int len = param.length;
		Class<?>[] argsClass = new Class[len];
		for (int i = 0; i < len; i++) {
			argsClass[i] = param[i].getClass();
		}
		Method method = cls.getMethod(methodName, argsClass);
		Object obj = method.invoke(cls.newInstance(), param);
		return obj;
	}

	/**
	 * 传入对象的实例，方法名，参数类型，参数
	 * 
	 * @param instance
	 * @param methodName
	 * @param argsClass
	 * @param param
	 * @return
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 */
	public static Object invoke(Object instance, String methodName, Class<?>[] argsClass, Object[] param)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, InstantiationException {
		Class<?> cls = instance.getClass();
		Method method = cls.getMethod(methodName, argsClass);
		Object obj = method.invoke(instance, param);
		return obj;
	}
}
