package com.nexiilabs.autolifecycle.jira;

import java.util.List;

import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.RestClient;
import net.sf.json.JSONObject;

public class AvatarModel extends AgileResource {
	public AvatarModel(RestClient restclient, JSONObject json) throws JiraException {
		super(restclient, json);
	}
	private String avatarid;

	public String getAvatarid() {
		return avatarid;
	}

	public void setAvatarid(String avatarid) {
		this.avatarid = avatarid;
	}

	@Override
	void deserialize(JSONObject json) throws JiraException {
		super.deserialize(json);
		//System.err.println( Field.getInteger(json.get("id")));
		this.avatarid = Field.getString(json.get("id"));
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AvatarModel [avatarid=");
		builder.append(avatarid);
		builder.append("]");
		return builder.toString();
	}
	public static List<AvatarModel> getAvatarIdsForProject(RestClient restclient) throws JiraException {
		return AgileResource.list(restclient,AvatarModel.class ,"/rest/api/2/avatar/project/system","system");
	}
}
