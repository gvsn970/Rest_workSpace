package com.nexiilabs.autolifecycle.releasephases;

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

import com.nexiilabs.autolifecycle.releasephasesmaping.ReleasePhasesmapingModel;
import com.nexiilabs.autolifecycle.releases.ResponseDTO;

@Transactional
@Repository
public class ReleasePhasesDaoImpl implements ReleasePhasesDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ResponseDTO addReleasePhasesForPhase(ReleasePhasesModel releasePhasesModel, String startDate, String endDate,
			int fkreleaseId) {
		ResponseDTO responseDTO = new ResponseDTO();
		ReleasePhasesmapingModel releasePhasesmapingModel = new ReleasePhasesmapingModel();
		try {
			String internalDate = null;
			// String endDate = releasePhasesmapingModel.getEnd_date();
			String externaldate = null;

			String query = "select r.release_date_internal,r.release_date_external from " + "product_releases r where "
					+ " r.release_id ='" + fkreleaseId + "' and r.delete_status=0";
			List<Object> list = entityManager.createNativeQuery(query).getResultList();

			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					internalDate = String.valueOf(obj[0]);
					externaldate = String.valueOf(obj[1]);
				}
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = sdf.parse(internalDate);
			Date date2 = sdf.parse(endDate);
			boolean check = releasePhaseExistencyCheckForPhase(releasePhasesModel,fkreleaseId);
			boolean checkMileStoneName = releasePhaseExistencyCheckForMileStone(releasePhasesModel, fkreleaseId);

			if (date1.compareTo(date2) >= 0) {
				if (!check) {
					if(!checkMileStoneName) {
					entityManager.persist(releasePhasesModel);
					if (releasePhasesModel.getRelease_phase_id() != 0) {
						releasePhasesmapingModel.setFk_release_phase_id(releasePhasesModel.getRelease_phase_id());
						releasePhasesmapingModel.setFk_release_phase_type_id(1);
						releasePhasesmapingModel.setFk_release_id(fkreleaseId);
						releasePhasesmapingModel.setStart_date(startDate);
						releasePhasesmapingModel.setEnd_date(endDate);
						releasePhasesmapingModel.setDescription(releasePhasesModel.getRelease_phase_description());
						releasePhasesmapingModel.setCreated_by(1);
						releasePhasesmapingModel.setFk_status_id(1);
						entityManager.persist(releasePhasesmapingModel);
						responseDTO.setReleasephaseId(releasePhasesmapingModel.getFk_release_phase_id());
						responseDTO.setReleasephasemapId(releasePhasesmapingModel.getRelease_phase_map_id());
						responseDTO.setReleaseId(releasePhasesmapingModel.getFk_release_id());
						responseDTO.setMessage("Release Phase added successfully");
						responseDTO.setStatusCode(1);
					} else {
						responseDTO.setMessage("Release Phase addition Failed");
						responseDTO.setStatusCode(0);
					}
				}else {
					responseDTO.setMessage("MileStone Name Shoud not Added into phase");
					responseDTO.setStatusCode(2);
				}
					
				} else {
					responseDTO.setMessage("Release Phase Already exists");
					responseDTO.setStatusCode(3);
				}
			} else if (date1.compareTo(date2) < 0) {
				responseDTO.setMessage("ReleasePhase End Date Should be Less Then Release Internal Date");
				responseDTO.setStatusCode(4);
			}

		} catch (Exception e) {
			//e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage(e.getMessage());
		}

		return responseDTO;
	}

	@Override
	public ResponseDTO addReleasePhasesForMileStone(ReleasePhasesModel releasePhasesModel, String endDate,
			int fkreleaseId) {
		// TODO Auto-generated method stub
		ResponseDTO responseDTO = new ResponseDTO();
		ReleasePhasesmapingModel releasePhasesmapingModel = new ReleasePhasesmapingModel();
		try {

			String internalDate = null;
			// String endDate = releasePhasesmapingModel.getEnd_date();
			String externaldate = null;

			String query = "select r.release_date_internal,r.release_date_external from " + "product_releases r where "
					+ " r.release_id ='" + fkreleaseId + "' and r.delete_status=0";
			List<Object> list = entityManager.createNativeQuery(query).getResultList();

			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();

					internalDate = String.valueOf(obj[0]);

					externaldate = String.valueOf(obj[1]);

				}
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = sdf.parse(internalDate);
			Date date2 = sdf.parse(endDate);
			boolean check = releasePhaseExistencyCheckForMileStone(releasePhasesModel, fkreleaseId);
			boolean checkPhase = releasePhaseExistencyCheckForPhase(releasePhasesModel,fkreleaseId);
			if (date1.compareTo(date2) >= 0) {
				if (!check) {
					if(!checkPhase) {
					entityManager.persist(releasePhasesModel);
					if (releasePhasesModel.getRelease_phase_id() != 0) {
						releasePhasesmapingModel.setFk_release_phase_id(releasePhasesModel.getRelease_phase_id());
						releasePhasesmapingModel.setFk_release_phase_type_id(2);
						releasePhasesmapingModel.setFk_release_id(fkreleaseId);
						// releasePhasesmapingModel.setStart_date(startDate);
						releasePhasesmapingModel.setEnd_date(endDate);
						releasePhasesmapingModel.setDescription(releasePhasesModel.getRelease_phase_description());
						releasePhasesmapingModel.setCreated_by(1);
						releasePhasesmapingModel.setFk_status_id(1);
						entityManager.persist(releasePhasesmapingModel);
						responseDTO.setReleasephaseId(releasePhasesmapingModel.getFk_release_phase_id());
						responseDTO.setReleasephasemapId(releasePhasesmapingModel.getRelease_phase_map_id());
						responseDTO.setReleaseId(releasePhasesmapingModel.getFk_release_id());
						responseDTO.setMessage(" MileStone added successfully");
						responseDTO.setStatusCode(1);
					} else {
						responseDTO.setMessage(" MileStone addition Failed");
						responseDTO.setStatusCode(0);
					}
				}else {
					responseDTO.setMessage("Phase Name Shoud not Added into mileStone");
					responseDTO.setStatusCode(2);
				}
				} else {
					responseDTO.setMessage("MileStone Already exists");
					responseDTO.setStatusCode(3);
				}
			} else if (date1.compareTo(date2) < 0) {
				responseDTO.setMessage(" MileStone End Date Should be Less Then Release Internal Date");
				responseDTO.setStatusCode(4);
			}

		} catch (Exception e) {
			//e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage(e.getMessage());
		}

		return responseDTO;
	}

	private boolean releasePhaseExistencyCheckForPhase(ReleasePhasesModel releasePhasesModel, int fkreleaseId) {
		List list = null;
		try {
			Query sql = entityManager.createNativeQuery(
					"select rpm.fk_release_id,rp.release_phase_name from release_phases rp,release_phases_mapping rpm " + 
					"where rp.release_phase_name=:release_phase_name and " + 
					"rp.fk_release_phase_type=1 and  rp.delete_status=0 " + 
					"and rp.release_phase_id=rpm.fk_release_phase_id and rpm.fk_release_id=:release_id");
			
			sql.setParameter("release_phase_name", releasePhasesModel.getRelease_phase_name());
			sql.setParameter("release_id", fkreleaseId);

			list = sql.getResultList();
			//System.err.println(":::::::::::::::::"+list.size());
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

	private boolean releasePhaseExistencyCheckForMileStone(ReleasePhasesModel releasePhasesModel, int fkreleaseId) {
		List list = null;
		try {
			Query sql = entityManager.createNativeQuery(
					"select rpm.fk_release_id,rp.release_phase_name from release_phases rp,release_phases_mapping rpm  " + 
					"where rp.release_phase_name=:release_phase_name and " + 
					"rp.fk_release_phase_type=2 and rp.delete_status=0 " + 
					"and rp.release_phase_id=rpm.fk_release_phase_id and rpm.delete_status=0 and rpm.fk_release_phase_type_id=2 and rpm.fk_release_id=:release_id");
			sql.setParameter("release_phase_name", releasePhasesModel.getRelease_phase_name());
			sql.setParameter("release_id", fkreleaseId);
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
	public ResponseDTO updateReleasePhasesForPhase(ReleasePhasesModel releasePhasesModel, String startDate,
			String endDate, int fkreleaseId) {
	
		ResponseDTO responseDTO = new ResponseDTO();
		ResponseDTO responseDTO1 = new ResponseDTO();
		ReleasePhasesmapingModel releasePhasesmapingModel = new ReleasePhasesmapingModel();
		int update = 0;
		int update2 = 0;
		try {
			String internalDate = null;
			// String endDate = releasePhasesmapingModel.getEnd_date();
			String externaldate = null;

			String sqlquery = "select r.release_date_internal,r.release_date_external from " + "product_releases r where "
					+ " r.release_id ='" + fkreleaseId + "' and r.delete_status=0";
			List<Object> list = entityManager.createNativeQuery(sqlquery).getResultList();

			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();

					internalDate = String.valueOf(obj[0]);

					externaldate = String.valueOf(obj[1]);
				}
			}
			boolean check = releasePhaseExistencyCheckForPhaseUpdate(releasePhasesModel,fkreleaseId);
			boolean checkMileStoneNameEmpty=mileStoneExistencyCheckForPhaseUpdate(releasePhasesModel,fkreleaseId);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = sdf.parse(internalDate);
			Date date2 = sdf.parse(endDate);
			releasePhasesmapingModel.setFk_release_phase_id(releasePhasesModel.getRelease_phase_id());
			releasePhasesmapingModel.setFk_release_phase_type_id(1);
			releasePhasesmapingModel.setFk_release_id(fkreleaseId);
			releasePhasesmapingModel.setStart_date(startDate);
			releasePhasesmapingModel.setEnd_date(endDate);
			releasePhasesmapingModel.setDescription(releasePhasesModel.getRelease_phase_description());
			releasePhasesmapingModel.setUpdated_by(1);
			responseDTO = noChangesFoundForReleasePhasesForPhase(releasePhasesModel);
			responseDTO1 = noChangesFoundForReleasePhasesForPhaseMaping(releasePhasesmapingModel);
		
			if (date1.compareTo(date2) >= 0) {
			if (responseDTO.getStatusCode() == 1 ||responseDTO1.getStatusCode() == 1 ) {
				if(check) {
				if(checkMileStoneNameEmpty) {
				String query = "update release_phases set fk_release_phase_type=1,release_phase_name='"
						+ releasePhasesModel.getRelease_phase_name() + "',"
						+ "updated_on=CURRENT_TIMESTAMP,updated_by='" + releasePhasesModel.getUpdated_by()
						+ "' where delete_status=0 and release_phase_id='" + releasePhasesModel.getRelease_phase_id()
						+ "'";
				update = entityManager.createNativeQuery(query).executeUpdate();
				if (update > 0) {
			
						String query1 = "update release_phases_mapping set fk_release_phase_type_id=1,fk_release_phase_id='"
								+ releasePhasesmapingModel.getFk_release_phase_id() + "',fk_release_id='"
								+ releasePhasesmapingModel.getFk_release_id() + "',"
								+ "updated_on=CURRENT_TIMESTAMP,updated_by='" + releasePhasesmapingModel.getUpdated_by()
								+ "',start_date='" + releasePhasesmapingModel.getStart_date() + "',end_date='"
								+ releasePhasesmapingModel.getEnd_date()+"', description='"+releasePhasesmapingModel.getDescription()
								+ "' where delete_status=0  and fk_release_phase_id='"
								+ releasePhasesmapingModel.getFk_release_phase_id() + "' and fk_release_id='"+releasePhasesmapingModel.getFk_release_id()+"'";
						update2 = entityManager.createNativeQuery(query1).executeUpdate();
						if (update2 > 0) {

							responseDTO.setReleasephasemapId(releasePhasesmapingModel.getRelease_phase_map_id());

							responseDTO.setMessage("ReleasePhase  updated successfully");
							responseDTO.setStatusCode(1);
						} else {
							responseDTO.setMessage("ReleasePhase  updation Failed");
							responseDTO.setStatusCode(0);
						}
					
				}
				}else {
					responseDTO.setStatusCode(2);
					responseDTO.setMessage("MileStone Name Shoud not Updated into phase");
					return responseDTO;
				}
				}
				else {
					responseDTO.setStatusCode(3);
					responseDTO.setMessage("ReleasePhase Already exists");
					return responseDTO;
				}
			}
			else {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage("No Changes Found");
				return responseDTO;
			}
			}else if (date1.compareTo(date2) < 0) {
				responseDTO.setMessage("ReleasePhase End Date Should be Less Then Release Internal Date");
				responseDTO.setStatusCode(4);
			}

		} catch (Exception e) {
			responseDTO.setStatusCode(0);
			//System.err.println(":::::::::::::::::::::::"+responseDTO);
			responseDTO.setMessage("Exception occured");
		}
		return responseDTO;
	}
	public boolean releasePhaseExistencyCheckForPhaseUpdate(ReleasePhasesModel releasePhasesModel, int fkreleaseId) {
		String query = "select rpm.fk_release_id,rp.release_phase_name from release_phases rp,release_phases_mapping rpm  " + 
				"where rp.release_phase_name='"+releasePhasesModel.getRelease_phase_name()+"' and " + 
				"rp.fk_release_phase_type=1 and  rp.delete_status=0 " + 
				"and rp.release_phase_id=rpm.fk_release_phase_id and rpm.fk_release_id='"+fkreleaseId+"' and rpm.fk_release_phase_id!='"+releasePhasesModel.getRelease_phase_id()+"'";
		boolean releasePhaseslist = entityManager.createNativeQuery(query).getResultList().isEmpty();
		if (releasePhaseslist) {
			return true;
		}
		return false;
	}
	public boolean mileStoneExistencyCheckForPhaseUpdate(ReleasePhasesModel releasePhasesModel, int fkreleaseId) {
		String query = "select rpm.fk_release_id,rp.release_phase_name from release_phases rp,release_phases_mapping rpm  " + 
				"where rp.release_phase_name='"+releasePhasesModel.getRelease_phase_name()+"' and " + 
				"rp.fk_release_phase_type=2 and  rp.delete_status=0 " + 
				"and rp.release_phase_id=rpm.fk_release_phase_id and rpm.fk_release_id='"+fkreleaseId+"' and rpm.fk_release_phase_id!='"+releasePhasesModel.getRelease_phase_id()+"'";
		boolean releasePhaseslist = entityManager.createNativeQuery(query).getResultList().isEmpty();
		if (releasePhaseslist) {
			return true;
		}
		return false;
	}
	public ResponseDTO noChangesFoundForReleasePhasesForPhase(ReleasePhasesModel releasePhasesModel) {
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			Query query = entityManager.createQuery(
					"from ReleasePhasesModel where release_phase_name=:release_phase_name and release_phase_id=:release_phase_id "
							+ " and"
							+ " delete_status=:delete_status ");
			query.setParameter("release_phase_name", releasePhasesModel.getRelease_phase_name());
			query.setParameter("release_phase_id", releasePhasesModel.getRelease_phase_id());
			//query.setParameter("release_phase_description", releasePhasesModel.getRelease_phase_description());
			//query.setParameter("fk_release_phase_type", 1);
			// query.setParameter("is_default", releasePhasesModel.getIs_default());
			query.setParameter("delete_status", 0);

			List list = query.getResultList();
			if (list.size() == 0) {
				responseDTO.setStatusCode(1);
				responseDTO.setMessage("Changes Found");
				// System.err.println("innnnnnnnnnnnnnn ::::::::::Found
				// Changes:::::::::::"+list);

			} else {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage("No Changes Found");
				// System.err.println("innnnnnnnnnnnnnn ::::::::::NO Changes:::::::::::"+list);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return responseDTO;
	}

	public ResponseDTO noChangesFoundForReleasePhasesForPhaseMaping(ReleasePhasesmapingModel releasePhasesmapingModel) {
		ResponseDTO responseDTO1 = new ResponseDTO();
		try {
			//System.err.println("releasePhasesmapingModel"+releasePhasesmapingModel.toString());
			Query query = entityManager.createQuery(
					"from ReleasePhasesmapingModel where fk_release_phase_id=:fk_release_phase_id "
							+ "and fk_release_id=:fk_release_id  and start_date=:start_date and end_date=:end_date "
							+ "and  delete_status=:delete_status and description=:description ");
			//query.setParameter("release_phase_map_id", releasePhasesmapingModel.getRelease_phase_map_id());
			query.setParameter("fk_release_phase_id", releasePhasesmapingModel.getFk_release_phase_id());
			query.setParameter("fk_release_id", releasePhasesmapingModel.getFk_release_id());
			query.setParameter("start_date", releasePhasesmapingModel.getStart_date());
			query.setParameter("end_date", releasePhasesmapingModel.getEnd_date());
			query.setParameter("description", releasePhasesmapingModel.getDescription());
			// query.setParameter("is_default", releasePhasesModel.getIs_default());
			query.setParameter("delete_status", 0);

			List list = query.getResultList();
			if (list.size() == 0) {
				//responseDTO.setReleasephasemapId(Integer.parseInt((String) list.get(0)));
				responseDTO1.setStatusCode(1);
				responseDTO1.setMessage("Changes Found");
				// System.err.println("innnnnnnnnnnnnnn ::::::::::Found Changes:::::::::::"+list);

			} else {
				responseDTO1.setStatusCode(0);
				responseDTO1.setMessage("No Changes Found");
				// System.err.println("innnnnnnnnnnnnnn ::::::::::NO Changes:::::::::::"+list);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//System.err.println("in::::::::::::::::"+responseDTO1.toString());
		return responseDTO1;
	}
	
	@Override
	public ResponseDTO updateReleasePhasesForMileStone(ReleasePhasesModel releasePhasesModel, String endDate,
			int fkreleaseId) {
		// TODO Auto-generated method stub
		ResponseDTO responseDTO = new ResponseDTO();
		ResponseDTO responseDTO1 = new ResponseDTO();
		ReleasePhasesmapingModel releasePhasesmapingModel = new ReleasePhasesmapingModel();
		int update = 0;
		int update2 = 0;
		try {
			String internalDate = null;
			// String endDate = releasePhasesmapingModel.getEnd_date();
			String externaldate = null;

			String sqlquery = "select r.release_date_internal,r.release_date_external from " + "product_releases r where "
					+ " r.release_id ='" + fkreleaseId + "' and r.delete_status=0";
			List<Object> list = entityManager.createNativeQuery(sqlquery).getResultList();

			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();

					internalDate = String.valueOf(obj[0]);

					externaldate = String.valueOf(obj[1]);

				}
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date1 = sdf.parse(internalDate);
			Date date2 = sdf.parse(endDate);
			boolean checkPhaseNameEmpty = releasePhaseExistencyCheckForPhaseUpdate(releasePhasesModel,fkreleaseId);
			boolean checkMileStoneNameEmpty=mileStoneExistencyCheckForPhaseUpdate(releasePhasesModel,fkreleaseId);
			releasePhasesmapingModel.setFk_release_phase_id(releasePhasesModel.getRelease_phase_id());
			releasePhasesmapingModel.setFk_release_phase_type_id(2);
			releasePhasesmapingModel.setFk_release_id(fkreleaseId);
			//releasePhasesmapingModel.setStart_date(startDate);
			releasePhasesmapingModel.setEnd_date(endDate);
			releasePhasesmapingModel.setDescription(releasePhasesModel.getRelease_phase_description());
			releasePhasesmapingModel.setUpdated_by(1);
			responseDTO = noChangesFoundForReleasePhasesForMileStone(releasePhasesModel);
			responseDTO1 = noChangesFoundForReleasePhasesForMileStone(releasePhasesmapingModel);
			//boolean check = releasePhaseExistencyCheckForMileStone(releasePhasesModel, fkreleaseId);
			if (date1.compareTo(date2) >= 0) {
				if (responseDTO.getStatusCode() == 1 ||responseDTO1.getStatusCode() == 1 ) {
				
					if(checkMileStoneNameEmpty) {
						if(checkPhaseNameEmpty) {
					String query = "update release_phases set fk_release_phase_type=2,release_phase_name='"
							+ releasePhasesModel.getRelease_phase_name() 
							+ "',updated_on=CURRENT_TIMESTAMP,updated_by='" + releasePhasesModel.getUpdated_by()
							+ "' where delete_status=0 and release_phase_id='" + releasePhasesModel.getRelease_phase_id()
							+ "'";
					update = entityManager.createNativeQuery(query).executeUpdate();
					if (update > 0) {
					
							String query1 = "update release_phases_mapping set fk_release_phase_type_id=2,fk_release_phase_id='"
									+ releasePhasesmapingModel.getFk_release_phase_id() + "',fk_release_id='"
									+ releasePhasesmapingModel.getFk_release_id() 
									+ "',updated_on=CURRENT_TIMESTAMP,updated_by='" + releasePhasesmapingModel.getUpdated_by()
									+ "',end_date='"
									+ releasePhasesmapingModel.getEnd_date()+"',description='"+releasePhasesmapingModel.getDescription()
									+ "' where delete_status=0  and fk_release_phase_id='"
									+ releasePhasesmapingModel.getFk_release_phase_id() + "' and fk_release_id='"+releasePhasesmapingModel.getFk_release_id()+"'";
							update2 = entityManager.createNativeQuery(query1).executeUpdate();
							if (update2 > 0) {

								responseDTO.setReleasephasemapId(releasePhasesmapingModel.getRelease_phase_map_id());

								responseDTO.setMessage("MileStone updated successfully");
								responseDTO.setStatusCode(1);
							} else {
								responseDTO.setMessage("MileStone updation Failed");
								responseDTO.setStatusCode(0);
							}
						
					}
					}else {
						responseDTO.setStatusCode(2);
						responseDTO.setMessage("Phase Name Shoud not Updated into MileStone");
						return responseDTO;
					}
				}else {
					responseDTO.setStatusCode(3);
					responseDTO.setMessage("MileStone Already exists");
					return responseDTO;
				}
					}
				else {
					responseDTO.setStatusCode(0);
					responseDTO.setMessage("No Changes Found");
					return responseDTO;
				}
				}else if (date1.compareTo(date2) < 0) {
					responseDTO.setMessage("ReleasePhase MileStone End Date Should be Less Then Release Internal Date");
					responseDTO.setStatusCode(4);
				}

	
		}catch(Exception e) {
			responseDTO.setStatusCode(0);
			responseDTO.setMessage("Exception occured");
		}
		return responseDTO;
	}
	
	public ResponseDTO noChangesFoundForReleasePhasesForMileStone(ReleasePhasesModel releasePhasesModel) {
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			Query query = entityManager.createQuery(
					"from ReleasePhasesModel where release_phase_name=:release_phase_name and release_phase_id=:release_phase_id "
							+ " and"
							+ " delete_status=:delete_status ");
			query.setParameter("release_phase_name", releasePhasesModel.getRelease_phase_name());
			query.setParameter("release_phase_id", releasePhasesModel.getRelease_phase_id());
			//query.setParameter("release_phase_description", releasePhasesModel.getRelease_phase_description());
			//query.setParameter("fk_release_phase_type", 2);
			// query.setParameter("is_default", releasePhasesModel.getIs_default());
			query.setParameter("delete_status", 0);

			List list = query.getResultList();
			if (list.size() == 0) {
				responseDTO.setStatusCode(1);
				responseDTO.setMessage("Changes Found");
			

			} else {
				responseDTO.setStatusCode(0);
				responseDTO.setMessage("No Changes Found");
				// System.err.println("innnnnnnnnnnnnnn ::::::::::NO Changes:::::::::::"+list);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return responseDTO;
	}


	public ResponseDTO noChangesFoundForReleasePhasesForMileStone(ReleasePhasesmapingModel releasePhasesmapingModel) {
		ResponseDTO responseDTO1 = new ResponseDTO();
		try {
			//System.err.println("releasePhasesmapingModel"+releasePhasesmapingModel.toString());
			Query query = entityManager.createQuery(
					"from ReleasePhasesmapingModel where fk_release_phase_id=:fk_release_phase_id "
							+ "and fk_release_id=:fk_release_id  and  end_date=:end_date "
							+ "and  delete_status=:delete_status and description=:description ");
			//query.setParameter("release_phase_map_id", releasePhasesmapingModel.getRelease_phase_map_id());
			query.setParameter("fk_release_phase_id", releasePhasesmapingModel.getFk_release_phase_id());
			query.setParameter("fk_release_id", releasePhasesmapingModel.getFk_release_id());
			query.setParameter("description", releasePhasesmapingModel.getDescription());
			query.setParameter("end_date", releasePhasesmapingModel.getEnd_date());
			//query.setParameter("fk_release_phase_type_id", 2);
			// query.setParameter("is_default", releasePhasesModel.getIs_default());
			query.setParameter("delete_status", 0);

			List list = query.getResultList();
			if (list.size() == 0) {
				//responseDTO.setReleasephasemapId(Integer.parseInt((String) list.get(0)));
				responseDTO1.setStatusCode(1);
				responseDTO1.setMessage("Changes Found");
				// System.err.println("innnnnnnnnnnnnnn ::::::::::Found Changes:::::::::::"+list);

			} else {
				responseDTO1.setStatusCode(0);
				responseDTO1.setMessage("No Changes Found");
				// System.err.println("innnnnnnnnnnnnnn ::::::::::NO Changes:::::::::::"+list);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//System.err.println("in::::::::::::::::"+responseDTO1.toString());
		return responseDTO1;
	}
	
	@Override
	public ResponseDTO deleteReleasePhases(ReleasePhasesmapingModel mapingModel) {
		// TODO Auto-generated method stub
		ResponseDTO responseDTO = new ResponseDTO();
		int update = 0;
		try {
			Query hql = entityManager
					.createQuery("update ReleasePhasesmapingModel set delete_status=:delete_status,deleted_by=:deleted_by"
							+ " ,deleted_on=CURRENT_TIMESTAMP  where  fk_release_phase_id=:fk_release_phase_id and fk_release_id=:fk_release_id");
			hql.setParameter("deleted_by", mapingModel.getDelete_status());
			hql.setParameter("delete_status", mapingModel.getDeleted_by());
			hql.setParameter("fk_release_phase_id", mapingModel.getFk_release_phase_id());
			hql.setParameter("fk_release_id", mapingModel.getFk_release_id());
			update = hql.executeUpdate();
			if (update > 0) {
				responseDTO.setMessage("ReleasePhase Deleted successfully");
				responseDTO.setStatusCode(1);
			} else {
				responseDTO.setMessage("ReleasePhase Deletion Failed");
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
	public List<ReleasePhaseslistModel> getReleasePhaseDetails(int releasephaseId) {
		// TODO Auto-generated method stub
		List<ReleasePhaseslistModel> releasePhaseslist = new ArrayList<>();
		ReleasePhaseslistModel model = null;
		int fkreleaseId=0;
		String startDate[]=null;
		String endDate[]=null;
		try {
			String query = "select rp.release_phase_id,rp.release_phase_name,rp.release_phase_description, " + 
					"rpm.start_date,rpm.end_date,rpt.phase_type,r.release_name " + 
					"from release_phases rp , release_phase_type rpt,release_phases_mapping rpm,product_releases r " + 
					"where rp.release_phase_id=rpm.fk_release_phase_id and rpm.fk_release_phase_type_id=rpt.phase_type_id " + 
					"and rp.release_phase_id='"+releasephaseId+"' and rp.delete_status=0 and rpm.delete_status=0 and rpt.delete_status=0 " + 
					"and rpm.fk_release_id=r.release_id";

			List<Object> list = entityManager.createNativeQuery(query).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					model = new ReleasePhaseslistModel();
					model.setRelease_phase_id(Integer.parseInt(String.valueOf(obj[0])));
					model.setRelease_phase_name(String.valueOf(obj[1]));
					model.setRelease_phase_description(String.valueOf(obj[2]));
					startDate=String.valueOf(obj[3]).split(" ");
					endDate=String.valueOf(obj[4]).split(" ");
					//model.setFk_release_phase_type(String.valueOf(obj[3]));
					model.setStartdate(startDate[0]);
					model.setEndDate(endDate[0]);
					model.setFk_release_phase_type(String.valueOf(obj[5]));
					model.setReleaseName(String.valueOf(obj[6]));

					//model.setIs_default(Integer.parseInt(String.valueOf(obj[3])));

					List<ReleasePhasesUploadModel> files = getReleasePhaseFile(model.getRelease_phase_id(),fkreleaseId);
					model.setFiles(files);
					releasePhaseslist.add(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return releasePhaseslist;
	}

	@Override
	public List<ReleasePhasesUploadModel> getReleasePhaseFile(int fkreleasephaseId ,int fkreleaseId) {
		// TODO Auto-generated method stub
		ReleasePhasesUploadModel model = null;
		List<ReleasePhasesUploadModel> filesList = new ArrayList<>();
		try {
			Query hql = entityManager.createNativeQuery(
					"SELECT * FROM release_phase_attachments where fk_release_phase_id=:fk_release_phase_id and fk_release_id=:fk_release_id and delete_status=0");
			hql.setParameter("fk_release_phase_id", fkreleasephaseId);
			hql.setParameter("fk_release_id", fkreleaseId);
			List<Object> list = hql.getResultList();
			if (list.size() > 0) {
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					model = new ReleasePhasesUploadModel();
					model.setAttachmentId(Integer.parseInt(String.valueOf(obj[0])));
					model.setFileType(String.valueOf(obj[1]));
					model.setFileName(String.valueOf(obj[2]));
					model.setFileLocation(String.valueOf(obj[3]));
					model.setFkreleaseId(Integer.parseInt(String.valueOf(obj[5])));
					model.setFkreleasephaseId(Integer.parseInt(String.valueOf(obj[6])));
					model.setFkreleasephasemapId(Integer.parseInt(String.valueOf(obj[7])));
					filesList.add(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filesList;
	}

	@Override
	public List<ReleasePhaseslistModel> getAllReleasePhases() {
		// TODO Auto-generated method stub
		List<ReleasePhaseslistModel> releasePhaseslist = new ArrayList<>();
		ReleasePhaseslistModel model = null;
		String startDate[]=null;
		int fkreleaseId=0;
		String endDate[]=null;
		try {
			String query = "select rp.release_phase_id,rp.release_phase_name,rp.release_phase_description, " + 
					"rpm.start_date,rpm.end_date,rpt.phase_type,r.release_name " + 
					"from release_phases rp , release_phase_type rpt,release_phases_mapping rpm,product_releases r " + 
					"where rp.release_phase_id=rpm.fk_release_phase_id and rpm.fk_release_phase_type_id=rpt.phase_type_id " + 
					" and rp.delete_status=0 and rpm.delete_status=0 and rpt.delete_status=0 " + 
					"and rpm.fk_release_id=r.release_id";

			List<Object> list = entityManager.createNativeQuery(query).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					model = new ReleasePhaseslistModel();
					model.setRelease_phase_id(Integer.parseInt(String.valueOf(obj[0])));
					model.setRelease_phase_name(String.valueOf(obj[1]));
					model.setRelease_phase_description(String.valueOf(obj[2]));
					startDate=String.valueOf(obj[3]).split(" ");
					endDate=String.valueOf(obj[4]).split(" ");
					//model.setFk_release_phase_type(String.valueOf(obj[3]));
					model.setStartdate(startDate[0]);
					model.setEndDate(endDate[0]);
					
					model.setFk_release_phase_type(String.valueOf(obj[5]));
					model.setReleaseName(String.valueOf(obj[6]));

					//model.setIs_default(Integer.parseInt(String.valueOf(obj[3])));

					List<ReleasePhasesUploadModel> files = getReleasePhaseFile(model.getRelease_phase_id(),fkreleaseId);
					model.setFiles(files);
					releasePhaseslist.add(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return releasePhaseslist;
	}

	@Override
	public ResponseDTO deleteReleasePhaseFiles(ReleasePhasesUploadModel releasePhasesUploadModel) {
		// TODO Auto-generated method stub
		ResponseDTO responseDTO = new ResponseDTO();
		int update = 0;
		try {
			Query hql = entityManager
					.createQuery("update ReleasePhasesUploadModel set delete_status=1,deleted_by=:deleted_by"
							+ " ,deleted_on=CURRENT_TIMESTAMP  where  release_phase_attachment_id=:release_phase_attachment_id");
			hql.setParameter("release_phase_attachment_id", releasePhasesUploadModel.getAttachmentId());
		//	hql.setParameter("fk_release_phase_id", releasePhasesUploadModel.getFkreleasephaseId());
		//	hql.setParameter("fk_release_id", releasePhasesUploadModel.getFkreleaseId());
			hql.setParameter("deleted_by", releasePhasesUploadModel.getDeletedBy());
			update = hql.executeUpdate();
			if (update > 0) {
				responseDTO.setMessage("ReleasePhase File Deleted successfully");
				responseDTO.setStatusCode(1);
			} else {
				responseDTO.setMessage("ReleasePhase File Deletion Failed");
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
	public List<ReleasePhaseslistModel> getAllReleasePhasesByReleaseId(int fkreleaseId) {
		List<ReleasePhaseslistModel> releasePhaseslist = new ArrayList<>();
		ReleasePhaseslistModel model = null;
		String startDate[]=null;
		String endDate[]=null;
		try {
			
			String query="select rp.release_phase_id,rp.release_phase_name,rpm.description, " + 
					"rpm.start_date,rpm.end_date,rpt.phase_type,r.release_name " + 
					"from release_phases rp , release_phase_type rpt,release_phases_mapping rpm,product_releases r " + 
					"where rp.release_phase_id=rpm.fk_release_phase_id and rpm.fk_release_phase_type_id=rpt.phase_type_id " + 
					"and rpm.fk_release_id='"+fkreleaseId+"' and r.delete_status=0 and rp.delete_status=0 and rpm.delete_status=0 and rpt.delete_status=0 " + 
					"and rpm.fk_release_id=r.release_id";
		

			List<Object> list = entityManager.createNativeQuery(query).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					model = new ReleasePhaseslistModel();
					model.setRelease_phase_id(Integer.parseInt(String.valueOf(obj[0])));
					model.setRelease_phase_name(String.valueOf(obj[1]));
					model.setRelease_phase_description(String.valueOf(obj[2]));
					startDate=String.valueOf(obj[3]).split(" ");
					endDate=String.valueOf(obj[4]).split(" ");
					//model.setFk_release_phase_type(String.valueOf(obj[3]));
					model.setStartdate(startDate[0]);
					model.setEndDate(endDate[0]);
					model.setFk_release_phase_type(String.valueOf(obj[5]));
					model.setReleaseName(String.valueOf(obj[6]));

				//	model.setIs_default(Integer.parseInt(String.valueOf(obj[3])));

					List<ReleasePhasesUploadModel> files = getReleasePhaseFile(model.getRelease_phase_id(),fkreleaseId);
					model.setFiles(files);
					releasePhaseslist.add(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return releasePhaseslist;
	}

	@Override
	public int getreleaseMapId(int releasephaseId, int fkreleaseId) {
		// TODO Auto-generated method stub
		List<Integer> releasePhaseMapId =null;
		int releaseMapId=0;
		try {
			String query = "select release_phase_map_id from release_phases_mapping rpm " + 
					"where rpm.fk_release_phase_id='"+releasephaseId+"' and rpm.fk_release_id='"+fkreleaseId+"' and rpm.delete_status=0";

			releasePhaseMapId = entityManager.createNativeQuery(query).getResultList();
			//System.err.println("releasePhaseMapId::::::::::::"+releasePhaseMapId.toString());
			if(releasePhaseMapId.size()==0) {
				return releaseMapId;
			}else {
				releaseMapId=releasePhaseMapId.get(0).intValue();
				return releaseMapId;
			}
			
		
		}
		// System.err.println("AFTER QUERY ::::::::::::::::::::::::::::"+list);

		catch (Exception e) {
			e.printStackTrace();
		}
		return releaseMapId;
	}

	@Override
	public List<ReleasePhaseslistModel> getAllReleasePhaseType() {
		// TODO Auto-generated method stub
		List<ReleasePhaseslistModel> releasePhaseTypelist = new ArrayList<>();
		ReleasePhaseslistModel model = null;
		try {
			String query = "SELECT phase_type_id,phase_type FROM release_phase_type where delete_status=0;";

			List<Object> list = entityManager.createNativeQuery(query).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					model = new ReleasePhaseslistModel();
					model.setPhase_type_id(Integer.parseInt(String.valueOf(obj[0])));
					model.setPhase_type(String.valueOf(obj[1]));
					
					releasePhaseTypelist.add(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return releasePhaseTypelist;
	}



}
