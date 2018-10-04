package com.nexiilabs.autolifecycle.jira;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.nexiilabs.autolifecycle.features.FeaturesModel;
import com.nexiilabs.autolifecycle.product.Product;
import com.nexiilabs.autolifecycle.productline.Response;
import com.nexiilabs.autolifecycle.releases.ReleaseListModel;
import com.nexiilabs.autolifecycle.releases.ReleaseModel;
import com.nexiilabs.autolifecycle.releases.ResponseDTO;

@Transactional
@Repository
public class JiraRepositoryImpl implements JiraRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public JiraConfigurationModel getJiraCredentialsByUserId(int fk_user_id) {

		JiraConfigurationModel model = null;
		try {
			String hql = "Select jc.jira_configuration_id,jc.application_url,jc.user_name,jc.password from jira_configurations jc"
					+ " where jc.fk_user_id=" + fk_user_id + " and jc.delete_status=0;";
			List<Object> list = entityManager.createNativeQuery(hql).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();
					model = new JiraConfigurationModel();
					model.setJira_configuration_id(Integer.parseInt((String.valueOf(obj[0]))));
					model.setApplication_url((String.valueOf(obj[1])));
					model.setUser_name((String.valueOf(obj[2])));
					model.setPassword((String.valueOf(obj[3])));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@Override
	public Response addJiraCredentials(String username, String password, String url, int fk_user_id) {
		JiraConfigurationModel model = new JiraConfigurationModel();
		Response response = new Response();

		try {
			// model = new JiraConfigurationModel();
			model.setUser_name(username);
			model.setPassword(password);
			model.setApplication_url(url);
			model.setFk_user_id(fk_user_id);
			boolean isUserNotExist = isUserNotExist(fk_user_id);
			if (isUserNotExist) {
				entityManager.persist(model);
				if (model.getJira_configuration_id() != 0) {
					response.setStatusCode(1);
					response.setMessage("Jira Credentials added Succesfully.");
				} else {
					response.setStatusCode(0);
					response.setMessage("Jira Credentials insertion failed.");
				}
			} else {
				response.setStatusCode(0);
				response.setMessage("Jira Credentials already exist with this User Id:" + fk_user_id);
			}
		} catch (Exception e) {
			response.setStatusCode(0);
			response.setMessage(e.getMessage());

		}

		return response;
	}

	public boolean isUserNotExist(int fk_user_id) {
		String hql = "SELECT * FROM jira_configurations where fk_user_id=" + fk_user_id + " and delete_status=0;";
		boolean empty = entityManager.createNativeQuery(hql).getResultList().isEmpty();
		if (empty) {
			return true;
		}

		return false;
	}

	@Override
	public List<JiraConfigurationModel> getJiraDetails(int fk_user_id) {
		List<JiraConfigurationModel> jiralist = new ArrayList<>();
		JiraConfigurationModel model = null;
		try {
			String hql = "Select jc.jira_configuration_id,jc.application_url,jc.user_name,jc.password from jira_configurations jc"
					+ " where jc.fk_user_id=" + fk_user_id + " and jc.delete_status=0;";
			List<Object> list = entityManager.createNativeQuery(hql).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();
					model = new JiraConfigurationModel();
					model.setJira_configuration_id(Integer.parseInt((String.valueOf(obj[0]))));
					model.setApplication_url((String.valueOf(obj[1])));
					model.setUser_name((String.valueOf(obj[2])));
					model.setPassword((String.valueOf(obj[3])));
					jiralist.add(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return jiralist;
	}

	@Override
	public Response updateJiraCredentials(String username, String password, String url, int fk_user_id,
			int jira_configuration_id) {
		Response response = new Response();
		boolean nochangesFound = nochangesFound(jira_configuration_id, username, password, url, fk_user_id);
		if (nochangesFound) {
			String hql = "UPDATE jira_configurations jc set jc.application_url='" + url + "',jc.user_name='" + username
					+ "'," + "jc.password='" + password + "',jc.updated_on=CURRENT_TIMESTAMP where "
					+ " jc.fk_user_id= " + fk_user_id + " and jc.delete_status=0 and jc.jira_configuration_id="
					+ jira_configuration_id + ";";
			int isupdated = entityManager.createNativeQuery(hql).executeUpdate();
			if (isupdated > 0) {
				response.setStatusCode(1);
				response.setMessage("Jira Credentials Updated Successfully.");

			} else {
				response.setStatusCode(0);
				response.setMessage("Jira Credentials Updation Failed.");
			}
		} else {
			response.setStatusCode(0);
			response.setMessage("No Changes Found.");
		}
		return response;
	}

	private boolean nochangesFound(int jira_configuration_id, String username, String password, String url,
			int fk_user_id) {
		String hql = "Select * from jira_configurations jc " + "	 where jc.fk_user_id=" + fk_user_id
				+ " and jc.delete_status=0 and jc.jira_configuration_id=" + jira_configuration_id + ""
				+ " and jc.application_url='" + url + "' and jc.user_name='" + username + "' and jc.password='"
				+ password + "';";
		List credentiallist = entityManager.createNativeQuery(hql).getResultList();
		if (!credentiallist.isEmpty()) {
			return false;
		}
		return true;
	}

	private int isAlreadyExistenceCheckofBacklogsOfBoardsInDatabase(ImportBacklogModel model, long boardId,
			long issueid, int fk_feature_id, String boardName, String projectName, String projectId) {

		String hql = "SELECT jm.feature_jirametadata_id FROM feature_jira_metadata jm where jm.board_id=" + boardId
				+ " and " + "jm.project_id='" + projectId + "' and jm.issueid=" + issueid
				+ " and jm.sprint_id=0 and jm.epic_id=0 and jm.delete_status=0 and jm.project_name='" + projectName
				+ "';";
		List<Object> list = entityManager.createNativeQuery(hql).getResultList();

		if (list.isEmpty()) {
			return 1;
		} else {
			/*
			 * Iterator iterator = list.iterator(); while (iterator.hasNext()) { Object obj
			 * = (Object) iterator.next(); int jirametadataId =
			 * Integer.parseInt(String.valueOf(obj)); String hql1 =
			 * "SELECT jm.feature_jirametadata_id FROM feature_jira_metadata jm WHERE " +
			 * "  jm.board_id = " + boardId + " AND jm.fk_feature_id=" + fk_feature_id +
			 * "  AND jm.issueid = " + issueid + " AND jm.board_name='" + boardName + "' " +
			 * "  AND jm.issue_assignee_email='" + model.getIssueAssigneeEmail() +
			 * "'  AND jm.issue_assignee_name='" + model.getIssueAssigneeName() + "' " +
			 * "  AND jm.issue_creater_email='" + model.getIssueCreaterEmail() +
			 * "'  AND jm.issue_creater_name='" + model.getIssueCreaterName() + "' " +
			 * "  AND jm.issue_description='" + model.getIssueDescription() +
			 * "' AND jm.issue_name='" + model.getIssueName() + "' " +
			 * "  AND jm.issue_key='" + model.getIssueKey() + "'  AND jm.issue_type_name='"
			 * + model.getIssueTypeName() + "' " + "  AND jm.issue_type_description='" +
			 * model.getIssueTypeDescription() + "'  AND jm.sprint_id = 0 " +
			 * "  AND jm.epic_id = 0  AND jm.delete_status = 0;";
			 * 
			 * List<Object> listofDetails =
			 * entityManager.createNativeQuery(hql1).getResultList(); if
			 * (listofDetails.isEmpty()) { String hql2 =
			 * "UPDATE feature_jira_metadata jm SET " + "  jm.board_name = '" + boardName +
			 * "',jm.issue_name = '" + model.getIssueName() + "'," + "  jm.issue_key = '" +
			 * model.getIssueKey() + "',jm.issue_description = '" +
			 * model.getIssueDescription() + "'," + "  jm.issue_assignee_name = '" +
			 * model.getIssueAssigneeName() + "'," + "  jm.issue_assignee_email = '" +
			 * model.getIssueAssigneeEmail() + "'," + "  jm.issue_creater_email = '" +
			 * model.getIssueCreaterEmail() + "',jm.issue_creater_name = '" +
			 * model.getIssueCreaterName() + "'," + "  jm.issue_type_name = '" +
			 * model.getIssueTypeName() + "',jm.issue_type_description = '" +
			 * model.getIssueTypeDescription() + "' " + "  WHERE jm.board_id =" + boardId +
			 * " AND jm.issueid = " + issueid + " AND jm.fk_feature_id=" + fk_feature_id +
			 * "  AND jm.sprint_id = 0 AND jm.epic_id = 0 " +
			 * "  AND jm.delete_status = 0 and jm.feature_jirametadata_id=" + jirametadataId
			 * + ";"; int isUpdate = entityManager.createNativeQuery(hql2).executeUpdate();
			 * if (isUpdate > 0) { return 3; }
			 * 
			 * }
			 * 
			 * }
			 */
			return 2;
		}

	}

	private int isAlreadyExistenceCheckofIssuesOfSprintInDatabase(ImportBacklogModel model, long boardId,
			String boardName, long issueid, String sprintName, long fk_feature_id, long sprintId) {

		String hql = "SELECT jm.feature_jirametadata_id FROM feature_jira_metadata jm " + " where jm.board_id="
				+ boardId + " and jm.issueid=" + issueid + " " + " and jm.fk_feature_id=" + fk_feature_id
				+ " and jm.sprint_id=" + sprintId + " and" + " jm.epic_id=0 and jm.delete_status=0;";

		List<Object> list = entityManager.createNativeQuery(hql).getResultList();
		System.out.println("jm ids of Sprint issues: " + list.toString());
		if (list.isEmpty()) {
			System.out.println("Inside**********jm ids of Sprint issues: " + list.toString());
			return 1;
		} else {
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				Object obj = (Object) iterator.next();
				int jirametadataId = Integer.parseInt(String.valueOf(obj));
				String hql1 = "SELECT jm.feature_jirametadata_id FROM feature_jira_metadata jm WHERE "
						+ "  jm.board_id = " + boardId + " AND jm.fk_feature_id=" + fk_feature_id
						+ "  AND jm.issueid = " + issueid + " AND jm.board_name='" + boardName + "' "
						+ "  AND jm.issue_assignee_email='" + model.getIssueAssigneeEmail()
						+ "'  AND jm.issue_assignee_name='" + model.getIssueAssigneeName() + "' "
						+ "  AND jm.issue_creater_email='" + model.getIssueCreaterEmail()
						+ "'  AND jm.issue_creater_name='" + model.getIssueCreaterName() + "' "
						+ "  AND jm.issue_description='" + model.getIssueDescription() + "' AND jm.issue_name='"
						+ model.getIssueName() + "' " + "  AND jm.issue_key='" + model.getIssueKey()
						+ "'  AND jm.issue_type_name='" + model.getIssueTypeName() + "' "
						+ "  AND jm.issue_type_description='" + model.getIssueTypeDescription()
						+ "'  AND jm.sprint_id = " + sprintId + " AND jm.sprint_name ='" + sprintName
						+ "'  AND jm.epic_id = 0  AND jm.delete_status = 0;";

				List<Object> listofDetails = entityManager.createNativeQuery(hql1).getResultList();
				if (listofDetails.isEmpty()) {
					String hql2 = "UPDATE feature_jira_metadata jm SET " + "  jm.board_name = '" + boardName
							+ "',jm.issue_name = '" + model.getIssueName() + "'," + "  jm.issue_key = '"
							+ model.getIssueKey() + "',jm.issue_description = '" + model.getIssueDescription() + "',"
							+ "  jm.sprint_name = '" + sprintName + "'," + "  jm.issue_assignee_name = '"
							+ model.getIssueAssigneeName() + "'," + "  jm.issue_assignee_email = '"
							+ model.getIssueAssigneeEmail() + "'," + "  jm.issue_creater_email = '"
							+ model.getIssueCreaterEmail() + "',jm.issue_creater_name = '" + model.getIssueCreaterName()
							+ "'," + "  jm.issue_type_name = '" + model.getIssueTypeName()
							+ "',jm.issue_type_description = '" + model.getIssueTypeDescription() + "' "
							+ "  WHERE jm.board_id =" + boardId + " AND jm.issueid = " + issueid
							+ " AND jm.fk_feature_id=" + fk_feature_id + "  AND jm.sprint_id =" + sprintId
							+ " AND jm.epic_id = 0 " + "  AND jm.delete_status = 0 and jm.feature_jirametadata_id="
							+ jirametadataId + ";";
					int isUpdate = entityManager.createNativeQuery(hql2).executeUpdate();
					if (isUpdate > 0) {
						return 3;
					}

				}

			}
			return 2;
		}
	}

	@Override
	public List<Response> addIssuesOfEpic(List<ImportBacklogModel> backLogsModel, long epicId, String epicName,
			String epicKey, long boardId, String boardName) {
		List<FeatureJiraMetaDataModel> list = new ArrayList<>();
		FeatureJiraMetaDataModel featureJiraMetaDataModel;
		// BackLogsModel backLogs;
		// List<FeaturesModel> featuresModel=new ArrayList<>();
		FeaturesModel featuresModel;
		List<Response> listResponse = new ArrayList<>();
		Response response;
		try {

			int i = 0;
			for (ImportBacklogModel model : backLogsModel) {
				featuresModel = new FeaturesModel();
				featuresModel.setFeature_name(model.getIssueName());
				featuresModel.setFeature_description(model.getIssueDescription());
				// featuresModel.setFk_product_id(product_id);

				// if(model.get)
				// featuresModel.setFk_release_id(0);
				// featuresModel.setIs_backlog(1);
				featuresModel.setStory_points(0);

				int getFeatureTypeId = getFeatureTypeIdbyIssueType(model.getIssueTypeId());
				System.out.println("getFeatureTypeId:*******" + getFeatureTypeId);
				featuresModel.setFk_feature_type_id(getFeatureTypeId);

				int getFeatureStatusId = 0;
				if (model.getIssueStatusId() != 3) {
					getFeatureStatusId = getFeatureStatusIdbyIssueStatus(model.getIssueStatusId());
					featuresModel.setFk_feature_status_id(getFeatureStatusId);
				} else {
					featuresModel.setFk_feature_status_id(2);
				}

				int getUserId = 0;
				if (model.getIssueCreaterEmail() != null) {
					getUserId = getUserIdbyJiraEmail(model.getIssueCreaterEmail());
					featuresModel.setCreated_by(getUserId);
				} else {
					featuresModel.setCreated_by(getUserId);
				}

				int getAssigneUserId = 0;
				if (model.getIssueAssigneeEmail() != null) {
					getAssigneUserId = getUserIdbyJiraEmail(model.getIssueAssigneeEmail());
					featuresModel.setAssigned_To(getAssigneUserId);
				} else {
					featuresModel.setAssigned_To(getAssigneUserId);
				}
				// featuresModel.setCreated_by(created_by);
				// featuresModel.setFk_assignment_id(fk_assignment_id);
				// featuresModel.setFk_feature_status_id(fk_feature_status_id);
				// featuresModel.setFk_feature_type_id(fk_feature_type_id);
				// featuresModel.setFk_release_id(fk_release_id);
				// featuresModel.setStory_points(story_points);
				// entityManager.getTransaction().begin();
				entityManager.persist(featuresModel);
				System.out.println("feature id:" + featuresModel.getFeature_id());
				if (featuresModel.getFeature_id() > 0) {

					int isNotExist = isAlreadyExistenceCheckofIssuesOfEpicInDatabase(model, boardId, boardName,
							epicName, epicKey, featuresModel.getFeature_id(), model.getIssueid(), epicId);
					if (isNotExist == 1) {
						featureJiraMetaDataModel = new FeatureJiraMetaDataModel();
						featureJiraMetaDataModel.setFk_feature_id(featuresModel.getFeature_id());
						featureJiraMetaDataModel.setBoardId(boardId);
						featureJiraMetaDataModel.setBoardName(boardName);
						featureJiraMetaDataModel.setEpicId(epicId);
						featureJiraMetaDataModel.setEpicKey(epicKey);
						featureJiraMetaDataModel.setEpicName(epicName);
						featureJiraMetaDataModel.setIssueid(model.getIssueid());
						featureJiraMetaDataModel.setIssueKey(model.getIssueKey());
						featureJiraMetaDataModel.setIssueName(model.getIssueName());
						featureJiraMetaDataModel.setIssueCreatedon(model.getIssueCreatedon());
						featureJiraMetaDataModel.setIssueDescription(model.getIssueDescription());
						featureJiraMetaDataModel.setIssueTypeName(model.getIssueTypeName());
						featureJiraMetaDataModel.setIssueTypeDescription(model.getIssueTypeDescription());
						featureJiraMetaDataModel.setIssueCreaterName(model.getIssueCreaterName());
						featureJiraMetaDataModel.setIssueCreaterEmail(model.getIssueCreaterEmail());
						featureJiraMetaDataModel.setIssueAssigneeName(model.getIssueAssigneeName());
						featureJiraMetaDataModel.setIssueAssigneeEmail(model.getIssueAssigneeEmail());

						entityManager.persist(featureJiraMetaDataModel);
						list.add(featureJiraMetaDataModel);

						if (list.get(i).getFeature_jirametadata_id() > 0) {
							response = new Response();
							response.setStatusCode(1);
							response.setMessage("Issues of Epic with this BoardId:" + boardId + ",EpicId:" + epicId
									+ ",Epic key:" + epicKey + " ,IssueId:" + model.getIssueid() + " and IssueKey:"
									+ model.getIssueKey() + " imported to Database Successfully.");
							listResponse.add(response);
							i++;
						}

						else {
							response = new Response();
							response.setStatusCode(0);
							response.setMessage("Failed to import due to Database Problem.");
							listResponse.add(response);
						}

					} else if (isNotExist == 2) {
						response = new Response();
						response.setStatusCode(0);
						response.setMessage("Issue of Epic with this BoardId:" + boardId + " ,EpicId:" + epicId
								+ " ,Epic key:" + epicKey + " ,IssueId:" + model.getIssueid() + " and IssueKey:"
								+ model.getIssueKey() + " already exist in database.");
						listResponse.add(response);
					} else if (isNotExist == 3) {
						response = new Response();
						response.setStatusCode(0);
						response.setMessage("Changes Found.Backlog of Board with this BoardId:" + boardId
								+ " , IssueId:" + model.getIssueid() + " and IssueKey:" + model.getIssueKey()
								+ " updated in database.");
						listResponse.add(response);

					}
				} else {

				}
			}
		} catch (Exception e) {
			response = new Response();
			response.setMessage(e.getMessage());
			listResponse.add(response);
		}
		return listResponse;
	}

	private int isAlreadyExistenceCheckofIssuesOfEpicInDatabase(ImportBacklogModel model, long boardId,
			String boardName, String epicName, String epicKey, long fk_feature_id, long issueid, long epicId) {
		String hql = "SELECT jm.feature_jirametadata_id FROM feature_jira_metadata jm where jm.board_id=" + boardId
				+ " and " + "jm.issueid=" + issueid + " and jm.sprint_id=0 and jm.epic_id=" + epicId
				+ "  and jm.delete_status=0 and jm.fk_feature_id=" + fk_feature_id + ";";

		List<Object> list = entityManager.createNativeQuery(hql).getResultList();
		System.out.println("******Before list** ");

		System.out.println("list:" + list.toString());
		if (list.isEmpty()) {
			System.out.println("****after list****" + list.toString());
			return 1;
		} else {
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				Object obj = (Object) iterator.next();
				int jirametadataId = Integer.parseInt(String.valueOf(obj));
				String hql1 = "SELECT jm.feature_jirametadata_id FROM feature_jira_metadata jm WHERE "
						+ "  jm.board_id = " + boardId + " AND jm.fk_feature_id=" + fk_feature_id
						+ "  AND jm.issueid = " + issueid + " AND jm.board_name='" + boardName + "' "
						+ "  AND jm.issue_assignee_email='" + model.getIssueAssigneeEmail()
						+ "'  AND jm.issue_assignee_name='" + model.getIssueAssigneeName() + "' "
						+ "  AND jm.issue_creater_email='" + model.getIssueCreaterEmail()
						+ "'  AND jm.issue_creater_name='" + model.getIssueCreaterName() + "' "
						+ "  AND jm.issue_description='" + model.getIssueDescription() + "' AND jm.issue_name='"
						+ model.getIssueName() + "' " + "  AND jm.issue_key='" + model.getIssueKey()
						+ "'  AND jm.issue_type_name='" + model.getIssueTypeName() + "' "
						+ "  AND jm.issue_type_description='" + model.getIssueTypeDescription()
						+ "'  AND jm.sprint_id = 0 " + "  AND jm.epic_id =" + epicId + " AND jm.epic_name='" + epicName
						+ "' AND jm.epic_key='" + epicKey + "'  AND jm.delete_status = 0;";

				List<Object> listofDetails = entityManager.createNativeQuery(hql1).getResultList();
				if (listofDetails.isEmpty()) {
					String hql2 = "UPDATE feature_jira_metadata jm SET " + "  jm.board_name = '" + boardName
							+ "',jm.issue_name = '" + model.getIssueName() + "'," + "  jm.issue_key = '"
							+ model.getIssueKey() + "',jm.issue_description = '" + model.getIssueDescription() + "',"
							+ "  jm.issue_assignee_name = '" + model.getIssueAssigneeName() + "',"
							+ "  jm.issue_assignee_email = '" + model.getIssueAssigneeEmail() + "',"
							+ "  jm.issue_creater_email = '" + model.getIssueCreaterEmail()
							+ "',jm.issue_creater_name = '" + model.getIssueCreaterName() + "',"
							+ "  jm.issue_type_name = '" + model.getIssueTypeName() + "',jm.issue_type_description = '"
							+ model.getIssueTypeDescription() + "' " + "  WHERE jm.board_id =" + boardId
							+ " AND jm.issueid = " + issueid + " AND jm.fk_feature_id=" + fk_feature_id
							+ "  AND jm.sprint_id = 0 AND jm.epic_id = " + epicId + "  AND jm.epic_name='" + epicName
							+ "' " + "  AND jm.delete_status = 0 AND jm.epic_key='" + epicKey
							+ "'  AND jm.feature_jirametadata_id=" + jirametadataId + ";";
					int isUpdate = entityManager.createNativeQuery(hql2).executeUpdate();
					if (isUpdate > 0) {
						return 3;
					}

				}

			}
			return 2;
		}
	}

	@Override
	public List<FeatureJiraMetaDataModel> listOfBacklogsofBoard(int fk_feature_id, long boardId) {
		List<FeatureJiraMetaDataModel> list = new ArrayList<>();
		FeatureJiraMetaDataModel model = null;
		try {
			String hql = "SELECT jm.feature_jirametadata_id,jm.board_id,jm.board_name,jm.issueid,"
					+ "  jm.issue_key,jm.issue_name,jm.issue_description,jm.issue_assignee_name,"
					+ "  jm.issue_assignee_email,jm.issue_createdon,jm.issue_creater_name,"
					+ "  jm.issue_creater_email,jm.issue_type_name,"
					+ "  jm.issue_type_description FROM feature_jira_metadata jm where jm.board_id=" + boardId
					+ " and jm.fk_feature_id=" + fk_feature_id + ""
					+ " and jm.epic_id=0 and jm.sprint_id=0 and jm.delete_status=0;";
			List<Object> listofDetails = entityManager.createNativeQuery(hql).getResultList();
			if (!listofDetails.isEmpty()) {
				Iterator iterator = listofDetails.iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();
					model = new FeatureJiraMetaDataModel();
					model.setFeature_jirametadata_id(Integer.parseInt(String.valueOf(obj[0])));
					model.setBoardId(Long.parseLong(String.valueOf(obj[1])));
					model.setBoardName(String.valueOf(obj[2]));
					model.setIssueid(Long.parseLong(String.valueOf(obj[3])));
					model.setIssueKey(String.valueOf(obj[4]));
					model.setIssueName(String.valueOf(obj[5]));
					model.setIssueDescription(String.valueOf(obj[6]));
					model.setIssueAssigneeName(String.valueOf(obj[7]));
					model.setIssueAssigneeEmail(String.valueOf(obj[8]));
					model.setIssueCreatedon((Date) obj[9]);
					model.setIssueCreaterName(String.valueOf(obj[10]));
					model.setIssueCreaterEmail(String.valueOf(obj[11]));
					model.setIssueTypeName(String.valueOf(obj[12]));
					model.setIssueTypeDescription(String.valueOf(obj[13]));
					list.add(model);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	@Override
	public List<FeatureJiraMetaDataModel> listOfImportedIssuesofSprint(int fk_feature_id, long boardId, long sprintId) {
		List<FeatureJiraMetaDataModel> list = new ArrayList<>();
		FeatureJiraMetaDataModel model = null;
		try {
			String hql = "SELECT jm.feature_jirametadata_id,jm.board_id,jm.board_name,jm.issueid,"
					+ "  jm.issue_key,jm.issue_name,jm.issue_description,jm.issue_assignee_name,"
					+ "  jm.issue_assignee_email,jm.issue_createdon,jm.issue_creater_name,"
					+ "  jm.issue_creater_email,jm.issue_type_name,"
					+ "  jm.issue_type_description,jm.sprint_name FROM feature_jira_metadata jm where jm.board_id="
					+ boardId + " and jm.fk_feature_id=" + fk_feature_id + "" + " and jm.epic_id=0 and jm.sprint_id="
					+ sprintId + " and jm.delete_status=0;";
			List<Object> listofDetails = entityManager.createNativeQuery(hql).getResultList();
			if (!listofDetails.isEmpty()) {
				Iterator iterator = listofDetails.iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();
					model = new FeatureJiraMetaDataModel();
					model.setFeature_jirametadata_id(Integer.parseInt(String.valueOf(obj[0])));
					model.setBoardId(Long.parseLong(String.valueOf(obj[1])));
					model.setBoardName(String.valueOf(obj[2]));
					model.setIssueid(Long.parseLong(String.valueOf(obj[3])));
					model.setIssueKey(String.valueOf(obj[4]));
					model.setIssueName(String.valueOf(obj[5]));
					model.setIssueDescription(String.valueOf(obj[6]));
					model.setIssueAssigneeName(String.valueOf(obj[7]));
					model.setIssueAssigneeEmail(String.valueOf(obj[8]));
					model.setIssueCreatedon((Date) obj[9]);
					model.setIssueCreaterName(String.valueOf(obj[10]));
					model.setIssueCreaterEmail(String.valueOf(obj[11]));
					model.setIssueTypeName(String.valueOf(obj[12]));
					model.setIssueTypeDescription(String.valueOf(obj[13]));
					model.setSprintName(String.valueOf(obj[14]));
					list.add(model);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	@Override
	public List<FeatureJiraMetaDataModel> listOfImportedIssuesofEpic(int fk_feature_id, long boardId, long epicId) {
		List<FeatureJiraMetaDataModel> list = new ArrayList<>();
		FeatureJiraMetaDataModel model = null;
		try {
			String hql = "SELECT jm.feature_jirametadata_id,jm.board_id,jm.board_name,jm.issueid,"
					+ "  jm.issue_key,jm.issue_name,jm.issue_description,jm.issue_assignee_name,"
					+ "  jm.issue_assignee_email,jm.issue_createdon,jm.issue_creater_name,"
					+ "  jm.issue_creater_email,jm.issue_type_name,"
					+ "  jm.issue_type_description,jm.epic_name,jm.epic_key FROM feature_jira_metadata jm where jm.board_id="
					+ boardId + " and jm.fk_feature_id=" + fk_feature_id + "" + " and jm.epic_id=" + epicId
					+ " and jm.sprint_id=0 and jm.delete_status=0;";
			List<Object> listofDetails = entityManager.createNativeQuery(hql).getResultList();
			if (!listofDetails.isEmpty()) {
				Iterator iterator = listofDetails.iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();
					model = new FeatureJiraMetaDataModel();
					model.setFeature_jirametadata_id(Integer.parseInt(String.valueOf(obj[0])));
					model.setBoardId(Long.parseLong(String.valueOf(obj[1])));
					model.setBoardName(String.valueOf(obj[2]));
					model.setIssueid(Long.parseLong(String.valueOf(obj[3])));
					model.setIssueKey(String.valueOf(obj[4]));
					model.setIssueName(String.valueOf(obj[5]));
					model.setIssueDescription(String.valueOf(obj[6]));
					model.setIssueAssigneeName(String.valueOf(obj[7]));
					model.setIssueAssigneeEmail(String.valueOf(obj[8]));
					model.setIssueCreatedon((Date) obj[9]);
					model.setIssueCreaterName(String.valueOf(obj[10]));
					model.setIssueCreaterEmail(String.valueOf(obj[11]));
					model.setIssueTypeName(String.valueOf(obj[12]));
					model.setIssueTypeDescription(String.valueOf(obj[13]));
					model.setEpicName(String.valueOf(obj[14]));
					model.setEpicKey(String.valueOf(obj[15]));
					list.add(model);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	@Override
	public List<Product> productConfigurationwithJiraProject(String productName, int fk_product_line) {
		Product product;
		List<Product> productlist = new ArrayList<>();
		try {
			String hql = "SELECT p.fk_jira_boardid,p.fk_jira_projectid,p.jira_board_name,p.jira_project_name FROM product p "
					+ " where p.product_name='" + productName
					+ "' and p.delete_status=0 and p.fk_jira_boardid>0 and p.fk_product_line=" + fk_product_line + "; ";

			List<Object> listofDetails = entityManager.createNativeQuery(hql).getResultList();
			if (!listofDetails.isEmpty()) {
				Iterator iterator = listofDetails.iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();

					product = new Product();
					product.setFk_jira_boardid(Long.parseLong(String.valueOf(obj[0])));
					product.setFk_jira_projectid(String.valueOf(obj[1]));
					product.setJiraBoardName(String.valueOf(obj[2]));
					product.setJiraprojectName(String.valueOf(obj[3]));
					/*
					 * model.setFeature_jirametadata_id(Integer.parseInt(String.valueOf(obj[0])));
					 * model.setBoardId(Long.parseLong(String.valueOf(obj[1])));
					 */
					productlist.add(product);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productlist;
	}

	@Override
	public List<Product> getproductConfigurationwithJiraProject(int product_id) {
		Product product;
		List<Product> productlist = new ArrayList<>();
		try {
			String hql = "SELECT p.fk_jira_boardid,p.fk_jira_projectid,p.jira_board_name,p.jira_project_name,p.product_name,p.product_desc,p.status FROM product p "
					+ " where p.product_id=" + product_id + " and p.delete_status=0 and p.fk_jira_boardid>0;";

			List<Object> listofDetails = entityManager.createNativeQuery(hql).getResultList();
			if (!listofDetails.isEmpty()) {
				Iterator iterator = listofDetails.iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();

					product = new Product();
					product.setFk_jira_boardid(Long.parseLong(String.valueOf(obj[0])));
					product.setFk_jira_projectid(String.valueOf(obj[1]));
					product.setJiraBoardName(String.valueOf(obj[2]));
					product.setJiraprojectName(String.valueOf(obj[3]));
					product.setProduct_name(String.valueOf(obj[4]));
					product.setProduct_desc(String.valueOf(obj[5]));
					product.setStatus(Integer.parseInt(String.valueOf(obj[6])));
					/*
					 * model.setFeature_jirametadata_id(Integer.parseInt(String.valueOf(obj[0])));
					 * model.setBoardId(Long.parseLong(String.valueOf(obj[1])));
					 */
					productlist.add(product);

				}
			} else {
				listofDetails.add(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productlist;
	}

	@Override
	public List<BoardModel> mergeProjectsandBoards(List<BoardModel> boardModel) {
		List<BoardModel> getListofUnassignedBoards = getListofUnassignedBoards(boardModel);
		return getListofUnassignedBoards;
	}

	private List<BoardModel> getListofUnassignedBoards(List<BoardModel> boardModel) {
		List<BoardModel> unassignedBoardList = new ArrayList<>();

		try {
			for (BoardModel b : boardModel) {
				String hql = "SELECT * FROM product p " + " where p.fk_jira_boardid=" + b.getBoardId()
						+ " and p.delete_status=0 and p.fk_jira_projectid='" + b.getProjectId() + "';";

				List<Object> listofDetails = entityManager.createNativeQuery(hql).getResultList();
				if (listofDetails.isEmpty()) {
					unassignedBoardList.add(b);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return unassignedBoardList;

	}

	@Override
	public Response boardassignedtoAutoProduct(int product_id, int boardId, String projectName, String projectId,
			String boardName) {
		Response response = null;
		List<Product> alreadyhaveJiraBoad = getproductConfigurationwithJiraProject(product_id);
		if (alreadyhaveJiraBoad.isEmpty()) {

			response = new Response();
			String hql = "Update product p set p.fk_jira_boardid=" + boardId + " , p.fk_jira_projectid='" + projectId
					+ "' " + ",p.jira_board_name='" + boardName + "' ,p.jira_project_name='" + projectName
					+ "' where p.delete_status=0 and p.product_id=" + product_id + ";";
			int isUpdate = entityManager.createNativeQuery(hql).executeUpdate();
			if (isUpdate > 0) {

				response.setStatusCode(1);
				response.setMessage("Product is Succssfully mapped with Jira Board:" + projectName);
			} else {

				response.setStatusCode(0);
				response.setMessage("Product failed to map with Jira Board:" + projectName);

			}

		} else {

			response = new Response();
			response.setStatusCode(0);
			response.setMessage("Product already mapped with Jira Board.");
		}
		return response;
	}

	@Override
	public List<Response> addBackLogs(List<ImportBacklogModel> backLogsModel, int product_id, int fk_user_id,
			String boardName, String projectName, long boardId, String projectId) {
		List<FeatureJiraMetaDataModel> list = new ArrayList<>();
		FeaturesModel featuresModel;
		FeatureJiraMetaDataModel featureJiraMetaDataModel;
		List<Response> listResponse = new ArrayList<>();
		Response response;
		try {

			int i = 0;

			for (ImportBacklogModel model : backLogsModel) {
				int isFeatureNotExist = isAlreadyExistenceCheckofFeaturesOfProjectInDatabase(model, boardId,
						model.getIssueid(), boardName, projectName, projectId, product_id);
				if (isFeatureNotExist == 0) {
					featuresModel = new FeaturesModel();
					featuresModel.setIssue_id(model.getIssueid());
					featuresModel.setFeature_name(model.getIssueName());
					featuresModel.setFeature_description(model.getIssueDescription());
					featuresModel.setFk_product_id(product_id);
					featuresModel.setFk_release_id(0);
					featuresModel.setIs_backlog(1);
					featuresModel.setStory_points(0);
					featuresModel.setJira_issue_type(model.getIssueTypeName());

					int getFeatureTypeId = getFeatureTypeIdbyIssueType(model.getIssueTypeId());
					System.out.println("getFeatureTypeId:*******" + getFeatureTypeId);
					featuresModel.setFk_feature_type_id(getFeatureTypeId);

					// int getFeatureStatusId = 0;
					if (model.getIssueStatusId() != 3) {
						int getFeatureStatusId = getFeatureStatusIdbyIssueStatus(model.getIssueStatusId());
						featuresModel.setFk_feature_status_id(getFeatureStatusId);
					} else {
						featuresModel.setFk_feature_status_id(2);
					}

					int getUserId = 0;
					if (model.getIssueCreaterEmail() != null) {
						getUserId = getUserIdbyJiraEmail(model.getIssueCreaterEmail());
						featuresModel.setCreated_by(getUserId);
					} else {
						featuresModel.setCreated_by(getUserId);
					}

					int getAssigneUserId = 0;
					if (model.getIssueAssigneeEmail() != null) {
						getAssigneUserId = getUserIdbyJiraEmail(model.getIssueAssigneeEmail());
						featuresModel.setAssigned_To(getAssigneUserId);
					} else {
						featuresModel.setAssigned_To(getAssigneUserId);
					}

					entityManager.persist(featuresModel);
					System.out.println("feature id:" + featuresModel.getFeature_id());
					if (featuresModel.getFeature_id() > 0) {
						response = new Response();
						response.setStatusCode(1);
						response.setMessage("Backlogs of Board imported to Database Successfully with this BoardId:"
								+ boardId + " , IssueId:" + model.getIssueid() + " and IssueKey:" + model.getIssueKey()
								+ ".");
						response.setIssue_id(model.getIssueid());
						response.setIssuekey(model.getIssueKey());
						listResponse.add(response);

						/*
						 * Backlogs to FeatureJirametadata Service
						 * 
						 * int isNotExist = isAlreadyExistenceCheckofBacklogsOfBoardsInDatabase(model,
						 * boardId, model.getIssueid(), featuresModel.getFeature_id(), boardName,
						 * projectName, projectId); if (isNotExist == 1) { featureJiraMetaDataModel =
						 * new FeatureJiraMetaDataModel();
						 * featureJiraMetaDataModel.setFk_feature_id(featuresModel.getFeature_id());
						 * featureJiraMetaDataModel.setBoardId(boardId);
						 * featureJiraMetaDataModel.setBoardName(boardName);
						 * featureJiraMetaDataModel.setProjectId(projectId);
						 * featureJiraMetaDataModel.setProjectName(projectName);
						 * 
						 * featureJiraMetaDataModel.setIssueid(model.getIssueid());
						 * featureJiraMetaDataModel.setIssueKey(model.getIssueKey());
						 * featureJiraMetaDataModel.setIssueName(model.getIssueName());
						 * featureJiraMetaDataModel.setIssueCreatedon(model.getIssueCreatedon());
						 * featureJiraMetaDataModel.setIssueDescription(model.getIssueDescription());
						 * featureJiraMetaDataModel.setIssueTypeName(model.getIssueTypeName());
						 * featureJiraMetaDataModel.setIssueTypeDescription(model.
						 * getIssueTypeDescription());
						 * featureJiraMetaDataModel.setIssueCreaterName(model.getIssueCreaterName());
						 * featureJiraMetaDataModel.setIssueCreaterEmail(model.getIssueCreaterEmail());
						 * featureJiraMetaDataModel.setIssueAssigneeName(model.getIssueAssigneeName());
						 * featureJiraMetaDataModel.setIssueAssigneeEmail(model.getIssueAssigneeEmail())
						 * ;
						 * 
						 * entityManager.persist(featureJiraMetaDataModel);
						 * 
						 * list.add(featureJiraMetaDataModel);
						 * 
						 * if (list.get(i).getFeature_jirametadata_id() > 0) { response = new
						 * Response(); response.setStatusCode(1); response.setMessage(
						 * "Backlogs of Board imported to Database Successfully with this BoardId:" +
						 * boardId + " , IssueId:" + model.getIssueid() + " and IssueKey:" +
						 * model.getIssueKey() + "."); response.setIssue_id(model.getIssueid());
						 * response.setIssuekey(model.getIssueKey()); listResponse.add(response); i++; }
						 * 
						 * else { response = new Response(); response.setStatusCode(0);
						 * response.setMessage("Failed to import due to Database Problem.");
						 * response.setIssue_id(model.getIssueid());
						 * response.setIssuekey(model.getIssueKey()); listResponse.add(response); }
						 * 
						 * } else if (isNotExist == 2) {
						 * 
						 * response = new Response(); response.setStatusCode(0); response.setMessage(
						 * "Backlog of Board with this BoardId:" + boardId + " , IssueId:" +
						 * model.getIssueid() + " and IssueKey:" + model.getIssueKey() +
						 * " already exist in database."); listResponse.add(response);
						 * 
						 * } else if (isNotExist == 3) { response = new Response();
						 * response.setStatusCode(0);
						 * response.setMessage("Changes Found.Backlog of Board with this BoardId:" +
						 * boardId + " , IssueId:" + model.getIssueid() + " and IssueKey:" +
						 * model.getIssueKey() + " updated in database."); listResponse.add(response);
						 * 
						 * }
						 * 
						 */} else {
						response = new Response();
						response.setStatusCode(0);
						response.setMessage("Failed to insert into Feature Model.");
						listResponse.add(response);

					}

				} else {
					response = new Response();
					response.setStatusCode(2);
					response.setMessage(
							"Backlog of Board:'" + model.getIssueName() + "' already exist in Feature Model.");
					response.setIssue_id(model.getIssueid());
					response.setIssuekey(model.getIssueKey());
					listResponse.add(response);
				}
			}
		} catch (Exception e) {
			// entityManager.close();
			response = new Response();
			response.setMessage(e.getMessage());
			listResponse.add(response);
		}
		return listResponse;
	}

	@Override
	public int getUserIdbyJiraEmail(String issueCreaterEmail) {
		int userId = 0;
		String hql = "SELECT jc.fk_user_id FROM jira_configurations jc where " + "jc.user_name='" + issueCreaterEmail
				+ "' and jc.delete_status=0;";
		List<Object> list = entityManager.createNativeQuery(hql).getResultList();

		if (list.size() > 0) {
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				Object obj = (Object) iterator.next();
				userId = Integer.parseInt(String.valueOf(obj));
			}

		}
		return userId;
	}

	@Override
	public int getFeatureStatusIdbyIssueStatus(int issueStatusId) {
		String hql = "SELECT fs.status_id FROM features_status fs where " + "fs.fk_jira_issue_status_id="
				+ issueStatusId + " and fs.delete_status=0;";
		List<Object> list = entityManager.createNativeQuery(hql).getResultList();

		int featurestatusid = 0;
		if (list.size() > 0) {
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				Object obj = (Object) iterator.next();
				featurestatusid = Integer.parseInt(String.valueOf(obj));
			}
		}
		return featurestatusid;
	}

	@Override
	public int getFeatureTypeIdbyIssueType(long l) {
		String hql = "SELECT ft.feature_type_id FROM features_type ft " + "where ft.jira_issue_type_id=" + l
				+ " and ft.delete_status=0;";

		List<Object> list = entityManager.createNativeQuery(hql).getResultList();
		int featuretypeid = 0;
		if (list.size() > 0) {
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				Object obj = (Object) iterator.next();
				featuretypeid = Integer.parseInt(String.valueOf(obj));
			}
		}
		return featuretypeid;
	}

	private int isAlreadyExistenceCheckofFeaturesOfProjectInDatabase(ImportBacklogModel model, long boardId,
			long issueid, String boardName, String projectName, String projectId, int product_id) {
		/*
		 * String hql = "SELECT \r\n" + "    f.feature_id \r\n" + "FROM\r\n" +
		 * "    features f\r\n" + "        JOIN\r\n" +
		 * "    feature_jira_metadata jm ON f.feature_id = jm.fk_feature_id\r\n" +
		 * "WHERE\r\n" + "    f.feature_name = '" + model.getIssueName() + "'\r\n" +
		 * " AND f.issue_id="+model.getIssueid()+"  AND f.fk_product_id = " + product_id
		 * + "\r\n" + "        AND f.feature_description = '" +
		 * model.getIssueDescription() + "'\r\n" + "        AND f.is_backlog = 1\r\n" +
		 * "        AND f.fk_release_id = 0\r\n" + "        AND f.delete_status = 0\r\n"
		 * + "        AND jm.issueid = " + model.getIssueid() + "\r\n" +
		 * "        AND jm.project_id = '" + projectId + "'\r\n" +
		 * "        AND jm.board_id = " + boardId + "\r\n" +
		 * "        AND jm.delete_status = 0 AND  f.fk_feature_type_id=" +
		 * getFeatureTypeIdbyIssueType(model.getIssueTypeId()) + " \r\n" +
		 * "AND f.assigned_to=" + getUserIdbyJiraEmail(model.getIssueAssigneeEmail()) +
		 * " AND f.created_by=" + getUserIdbyJiraEmail(model.getIssueCreaterEmail()) +
		 * ";";
		 */
		String hql = "SELECT \n" + "    f.feature_id\n" + "FROM\n" + "    features f\n" + "WHERE\n"
				+ "    f.feature_name = '" + model.getIssueName() + "'\n" + "        AND f.issue_id = "
				+ model.getIssueid() + " " + "        AND f.fk_product_id = " + product_id + " \n"
				+ "        AND f.is_backlog = 1\n" + "        AND f.fk_release_id = 0\n"
				+ "        AND f.delete_status = 0\n" + "        AND f.fk_feature_type_id ="
				+ getFeatureTypeIdbyIssueType(model.getIssueTypeId()) + " \n" + "        AND f.assigned_to ="
				+ getUserIdbyJiraEmail(model.getIssueAssigneeEmail()) + " \n" + "        AND f.created_by = "
				+ getUserIdbyJiraEmail(model.getIssueCreaterEmail()) + ";";
		List featurelist = entityManager.createNativeQuery(hql).getResultList();
		if (featurelist.size() > 0) {
			return 1;
		}
		return 0;
	}

	@Override
	public List<SprintModel> getListOfUnassignedSprints(List<SprintModel> sprintModel, int product_id) {
		List<SprintModel> unassignedsprintlist = new ArrayList<>();
		try {
			for (SprintModel s : sprintModel) {
				String hql = "SELECT * FROM product_releases pr where pr.delete_status=0 and pr.fk_jira_sprint_id="
						+ s.getSprintId() + " and pr.fk_product_id=" + product_id + ";";

				List<Object> listofDetails = entityManager.createNativeQuery(hql).getResultList();
				if (listofDetails.isEmpty()) {
					List<Product> productDetails = getproductConfigurationwithJiraProject(product_id);
					if (s.getBoardIdofSprint() == productDetails.get(0).getFk_jira_boardid()) {
						s.setBoardName(productDetails.get(0).getJiraBoardName());
						unassignedsprintlist.add(s);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return unassignedsprintlist;
	}

	@Override
	public Response assignSprinttoRelease(int product_id, int sprintId, String sprintName, int release_Id) {
		Response response;
		List<Product> isProductMapped = getproductConfigurationwithJiraProject(product_id);
		if (isProductMapped.size() > 0) {
			boolean isalreadyMappedwithSprint = isalreadyMappedwithSprint(product_id, release_Id);
			boolean sprintMapCheck = isSprintAlreadyMapped(sprintId);
			// System.err.println("isalreadyMappedwithSprint:::::::::::::::::::::"+isalreadyMappedwithSprint);
			if (isalreadyMappedwithSprint && sprintMapCheck) {
				String hql = "update product_releases pr set pr.fk_jira_sprint_id=" + sprintId
						+ " ,pr.jira_sprint_name='" + sprintName + "'" + " where pr.release_id=" + release_Id
						+ " and pr.delete_status=0 and pr.fk_product_id=" + product_id + ";";
				int isUpdate = entityManager.createNativeQuery(hql).executeUpdate();
				if (isUpdate > 0) {
					response = new Response();
					response.setStatusCode(1);
					response.setMessage("Product_Release is Successfully mapped with Jira Sprint:" + sprintName);
				} else {
					response = new Response();
					response.setStatusCode(0);
					response.setMessage("Product_Release failed to map with Jira Sprint:" + sprintName);

				}
			} else {

				response = new Response();
				response.setStatusCode(0);
				response.setMessage("Product_Release already mapped with Jira Sprint.");
			}
		} else {
			response = new Response();
			response.setStatusCode(0);
			response.setMessage("Product is not mapped with Jira Board.");
		}
		return response;
	}

	private boolean isSprintAlreadyMapped(int sprintId) {
		String hql = "SELECT pr.fk_jira_sprint_id FROM product_releases pr where pr.delete_status=0 and "
				+ "  pr.fk_jira_sprint_id=" + sprintId;
		List<Object> listofDetails = entityManager.createNativeQuery(hql).getResultList();
		if (listofDetails.isEmpty()) {
			return true;
		}
		return false;
	}

	private boolean isalreadyMappedwithSprint(int product_id, int release_Id) {
		String hql = "SELECT pr.fk_jira_sprint_id  FROM product_releases pr where pr.delete_status=0 and "
				+ "pr.release_id=" + release_Id + " and pr.fk_jira_sprint_id >0 and pr.fk_product_id=" + product_id
				+ ";";
		List<Object> listofDetails = entityManager.createNativeQuery(hql).getResultList();
		if (listofDetails.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public List<JiraReleaseSprintDTO> getProductReleaseofJiraByReleaseId(int release_Id) {

		JiraReleaseSprintDTO release;
		List<JiraReleaseSprintDTO> releaselist = new ArrayList<>();

		try {
			String hql = "SELECT pr.release_name,pr.fk_product_id,pr.fk_jira_sprint_id, "
					+ "pr.jira_sprint_name,p.fk_jira_boardid,"
					+ "p.fk_jira_projectid,p.jira_board_name,p.jira_project_name,pr.release_id,pr.release_date_internal,pr.release_date_external "
					+ " FROM product_releases pr join product p on pr.fk_product_id=p.product_id"
					+ " where pr.delete_status=0 and " + "pr.release_id=" + release_Id + " ;";

			List<Object> listofDetails = entityManager.createNativeQuery(hql).getResultList();
			if (!listofDetails.isEmpty()) {
				Iterator iterator = listofDetails.iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();

					release = new JiraReleaseSprintDTO();
					release.setReleaseName(String.valueOf(obj[0]));
					release.setFkProductId(Integer.parseInt(String.valueOf(obj[1])));
					release.setFk_jira_sprint_id(Integer.parseInt(String.valueOf(obj[2])));
					release.setJira_sprintName(String.valueOf(obj[3]));
					release.setFk_jira_boardid(Long.parseLong(String.valueOf(obj[4])));
					release.setFk_jira_projectid(String.valueOf(obj[5]));
					release.setJiraBoardName(String.valueOf(obj[6]));
					release.setJiraprojectName(String.valueOf(obj[7]));
					release.setReleaseId(Integer.parseInt(String.valueOf(obj[8])));
					release.setReleaseDateInternal(String.valueOf(obj[9]));
					release.setReleaseDateExternal(String.valueOf(obj[10]));
					releaselist.add(release);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return releaselist;
		// return listofDetails;

	}

	/*
	 * @Override public List<Response> addIssues(List<ImportBacklogModel>
	 * backLogsModel, int product_id, int release_Id) { // TODO Auto-generated
	 * method stub return null; }
	 */

	@Override
	public List<Response> addIssues(List<ImportBacklogModel> backLogsModel, int product_id, int release_Id,
			int sprintId, String releaseName, String jira_sprintName, long fk_jira_boardid, String fk_jira_projectid,
			String jiraprojectName, String jiraBoardName) {
		List<FeatureJiraMetaDataModel> list = new ArrayList<>();
		FeatureJiraMetaDataModel featureJiraMetaDataModel; // BackLogsModel backLogs;
		// List<FeaturesModel> featuresModel=new ArrayList<>();
		FeaturesModel featuresModel;
		List<Response> listResponse = new ArrayList<>();
		Response response;
		try {

			int i = 0;
			for (ImportBacklogModel model : backLogsModel) {
				int isFeatureofReleaseNotExist = isAlreadyExistenceCheckofFeaturesOfSprintofProjectInDatabase(model,
						fk_jira_boardid, model.getIssueid(), jiraBoardName, jiraprojectName, fk_jira_projectid,
						product_id, release_Id, sprintId, jira_sprintName);
				if (isFeatureofReleaseNotExist == 0) {
					featuresModel = new FeaturesModel(); // featuresModel.set
					featuresModel.setIssue_id(model.getIssueid());
					if (model.getIssueDescription() == null) {
						featuresModel.setFeature_description(model.getIssueName());
					} else {
						featuresModel.setFeature_description(model.getIssueDescription());
					}
					featuresModel.setFeature_name(model.getIssueName());
					featuresModel.setFeature_description(model.getIssueDescription());
					featuresModel.setFk_product_id(product_id);
					featuresModel.setFk_release_id(release_Id);
					featuresModel.setIs_backlog(0);
					featuresModel.setStory_points(1);

					int getFeatureTypeId = getFeatureTypeIdbyIssueType(model.getIssueTypeId());
					featuresModel.setFk_feature_type_id(getFeatureTypeId);

					// int getFeatureStatusId = 0;
					if (model.getIssueStatusId() != 3) {
						int getFeatureStatusId = getFeatureStatusIdbyIssueStatus(model.getIssueStatusId());
						featuresModel.setFk_feature_status_id(getFeatureStatusId);
					} else {
						featuresModel.setFk_feature_status_id(2);
					}

					int getUserId = 0;
					if (model.getIssueCreaterEmail() != null) {
						getUserId = getUserIdbyJiraEmail(model.getIssueCreaterEmail());
						featuresModel.setCreated_by(getUserId);
					} else {
						featuresModel.setCreated_by(getUserId);
					}

					int getAssigneUserId = 0;
					if (model.getIssueAssigneeEmail() != null) {
						getAssigneUserId = getUserIdbyJiraEmail(model.getIssueAssigneeEmail());
						featuresModel.setAssigned_To(getAssigneUserId);
					} else {
						featuresModel.setAssigned_To(getAssigneUserId);
					}
					entityManager.persist(featuresModel);

					System.out.println("feature id:" + featuresModel.getFeature_id());
					if (featuresModel.getFeature_id() > 0) {
						response = new Response();
						response.setStatusCode(1);
						response.setMessage("Issues of Sprint with BoardId:" + fk_jira_boardid + ",SprintId:" + sprintId
								+ ",SprintName:'" + jira_sprintName + "' " + " ,IssueId:" + model.getIssueid()
								+ " and IssueKey:'" + model.getIssueKey() + "' imported to Database Successfully.");
						response.setIssue_id(model.getIssueid());
						response.setIssuekey(model.getIssueKey());
						listResponse.add(response);

						/*
						 * Issue of Sprint to FeatureMetaData
						 * 
						 * int isNotExist = isAlreadyExistenceCheckofIssuesOfSprintInDatabase(model,
						 * fk_jira_boardid, jiraBoardName, model.getIssueid(), jira_sprintName,
						 * featuresModel.getFeature_id(), sprintId); if (isNotExist == 1) {
						 * featureJiraMetaDataModel = new FeatureJiraMetaDataModel();
						 * featureJiraMetaDataModel.setFk_feature_id(featuresModel.getFeature_id());
						 * featureJiraMetaDataModel.setBoardId(fk_jira_boardid);
						 * featureJiraMetaDataModel.setBoardName(jiraBoardName);
						 * featureJiraMetaDataModel.setProjectId(fk_jira_projectid);
						 * featureJiraMetaDataModel.setProjectName(jiraprojectName);
						 * featureJiraMetaDataModel.setSprintId(sprintId);
						 * featureJiraMetaDataModel.setSprintName(jira_sprintName);
						 * featureJiraMetaDataModel.setIssueid(model.getIssueid());
						 * featureJiraMetaDataModel.setIssueKey(model.getIssueKey());
						 * featureJiraMetaDataModel.setIssueName(model.getIssueName());
						 * featureJiraMetaDataModel.setIssueCreatedon(model.getIssueCreatedon());
						 * featureJiraMetaDataModel.setIssueDescription(model.getIssueDescription());
						 * featureJiraMetaDataModel.setIssueTypeName(model.getIssueTypeName());
						 * featureJiraMetaDataModel.setIssueTypeDescription(model.
						 * getIssueTypeDescription());
						 * featureJiraMetaDataModel.setIssueCreaterName(model.getIssueCreaterName());
						 * featureJiraMetaDataModel.setIssueCreaterEmail(model.getIssueCreaterEmail());
						 * featureJiraMetaDataModel.setIssueAssigneeName(model.getIssueAssigneeName());
						 * featureJiraMetaDataModel.setIssueAssigneeEmail(model.getIssueAssigneeEmail())
						 * ; entityManager.persist(featureJiraMetaDataModel);
						 * list.add(featureJiraMetaDataModel);
						 * 
						 * if (list.get(i).getFeature_jirametadata_id() > 0) { response = new
						 * Response(); response.setStatusCode(1);
						 * response.setMessage("Issues of Sprint with BoardId:" + fk_jira_boardid +
						 * ",SprintId:" + sprintId + ",SprintName:'" + jira_sprintName + "' " +
						 * " ,IssueId:" + model.getIssueid() + " and IssueKey:'" + model.getIssueKey() +
						 * "' imported to Database Successfully.");
						 * response.setIssue_id(model.getIssueid());
						 * response.setIssuekey(model.getIssueKey()); listResponse.add(response); i++; }
						 * 
						 * else { response = new Response(); response.setStatusCode(0);
						 * response.setMessage("Failed to import due to Database Problem.");
						 * response.setIssue_id(model.getIssueid());
						 * response.setIssuekey(model.getIssueKey()); listResponse.add(response); }
						 * 
						 * } else if (isNotExist == 2) {
						 * 
						 * response = new Response(); response.setStatusCode(0);
						 * response.setMessage("Issue of Sprint with this BoardId:" + fk_jira_boardid +
						 * " ,SprintId:" + sprintId + " ,IssueId:" + model.getIssueid() +
						 * " and IssueKey:" + model.getIssueKey() + " already exist in database.");
						 * listResponse.add(response); } else if (isNotExist == 3) { response = new
						 * Response(); response.setStatusCode(0);
						 * response.setMessage("Changes Found.Backlog of Board with this BoardId:" +
						 * fk_jira_boardid + " ,SprintId:" + sprintId + ", IssueId:" +
						 * model.getIssueid() + " and IssueKey:" + model.getIssueKey() +
						 * " updated in database."); listResponse.add(response);
						 * 
						 * }
						 */} else {

						response = new Response();
						response.setStatusCode(0);
						response.setMessage("Failed to insertion in Feature due to Database Problem.");
						listResponse.add(response);

					}
				} else {

					response = new Response();
					response.setStatusCode(2);
					response.setMessage(
							"Issue of Sprint:'" + model.getIssueName() + "' already exist in Feature Model.");
					response.setIssue_id(model.getIssueid());
					response.setIssuekey(model.getIssueKey());
					listResponse.add(response);

				}
			}
		} catch (Exception e) {
			response = new Response();
			response.setMessage(e.getMessage());
			listResponse.add(response);
		}
		return listResponse;
	}

	private int isAlreadyExistenceCheckofFeaturesOfSprintofProjectInDatabase(ImportBacklogModel model,

			long fk_jira_boardid, long issueid, String jiraBoardName, String jiraprojectName, String fk_jira_projectid,
			int product_id, int release_Id, int sprintId, String jira_sprintName) {

		int getFeatureTypeId = getFeatureTypeIdbyIssueType(model.getIssueTypeId());

		/*
		 * int getOriginalFeatureStatusId=0; int getFeatureStatusId =
		 * getFeatureStatusIdbyIssueStatus(model.getIssueStatusId());
		 * if(getFeatureStatusId==3) { getOriginalFeatureStatusId=2; }
		 */

		int getUserId = getUserIdbyJiraEmail(model.getIssueCreaterEmail());
		int getAssigneUserId = getUserIdbyJiraEmail(model.getIssueAssigneeEmail());

		/*
		 * String hql = "SELECT f.feature_id FROM  features f  JOIN " +
		 * "    feature_jira_metadata jm ON f.feature_id = jm.fk_feature_id WHERE " +
		 * "    f.feature_name = '" + model.getIssueName() +
		 * "' AND f.issue_id="+model.getIssueid()+"   AND f.fk_product_id = " +
		 * product_id + "  AND f.feature_description = '" + model.getIssueDescription()
		 * + "' " + "  AND f.is_backlog = 0  AND f.fk_release_id = " + release_Id + " "
		 * + "  AND f.delete_status = 0  AND jm.issueid = " + model.getIssueid() + " " +
		 * "  AND jm.project_id = '" + fk_jira_projectid + "'  AND jm.board_id = " +
		 * fk_jira_boardid + "  AND jm.delete_status=0 AND jm.sprint_id=" + sprintId +
		 * " AND jm.sprint_name='" + jira_sprintName + "'  AND f.fk_feature_type_id=" +
		 * getFeatureTypeId + " AND f.assigned_to=" + getAssigneUserId + "" +
		 * " AND f.created_by=" + getUserId + " ;";
		 */
		String hql = "SELECT  f.feature_id " + "	FROM " + "  features f " + "	WHERE  f.feature_name = '"
				+ model.getIssueName() + "'  AND f.issue_id = " + model.getIssueid() + "  " + "	 AND f.fk_product_id = "
				+ product_id + " " + "	  AND f.is_backlog = 0 " + "	  AND f.fk_release_id =" + release_Id + " "
				+ "		  AND f.delete_status = 0 " + "	  AND f.fk_feature_type_id =" + getFeatureTypeId + " "
				+ "	  AND f.assigned_to =" + getAssigneUserId + " " + "	   AND f.created_by = " + getUserId + ";";
		List<Object> featurelist = entityManager.createNativeQuery(hql).getResultList();
		if (featurelist.size() > 0) {
			return 1;
		}
		return 0;
	}

	@Override
	public Product getProductDetailsInAuto(int productId) {
		Product product = null;
		try {
			String hql = "SELECT * FROM product p  where p. product_id=" + productId + " and p.delete_status=0";
			List<Object> list = entityManager.createNativeQuery(hql).getResultList();
			if (list.size() > 0) {
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					product = new Product();
					product.setProduct_name(String.valueOf(obj[1]));
					product.setProduct_desc(String.valueOf(obj[2]));
					product.setFk_jira_projectid(String.valueOf(obj[13]));
					product.setFk_jira_boardid(Long.parseLong(String.valueOf(obj[12])));
					product.setJiraBoardName(String.valueOf(obj[10]));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return product;
	}

	@Override
	public List<ReleaseListModel> getUnmappedReleasesList() {
		List<ReleaseListModel> releaselist = new ArrayList<>();
		ReleaseListModel model = null;
		String internalDate[] = null;
		String externalDate[] = null;
		try {
			Query hql = entityManager.createNativeQuery(
					"SELECT r.release_id,r.release_name,r.fk_product_id,r.release_date_internal,r.release_date_external"
							+ ",r.release_description,r.fk_release_owner,r.fk_status_id,p.product_name,pl.product_line_name,rs.status ,u.user_name,p.fk_jira_boardid  "
							+ "FROM product_releases r, product p,product_line pl,product_release_status rs, product_users pu,users u "
							+ "where r.delete_status=0 and r.fk_product_id=p.product_id and p.fk_product_line=pl.product_line_id and r.fk_status_id=rs.status_id"
							+ " and r.fk_release_owner=pu.product_user_id and pu.fk_user_id=u.user_id and (r.fk_jira_sprint_id is null or r.fk_jira_sprint_id=0) ");
			List<Object> list = hql.getResultList();
			if (list.size() > 0) {

				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					if (Integer.parseInt(String.valueOf(obj[12])) > 0) {
						model = new ReleaseListModel();
						internalDate = String.valueOf(obj[3]).split(" ");
						externalDate = String.valueOf(obj[4]).split(" ");
						model.setReleaseId(Integer.parseInt(String.valueOf(obj[0])));
						model.setReleaseName(String.valueOf(obj[1]));
						model.setFkProductId(Integer.parseInt(String.valueOf(obj[2])));
						model.setReleaseDateInternal(internalDate[0]);
						model.setReleaseDateExternal(externalDate[0]);
						model.setReleaseDescription(String.valueOf(obj[5]));
						model.setFkReleaseOwner(Integer.parseInt(String.valueOf(obj[6])));
						model.setFkStatusId(Integer.parseInt(String.valueOf(obj[7])));
						model.setProductName(String.valueOf(obj[8]));
						model.setProductLineName(String.valueOf(obj[9]));
						model.setStatus(String.valueOf(obj[10]));
						model.setOwner(String.valueOf(obj[11]));
						model.setProductMapped(true);
						releaselist.add(model);
					} else {

						model = new ReleaseListModel();
						internalDate = String.valueOf(obj[3]).split(" ");
						externalDate = String.valueOf(obj[4]).split(" ");
						model.setReleaseId(Integer.parseInt(String.valueOf(obj[0])));
						model.setReleaseName(String.valueOf(obj[1]));
						model.setFkProductId(Integer.parseInt(String.valueOf(obj[2])));
						model.setReleaseDateInternal(internalDate[0]);
						model.setReleaseDateExternal(externalDate[0]);
						model.setReleaseDescription(String.valueOf(obj[5]));
						model.setFkReleaseOwner(Integer.parseInt(String.valueOf(obj[6])));
						model.setFkStatusId(Integer.parseInt(String.valueOf(obj[7])));
						model.setProductName(String.valueOf(obj[8]));
						model.setProductLineName(String.valueOf(obj[9]));
						model.setStatus(String.valueOf(obj[10]));
						model.setOwner(String.valueOf(obj[11]));
						model.setProductMapped(false);
						releaselist.add(model);

					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return releaselist;
	}

	@Override
	public ReleaseModel getReleaseDetailsInAuto(int releaseId) {
		ReleaseModel release = null;
		String internalDate[] = null;
		String externalDate[] = null;
		try {
			String hql = "SELECT * FROM product_releases p  where p. release_id=" + releaseId
					+ " and p.delete_status=0";
			List<ReleaseModel> list = entityManager.createNativeQuery(hql).getResultList();
			if (list.size() > 0) {
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					release = new ReleaseModel();
					internalDate = String.valueOf(obj[8]).split(" ");
					externalDate = String.valueOf(obj[3]).split(" ");
					release.setReleaseName(String.valueOf(obj[1]));
					release.setFkProductId(Integer.parseInt(String.valueOf(obj[2])));
					release.setReleaseDateExternal(externalDate[0]);
					release.setReleaseDateInternal(internalDate[0]);
					release.setReleaseDescription(String.valueOf(obj[5]));
					release.setFk_jira_sprint_id(Integer.parseInt(String.valueOf(obj[15])));
					release.setJira_sprintName(String.valueOf(obj[16]));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return release;
	}

	@Override
	public List<BackLogsModel> listofnotassignedIssuesofSprintforReleaseMapping(List<BackLogsModel> issues,
			int release_Id) {
		List<BackLogsModel> listofUnassignedIssuesList = new ArrayList<>();
		try {
			for (BackLogsModel b : issues) {
				long IssueId = b.getIssueid();
				String hql = "SELECT * FROM features f where " + "f.delete_status=0 and f.issue_id=" + IssueId
						+ " and f.fk_release_id=" + release_Id + ";";
				List<Object> list = entityManager.createNativeQuery(hql).getResultList();
				if (list.isEmpty()) {
					listofUnassignedIssuesList.add(b);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listofUnassignedIssuesList;
	}

	@Override
	public Response issueassignedtoAutoFeature(int product_id, int release_Id, int feature_Id, long issueId,
			String jiraIssueType) {

		Response response;
		// boolean isalreadyMappedwithIssue = isalreadyMappedwithIssue(feature_Id,
		// product_id, release_Id);
		// if (isalreadyMappedwithIssue) {
		FeaturesModel featuresModel = getBacklogStaus(issueId);
		if (featuresModel == null) {
			String hql = "update features f set f.issue_id=" + issueId + " ,f.jira_issue_type='" + jiraIssueType
					+ "'  where f.delete_status=0 and f.feature_id=" + feature_Id + ";";
			int isUpdate = entityManager.createNativeQuery(hql).executeUpdate();
			if (isUpdate > 0) {
				response = new Response();
				response.setStatusCode(1);
				response.setMessage("Feature is Successfully mapped with Jira Issue:" + issueId);
			} else {
				response = new Response();
				response.setStatusCode(0);
				response.setMessage("Feature failed to map with Jira Issue:" + issueId);

			}
		} else {

			String hql1 = "update features f set f.delete_status=1 where f.feature_id=" + featuresModel.getFeature_id()
					+ " and f.delete_status=0 and (f.is_backlog=1 or f.is_backlog=0) ;";

			int isFeatureDeleteStatusUpdated = entityManager.createNativeQuery(hql1).executeUpdate();
			if (isFeatureDeleteStatusUpdated > 0) {

				String hql = "update features f set f.issue_id=" + issueId + " ,f.jira_issue_type='" + jiraIssueType
						+ "'  where f.delete_status=0 and f.feature_id=" + feature_Id + ";";
				int isUpdate = entityManager.createNativeQuery(hql).executeUpdate();
				if (isUpdate > 0) {
					response = new Response();
					response.setStatusCode(1);
					response.setMessage("Feature is Successfully mapped with Jira Issue:" + issueId);
				} else {
					response = new Response();
					response.setStatusCode(0);
					response.setMessage("Feature failed to map with Jira Issue:" + issueId);

				}
			} else {

				response = new Response();
				response.setStatusCode(0);
				response.setMessage("Feature failed to map with Jira Issue:" + issueId);

			}

		}
		/*
		 * } else {
		 * 
		 * response = new Response(); response.setStatusCode(0);
		 * response.setMessage("Product_Release already mapped with Jira Sprint."); }
		 */
		return response;

	}

	private FeaturesModel getBacklogStaus(long issueId) {
		String hql = "SELECT f.feature_id,f.is_backlog FROM features f where f.issue_id=" + issueId
				+ " and f.delete_status=0 and (f.is_backlog=1 or f.is_backlog=0) ;";
		FeaturesModel featuresModel = null;
		try {
			List<Object> list = entityManager.createNativeQuery(hql).getResultList();
			int isBacklogStatus = 0;
			if (list.size() > 0) {
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();
					featuresModel = new FeaturesModel();
					featuresModel.setFeature_id(Integer.parseInt(String.valueOf(obj[0])));
					featuresModel.setIs_backlog(Integer.parseInt(String.valueOf(obj[1])));
					return featuresModel;

				}

			}
		} catch (Exception e) {
			e.getMessage();
		}
		return featuresModel;

	}

	@Override
	public List<FeaturesModel> isFeatureNameAlreadyConfigurewithJiraIssue(String featureName, int release_Id) {
		FeaturesModel feature;
		List<FeaturesModel> featurelist = new ArrayList<>();
		try {
			String hql = "SELECT f.feature_id,f.fk_release_id,f.issue_id FROM features f where \n"
					+ "f.delete_status=0 and f.feature_name='" + featureName + "' and \n" + "f.fk_release_id="
					+ release_Id + " and f.issue_id>0; ";

			List<Object> listofDetails = entityManager.createNativeQuery(hql).getResultList();
			if (!listofDetails.isEmpty()) {
				Iterator iterator = listofDetails.iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();

					feature = new FeaturesModel();
					feature.setFeature_id(Integer.parseInt(String.valueOf(obj[0])));
					feature.setFk_release_id(Integer.parseInt(String.valueOf(obj[1])));
					feature.setIssue_id(Long.parseLong(String.valueOf(obj[2])));
					/*
					 * model.setFeature_jirametadata_id(Integer.parseInt(String.valueOf(obj[0])));
					 * model.setBoardId(Long.parseLong(String.valueOf(obj[1])));
					 */
					featurelist.add(feature);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return featurelist;
	}

	@Override
	public List<ReleaseModel> isreleaseConfigurationwithJiraSprint(String releaseName) {

		ReleaseModel release;
		List<ReleaseModel> releaselist = new ArrayList<>();

		try {
			String hql = "SELECT pr.release_name,pr.fk_product_id,pr.fk_jira_sprint_id, " + "pr.jira_sprint_name,"
					+ "pr.release_id " + " FROM product_releases pr " + " where pr.delete_status=0 and "
					+ "pr.release_name='" + releaseName + "' and pr.fk_jira_sprint_id>0 ;";

			List<Object> listofDetails = entityManager.createNativeQuery(hql).getResultList();
			if (!listofDetails.isEmpty()) {
				Iterator iterator = listofDetails.iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();

					release = new ReleaseModel();
					release.setReleaseName(String.valueOf(obj[0]));
					release.setFkProductId(Integer.parseInt(String.valueOf(obj[1])));
					release.setFk_jira_sprint_id(Integer.parseInt(String.valueOf(obj[2])));
					release.setJira_sprintName(String.valueOf(obj[3]));
					release.setReleaseId(Integer.parseInt(String.valueOf(obj[4])));
					// release.setFk_jira_boardid(Long.parseLong(String.valueOf(obj[4])));
					// release.setFk_jira_projectid(String.valueOf(obj[5]));
					// release.setJiraBoardName(String.valueOf(obj[6]));
					// release.setJiraprojectName(String.valueOf(obj[7]));

					releaselist.add(release);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return releaselist;
		// return listofDetails;

	}

	@Override
	public List<FeaturesModel> isFeatureConfigurationwithJiraSprintIssue(int release_Id, String feature_name) {
		FeaturesModel feature;
		List<FeaturesModel> featurelist = new ArrayList<>();
		try {
			String hql = "SELECT f.feature_id,f.fk_release_id,f.issue_id FROM features f where \n"
					+ "f.delete_status=0 and f.feature_name='" + feature_name + "' and \n" + "f.fk_release_id="
					+ release_Id + " and f.issue_id>0; ";

			List<Object> listofDetails = entityManager.createNativeQuery(hql).getResultList();
			if (!listofDetails.isEmpty()) {
				Iterator iterator = listofDetails.iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();

					feature = new FeaturesModel();
					feature.setFeature_id(Integer.parseInt(String.valueOf(obj[0])));
					feature.setFk_release_id(Integer.parseInt(String.valueOf(obj[1])));
					feature.setIssue_id(Long.parseLong(String.valueOf(obj[2])));
					/*
					 * model.setFeature_jirametadata_id(Integer.parseInt(String.valueOf(obj[0])));
					 * model.setBoardId(Long.parseLong(String.valueOf(obj[1])));
					 */
					featurelist.add(feature);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return featurelist;
	}

	@Override
	public FeaturesModel getFeatureDetails(int featureId) {
		FeaturesModel feature = null;
		try {
			String hql = "SELECT * FROM features f  where f.feature_id=" + featureId + " and f.delete_status=0";
			List<FeaturesModel> list = entityManager.createNativeQuery(hql).getResultList();
			if (list.size() > 0) {
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					feature = new FeaturesModel();
					feature.setFeature_name(String.valueOf(obj[1]));
					feature.setFeature_description(String.valueOf(obj[2]));
					feature.setStory_points(Integer.parseInt(String.valueOf(obj[3])));
					feature.setFk_feature_type_id(Integer.parseInt(String.valueOf(obj[4])));
					feature.setJira_issue_type(String.valueOf(obj[20]));
					feature.setIssue_id(Integer.parseInt(String.valueOf(obj[10])));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return feature;
	}

	@Override
	public Response syncJiraBoardToAutoProduct(int productId, long boardId, String boardName, String projectName) {
		// TODO Auto-generated method stub
		Response response = new Response();
		try {
			String query = "update product p set p.jira_board_name='" + boardName + "',p.jira_project_name='"
					+ projectName + "' where p.product_id='" + productId + "' and " + "p.fk_jira_boardid='" + boardId
					+ "' and p.delete_status=0;";
			int isUpdated = entityManager.createNativeQuery(query).executeUpdate();
			if (isUpdated > 0) {
				response.setMessage("JiraBoard To AutoProduct updated successfully");
				response.setStatus(1);
			} else {
				response.setMessage("JiraBoard To AutoProduct Not updated ");
				response.setStatus(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public Response syncJiraSprintToAutoRelease(int releaseId, long sprintId, long boardId, String sprintName) {
		// TODO Auto-generated method stub
		Response response = new Response();
		try {
			String query = "update product_releases pr set pr.jira_sprint_name='" + sprintName
					+ "' where pr.fk_jira_sprint_id='" + sprintId + "' and " + "pr.release_id='" + releaseId
					+ "' and pr.delete_status=0";
			int isUpdated = entityManager.createNativeQuery(query).executeUpdate();
			if (isUpdated > 0) {
				response.setMessage("JiraSprint To AutoRelease updated successfully");
				response.setStatus(1);
			} else {
				response.setMessage("JiraSprint To AutoRelease Not updated ");
				response.setStatus(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public boolean getIssueExistencyinDatabase(long issueId) {
		boolean val = false;
		try {
			String query = "select * from features where issue_id='" + issueId + "' and delete_status=0";
			List l = entityManager.createNativeQuery(query).getResultList();
			if (l.size() == 0) {
				val = false;
				return val;
			} else {
				val = true;
				return val;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return val;
	}

	@Override
	public List<ReleaseListModel> mappedReleasesList() {
		List<ReleaseListModel> releaselist = new ArrayList<>();
		ReleaseListModel model = null;
		String internalDate[] = null;
		String externalDate[] = null;
		try {
			Query hql = entityManager
					.createNativeQuery("SELECT r.release_id,r.release_name,r.fk_product_id,r.release_date_internal,"
							+ "r.release_date_external,r.release_description,r.fk_release_owner,r.fk_status_id,p.product_name,pl.product_line_name,rs.status ,u.user_name "
							+ "FROM product_releases r, product p,product_line pl,product_release_status rs, product_users pu,users u "
							+ "where r.delete_status=0 and r.fk_product_id=p.product_id and p.fk_product_line=pl.product_line_id and r.fk_status_id=rs.status_id  and r.fk_release_owner=pu.product_user_id and"
							+ " pu.fk_user_id=u.user_id and r.fk_jira_sprint_id >0 ");
			List<Object> list = hql.getResultList();
			if (list.size() > 0) {
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					model = new ReleaseListModel();
					internalDate = String.valueOf(obj[3]).split(" ");
					externalDate = String.valueOf(obj[4]).split(" ");
					model.setReleaseId(Integer.parseInt(String.valueOf(obj[0])));
					model.setReleaseName(String.valueOf(obj[1]));
					model.setFkProductId(Integer.parseInt(String.valueOf(obj[2])));
					model.setReleaseDateInternal(internalDate[0]);
					model.setReleaseDateExternal(externalDate[0]);
					model.setReleaseDescription(String.valueOf(obj[5]));
					model.setFkReleaseOwner(Integer.parseInt(String.valueOf(obj[6])));
					model.setFkStatusId(Integer.parseInt(String.valueOf(obj[7])));
					model.setProductName(String.valueOf(obj[8]));
					model.setProductLineName(String.valueOf(obj[9]));
					model.setStatus(String.valueOf(obj[10]));
					model.setOwner(String.valueOf(obj[11]));
					releaselist.add(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return releaselist;
	}

	@Override
	public List<BackLogsModel> listofnotassignedIssuesofSprintforReleaseMapping(List<BackLogsModel> issues,
			int release_Id, int fk_feature_type_id) {
		List<BackLogsModel> listofUnassignedIssuesList = new ArrayList<>();
		String JiraIssueTypeName = getJiraIssueTypeName(fk_feature_type_id);
		try {
			for (BackLogsModel b : issues) {
				if (JiraIssueTypeName.equals(b.getIssueTypeName())) {
					long IssueId = b.getIssueid();
					String hql = "SELECT * FROM features f where " + "f.delete_status=0 and f.issue_id=" + IssueId
							+ " and f.fk_release_id=" + release_Id + ";";
					List<Object> list = entityManager.createNativeQuery(hql).getResultList();

					if (list.isEmpty()) {
						listofUnassignedIssuesList.add(b);
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listofUnassignedIssuesList;
	}

	@Override
	public String getJiraIssueTypeName(int fk_feature_type_id) {
		String hql = "SELECT ft.jira_issue_type FROM features_type ft where ft.feature_type_id=" + fk_feature_type_id
				+ " and ft.delete_status=0;";
		Object jiraIssueType = entityManager.createNativeQuery(hql).getSingleResult();
		System.out.println("*************jiraIssueType:" + jiraIssueType.toString());
		return (String) jiraIssueType;
	}

	@Override
	public FeaturesModel getJiraIssueDetailsByFeatureId(int feature_id) {

		FeaturesModel featuresModel = new FeaturesModel();
		String hql = "SELECT f.fk_release_id,f.issue_id,f.fk_product_id FROM features f where f.feature_id="
				+ feature_id + " and f.delete_status=0;";
		List<Object> listofDetails = entityManager.createNativeQuery(hql).getResultList();
		if (!listofDetails.isEmpty()) {
			Iterator iterator = listofDetails.iterator();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				featuresModel.setFk_release_id(Integer.parseInt(String.valueOf(obj[0])));
				featuresModel.setIssue_id(Long.parseLong(String.valueOf(obj[1])));
				featuresModel.setFk_product_id(Integer.parseInt(String.valueOf(obj[2])));

			}

		}
		return featuresModel;

	}

	@Override
	public Response syncJiraIssuesToAutoFeature(int feature_id, BackLogsModel bm) {
		// TODO Auto-generated method stub
		Response response = new Response();
		int assignedTo = getUserIdbyJiraEmail(bm.getIssueAssigneeEmail());
		int createdBy = getUserIdbyJiraEmail(bm.getIssueCreaterEmail());
		long featureStatus = getFeatureStatusIdbyIssueStatus((int) bm.getIssueStatusId());
		int featureTypeId = getFeatureTypeIdbyIssueType(bm.getIssueTypeId());

		try {

			if (bm.getSprintId() > 0) {
				String query = "update features f set f.feature_name='" + bm.getIssueName() + "' ,"
						+ "f.feature_description='" + bm.getIssueDescription() + "' ," + "f.assigned_to=" + assignedTo
						+ "," + "f.created_by=" + createdBy + ",f.issue_id=" + bm.getIssueid()
						+ ",f.fk_feature_status_id=" + featureStatus + "," + "f.fk_feature_type_id=" + featureTypeId
						+ "," + "f.jira_issue_type='" + bm.getIssueTypeName() + "' where f.feature_id=" + feature_id
						+ " and f.delete_status=0;";
				int isUpdated = entityManager.createNativeQuery(query).executeUpdate();
				if (isUpdated > 0) {
					response.setMessage("JiraRelease To AutoIssue updated successfully");
					response.setStatus(1);
				} else {
					response.setMessage("JiraRelease To AutoIssue Not updated ");
					response.setStatus(0);
				}
			} else {

				String query = "update features f set f.feature_name='" + bm.getIssueName() + "' ,"
						+ "f.feature_description='" + bm.getIssueDescription() + "' ," + "f.assigned_to=" + assignedTo
						+ "," + "f.created_by=" + createdBy + "," + "f.fk_feature_status_id=" + featureStatus + ","
						+ "f.fk_feature_type_id=" + featureTypeId + "," + "f.jira_issue_type='" + bm.getIssueTypeName()
						+ "'," + "f.is_backlog=1 , f.fk_release_id=0 where f.feature_id=" + feature_id
						+ " and f.delete_status=0;";
				int isUpdated = entityManager.createNativeQuery(query).executeUpdate();
				if (isUpdated > 0) {
					response.setMessage("JiraIssue To AutoFeature updated successfully");
					response.setStatus(1);
				} else {
					response.setMessage("JiraIssue To AutoFeature Not updated ");
					response.setStatus(0);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public int isSprintAlreadyExist(int productId, long sprintId, long boardIdofSprint, int fk_user_id,
			String sprintname) {
		ReleaseModel release = null;
		try {
			String hql = "SELECT * FROM product_releases pr where pr.fk_jira_sprint_id=" + sprintId + " and pr.delete_status=0 and pr.fk_product_id="
					+ productId + ";";
			// Object ReleaseId = entityManager.createNativeQuery(hql).getSingleResult();
			List<ReleaseModel> list = entityManager.createNativeQuery(hql).getResultList();
			System.out.println("*************ReleaseList:" + list.toString());
			/*
			 * if ((int) ReleaseId > 0) { return (int) ReleaseId; }
			 */
			if (list.size() > 0) {
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					release = new ReleaseModel();
					int ReleaseId = Integer.parseInt(String.valueOf(obj[0]));
					return ReleaseId;

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;

	}

	@Override
	public List<Product> getproductDetailsByProductId(int product_id) {
		Product product;
		List<Product> productlist = new ArrayList<>();
		try {
			String hql = "SELECT p.fk_jira_boardid,p.fk_jira_projectid,p.jira_board_name,p.jira_project_name,p.product_name,p.product_desc,p.status FROM product p "
					+ " where p.product_id=" + product_id + " and p.delete_status=0;";

			List<Object> listofDetails = entityManager.createNativeQuery(hql).getResultList();
			if (!listofDetails.isEmpty()) {
				Iterator iterator = listofDetails.iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();

					product = new Product();
					product.setFk_jira_boardid(Long.parseLong(String.valueOf(obj[0])));
					product.setFk_jira_projectid(String.valueOf(obj[1]));
					product.setJiraBoardName(String.valueOf(obj[2]));
					product.setJiraprojectName(String.valueOf(obj[3]));
					product.setProduct_name(String.valueOf(obj[4]));
					product.setProduct_desc(String.valueOf(obj[5]));
					product.setStatus(Integer.parseInt(String.valueOf(obj[6])));
					/*
					 * model.setFeature_jirametadata_id(Integer.parseInt(String.valueOf(obj[0])));
					 * model.setBoardId(Long.parseLong(String.valueOf(obj[1])));
					 */
					productlist.add(product);

				}
			} else {
				listofDetails.add(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productlist;
	}

	@Override
	public ResponseDTO assignFullDetailsofSprinttoRelease(int productId, long sprintId, String sprintName,
			int sprintExistanceByReleaseId, Date sprintstartDate, Date sprintendDate, int fk_user_id, SprintModel sm) {
		ResponseDTO response = null;

		try {

			// System.err.println("isalreadyMappedwithSprint:::::::::::::::::::::"+isalreadyMappedwithSprint);
			if (sprintendDate == null) {

				String hql = "update product_releases pr set pr.fk_jira_sprint_id=" + sprintId
						+ " ,pr.jira_sprint_name='" + sprintName + "'" + ",pr.release_name='" + sprintName
						+ "', pr.updated_on=CURRENT_TIMESTAMP " + " where pr.release_id=" + sprintExistanceByReleaseId
						+ " and pr.delete_status=0 and pr.fk_product_id=" + productId + ";";
				int isUpdate = entityManager.createNativeQuery(hql).executeUpdate();
				if (isUpdate > 0) {
					response = new ResponseDTO();
					response.setStatusCode(1);
					response.setMessage("Product_Release is Successfully mapped with Jira Sprint:" + sprintName);
				} else {
					response = new ResponseDTO();
					response.setStatusCode(0);
					response.setMessage("Product_Release failed to map with Jira Sprint:" + sprintName);

				}
			} else {
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				// String sprintendDate1 = null;

				String sprintendDate1 = df.format(sprintendDate);

				String hql = "update product_releases pr set pr.fk_jira_sprint_id=" + sprintId
						+ " ,pr.jira_sprint_name='" + sprintName + "'" + ",pr.release_name='" + sprintName
						+ "', pr.updated_on=CURRENT_TIMESTAMP, " + "pr.release_date_internal='"
						+ sprintendDate1.toString() + "' " + " where pr.release_id=" + sprintExistanceByReleaseId
						+ " and pr.delete_status=0 and pr.fk_product_id=" + productId + ";";
				int isUpdate = entityManager.createNativeQuery(hql).executeUpdate();
				if (isUpdate > 0) {
					response = new ResponseDTO();
					response.setStatusCode(1);
					response.setMessage("Product_Release is Successfully mapped with Jira Sprint:" + sprintName);
				} else {
					response = new ResponseDTO();
					response.setStatusCode(0);
					response.setMessage("Product_Release failed to map with Jira Sprint:" + sprintName);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public List<JiraReleaseSprintDTO> getReleaseDetailsByReleaseId(int release_Id) {

		JiraReleaseSprintDTO release;
		List<JiraReleaseSprintDTO> releaselist = new ArrayList<>();
		String internalDate[] = null;
		String externalDate[] = null;

		try {
			String hql = "SELECT pr.release_name,pr.fk_product_id,pr.fk_jira_sprint_id, " + "pr.jira_sprint_name,"
					+ "pr.release_id,pr.release_date_internal,pr.created_on " + " FROM product_releases pr "
					+ " where pr.delete_status=0 and " + "pr.release_id=" + release_Id + " ;";

			List<Object> listofDetails = entityManager.createNativeQuery(hql).getResultList();
			if (!listofDetails.isEmpty()) {
				Iterator iterator = listofDetails.iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();

					release = new JiraReleaseSprintDTO();
					release.setReleaseName(String.valueOf(obj[0]));
					release.setFkProductId(Integer.parseInt(String.valueOf(obj[1])));
					release.setFk_jira_sprint_id(Integer.parseInt(String.valueOf(obj[2])));
					release.setJira_sprintName(String.valueOf(obj[3]));
					// release.setFk_jira_boardid(Long.parseLong(String.valueOf(obj[4])));
					// release.setFk_jira_projectid(String.valueOf(obj[5]));
					// release.setJiraBoardName(String.valueOf(obj[6]));
					// release.setJiraprojectName(String.valueOf(obj[7]));
					release.setReleaseId(Integer.parseInt(String.valueOf(obj[4])));

					internalDate = String.valueOf(obj[6]).split(" ");
					externalDate = String.valueOf(obj[5]).split(" ");

					release.setReleaseDateInternal(internalDate[0]);
					release.setReleaseDateExternal(externalDate[0]);
					releaselist.add(release);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return releaselist;
		// return listofDetails;

	}

	@Override
	public int isIssueExistanceByFeatureId(int productId, int fk_user_id, int releaseId, long issueid,
			BackLogsModel b) {

		FeaturesModel feature = null;
		// int getFeatureTypeId = getFeatureTypeIdbyIssueType(b.getIssueTypeId());

		// int getUserId = getUserIdbyJiraEmail(b.getIssueCreaterEmail());
		// int getAssigneUserId = getUserIdbyJiraEmail(b.getIssueAssigneeEmail());

		String hql = "SELECT  * " + "	FROM " + "  features f " + "	WHERE  f.issue_id = " + b.getIssueid() + "  "
				+ "	 AND f.fk_product_id = " + productId + " "
				// + " AND f.is_backlog = 0 " + " AND f.fk_release_id =" + releaseId + " "
				+ "		  AND f.delete_status = 0 " + " ;";
		// Object featurelist = entityManager.createNativeQuery(hql).getFirstResult();

		List<FeaturesModel> featurelist = entityManager.createNativeQuery(hql).getResultList();
		System.out.println("*************featurelist:" + featurelist.toString());
		/*
		 * if ((int) ReleaseId > 0) { return (int) ReleaseId; }
		 */
		if (featurelist.size() > 0) {
			Iterator itr = featurelist.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				feature = new FeaturesModel();
				int FeatureId = Integer.parseInt(String.valueOf(obj[0]));
				return FeatureId;

			}
		}
		return 0;
	}

	@Override
	public ResponseDTO assignFullDetailsofIssuetoExistFeature(BackLogsModel b, int productId, int releaseId,
			int fk_user_id, int issueExistanceByFeatureId) {
		// TODO Auto-generated method stub
		ResponseDTO response = null;
		int assignedTo = getUserIdbyJiraEmail(b.getIssueAssigneeEmail());
		int createdBy = getUserIdbyJiraEmail(b.getIssueCreaterEmail());
		long featureStatus = getFeatureStatusIdbyIssueStatus((int) b.getIssueStatusId());
		int featureTypeId = getFeatureTypeIdbyIssueType(b.getIssueTypeId());

		try {

			if (b.getIssueDescription() == null) {
				String query = "update features f set f.feature_name='" + b.getIssueName() + "' ,"
						+ "f.feature_description='" + b.getIssueName() + "' ," + "f.assigned_to=" + assignedTo + ","
						+ "f.created_by=" + createdBy + "," + "f.fk_feature_status_id=" + featureStatus + ","
						+ "f.fk_feature_type_id=" + featureTypeId + "," + "f.jira_issue_type='" + b.getIssueTypeName()
						+ "',f.issue_id=" + b.getIssueid() + ",f.is_backlog=0,f.fk_release_id=" + releaseId
						+ ",f.fk_product_id=" + productId + " where f.feature_id=" + issueExistanceByFeatureId
						+ " and f.delete_status=0;";
				int isUpdated = entityManager.createNativeQuery(query).executeUpdate();
				if (isUpdated > 0) {
					response = new ResponseDTO();
					response.setMessage("JiraRelease To AutoIssue updated successfully");
					response.setStatusCode(1);
				} else {
					response = new ResponseDTO();
					response.setMessage("JiraRelease To AutoIssue Not updated ");
					response.setStatusCode(0);
				}
			} else {
				String query = "update features f set f.feature_name='" + b.getIssueName() + "' ,"
						+ "f.feature_description='" + b.getIssueDescription() + "' ," + "f.assigned_to=" + assignedTo
						+ "," + "f.created_by=" + createdBy + "," + "f.fk_feature_status_id=" + featureStatus + ","
						+ "f.fk_feature_type_id=" + featureTypeId + "," + "f.jira_issue_type='" + b.getIssueTypeName()
						+ "',f.issue_id=" + b.getIssueid() + ",f.is_backlog=0,f.fk_release_id=" + releaseId
						+ ",f.fk_product_id=" + productId + " where f.feature_id=" + issueExistanceByFeatureId
						+ " and f.delete_status=0;";
				int isUpdated = entityManager.createNativeQuery(query).executeUpdate();
				if (isUpdated > 0) {
					response = new ResponseDTO();
					response.setMessage("JiraRelease To AutoIssue updated successfully");
					response.setStatusCode(1);
				} else {
					response = new ResponseDTO();
					response.setMessage("JiraRelease To AutoIssue Not updated ");
					response.setStatusCode(0);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public List<ReleaseModel> getReleaseDetailsInAutoByProductId(int productId) {
		List<ReleaseModel> releaselist = new ArrayList<>();
		ReleaseModel release = null;
		String internalDate[] = null;
		String externalDate[] = null;
		try {
			String hql = "SELECT * FROM product_releases p  where p. fk_product_id=" + productId
					+ " and p.delete_status=0 and p.fk_status_id<4";
			List<ReleaseModel> list = entityManager.createNativeQuery(hql).getResultList();
			if (list.size() > 0) {
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					release = new ReleaseModel();
					release.setReleaseId(Integer.parseInt(String.valueOf(obj[0])));
					internalDate = String.valueOf(obj[8]).split(" ");
					externalDate = String.valueOf(obj[3]).split(" ");
					release.setReleaseName(String.valueOf(obj[1]));
					release.setReleaseDateExternal(externalDate[0]);
					release.setReleaseDateInternal(internalDate[0]);
					release.setReleaseDescription(String.valueOf(obj[5]));
					release.setFk_jira_sprint_id(Integer.parseInt(String.valueOf(obj[15])));
					release.setJira_sprintName(String.valueOf(obj[16]));
					releaselist.add(release);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("########:"+releaselist.toString());
		return releaselist;
	}

	@Override
	public List<FeaturesModel> getFeatureDetailsByReleaseId(int releaseId) {
		List<FeaturesModel> featurelist = new ArrayList<>();
		FeaturesModel feature = null;
		try {
			String hql = "SELECT * FROM features f  where f.fk_release_id=" + releaseId + " and f.delete_status=0";
			List<FeaturesModel> list = entityManager.createNativeQuery(hql).getResultList();
			if (list.size() > 0) {
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					feature = new FeaturesModel();
					feature.setFeature_id(Integer.parseInt(String.valueOf(obj[0])));
					feature.setFeature_name(String.valueOf(obj[1]));
					feature.setFeature_description(String.valueOf(obj[2]));
					feature.setStory_points(Integer.parseInt(String.valueOf(obj[3])));
					feature.setFk_feature_type_id(Integer.parseInt(String.valueOf(obj[4])));
					feature.setJira_issue_type(String.valueOf(obj[20]));
					feature.setIssue_id(Integer.parseInt(String.valueOf(obj[10])));
					featurelist.add(feature);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return featurelist;
	}

	@Override
	public String assigneeByAssigneeId(int assigned_To) {
		String userId = null;
		String hql = "SELECT jc.user_name FROM jira_configurations jc where " + "jc.fk_user_id='" + assigned_To
				+ "' and jc.delete_status=0;";
		List<Object> list = entityManager.createNativeQuery(hql).getResultList();

		if (list.size() > 0) {
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				Object obj = (Object) iterator.next();
				userId = String.valueOf(obj);
			}

		}
		return userId;
	}

	@Override
	public String getJira_issue_typebyFeatureTypeId(int fk_feature_type_id) {
		String hql = "SELECT ft.jira_issue_type FROM features_type ft " + "where ft.fk_feature_type_id="
				+ fk_feature_type_id + " and ft.delete_status=0;";

		List<Object> list = entityManager.createNativeQuery(hql).getResultList();
		String Jira_issue_type = null;
		if (list.size() > 0) {
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				Object obj = (Object) iterator.next();
				Jira_issue_type = String.valueOf(obj);
			}
		}
		return Jira_issue_type;
	}

	@Override
	public int releasestatusidBySprintStatus(String sprintStatus) {
		String hql = "SELECT prs.status_id FROM product_release_status prs where prs.product_release_sprint_status='"
				+ sprintStatus + "' and prs.delete_status=0;";

		List<Object> list = entityManager.createNativeQuery(hql).getResultList();
		int releasestatusid = 0;
		if (list.size() > 0) {
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				Object obj = (Object) iterator.next();
				releasestatusid = Integer.parseInt(String.valueOf(obj));
			}
		}
		return releasestatusid;
	}

	@Override
	public ResponseDTO assignFullDetailsofSprinttoReleaseAsClosedSprint(int productId, long sprintId, String sprintName,
			int sprintExistanceByReleaseId, Date startDate, Date endDate, int fk_user_id, SprintModel sm) {

		ResponseDTO response = null;
		try {

			int releasestatusid = releasestatusidBySprintStatus(sm.getSprintStatus());
			String hql = "update product_releases pr set pr.fk_jira_sprint_id=" + sprintId + " ,pr.jira_sprint_name='"
					+ sprintName + "'" + ",pr.release_name='" + sprintName + "', pr.updated_on=CURRENT_TIMESTAMP "
					+ ",pr.fk_status_id=" + releasestatusid + " where pr.release_id=" + sprintExistanceByReleaseId
					+ " and pr.delete_status=0 and pr.fk_product_id=" + productId + ";";
			int isUpdate = entityManager.createNativeQuery(hql).executeUpdate();
			if (isUpdate > 0) {
				response = new ResponseDTO();
				response.setStatusCode(1);
				response.setMessage("Product_Release is Successfully mapped with Jira Sprint:" + sprintName);
			} else {
				response = new ResponseDTO();
				response.setStatusCode(0);
				response.setMessage("Product_Release failed to map with Jira Sprint:" + sprintName);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public int releaseIdBySprintIdAsClosedStatus(long sprintId, SprintModel sm, int productId) {
		String hql = "SELECT pr.release_id FROM product_releases pr "
				+ "where pr.fk_status_id=4 and pr.fk_jira_sprint_id=" + sprintId
				+ " and pr.delete_status=0 and pr.fk_product_id=" + productId + ";";

		List<Object> list = entityManager.createNativeQuery(hql).getResultList();
		int releaseid = 0;
		if (list.size() > 0) {
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				Object obj = (Object) iterator.next();
				releaseid = Integer.parseInt(String.valueOf(obj));
			}
		}
		return releaseid;
	}

	@Override
	public int updateFeaturesStatusAsDone(FeaturesModel fm, int releaseIdBySprintId, int productId) {

		int response = 0;
		try {

			String hql = "update features f set f.fk_feature_status_id=6 where f.fk_release_id=" + releaseIdBySprintId
					+ " and f.fk_product_id=" + productId + " and " + "f.delete_status=0 and f.feature_id="
					+ fm.getFeature_id() + ";";
			int isUpdate = entityManager.createNativeQuery(hql).executeUpdate();
			if (isUpdate > 0) {
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public int updatefeaturesasbacklogs(BackLogsModel bl, int productId) {

		int response = 0;
		try {

			String hql1 = "SELECT f.feature_id FROM features f where f.issue_id=" + bl.getIssueid()
					+ " and f.fk_product_id=" + productId + " and f.delete_status=0;";
			List<Object> list = entityManager.createNativeQuery(hql1).getResultList();

			if (list.size() > 0) {
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
					Object obj = (Object) iterator.next();
					int featureid = Integer.parseInt(String.valueOf(obj));
					String FeatureStatus1 = String.valueOf(bl.getIssueStatusId());
					int FeatureStatusId = getFeatureStatusIdbyIssueStatus(Integer.parseInt(FeatureStatus1));
					int FeatureTypeId=getFeatureTypeIdbyIssueType(bl.getIssueTypeId());
					int AssigneeUserId=getUserIdbyJiraEmail(bl.getIssueAssigneeEmail());
					
					String hql = "update features f set f.is_backlog=1,f.fk_release_id=0,f.feature_name='"
							+ bl.getIssueName() + "'," + "f.feature_description='" + bl.getIssueDescription()
							+ "',f.fk_feature_type_id="+FeatureTypeId+",f.fk_feature_status_id=" + FeatureStatusId + ",f.assigned_to="+AssigneeUserId+" "
							+ " where f.feature_id=" + featureid + " and f.fk_product_id=" + productId
							+ " and f.delete_status=0;";
					int isUpdate = entityManager.createNativeQuery(hql).executeUpdate();
					if (isUpdate > 0) {
						return 1;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

}
