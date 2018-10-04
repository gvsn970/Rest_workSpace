/**
 * jira-client - a simple JIRA REST client
 * Copyright (c) 2013 Bob Carroll (bob.carroll@alum.rit.edu)
 * <p>
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * <p>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package com.nexiilabs.autolifecycle.jira;

import java.util.List;

import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.RestClient;
import net.sf.json.JSONObject;

/**
 * Represents an Agile Priority.
 *
 * @author pldupont
 */

public class Priority extends AgileResource {
	
	 private String description;
	 private String priorityId;

    /**
     * Creates a new Agile resource.
     *
     * @param restclient REST client instance
     * @param json       JSON payload
     */
    public Priority(RestClient restclient, JSONObject json) throws JiraException {
        super(restclient, json);
    }
    /**
     * Deserialize the json to extract standard attributes and keep a reference of
     * other attributes.
     *
     * @param json The JSON object to read.
     */
    @Override
    void deserialize(JSONObject json) throws JiraException {
        super.deserialize(json);
        this.description = Field.getString(json.get("description"));
        this.priorityId=Field.getString(json.get("id"));
    }
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPriorityId() {
		return priorityId;
	}
	public void setPriorityId(String priorityId) {
		this.priorityId = priorityId;
	}
	public static List<Priority> getPriorityList(RestClient restclient) throws JiraException {
		return AgileResource.listOfArrays(restclient, Priority.class, "/rest/api/2/priority","priorities");
	}
}
