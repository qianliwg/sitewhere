/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.spring.handler;

import java.util.List;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandler;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import com.sitewhere.ehcache.DeviceManagementCacheProvider;
import com.sitewhere.hazelcast.HazelcastDistributedCacheProvider;
import com.sitewhere.hazelcast.SiteWhereHazelcastConfiguration;
import com.sitewhere.hbase.asset.HBaseAssetManagement;
import com.sitewhere.hbase.device.HBaseDeviceManagement;
import com.sitewhere.hbase.scheduling.HBaseScheduleManagement;
import com.sitewhere.mongodb.asset.MongoAssetManagement;
import com.sitewhere.mongodb.device.MongoDeviceManagement;
import com.sitewhere.mongodb.scheduling.MongoScheduleManagement;
import com.sitewhere.server.SiteWhereServerBeans;
import com.sitewhere.server.asset.DefaultAssetModuleInitializer;
import com.sitewhere.server.device.DefaultDeviceModelInitializer;
import com.sitewhere.server.scheduling.DefaultScheduleModelInitializer;

/**
 * Parses configuration for tenant datastore entries.
 * 
 * @author Derek
 */
public class TenantDatastoreParser extends AbstractBeanDefinitionParser {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.xml.AbstractBeanDefinitionParser#parseInternal
	 * (org.w3c.dom.Element, org.springframework.beans.factory.xml.ParserContext)
	 */
	@Override
	protected AbstractBeanDefinition parseInternal(Element element, ParserContext context) {
		List<Element> dsChildren = DomUtils.getChildElements(element);
		for (Element child : dsChildren) {
			if (!IConfigurationElements.SITEWHERE_CE_TENANT_NS.equals(child.getNamespaceURI())) {
				NamespaceHandler nested =
						context.getReaderContext().getNamespaceHandlerResolver().resolve(
								child.getNamespaceURI());
				if (nested != null) {
					nested.parse(child, context);
					continue;
				} else {
					throw new RuntimeException("Invalid nested element found in 'datastore' section: "
							+ child.toString());
				}
			}
			Elements type = Elements.getByLocalName(child.getLocalName());
			if (type == null) {
				throw new RuntimeException("Unknown datastore element: " + child.getLocalName());
			}
			switch (type) {
			case MongoTenantDatastore: {
				parseMongoTenantDatasource(child, context);
				break;
			}
			case HBaseTenantDatastore: {
				parseHBaseTenantDatasource(child, context);
				break;
			}
			case EHCacheDeviceManagementCache: {
				parseEHCacheDeviceManagementCache(child, context);
				break;
			}
			case HazelcastCache: {
				parseHazelcastCache(child, context);
				break;
			}
			case DefaultDeviceModelInitializer: {
				parseDefaultDeviceModelInitializer(child, context);
				break;
			}
			case DefaultAssetModelInitializer: {
				parseDefaultAssetModelInitializer(child, context);
				break;
			}
			case DefaultScheduleModelInitializer: {
				parseDefaultScheduleModelInitializer(child, context);
				break;
			}
			}
		}
		return null;
	}

	/**
	 * Add service provider implementations to support MongoDB as tenant datastore..
	 * 
	 * @param element
	 * @param context
	 */
	protected void parseMongoTenantDatasource(Element element, ParserContext context) {

		// Register Mongo device management implementation.
		BeanDefinitionBuilder dm = BeanDefinitionBuilder.rootBeanDefinition(MongoDeviceManagement.class);
		dm.addPropertyReference("mongoClient", "mongo");

		Attr useBulkEventInserts = element.getAttributeNode("useBulkEventInserts");
		if (useBulkEventInserts != null) {
			dm.addPropertyValue("useBulkEventInserts", useBulkEventInserts.getValue());
		}

		Attr bulkInsertMaxChunkSize = element.getAttributeNode("bulkInsertMaxChunkSize");
		if (bulkInsertMaxChunkSize != null) {
			dm.addPropertyValue("bulkInsertMaxChunkSize", bulkInsertMaxChunkSize.getValue());
		}

		context.getRegistry().registerBeanDefinition(SiteWhereServerBeans.BEAN_DEVICE_MANAGEMENT,
				dm.getBeanDefinition());

		// Register Mongo asset management implementation.
		BeanDefinitionBuilder am = BeanDefinitionBuilder.rootBeanDefinition(MongoAssetManagement.class);
		am.addPropertyReference("mongoClient", "mongo");
		context.getRegistry().registerBeanDefinition(SiteWhereServerBeans.BEAN_ASSET_MANAGEMENT,
				am.getBeanDefinition());

		// Register Mongo schedule management implementation.
		BeanDefinitionBuilder sm = BeanDefinitionBuilder.rootBeanDefinition(MongoScheduleManagement.class);
		sm.addPropertyReference("mongoClient", "mongo");
		context.getRegistry().registerBeanDefinition(SiteWhereServerBeans.BEAN_SCHEDULE_MANAGEMENT,
				sm.getBeanDefinition());
	}

	/**
	 * Parse an HBase datasource configuration and create beans needed to realize it.
	 * 
	 * @param element
	 * @param context
	 */
	protected void parseHBaseTenantDatasource(Element element, ParserContext context) {
		// Register HBase device management implementation.
		BeanDefinitionBuilder dm = BeanDefinitionBuilder.rootBeanDefinition(HBaseDeviceManagement.class);
		dm.addPropertyReference("client", "hbase");
		context.getRegistry().registerBeanDefinition(SiteWhereServerBeans.BEAN_DEVICE_MANAGEMENT,
				dm.getBeanDefinition());

		// Register HBase asset management implementation.
		BeanDefinitionBuilder am = BeanDefinitionBuilder.rootBeanDefinition(HBaseAssetManagement.class);
		am.addPropertyReference("client", "hbase");
		context.getRegistry().registerBeanDefinition(SiteWhereServerBeans.BEAN_ASSET_MANAGEMENT,
				am.getBeanDefinition());

		// Register HBase schedule management implementation.
		BeanDefinitionBuilder sm = BeanDefinitionBuilder.rootBeanDefinition(HBaseScheduleManagement.class);
		sm.addPropertyReference("client", "hbase");
		context.getRegistry().registerBeanDefinition(SiteWhereServerBeans.BEAN_SCHEDULE_MANAGEMENT,
				sm.getBeanDefinition());
	}

	/**
	 * Parse configuration for the EHCache device management cache provider.
	 * 
	 * @param element
	 * @param context
	 */
	protected void parseEHCacheDeviceManagementCache(Element element, ParserContext context) {
		BeanDefinitionBuilder cache =
				BeanDefinitionBuilder.rootBeanDefinition(DeviceManagementCacheProvider.class);
		Attr siteCacheMaxEntries = element.getAttributeNode("siteCacheMaxEntries");
		if (siteCacheMaxEntries != null) {
			cache.addPropertyValue("siteCacheMaxEntries", siteCacheMaxEntries.getValue());
		}
		Attr deviceSpecificationCacheMaxEntries =
				element.getAttributeNode("deviceSpecificationCacheMaxEntries");
		if (deviceSpecificationCacheMaxEntries != null) {
			cache.addPropertyValue("deviceSpecificationCacheMaxEntries",
					deviceSpecificationCacheMaxEntries.getValue());
		}
		Attr deviceCacheMaxEntries = element.getAttributeNode("deviceCacheMaxEntries");
		if (deviceCacheMaxEntries != null) {
			cache.addPropertyValue("deviceCacheMaxEntries", deviceCacheMaxEntries.getValue());
		}
		Attr deviceAssignmentCacheMaxEntries = element.getAttributeNode("deviceAssignmentCacheMaxEntries");
		if (deviceAssignmentCacheMaxEntries != null) {
			cache.addPropertyValue("deviceAssignmentCacheMaxEntries",
					deviceAssignmentCacheMaxEntries.getValue());
		}
		Attr siteCacheTtl = element.getAttributeNode("siteCacheTtl");
		if (siteCacheTtl != null) {
			cache.addPropertyValue("siteCacheTtl", siteCacheTtl.getValue());
		}
		Attr deviceSpecificationCacheTtl = element.getAttributeNode("deviceSpecificationCacheTtl");
		if (deviceSpecificationCacheTtl != null) {
			cache.addPropertyValue("deviceSpecificationCacheTtl", deviceSpecificationCacheTtl.getValue());
		}
		Attr deviceCacheTtl = element.getAttributeNode("deviceCacheTtl");
		if (deviceCacheTtl != null) {
			cache.addPropertyValue("deviceCacheTtl", deviceCacheTtl.getValue());
		}
		Attr deviceAssignmentCacheTtl = element.getAttributeNode("deviceAssignmentCacheTtl");
		if (deviceAssignmentCacheTtl != null) {
			cache.addPropertyValue("deviceAssignmentCacheTtl", deviceAssignmentCacheTtl.getValue());
		}
		context.getRegistry().registerBeanDefinition(
				SiteWhereServerBeans.BEAN_DEVICE_MANAGEMENT_CACHE_PROVIDER, cache.getBeanDefinition());
	}

	/**
	 * Parse configuration for Hazelcast distributed cache.
	 * 
	 * @param element
	 * @param context
	 */
	protected void parseHazelcastCache(Element element, ParserContext context) {
		BeanDefinitionBuilder cache =
				BeanDefinitionBuilder.rootBeanDefinition(HazelcastDistributedCacheProvider.class);
		cache.addPropertyReference("configuration",
				SiteWhereHazelcastConfiguration.HAZELCAST_CONFIGURATION_BEAN);
		context.getRegistry().registerBeanDefinition(
				SiteWhereServerBeans.BEAN_DEVICE_MANAGEMENT_CACHE_PROVIDER, cache.getBeanDefinition());
	}

	/**
	 * Parse configuration for default device model initializer.
	 * 
	 * @param element
	 * @param context
	 */
	protected void parseDefaultDeviceModelInitializer(Element element, ParserContext context) {
		BeanDefinitionBuilder init =
				BeanDefinitionBuilder.rootBeanDefinition(DefaultDeviceModelInitializer.class);
		Attr initializeIfNoConsole = element.getAttributeNode("initializeIfNoConsole");
		if ((initializeIfNoConsole == null) || ("true".equals(initializeIfNoConsole.getValue()))) {
			init.addPropertyValue("initializeIfNoConsole", "true");
		}
		context.getRegistry().registerBeanDefinition(SiteWhereServerBeans.BEAN_DEVICE_MODEL_INITIALIZER,
				init.getBeanDefinition());
	}

	/**
	 * Parse configuration for default asset model initializer.
	 * 
	 * @param element
	 * @param context
	 */
	protected void parseDefaultAssetModelInitializer(Element element, ParserContext context) {
		BeanDefinitionBuilder init =
				BeanDefinitionBuilder.rootBeanDefinition(DefaultAssetModuleInitializer.class);
		Attr initializeIfNoConsole = element.getAttributeNode("initializeIfNoConsole");
		if ((initializeIfNoConsole == null) || ("true".equals(initializeIfNoConsole.getValue()))) {
			init.addPropertyValue("initializeIfNoConsole", "true");
		}
		context.getRegistry().registerBeanDefinition(SiteWhereServerBeans.BEAN_ASSET_MODEL_INITIALIZER,
				init.getBeanDefinition());
	}

	/**
	 * Parse configuration for default schedule model initializer.
	 * 
	 * @param element
	 * @param context
	 */
	protected void parseDefaultScheduleModelInitializer(Element element, ParserContext context) {
		BeanDefinitionBuilder init =
				BeanDefinitionBuilder.rootBeanDefinition(DefaultScheduleModelInitializer.class);
		Attr initializeIfNoConsole = element.getAttributeNode("initializeIfNoConsole");
		if ((initializeIfNoConsole == null) || ("true".equals(initializeIfNoConsole.getValue()))) {
			init.addPropertyValue("initializeIfNoConsole", "true");
		}
		context.getRegistry().registerBeanDefinition(SiteWhereServerBeans.BEAN_SCHEDULE_MODEL_INITIALIZER,
				init.getBeanDefinition());
	}

	/**
	 * Expected child elements.
	 * 
	 * @author Derek
	 */
	public static enum Elements {

		/** Mongo tenant datastore service providers */
		MongoTenantDatastore("mongo-tenant-datastore"),

		/** HBase tenant datastore service providers */
		HBaseTenantDatastore("hbase-tenant-datastore"),

		/** EHCache device mananagement cache provider */
		EHCacheDeviceManagementCache("ehcache-device-management-cache"),

		/** Hazelcast cache provider */
		HazelcastCache("hazelcast-cache"),

		/** Creates sample data if no device data is present */
		DefaultDeviceModelInitializer("default-device-model-initializer"),

		/** Creates sample data if no asset data is present */
		DefaultAssetModelInitializer("default-asset-model-initializer"),

		/** Creates sample data if no schedule data is present */
		DefaultScheduleModelInitializer("default-schedule-model-initializer");

		/** Event code */
		private String localName;

		private Elements(String localName) {
			this.localName = localName;
		}

		public static Elements getByLocalName(String localName) {
			for (Elements value : Elements.values()) {
				if (value.getLocalName().equals(localName)) {
					return value;
				}
			}
			return null;
		}

		public String getLocalName() {
			return localName;
		}

		public void setLocalName(String localName) {
			this.localName = localName;
		}
	}
}