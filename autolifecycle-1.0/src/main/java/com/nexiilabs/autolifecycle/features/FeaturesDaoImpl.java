package com.nexiilabs.autolifecycle.features;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.nexiilabs.autolifecycle.featuresassignment.FeaturesAssignmentModel;
import com.nexiilabs.autolifecycle.releases.ResponseDTO;

@Transactional
@Repository
public class FeaturesDaoImpl implements FeaturesDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ResponseDTO addFeatures(FeaturesModel featuresModel) {

		ResponseDTO responseDTO = new ResponseDTO();
		try {
			boolean check = featuresExistencyCheck(featuresModel);

			if (!check) {
				entityManager.persist(featuresModel);
				if (featuresModel.getFeature_id() != 0) {
					responseDTO.setFeature_id(featuresModel.getFeature_id());
					responseDTO.setMessage("Feature added successfully");
					responseDTO.setStatusCode(1);
				} else {
					responseDTO.setMessage("Feature addition Failed");
					responseDTO.setStatusCode(0);
				}
			} else {
				responseDTO.setMessage("Feature Already exists");
				responseDTO.setStatusCode(2);
			}

		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage(e.getMessage());
		}

		return responseDTO;
	}

	private boolean featuresExistencyCheck(FeaturesModel featuresModel) {
		List list = null;
		try {
			Query sql = entityManager
					.createNativeQuery("" + "select f.feature_name,f.fk_release_id from features f,product_releases pr "
							+ " where f.fk_release_id=pr.release_id and "
							+ "f.feature_name=:feature_name and f.fk_release_id=:fk_release_id  "
							+ "and f.delete_status=0 and pr.delete_status=0");
			sql.setParameter("feature_name", featuresModel.getFeature_name());
			sql.setParameter("fk_release_id", featuresModel.getFk_release_id());
			list = sql.getResultList();
			if (list.size() > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public ResponseDTO updateFeatures(FeaturesModel featuresModel) {
		// TODO Auto-generated method stub
		ResponseDTO responseDTO = new ResponseDTO();
		ResponseDTO responseDTO1 = new ResponseDTO();
		FeaturesAssignmentModel featuresAssignmentModel = null;

		int update = 0;
		int updateForIsactvie = 0;
		int fkAssignmentId = 0;
		try {
			int featureId = 0;
			int is_active = 0;

			// String query1="select f.fk_assignment_id,f.feature_id from features f where
			// f.feature_id='"+featuresModel.getFeature_id()+"' and f.delete_status=0;";
			String query1 = "select f.fk_assignment_id,f.feature_id,fa.is_active from features f,features_assignment fa "
					+ " where f.fk_assignment_id=fa.feature_assignment_id and f.feature_id='"
					+ featuresModel.getFeature_id() + "' " + " and f.delete_status=0;";
			List<Object> list = entityManager.createNativeQuery(query1).getResultList();

			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();

					fkAssignmentId = Integer.parseInt(String.valueOf(obj[0]));
					featureId = Integer.parseInt(String.valueOf(obj[1]));
					is_active = Integer.parseInt(String.valueOf(obj[2]));

				}
			}
			// getFeatureAssignmentDeatiles(featuresModel.getFeature_id(),featuresModel.getAssigned_To());
			responseDTO = noChangesFoundForFeatures(featuresModel);
			// System.err.println("innnnnnnnnnn::::::::::"+responseDTO.toString());
			featuresAssignmentModel = new FeaturesAssignmentModel();
			featuresAssignmentModel.setFk_feature_id(featuresModel.getFeature_id());
			featuresAssignmentModel.setAssigned_by(featuresModel.getUpdated_by());
			featuresAssignmentModel.setAssigned_to(featuresModel.getAssigned_To());
			featuresAssignmentModel.setIs_active(is_active);
			responseDTO1 = noChangesFoundForFeaturesAssignement(featuresAssignmentModel);
			// System.err.println("innnnnnnnnnn::::::::::"+responseDTO1.toString());
			boolean checkisActive = checkIsActive(featuresModel.getFeature_id());
			boolean checkFeatureName = FeatureExistencyCheckForUpdate(featuresModel);
			if (responseDTO1.getStatusCode() == 1) {

				if (!checkisActive) {
					String query = "update features_assignment fa set fa.is_active=0 where fa.fk_feature_id='"
							+ featuresModel.getFeature_id() + "' AND fa.is_active=1;";
					updateForIsactvie = entityManager.createNativeQuery(query).executeUpdate();
					if (updateForIsactvie > 0) {
						featuresAssignmentModel.setIs_active(1);
						entityManager.persist(featuresAssignmentModel);
						fkAssignmentId = featuresAssignmentModel.getFeature_assignment_id();
					}
				} else {
					featuresAssignmentModel = new FeaturesAssignmentModel();
					featuresAssignmentModel.setFk_feature_id(featuresModel.getFeature_id());
					featuresAssignmentModel.setAssigned_by(featuresModel.getUpdated_by());
					featuresAssignmentModel.setAssigned_to(featuresModel.getAssigned_To());
					featuresAssignmentModel.setIs_active(1);
					entityManager.persist(featuresAssignmentModel);
					fkAssignmentId = featuresAssignmentModel.getFeature_assignment_id();
				}
			}
			if (responseDTO.getStatusCode() == 1) {

				if (checkFeatureName) {
					String query = "update features set feature_name='" + featuresModel.getFeature_name()
							+ "',feature_description='" + featuresModel.getFeature_description() + "',story_points='"
							+ featuresModel.getStory_points() + "',updated_by='" + featuresModel.getUpdated_by() + "',"
							+ "fk_feature_type_id='" + featuresModel.getFk_feature_type_id()
							+ "',fk_feature_status_id='" + featuresModel.getFk_feature_status_id() + "',"
							+ "assigned_to='" + featuresModel.getAssigned_To() + "',fk_release_id='"
							+ featuresModel.getFk_release_id() + "',fk_assignment_id='" + fkAssignmentId
							+ "',updated_on=CURRENT_TIMESTAMP where delete_status=0 and feature_id='"
							+ featuresModel.getFeature_id() + "'";
					update = entityManager.createNativeQuery(query).executeUpdate();
					if (update > 0) {

						responseDTO.setMessage("Feature updated successfully");
						responseDTO.setStatusCode(1);
					} else {
						responseDTO.setMessage("Feature updation Failed");
						responseDTO.setStatusCode(0);
					}
				} else {
					responseDTO.setMessage("Feature Already exists");
					responseDTO.setStatusCode(2);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage("Exception occured");
		}
		return responseDTO;
	}

	public boolean checkIsActive(int fkfeatureId) {

		String hql1 = "select * from features_assignment fa where fa.fk_feature_id='" + fkfeatureId
				+ "' and fa.is_active=1;";
		boolean isempty = entityManager.createNativeQuery(hql1).getResultList().isEmpty();
		if (isempty) {
			return true;
		}
		return false;
	}

	public boolean FeatureExistencyCheckForUpdate(FeaturesModel featuresModel) {
		String query = "select f.feature_name,f.fk_release_id from features f,product_releases pr "
				+ " where f.fk_release_id=pr.release_id and f.feature_name='" + featuresModel.getFeature_name()
				+ "' and f.fk_release_id='" + featuresModel.getFk_release_id() + "' "
				+ " and f.delete_status=0 and f.feature_id!='" + featuresModel.getFeature_id() + "';";
		boolean releasePhaseslist = entityManager.createNativeQuery(query).getResultList().isEmpty();
		if (releasePhaseslist) {
			return true;
		}
		return false;
	}

	public ResponseDTO noChangesFoundForFeatures(FeaturesModel featuresModel) {
		// System.err.println("featuresModel:::::::::::::::"+featuresModel.toString());
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			Query query = entityManager
					.createQuery("from FeaturesModel where feature_name=:feature_name and feature_id=:feature_id "
							+ "and feature_description=:feature_description and story_points=:story_points and fk_feature_type_id=:fk_feature_type_id and"
							+ "  fk_release_id=:fk_release_id and assigned_to=:assigned_to and fk_feature_status_id=:fk_feature_status_id and delete_status=:delete_status");
			query.setParameter("feature_name", featuresModel.getFeature_name());
			query.setParameter("feature_id", featuresModel.getFeature_id());
			query.setParameter("feature_description", featuresModel.getFeature_description());
			query.setParameter("story_points", featuresModel.getStory_points());
			query.setParameter("fk_feature_type_id", featuresModel.getFk_feature_type_id());
			query.setParameter("fk_release_id", featuresModel.getFk_release_id());
			query.setParameter("assigned_to", featuresModel.getAssigned_To());
			query.setParameter("fk_feature_status_id", featuresModel.getFk_feature_status_id());
			query.setParameter("delete_status", 0);

			List list = query.getResultList();
			// System.err.println("list:::::::::"+list.size());
			if (list.size() == 0) {
				responseDTO.setStatusCode(1);
				responseDTO.setMessage("Changes Found");
				// System.err.println("innnnnnnnnnnnnnn
				// Features::::::::::FoundChanges:::::::::::"+list);

			} else {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage("No Changes Found");
				// System.err.println("innnnnnnnnnnnnnn Features::::::::::NO Changes:::::::::::"
				// + list);
			}

		} catch (Exception e) {
			System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
			e.printStackTrace();
			// TODO: handle exception
		}
		// System.err.println("responseDTO<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+responseDTO.toString());
		return responseDTO;
	}

	public ResponseDTO noChangesFoundForFeaturesAssignement(FeaturesAssignmentModel featuresAssignmentModel) {
		ResponseDTO responseDTO1 = new ResponseDTO();
		try {
			Query query = entityManager.createQuery("from FeaturesAssignmentModel where  fk_feature_id=:fk_feature_id "
					+ "and assigned_by=:assigned_by  and assigned_to=:assigned_to and is_active=:is_active ");
			// query.setParameter("feature_assignment_id",
			// featuresAssignmentModel.getFeature_assignment_id());
			query.setParameter("fk_feature_id", featuresAssignmentModel.getFk_feature_id());
			query.setParameter("assigned_by", featuresAssignmentModel.getAssigned_by());
			query.setParameter("is_active", featuresAssignmentModel.getIs_active());
			query.setParameter("assigned_to", featuresAssignmentModel.getAssigned_to());

			List list = query.getResultList();
			if (list.size() == 0) {
				responseDTO1.setStatusCode(1);
				responseDTO1.setMessage("Changes Found");
				// System.err.println("innnnnnnnnnnnnnn ::::::::::Found
				// Changes:::::::::::"+list);

			} else {
				responseDTO1.setStatusCode(0);
				responseDTO1.setMessage("No Changes Found");
				// System.err.println("innnnnnnnnnnnnnn FeaturesAssignment::::::::::NO
				// Changes:::::::::::"+list);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		// System.err.println("response in asign::::::::::"+responseDTO.toString());
		return responseDTO1;
	}

	@Override
	public ResponseDTO deleteFeatures(FeaturesModel featuresModel) {
		ResponseDTO responseDTO = new ResponseDTO();
		int update = 0;
		try {
			Query hql = entityManager
					.createQuery("update FeaturesModel set delete_status=:delete_status,deleted_by=:deleted_by"
							+ " ,deleted_on=CURRENT_TIMESTAMP  where  feature_id=:feature_id");
			hql.setParameter("deleted_by", featuresModel.getDelete_status());
			hql.setParameter("delete_status", featuresModel.getDeleted_by());
			hql.setParameter("feature_id", featuresModel.getFeature_id());
			// hql.setParameter("fk_release_id", featuresModel.getFk_release_id());

			update = hql.executeUpdate();
			if (update > 0) {
				responseDTO.setMessage("Feature Deleted successfully");
				responseDTO.setStatusCode(1);
			} else {
				responseDTO.setMessage("Feature Deletion Failed");
				responseDTO.setStatusCode(0);
			}
		} catch (Exception e) {
			responseDTO.setStatusCode(0);
			responseDTO.setMessage(e.getMessage());
		}
		// System.err.println("userResponseDTO:::" + responseDTO.toString());
		return responseDTO;
	}

	@Override
	public List<FeatureslistModel> getFeatureDetailsByFeatureId(int featureId) {
		// TODO Auto-generated method stub
		List<FeatureslistModel> featurelist = new ArrayList<>();
		FeatureslistModel model = null;
		String createdOn[] = null;
		try {
			String query = "select f.feature_id,f.feature_name,f.feature_description,f.story_points, "
					+ "ft.feature_type_id,f.fk_feature_status_id,f.fk_assignment_id,f.assigned_to,f.created_on,f.created_by, "
					+ "pr.release_name,pr.release_id "
					+ "from features f,features_type ft ,features_status fs,product_releases pr "
					+ "where  f.fk_feature_type_id=ft.feature_type_id and f.fk_feature_status_id=fs.status_id and "
					+ "f.fk_release_id=pr.release_id and  f.delete_status=0 and f.feature_id='" + featureId
					+ "' and (f.created_by=1 " + " or f.assigned_to=1);";

			List<Object> list = entityManager.createNativeQuery(query).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					model = new FeatureslistModel();
					model.setFeature_id(Integer.parseInt(String.valueOf(obj[0])));
					model.setFeature_name(String.valueOf(obj[1]));
					model.setFeature_description(String.valueOf(obj[2]));
					model.setStory_points(Integer.parseInt(String.valueOf(obj[3])));
					model.setFeature_type_id(Integer.parseInt(String.valueOf(obj[4])));
					model.setFeature_status_id(Integer.parseInt(String.valueOf(obj[5])));
					model.setFkAssignmentId(Integer.parseInt(String.valueOf(obj[6])));
					model.setAssignedTo(Integer.parseInt(String.valueOf(obj[7])));
					createdOn = String.valueOf(obj[8]).split(" ");
					model.setCreatedOn(createdOn[0]);
					model.setCreatedBy(Integer.parseInt(String.valueOf(obj[9])));
					model.setReleaseName(String.valueOf(obj[10]));
					model.setFkReleaseId(Integer.parseInt(String.valueOf(obj[11])));

					List<FeaturesUploadModel> files = getFeatureFile(model.getFeature_id());
					model.setFiles(files);
					featurelist.add(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return featurelist;
	}

	@Override
	public List<FeatureslistModel> getFeatureDetailsByReleaseId(int fkReleaseId) {
		List<FeatureslistModel> featurelist = new ArrayList<>();
		FeatureslistModel model = null;
		String createdOn[] = null;
		try {
			String query = "select f.feature_id,f.feature_name,f.feature_description,f.story_points, "
					+ "ft.feature_type,fs.status,f.created_on," + "pr.release_name,pr.release_id "
					+ "from features f,features_type ft ,features_status fs,product_releases pr "
					+ "where  f.fk_feature_type_id=ft.feature_type_id and f.fk_feature_status_id=fs.status_id and "
					+ "f.fk_release_id=pr.release_id and  f.delete_status=0 and f.fk_release_id='" + fkReleaseId
					+ "' and (f.created_by=1" + " or f.assigned_to=1);";

			List<Object> list = entityManager.createNativeQuery(query).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					model = new FeatureslistModel();
					model.setFeature_id(Integer.parseInt(String.valueOf(obj[0])));
					model.setFeature_name(String.valueOf(obj[1]));
					model.setFeature_description(String.valueOf(obj[2]));
					model.setStory_points(Integer.parseInt(String.valueOf(obj[3])));
					model.setFeature_type(String.valueOf(obj[4]));
					model.setStatus(String.valueOf(obj[5]));
					createdOn = String.valueOf(obj[6]).split(" ");
					model.setCreatedOn(createdOn[0]);
					model.setReleaseName(String.valueOf(obj[7]));
					model.setFkReleaseId(Integer.parseInt(String.valueOf(obj[8])));
					List<FeaturesUploadModel> files = getFeatureFile(model.getFeature_id());
					model.setFiles(files);
					featurelist.add(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return featurelist;
	}

	@Override
	public List<FeatureslistModel> getFeatureDetailsByProductId(int fkproductId) {
		// TODO Auto-generated method stub
		List<FeatureslistModel> featurelist = new ArrayList<>();
		FeatureslistModel model = null;
		String createdOn[] = null;
		try {
			String query = "select f.feature_id,f.feature_name,f.feature_description,f.story_points,ft.feature_type,"
					+ "fs.status,f.created_on,pr.release_name,pr.release_id,p.product_id,p.product_name "
					+ "from features f,features_type ft ,features_status fs,product_releases pr,product p "
					+ "where  f.fk_feature_type_id=ft.feature_type_id and f.fk_feature_status_id=fs.status_id and "
					+ "f.fk_release_id=pr.release_id and pr.fk_product_id=p.product_id AND  f.delete_status=0 and p.product_id='"
					+ fkproductId + "' and " + "(f.created_by=1 or f.assigned_to=1);";

			List<Object> list = entityManager.createNativeQuery(query).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					model = new FeatureslistModel();
					model.setFeature_id(Integer.parseInt(String.valueOf(obj[0])));
					model.setFeature_name(String.valueOf(obj[1]));
					model.setFeature_description(String.valueOf(obj[2]));
					model.setStory_points(Integer.parseInt(String.valueOf(obj[3])));
					model.setFeature_type(String.valueOf(obj[4]));
					model.setStatus(String.valueOf(obj[5]));
					createdOn = String.valueOf(obj[6]).split(" ");
					model.setCreatedOn(createdOn[0]);
					model.setReleaseName(String.valueOf(obj[7]));
					model.setFkReleaseId(Integer.parseInt(String.valueOf(obj[8])));
					model.setFkproductId(Integer.parseInt(String.valueOf(obj[9])));
					model.setProductName(String.valueOf(obj[10]));
					List<FeaturesUploadModel> files = getFeatureFile(model.getFeature_id());
					model.setFiles(files);
					featurelist.add(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return featurelist;
	}

	@Override
	public List<FeatureslistModel> getAllFeatures() {
		List<FeatureslistModel> featurelist = new ArrayList<>();
		FeatureslistModel model = null;
		String createdOn[] = null;
		try {

			String Query = "select  f.feature_id,  f.feature_name, f.feature_description, f.story_points, ft.feature_type, "
					+ "  fs.status, pr.release_name, (select  count(f.feature_id) from features f "
					+ " where f.delete_status = 0 ) as count,f.created_on,f.fk_assignment_id,f.assigned_to from  features f ,features_type ft   "
					+ ",features_status fs , product_releases pr "
					+ " where f.fk_feature_type_id = ft.feature_type_id AND f.fk_feature_status_id = fs.status_id AND "
					+ "  f.fk_release_id = pr.release_id AND " + "f.delete_status = 0;";

			List<Object> list = entityManager.createNativeQuery(Query).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					model = new FeatureslistModel();
					model.setFeature_id(Integer.parseInt(String.valueOf(obj[0])));
					model.setFeature_name(String.valueOf(obj[1]));
					model.setFeature_description(String.valueOf(obj[2]));
					model.setStory_points(Integer.parseInt(String.valueOf(obj[3])));
					model.setFeature_type(String.valueOf(obj[4]));
					model.setStatus(String.valueOf(obj[5]));
					model.setReleaseName(String.valueOf(obj[6]));
					model.setTotalFeaturesCount(Integer.parseInt(String.valueOf(obj[7])));
					createdOn = String.valueOf(obj[8]).split(" ");
					model.setCreatedOn(createdOn[0]);
					model.setFkAssignmentId(Integer.parseInt(String.valueOf(obj[9])));
					model.setAssignedTo(Integer.parseInt(String.valueOf(obj[10])));
					List<FeaturesUploadModel> files = getFeatureFile(model.getFeature_id());
					model.setFiles(files);
					featurelist.add(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return featurelist;
	}

	@Override
	public List<FeaturesUploadModel> getFeatureFile(int fkfeatureId) {
		FeaturesUploadModel model = null;
		List<FeaturesUploadModel> filesList = new ArrayList<>();

		try {
			Query hql = entityManager.createNativeQuery(
					"SELECT * FROM features_attachments where fk_feature_id=:fk_feature_id and delete_status=0");
			hql.setParameter("fk_feature_id", fkfeatureId);
			List<Object> list = hql.getResultList();
			if (list.size() > 0) {
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					model = new FeaturesUploadModel();
					model.setAttachmentId(Integer.parseInt(String.valueOf(obj[0])));
					model.setFileType(String.valueOf(obj[1]));
					model.setFileName(String.valueOf(obj[2]));
					model.setFileLocation(String.valueOf(obj[3]));
					model.setFkfeatureId(Integer.parseInt(String.valueOf(obj[5])));
					filesList.add(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filesList;
	}

	@Override
	public ResponseDTO deletefeatureFiles(FeaturesUploadModel featuresUploadModel) {
		ResponseDTO responseDTO = new ResponseDTO();
		int update = 0;
		try {
			Query hql = entityManager
					.createQuery("update FeaturesUploadModel set delete_status=1,deleted_by=:deleted_by"
							+ " ,deleted_on=CURRENT_TIMESTAMP  where  feature_attachment_id=:feature_attachment_id and fk_feature_id=:fk_feature_id");
			hql.setParameter("feature_attachment_id", featuresUploadModel.getAttachmentId());
			hql.setParameter("fk_feature_id", featuresUploadModel.getFkfeatureId());
			hql.setParameter("deleted_by", featuresUploadModel.getDeletedBy());
			update = hql.executeUpdate();
			if (update > 0) {
				responseDTO.setMessage("Feature File Deleted successfully");
				responseDTO.setStatusCode(1);
			} else {
				responseDTO.setMessage("Feature File Deletion Failed");
				responseDTO.setStatusCode(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}

	@Override
	public ResponseDTO updateStatus(int featureId, int statusId) {
		ResponseDTO responseDTO = new ResponseDTO();
		int update = 0;
		try {
			Query hql = entityManager.createQuery(
					"update FeaturesModel set updated_on=CURRENT_TIMESTAMP ,fk_feature_status_id=:fk_feature_status_id where  feature_id=:feature_id");
			hql.setParameter("fk_feature_status_id", statusId);
			hql.setParameter("feature_id", featureId);
			update = hql.executeUpdate();
			if (update > 0) {
				responseDTO.setMessage("Feature Status updated successfully");
				responseDTO.setStatusCode(1);
			} else {
				responseDTO.setMessage("Feature updation Failed");
				responseDTO.setStatusCode(0);
			}
		} catch (Exception e) {
			responseDTO.setStatusCode(0);
			responseDTO.setMessage("Exception occured");
		}
		return responseDTO;
	}

	@Override
	public int countReadyToShipFeatures() {
		// TODO Auto-generated method stub
		// List<FeatureslistModel> countReadyToShip = new ArrayList<>();
		List<BigInteger> countReadyToShip = null;

		try {
			String query = "select count(f.feature_id) from features f JOIN\n"
					+ "features_status fs ON f.fk_feature_status_id=fs.status_id  "
					+ "where fs.status_id =5 and fs.delete_status=0 and f.delete_status=0;";

			countReadyToShip = entityManager.createNativeQuery(query).getResultList();

		}
		// System.err.println("AFTER QUERY ::::::::::::::::::::::::::::"+list);

		catch (Exception e) {
			e.printStackTrace();
		}
		return countReadyToShip.get(0).intValue();
	}

	@Override
	public List<FeatureslistModel> getFeaturesIsBackLogOne() {
		// TODO Auto-generated method stub
		List<FeatureslistModel> isbackLogOnefeaturelist = new ArrayList<>();
		FeatureslistModel model = null;
		try {

			String Query = "select f.feature_id,f.feature_name,f.feature_description,f.story_points,ft.feature_type, "
					+ "fs.status from features f,features_type ft,features_status fs "
					+ " where f.fk_feature_type_id=ft.feature_type_id and ft.delete_status=0 and f.fk_feature_status_id= "
					+ "fs.status_id and fs.delete_status=0  "
					+ " and f.delete_status=0 and f.is_backlog=1 and f.fk_release_id=0";

			List<Object> list = entityManager.createNativeQuery(Query).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					model = new FeatureslistModel();
					model.setFeature_id(Integer.parseInt(String.valueOf(obj[0])));
					model.setFeature_name(String.valueOf(obj[1]));
					model.setFeature_description(String.valueOf(obj[2]));
					model.setStory_points(Integer.parseInt(String.valueOf(obj[3])));

					model.setFeature_type(String.valueOf(obj[4]));
					model.setStatus(String.valueOf(obj[5]));

					isbackLogOnefeaturelist.add(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isbackLogOnefeaturelist;
	}

	@Override
	public List<FeatureslistModel> getFeaturesIsBackLogZero() {
		// TODO Auto-generated method stub
		List<FeatureslistModel> isbackLogZerofeaturelist = new ArrayList<>();
		FeatureslistModel model = null;
		try {
			String Query = "select f.feature_id,f.feature_name,f.feature_description,f.story_points,ft.feature_type,"
					+ "fs.status from features f,features_type ft,features_status fs"
					+ " where f.fk_feature_type_id=ft.feature_type_id and ft.delete_status=0 and f.fk_feature_status_id= "
					+ "fs.status_id and fs.delete_status=0 " + " and f.delete_status=0 and f.is_backlog=0;";

			List<Object> list = entityManager.createNativeQuery(Query).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					model = new FeatureslistModel();
					model.setFeature_id(Integer.parseInt(String.valueOf(obj[0])));
					model.setFeature_name(String.valueOf(obj[1]));
					model.setFeature_description(String.valueOf(obj[2]));
					model.setStory_points(Integer.parseInt(String.valueOf(obj[3])));

					model.setFeature_type(String.valueOf(obj[4]));
					model.setStatus(String.valueOf(obj[5]));
					// model.setIs_active(Integer.parseInt(String.valueOf(obj[6])));
					// model.setReleaseName(String.valueOf(obj[7]));
					// model.setTotalFeaturesCount(Integer.parseInt(String.valueOf(obj[8])));
					// List<FeaturesUploadModel> files = getFeatureFile(model.getFeature_id());
					// model.setFiles(files);
					isbackLogZerofeaturelist.add(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isbackLogZerofeaturelist;
	}

	@Override
	public ResponseDTO movetoBackLogOne(FeaturesModel featuresModel) {
		// TODO Auto-generated method stub
		ResponseDTO responseDTO = new ResponseDTO();
		int update = 0;
		try {
			// responseDTO=noChangesFoundForFeaturesAssignment(featuresAssignmentModel);

			String query = "update features f set f.is_backlog=1,f.fk_release_id=0,issue_id=0 where f.feature_id='"
					+ featuresModel.getFeature_id() + "' and " + "f.fk_release_id='" + featuresModel.getFk_release_id()
					+ "' and f.delete_status=0 and f.is_backlog=0;";
			update = entityManager.createNativeQuery(query).executeUpdate();
			if (update > 0) {
				responseDTO.setMessage("Feature BackLogOne Moved successfully");
				responseDTO.setStatusCode(1);
			} else {
				responseDTO.setMessage("Feature BackLogOne Moved Failed");
				responseDTO.setStatusCode(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage("Exception occured");
		}
		return responseDTO;
	}

	@Override
	public ResponseDTO movetoBackLogZero(FeaturesModel featuresModel) {
		// TODO Auto-generated method stub
		ResponseDTO responseDTO = new ResponseDTO();
		int update = 0;
		try {
			// responseDTO=noChangesFoundForFeaturesAssignment(featuresAssignmentModel);

			String query = "update features f set f.is_backlog=0,f.fk_release_id='" + featuresModel.getFk_release_id()
					+ "' where f.feature_id='" + featuresModel.getFeature_id() + "' "
					+ " and f.delete_status=0 and f.is_backlog=1;";
			update = entityManager.createNativeQuery(query).executeUpdate();
			if (update > 0) {
				responseDTO.setMessage("Feature BackLogZero Moved successfully");
				responseDTO.setStatusCode(1);
			} else {
				responseDTO.setMessage("Feature BackLogZero Moved Failed");
				responseDTO.setStatusCode(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage("Exception occured");
		}
		return responseDTO;
	}

	@Override
	public List<FeatureslistModel> getAllFeaturesType() {
		// TODO Auto-generated method stub
		List<FeatureslistModel> featurelistType = new ArrayList<>();
		FeatureslistModel model = null;
		try {
			String Query = "SELECT feature_type_id,feature_type,jira_issue_type_id FROM features_type where delete_status=0;";
			List<Object> list = entityManager.createNativeQuery(Query).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					model = new FeatureslistModel();
					model.setFeature_type_id(Integer.parseInt(String.valueOf(obj[0])));
					model.setFeature_type(String.valueOf(obj[1]));
					model.setJira_issue_type_id(Integer.parseInt(String.valueOf(obj[2])));
					featurelistType.add(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return featurelistType;
	}

	@Override
	public List<FeatureslistModel> getAllFeaturesStatus() {
		// TODO Auto-generated method stub
		List<FeatureslistModel> featurelistStatus = new ArrayList<>();
		FeatureslistModel model = null;
		try {

			String Query = "SELECT fs.status_id,fs.status FROM features_status fs where fs.delete_status=0;";

			List<Object> list = entityManager.createNativeQuery(Query).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					model = new FeatureslistModel();
					model.setFeature_status_id(Integer.parseInt(String.valueOf(obj[0])));
					model.setStatus(String.valueOf(obj[1]));

					featurelistStatus.add(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return featurelistStatus;
	}

	@Override
	public List<FeatureslistModel> getFeatureDetailsByStatusIdAndReleaseId(int statusId, int fkReleaseId) {
		// TODO Auto-generated method stub
		List<FeatureslistModel> featurelist = new ArrayList<>();
		FeatureslistModel model = null;
		String createdOn[] = null;
		try {
			String query = "select f.feature_id,f.feature_name,f.feature_description,f.story_points,"
					+ "ft.feature_type,fs.status,f.created_on,pr.release_name,pr.release_id "
					+ "from features f,features_type ft ,features_status fs,product_releases pr "
					+ "where  f.fk_feature_type_id=ft.feature_type_id and f.fk_feature_status_id=fs.status_id and "
					+ "f.fk_release_id=pr.release_id and  f.delete_status=0 and pr.delete_status=0 and f.fk_feature_status_id='"
					+ statusId + "' and f.fk_release_id='" + fkReleaseId + "' "
					+ " and (f.created_by=1 or f.assigned_to=1);";

			List<Object> list = entityManager.createNativeQuery(query).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					model = new FeatureslistModel();
					model.setFeature_id(Integer.parseInt(String.valueOf(obj[0])));
					model.setFeature_name(String.valueOf(obj[1]));
					model.setFeature_description(String.valueOf(obj[2]));
					model.setStory_points(Integer.parseInt(String.valueOf(obj[3])));
					model.setFeature_type(String.valueOf(obj[4]));
					model.setStatus(String.valueOf(obj[5]));
					createdOn = String.valueOf(obj[6]).split(" ");
					model.setCreatedOn(createdOn[0]);
					model.setReleaseName(String.valueOf(obj[7]));
					model.setFkReleaseId(Integer.parseInt(String.valueOf(obj[8])));
					List<FeaturesUploadModel> files = getFeatureFile(model.getFeature_id());
					model.setFiles(files);
					featurelist.add(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return featurelist;
	}

	@Override
	public List<FeatureslistModel> getFeaturesIsBackLogOneByFkProductId(int fkproductId) {
		// TODO Auto-generated method stub
		List<FeatureslistModel> isbackLogOnefeaturelist = new ArrayList<>();
		FeatureslistModel model = null;
		try {

			String Query = "select f.feature_id,f.feature_name,f.feature_description,f.story_points,ft.feature_type, "
					+ "fs.status,f.fk_product_id from features f,features_type ft,features_status fs  "
					+ " where f.fk_feature_type_id=ft.feature_type_id and ft.delete_status=0 and f.fk_feature_status_id=  "
					+ "fs.status_id and fs.delete_status=0 "
					+ " and f.delete_status=0 and f.is_backlog=1 and f.fk_release_id=0 and f.fk_product_id='"
					+ fkproductId + "'";

			List<Object> list = entityManager.createNativeQuery(Query).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					model = new FeatureslistModel();
					model.setFeature_id(Integer.parseInt(String.valueOf(obj[0])));
					model.setFeature_name(String.valueOf(obj[1]));
					model.setFeature_description(String.valueOf(obj[2]));
					model.setStory_points(Integer.parseInt(String.valueOf(obj[3])));

					model.setFeature_type(String.valueOf(obj[4]));
					model.setStatus(String.valueOf(obj[5]));
					model.setFkproductId(Integer.parseInt(String.valueOf(obj[6])));

					isbackLogOnefeaturelist.add(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isbackLogOnefeaturelist;
	}

	@Override
	public List<FeatureslistModel> getFeaturesTypeByFeatureTypeId(int fk_feature_typeId) {
		// TODO Auto-generated method stub
		List<FeatureslistModel> featureTypelist = new ArrayList<>();
		FeatureslistModel model = null;
		try {

			String Query = "SELECT ft.feature_type,ft.jira_issue_type FROM features_type ft where ft.feature_type_id='"
					+ fk_feature_typeId + "' and " + "ft.delete_status=0;";

			List<Object> list = entityManager.createNativeQuery(Query).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					model = new FeatureslistModel();

					model.setFeature_type(String.valueOf(obj[0]));
					model.setJira_issue_type(String.valueOf(obj[1]));

					featureTypelist.add(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return featureTypelist;
	}

	@Override
	public List<FeatureslistModel> getAllUnMapedFeatures() {
		// TODO Auto-generated method stub
		List<FeatureslistModel> upmapedFeaturelist = new ArrayList<>();
		FeatureslistModel model = null;
		try {

			String Query = "SELECT f.feature_id,f.feature_name,f.feature_description,ft.feature_type_id,ft.feature_type,pr.release_name,p.product_name,pr.release_id,p.fk_jira_boardid,pr.fk_jira_sprint_id "
					+ " FROM features f,features_type ft,product_releases pr,product p "
					+ "where  f.fk_feature_type_id=ft.feature_type_id and f.fk_release_id=pr.release_id and "
					+ "f.fk_product_id=p.product_id and "
					+ "f.delete_status=0 and f.issue_id=0 and pr.delete_status=0 and p.delete_status=0 and "
					+ "ft.delete_status=0;" + "";

			List<Object> list = entityManager.createNativeQuery(Query).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					if (Integer.parseInt(String.valueOf(obj[8])) > 0 && Integer.parseInt(String.valueOf(obj[9])) > 0) {
						model = new FeatureslistModel();
						model.setFeature_id(Integer.parseInt(String.valueOf(obj[0])));
						model.setFeature_name(String.valueOf(obj[1]));
						model.setFeature_description(String.valueOf(obj[2]));
						model.setFeature_type_id(Integer.parseInt(String.valueOf(obj[3])));
						model.setFeature_type(String.valueOf(obj[4]));
						model.setReleaseName(String.valueOf(obj[5]));
						model.setProductName(String.valueOf(obj[6]));
						model.setFkReleaseId(Integer.parseInt(String.valueOf(obj[7])));
						model.setProductMapped(true);
						model.setReleaseMapped(true);
						upmapedFeaturelist.add(model);
					} else if (Integer.parseInt(String.valueOf(obj[8])) > 0
							&& Integer.parseInt(String.valueOf(obj[9])) == 0) {

						model = new FeatureslistModel();
						model.setFeature_id(Integer.parseInt(String.valueOf(obj[0])));
						model.setFeature_name(String.valueOf(obj[1]));
						model.setFeature_description(String.valueOf(obj[2]));
						model.setFeature_type_id(Integer.parseInt(String.valueOf(obj[3])));
						model.setFeature_type(String.valueOf(obj[4]));
						model.setReleaseName(String.valueOf(obj[5]));
						model.setProductName(String.valueOf(obj[6]));
						model.setFkReleaseId(Integer.parseInt(String.valueOf(obj[7])));
						model.setProductMapped(true);
						model.setReleaseMapped(false);
						upmapedFeaturelist.add(model);

					}

					else {

						model = new FeatureslistModel();

						model.setFeature_id(Integer.parseInt(String.valueOf(obj[0])));
						model.setFeature_name(String.valueOf(obj[1]));
						model.setFeature_description(String.valueOf(obj[2]));
						model.setFeature_type_id(Integer.parseInt(String.valueOf(obj[3])));
						model.setFeature_type(String.valueOf(obj[4]));
						model.setReleaseName(String.valueOf(obj[5]));
						model.setProductName(String.valueOf(obj[6]));
						model.setFkReleaseId(Integer.parseInt(String.valueOf(obj[7])));
						model.setProductMapped(false);
						model.setReleaseMapped(false);
						upmapedFeaturelist.add(model);

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return upmapedFeaturelist;
	}

	@Override
	public List<FeatureslistModel> getAllMapedFeatures() {
		// TODO Auto-generated method stub
		List<FeatureslistModel> mapedFeaturelist = new ArrayList<>();
		FeatureslistModel model = null;
		try {

			String Query = "SELECT f.feature_id,f.feature_name,f.feature_description,ft.feature_type_id,ft.feature_type,pr.release_name,p.product_name,pr.release_id "
					+ " FROM features f,features_type ft,product_releases pr,product p  "
					+ "where  f.fk_feature_type_id=ft.feature_type_id and f.fk_release_id=pr.release_id and "
					+ "f.fk_product_id=p.product_id and "
					+ "f.delete_status=0 and f.issue_id>0 and pr.delete_status=0 and p.delete_status=0 and "
					+ "ft.delete_status=0;";

			List<Object> list = entityManager.createNativeQuery(Query).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					model = new FeatureslistModel();
					model.setFeature_id(Integer.parseInt(String.valueOf(obj[0])));
					model.setFeature_name(String.valueOf(obj[1]));
					model.setFeature_description(String.valueOf(obj[2]));
					model.setFeature_type_id(Integer.parseInt(String.valueOf(obj[3])));
					model.setFeature_type(String.valueOf(obj[4]));
					model.setReleaseName(String.valueOf(obj[5]));
					model.setProductName(String.valueOf(obj[6]));
					model.setFkReleaseId(Integer.parseInt(String.valueOf(obj[7])));
					mapedFeaturelist.add(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapedFeaturelist;
	}

}
