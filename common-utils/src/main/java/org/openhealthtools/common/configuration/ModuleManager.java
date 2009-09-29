/**
 *  Copyright (c) 2009 Misys Open Source Solutions (MOSS) and others
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 *
 *  Contributors:
 *    Misys Open Source Solutions - initial API and implementation
 *
 */
package org.openhealthtools.common.configuration;

import org.apache.log4j.Logger;
import org.openhealthtools.openxds.registry.api.XdsRegistryLifeCycleService;
import org.openhealthtools.openxds.registry.api.XdsRegistryPatientService;
import org.openhealthtools.openxds.registry.api.XdsRegistryQueryService;
import org.openhealthtools.openxds.repository.api.XdsRepositoryItem;
import org.openhealthtools.openxds.repository.api.XdsRepositoryService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This class manages each module Spring initialization.
 *
 * @author <a href="mailto:wenzhi.li@misys.com">Wenzhi Li</a>
 *
 */
public class ModuleManager {
	
	private static final Logger LOGGER = Logger.getLogger(ModuleManager.class);

	private static final ModuleManager SINGLETON = new ModuleManager();

	private ConfigurableApplicationContext applicationContext;

	private ModuleManager() {
		super();
		initializeSpring();
	}

	/**
	 * Singleton 
	 *
	 * @return the Singleton of ModuleManager
	 */
	public static ModuleManager getInstance() {
		return SINGLETON;
	}

	private void initializeSpring() {
		try {
		this.applicationContext = new ClassPathXmlApplicationContext(getConfigLocations());
		
		//add a shutdown hook for the above context... 
		this.applicationContext.registerShutdownHook();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String[] getConfigLocations() {
        return new String[] {
                "classpath*:/applicationContext-resources.xml",
//                "classpath:/applicationContext-dao.xml",
                "classpath*:/applicationContext.xml", // for modular projects
//                "classpath*:/applicationContext-service.xml",
//               "classpath*:/applicationContext-dao.xml",// for web projects
                "classpath*:/repository.cfg.xml",// for openxds repository modular projects
//                "classpath*:/externalidentifier.cfg.xml" // for openxds-registry-adapter-omar projects.
            };
    }

	/**
	 * Gets spring beans object of the given bean name.
	 * 
	 * @param beanName the bean name
	 * @return the bean object
	 */
	public Object getBean(String beanName) {
		return this.applicationContext.getBean(beanName);
	}

	/**
	 * The factory method to get {@link XdsRegistryLifeCycleService}
	 * 
	 * @return the singleton {@link XdsRegistryLifeCycleService} instance
	 */
	public static XdsRegistryLifeCycleService getXdsRegistryLifeCycleService() {
		return (XdsRegistryLifeCycleService)getInstance().getBean("registryLifeCycleService");
	}

	/**
	 * The factory method to get {@link XdsRegistryQueryService}
	 * 
	 * @return the singleton {@link XdsRegistryQueryService} instance
	 */
	public static XdsRegistryQueryService getXdsRegistryQueryService() {
		return(XdsRegistryQueryService)getInstance().getBean("registryQueryService");
	}
	
	/**
	 * The factory method to get {@link XdsRegistryPatientService}
	 * 
	 * @return the singleton {@link XdsRegistryPatientService} instance
	 */
	public static XdsRegistryPatientService getXdsRegistryPatientService() {
		return (XdsRegistryPatientService)getInstance().getBean("registryPatientService");
	}

	/**
	 * The factory method to get {@link XdsRepositoryService}
	 * 
	 * @return the singleton {@link XdsRepositoryService} instance
	 */
	public static XdsRepositoryService getXdsRepositoryService() {
		return (XdsRepositoryService)getInstance().getBean("repositoryService");		
	}

	/**
	 * The factory method to get {@link XdsRepositoryItem}. RepositoryItem is non-singleton 
	 * bean.
	 * 
	 * @return an {@link XdsRepositoryItem} instance
	 */
	public static XdsRepositoryItem getXdsReposiotryItem() {
		return (XdsRepositoryItem)getInstance().getBean("repositoryItem");
	}

}