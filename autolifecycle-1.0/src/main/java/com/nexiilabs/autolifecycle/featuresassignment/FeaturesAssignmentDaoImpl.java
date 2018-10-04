package com.nexiilabs.autolifecycle.featuresassignment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.nexiilabs.autolifecycle.releases.ResponseDTO;

@Transactional
@Repository
public class FeaturesAssignmentDaoImpl implements FeaturesAssignmentDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ResponseDTO addFeaturesAssignment(FeaturesAssignmentModel featuresAssignmentModel) {
		// TODO Auto-generated method stub
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			 boolean isvalidFeatureAssignment = isValidFeatureAssignment(featuresAssignmentModel.getFk_feature_id());

			 if(isvalidFeatureAssignment) {
			if (featuresAssignmentModel.getFk_feature_id() != 0) {
				entityManager.persist(featuresAssignmentModel);
				responseDTO.setMessage("FeaturesAssignment added successfully");
				responseDTO.setStatusCode(1);
			} else {
				responseDTO.setMessage("FeaturesAssignment addition Failed");
				responseDTO.setStatusCode(0);
			}
			 }else {
					responseDTO.setMessage("FeaturesAssignment  alredy Assigned");
					responseDTO.setStatusCode(2);
			 }

		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage(e.getMessage());
		}

		return responseDTO;
	}

	public boolean isValidFeatureAssignment(int fkfeatureId) {
		String query = "SELECT * FROM features_assignment fa where fa.fk_feature_id='" + fkfeatureId + "'";
		boolean featureassignmentlist = entityManager.createNativeQuery(query).getResultList().isEmpty();
		if (featureassignmentlist) {
			return true;
		}
		return false;
	}

	@Override
	public ResponseDTO updateFeaturesAssignment(FeaturesAssignmentModel featuresAssignmentModel) {
		// TODO Auto-generated method stub
		ResponseDTO responseDTO = new ResponseDTO();
		int update = 0;
		try {
			responseDTO=noChangesFoundForFeaturesAssignment(featuresAssignmentModel);
			if(responseDTO.getStatusCode()==1){
				String query="update features_assignment set fk_feature_id='"+featuresAssignmentModel.getFk_feature_id()+"',assigned_to='"+featuresAssignmentModel.getAssigned_to()+"' where \n" + 
						"feature_assignment_id='"+featuresAssignmentModel.getFeature_assignment_id()+"' and is_active=1";
				update =  entityManager.createNativeQuery(query).executeUpdate();
				if (update > 0) {
					responseDTO.setMessage("FeaturesAssignment updated successfully");
					responseDTO.setStatusCode(1);
				} else {
					responseDTO.setMessage("FeaturesAssignment updation Failed");
					responseDTO.setStatusCode(0);
				}
			}else{
				return responseDTO;
			}
		}catch(Exception e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage("Exception occured");
		}
		return responseDTO;
	}
	public ResponseDTO noChangesFoundForFeaturesAssignment(FeaturesAssignmentModel featuresAssignmentModel){
		ResponseDTO responseDTO=new ResponseDTO();
		try{
			Query query=entityManager.createQuery("from FeaturesAssignmentModel where fk_feature_id=:fk_feature_id and assigned_to=:assigned_to "
					+ "and feature_assignment_id=:feature_assignment_id");
			query.setParameter("fk_feature_id", featuresAssignmentModel.getFk_feature_id());
			query.setParameter("feature_assignment_id", featuresAssignmentModel.getFeature_assignment_id());
			query.setParameter("assigned_to", featuresAssignmentModel.getAssigned_to());
			//query.setParameter("is_active", featuresAssignmentModel.getIs_active());
		
			
			List list=query.getResultList();
			if(list.size()==0){
				responseDTO.setStatusCode(1);
				responseDTO.setMessage("Changes Found");
				//System.err.println("innnnnnnnnnnnnnn ::::::::::Found Changes:::::::::::"+list);
	
			}else{
				responseDTO.setStatusCode(0);
				responseDTO.setMessage("No Changes Found");
				//System.err.println("innnnnnnnnnnnnnn ::::::::::NO Changes:::::::::::"+list);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return responseDTO;
	}
	@Override
	public ResponseDTO deleteFeaturesAssignment(FeaturesAssignmentModel featuresAssignmentModel) {
		// TODO Auto-generated method stub
		ResponseDTO responseDTO = new ResponseDTO();
		int update = 0;
		try {
			Query hql = entityManager
					.createQuery("update FeaturesAssignmentModel set is_active=:is_active  where  feature_assignment_id=:feature_assignment_id");
			hql.setParameter("is_active",0);
			hql.setParameter("feature_assignment_id", featuresAssignmentModel.getFeature_assignment_id());
		
			update = hql.executeUpdate();
			if (update > 0) {
				responseDTO.setMessage("FeaturesAssignment Deleted successfully");
				responseDTO.setStatusCode(1);
			} else {
				responseDTO.setMessage("FeaturesAssignment Deletion Failed");
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
	public List<FeaturesAssignmtlistModel> getFeatureAssignmentDetails(int featureassignmentId) {
		// TODO Auto-generated method stub
		List<FeaturesAssignmtlistModel> featuresAssignmtlist = new ArrayList<>();
		FeaturesAssignmtlistModel model = null;
		try {
			String  query = "select " + 
					"    fa.feature_assignment_id," + 
					"    fa.assigned_by," + 
					"    fa.assigned_to," + 
					"    f.feature_name," + 
					"    f.feature_description," + 
					"	f.story_points " + 
					"from " + 
					"    features_assignment fa" + 
					"        JOIN" + 
					"    features f ON fa.fk_feature_id = f.feature_id " + 
					"where " + 
					"    feature_assignment_id = '"+featureassignmentId+"' and fa.is_active=1;";
			
			List<Object> list = entityManager.createNativeQuery(query).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					model = new FeaturesAssignmtlistModel();
					model.setFeature_assignment_id(Integer.parseInt(String.valueOf(obj[0])));
					model.setAssigned_by(Integer.parseInt(String.valueOf(obj[1])));
					model.setAssigned_to(Integer.parseInt(String.valueOf(obj[2])));
					model.setFeature_name(String.valueOf(obj[3]));
					model.setFeature_description(String.valueOf(obj[4]));
					model.setStory_points(Integer.parseInt(String.valueOf(obj[5])));
					featuresAssignmtlist.add(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return featuresAssignmtlist;
	}

	@Override
	public List<FeaturesAssignmtlistModel> getAllFeaturesAssignment() {
		// TODO Auto-generated method stub
		List<FeaturesAssignmtlistModel> featuresAssignmtlist = new ArrayList<>();
		FeaturesAssignmtlistModel model = null;
		try {
			String  query = "select " + 
					"    fa.feature_assignment_id," + 
					"    fa.assigned_by," + 
					"    fa.assigned_to," + 
					"    f.feature_name," + 
					"    f.feature_description," + 
					"	f.story_points " + 
					"from " + 
					"    features_assignment fa" + 
					"        JOIN" + 
					"    features f ON fa.fk_feature_id = f.feature_id " + 
					"where " + 
					"   fa.is_active=1;";
			
			List<Object> list = entityManager.createNativeQuery(query).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					model = new FeaturesAssignmtlistModel();
					model.setFeature_assignment_id(Integer.parseInt(String.valueOf(obj[0])));
					model.setAssigned_by(Integer.parseInt(String.valueOf(obj[1])));
					model.setAssigned_to(Integer.parseInt(String.valueOf(obj[2])));
					model.setFeature_name(String.valueOf(obj[3]));
					model.setFeature_description(String.valueOf(obj[4]));
					model.setStory_points(Integer.parseInt(String.valueOf(obj[5])));
					featuresAssignmtlist.add(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return featuresAssignmtlist;
	}



}
