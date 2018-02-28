package com.mutliOrder.common.conf;

import java.io.FileNotFoundException;
import java.util.HashMap;
/**
 * 配置文件工厂
 *
 */
public class ConfigFactory {


	private static ConfigFactory instance = new ConfigFactory();
	private HashMap<String, ConfProperties> configMap = new HashMap<String, ConfProperties>();

	public static ConfigFactory getInstance() {
		return instance;
	}

	private ConfigFactory() {

	}
	/**
	 * 线程安全的返回ConfigProperties
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 */
	synchronized public ConfProperties getConfigProperties(String filePath) throws FileNotFoundException {
		ConfProperties config = configMap.get(filePath);
		if (config == null) {
			config = new ConfProperties(filePath);
			configMap.put(filePath, config);
		}

		return config;
	}
}