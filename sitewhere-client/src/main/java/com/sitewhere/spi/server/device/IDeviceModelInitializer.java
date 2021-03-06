/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.spi.server.device;

import com.sitewhere.spi.SiteWhereException;
import com.sitewhere.spi.asset.IAssetModuleManager;
import com.sitewhere.spi.device.IDeviceManagement;
import com.sitewhere.spi.server.IModelInitializer;

/**
 * Class that initializes the device model with data needed to bootstrap the system.
 * 
 * @author Derek
 */
public interface IDeviceModelInitializer extends IModelInitializer {

	/**
	 * Initialize the device model.
	 * 
	 * @param deviceManagement
	 * @param assetModuleManager
	 * @throws SiteWhereException
	 */
	public void initialize(IDeviceManagement deviceManagement, IAssetModuleManager assetModuleManager)
			throws SiteWhereException;
}