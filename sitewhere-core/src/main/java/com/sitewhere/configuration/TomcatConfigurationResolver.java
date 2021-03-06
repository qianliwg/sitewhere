/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.configuration;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.io.FileSystemResource;

import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.configuration.IConfigurationResolver;
import com.sitewhere.spi.system.IVersion;
import com.sitewhere.spi.user.ITenant;

/**
 * Resolves SiteWhere configuration relative to the Tomcat installation base directory.
 * 
 * @author Derek
 */
public class TomcatConfigurationResolver implements IConfigurationResolver {

	/** Static logger instance */
	public static Logger LOGGER = Logger.getLogger(TomcatConfigurationResolver.class);

	/** File name for SiteWhere server config file */
	public static final String SERVER_CONFIG_FILE_NAME = "sitewhere-server.xml";

	/** File name for SiteWhere default tenant config file */
	public static final String TENANT_CONFIG_FILE_NAME = "sitewhere-tenant.xml";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sitewhere.spi.configuration.IConfigurationResolver#resolveSiteWhereContext(
	 * com.sitewhere.spi.system.IVersion)
	 */
	@Override
	public ApplicationContext resolveSiteWhereContext(IVersion version) throws SiteWhereException {
		LOGGER.info("Loading Spring configuration ...");
		File sitewhereConf = getSiteWhereConfigFolder();
		File serverConfigFile = new File(sitewhereConf, SERVER_CONFIG_FILE_NAME);
		if (!serverConfigFile.exists()) {
			throw new SiteWhereException("SiteWhere server configuration not found: "
					+ serverConfigFile.getAbsolutePath());
		}
		GenericApplicationContext context = new GenericApplicationContext();

		// Plug in custom property source.
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("sitewhere.edition", version.getEditionIdentifier().toLowerCase());

		MapPropertySource source = new MapPropertySource("sitewhere", properties);
		context.getEnvironment().getPropertySources().addLast(source);

		// Read context from XML configuration file.
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(context);
		reader.setValidationMode(XmlBeanDefinitionReader.VALIDATION_XSD);
		reader.loadBeanDefinitions(new FileSystemResource(serverConfigFile));

		context.refresh();
		return context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sitewhere.spi.configuration.IConfigurationResolver#getTenantConfiguration(com
	 * .sitewhere.spi.user.ITenant, com.sitewhere.spi.system.IVersion)
	 */
	@Override
	public String getTenantConfiguration(ITenant tenant, IVersion version) throws SiteWhereException {
		File sitewhereConf = getSiteWhereConfigFolder();
		File tenantConfigFile = getTenantConfigurationFile(sitewhereConf, tenant, version);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			FileInputStream in = new FileInputStream(tenantConfigFile);
			IOUtils.copy(in, out);
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
			return new String(out.toByteArray());
		} catch (FileNotFoundException e) {
			throw new SiteWhereException(e);
		} catch (IOException e) {
			throw new SiteWhereException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sitewhere.spi.configuration.IConfigurationResolver#resolveTenantContext(com
	 * .sitewhere.spi.user.ITenant, com.sitewhere.spi.system.IVersion,
	 * org.springframework.context.ApplicationContext)
	 */
	@Override
	public ApplicationContext resolveTenantContext(ITenant tenant, IVersion version, ApplicationContext global)
			throws SiteWhereException {
		LOGGER.info("Loading Spring configuration ...");
		File sitewhereConf = getSiteWhereConfigFolder();
		File tenantConfigFile = getTenantConfigurationFile(sitewhereConf, tenant, version);
		GenericApplicationContext context = new GenericApplicationContext(global);

		// Plug in custom property source.
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("sitewhere.edition", version.getEditionIdentifier().toLowerCase());
		properties.put("tenant.id", tenant.getId());

		MapPropertySource source = new MapPropertySource("sitewhere", properties);
		context.getEnvironment().getPropertySources().addLast(source);

		// Read context from XML configuration file.
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(context);
		reader.setValidationMode(XmlBeanDefinitionReader.VALIDATION_XSD);
		reader.loadBeanDefinitions(new FileSystemResource(tenantConfigFile));

		context.refresh();
		return context;
	}

	/**
	 * Get the tenant configuration file. Create one from the template if necessary.
	 * 
	 * @param sitewhereConf
	 * @param tenant
	 * @param version
	 * @return
	 * @throws SiteWhereException
	 */
	protected File getTenantConfigurationFile(File sitewhereConf, ITenant tenant, IVersion version)
			throws SiteWhereException {
		File tenantConfigFile = new File(sitewhereConf, tenant.getId() + "-tenant.xml");
		if (tenantConfigFile.exists()) {
			LOGGER.info("Tenant " + tenant.getName() + "(" + tenant.getId()
					+ ") configuration loading from: " + tenantConfigFile.getAbsolutePath());
			return tenantConfigFile;
		}
		LOGGER.info("Tenant " + tenant.getName() + "(" + tenant.getId() + ") configuration not found: "
				+ tenantConfigFile.getAbsolutePath());
		File tenantDefault = new File(sitewhereConf, TENANT_CONFIG_FILE_NAME);
		if (!tenantDefault.exists()) {
			throw new SiteWhereException("Default tenant configuration not found at: "
					+ tenantDefault.getAbsolutePath());
		}
		LOGGER.info("Copying configuration from " + tenantDefault.getAbsolutePath() + ".");
		copyDefaultTenantConfiguration(tenantDefault, tenantConfigFile);
		createTenantPropertiesFile(tenant, sitewhereConf);
		return tenantConfigFile;
	}

	/**
	 * Copy the default tenant configuration to initialize a new tenant.
	 * 
	 * @param defaultConfig
	 * @param tenantConfig
	 * @throws SiteWhereException
	 */
	protected void copyDefaultTenantConfiguration(File defaultConfig, File tenantConfig)
			throws SiteWhereException {
		try {
			// Copy the default configuration to the tenant configuration.
			tenantConfig.createNewFile();
			FileInputStream in = new FileInputStream(defaultConfig);
			FileOutputStream out = new FileOutputStream(tenantConfig);
			IOUtils.copy(in, out);
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		} catch (IOException e) {
			throw new SiteWhereException("Unable to copy tenant configuration file: "
					+ defaultConfig.getAbsolutePath(), e);
		}
	}

	/**
	 * Create a default properties file for a tenant.
	 * 
	 * @param tenant
	 * @param folder
	 * @throws SiteWhereException
	 */
	protected void createTenantPropertiesFile(ITenant tenant, File folder) throws SiteWhereException {
		File tenantPropsFile = new File(folder, tenant.getId() + "-tenant.properties");
		try {
			tenantPropsFile.createNewFile();
			String content = "# Properties for '" + tenant.getName() + "' tenant configuration.\n";
			ByteArrayInputStream in = new ByteArrayInputStream(content.getBytes());
			FileOutputStream out = new FileOutputStream(tenantPropsFile);
			IOUtils.copy(in, out);
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
		} catch (IOException e) {
			LOGGER.error("Unable to copy tenant configuration file: " + tenantPropsFile.getAbsolutePath(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sitewhere.spi.configuration.IConfigurationResolver#getConfigurationRoot()
	 */
	@Override
	public File getConfigurationRoot() throws SiteWhereException {
		return TomcatConfigurationResolver.getSiteWhereConfigFolder();
	}

	/**
	 * Gets the CATALINA/conf/sitewhere folder where configs are stored.
	 * 
	 * @return
	 * @throws SiteWhereException
	 */
	public static File getSiteWhereConfigFolder() throws SiteWhereException {
		String catalina = System.getProperty("catalina.base");
		if (catalina == null) {
			throw new SiteWhereException("CATALINA_HOME not set.");
		}
		File catFolder = new File(catalina);
		if (!catFolder.exists()) {
			throw new SiteWhereException("CATALINA_HOME folder does not exist.");
		}
		File confDir = new File(catalina, "conf");
		if (!confDir.exists()) {
			throw new SiteWhereException("CATALINA_HOME conf folder does not exist.");
		}
		File sitewhereDir = new File(confDir, "sitewhere");
		if (!confDir.exists()) {
			throw new SiteWhereException("CATALINA_HOME conf/sitewhere folder does not exist.");
		}
		return sitewhereDir;
	}

	/**
	 * Gets the CATALINA/data folder where data is stored.
	 * 
	 * @return
	 * @throws SiteWhereException
	 */
	public static File getSiteWhereDataFolder() throws SiteWhereException {
		String catalina = System.getProperty("catalina.base");
		if (catalina == null) {
			throw new SiteWhereException("CATALINA_HOME not set.");
		}
		File catFolder = new File(catalina);
		if (!catFolder.exists()) {
			throw new SiteWhereException("CATALINA_HOME folder does not exist.");
		}
		File dataDir = new File(catalina, "data");
		if (!dataDir.exists()) {
			dataDir.mkdir();
		}
		return dataDir;
	}
}