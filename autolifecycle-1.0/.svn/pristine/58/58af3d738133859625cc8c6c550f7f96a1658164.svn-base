package com.nexiilabs.autolifecycle.jira;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.RestClient;
import net.sf.json.JSONObject;

@Configuration
public class BeanDeclaration {

	@Bean
	public JiraClient getJiraClientBean(String uri) {
		return new JiraClient(uri);
	}
	
	@Bean
	public String getStringBean() {
		return new String();
	}
	
	@Bean
	public RestClient getRestClient() {
		return new RestClient(null, null);
	}
	
	@Bean
	public JSONObject getJSONObject() {
		return new JSONObject();
	}
	
	@Bean
	public Board getBoard() throws JiraException{
		return new Board(null,null);

	}
	

	
	@Bean
	public Sprint getSprint() throws JiraException{
		return new Sprint(null,null);

	}
}
