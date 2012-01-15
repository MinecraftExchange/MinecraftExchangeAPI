package org.mcexchange.api.plugin;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.Attributes;

/**
 * An instance of ExchangePluginLoader is used to load all of the plugins.
 */
public class ExchangePluginLoader {
	/**
	 * This method loads the registered ExchangePlugin(s) in the specified jar.
	 * 
	 * @throws IOException If the given jar file can't be read from.
	 * @throws ClassNotFoundException If the specified class in the jar can't be loaded.
	 * @throws IllegalAccessException If the specified class doesn't have a public constructor with no arguments.
	 * @throws InstantiationException If the specified class doesn't have a constructor with no arguments or if the specified class is abstract. 
	 */
	public List<ExchangePlugin> loadPlugins(URL jf) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		URL u = new URL("jar", "", jf + "!/");
		JarURLConnection uc = (JarURLConnection)u.openConnection();
		Attributes attr = uc.getMainAttributes();
		String name = attr.getValue("Exchange-Main");
		URLClassLoader ucl = new URLClassLoader(new URL[]{jf});
		Class<?> c = ucl.loadClass(name);
		List<Class<?>> interfaces = Arrays.asList(c.getInterfaces());
		if(interfaces.contains(ExchangePlugin.class)) {
			ExchangePlugin ep = c.asSubclass(ExchangePlugin.class).newInstance();
			return Arrays.asList(ep);
		} else if(interfaces.contains(PluginSuite.class)) {
			PluginSuite ps = c.asSubclass(PluginSuite.class).newInstance();
			return ps.getPlugins();
		} else throw new RuntimeException(jf.toString() + " specifies an invalid class as an ExchangePlugin.");
	}
	
	/**
	 * This method loads all of the ExchangePlugins available.
	 */
	public List<ExchangePlugin> loadAllPlugins() {
		List<ExchangePlugin> result = new ArrayList<ExchangePlugin>();
		File plugindir = new File("./plugins");
		File[] contents = plugindir.listFiles(new FilenameFilter() {
			public boolean accept(File arg0, String arg1) {
				return arg1.endsWith(".jar");
			}
		});
		if(contents==null) return result;
		for(File f : contents) {
			URL url = null;
			try {
				url = f.toURI().toURL();
			} catch(MalformedURLException e) {
				System.err.println("Could not create a URL for a plugin's jar file!");
				e.printStackTrace();
				continue;
			}
			try {
				result.addAll(loadPlugins(url));
			} catch(Exception e) {
				System.err.println("Could not load the plugin found at \"" + url.toString() + "\"!");
				e.printStackTrace();
			}
		}
		return result;
	}
}
