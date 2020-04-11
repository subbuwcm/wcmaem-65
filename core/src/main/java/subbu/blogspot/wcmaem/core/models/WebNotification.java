/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package subbu.blogspot.wcmaem.core.models;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;

@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class WebNotification {

    
    @SlingObject
    private Resource currentResource;
   
    private String notificationTitle;
    private String notificationText;
    
    @PostConstruct
    protected void init() {
    	 InheritanceValueMap inheritedProp = new HierarchyNodeInheritanceValueMap(currentResource);
    	 notificationTitle = inheritedProp.getInherited("notificationTitle", String.class);
    	 notificationText = inheritedProp.getInherited("notificationText", String.class);
    }

	public String getNotificationTitle() {
		return notificationTitle;
	}

	public String getNotificationText() {
		return notificationText;
	}
    
}
