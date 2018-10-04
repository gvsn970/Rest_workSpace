package com.nexiilabs.autolifecycle.releasephasesmaping;

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

import com.nexiilabs.autolifecycle.releases.ResponseDTO;

@Transactional
@Repository
public class ReleasePhasesmapingDaoimpl implements ReleasePhasesmapingDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ResponseDTO addReleasePhasesmaping(ReleasePhasesmapingModel mapingModel) {
		// TODO Auto-generated method stub
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			String internalDate = null;
			String endDate = mapingModel.getEnd_date();
			String externaldate = null;

			String query="select r.release_date_internal,r.release_date_external from\n" + 
					"product_releases r where\n" + 
					" r.release_id ='"+mapingModel.getFk_release_id()+"'";
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


			 boolean check = releasephasemapingExistencyCheck(mapingModel);
	
			if (date1.compareTo(date2) >= 0) {
		
				if (!check) {
					
					entityManager.persist(mapingModel);
					if (mapingModel.getRelease_phase_map_id() != 0) {
						responseDTO.setReleasephaseId(mapingModel.getRelease_phase_map_id());
						responseDTO.setMessage("ReleasePhasesmaping added successfully");
						responseDTO.setStatusCode(1);
					} else {
					
						responseDTO.setMessage("ReleasePhasesmaping addition Failed");
						responseDTO.setStatusCode(0);
					}

				} else {
					responseDTO.setMessage("ReleasePhasesmaping Already exists");
					responseDTO.setStatusCode(2);
				}
			} else if (date1.compareTo(date2) < 0) {
				responseDTO.setMessage("ReleasePhasesmaping End Date is Greater Then Release Internal Date");
				responseDTO.setStatusCode(3);
			} /*
				 * else if (date1.compareTo(date2) < 0) {
				 * System.out.println("Date1 is same as Date2"); }
				 */

			// long diff = date2.getTime() - date1.getTime();
			// int days = (int) (diff / (1000 * 60 * 60 * 24));
			// System.out.println("Difference: " + days + " days.");

		} catch (Exception e) {
			responseDTO.setStatusCode(0);
			responseDTO.setMessage(e.getMessage());
		}

		return responseDTO;
	}

	private boolean releasephasemapingExistencyCheck(ReleasePhasesmapingModel mapingModel) {
		List list = null;
		try {
			Query sql = entityManager.createNativeQuery(
					"select * from release_phases_mapping where  fk_release_phase_id=:fk_release_phase_id and fk_release_id=:fk_release_id and delete_status=0");

			sql.setParameter("fk_release_phase_id", mapingModel.getFk_release_phase_id());
			// sql.setParameter("release_phase_map_id",
			// mapingModel.getRelease_phase_map_id());
			sql.setParameter("fk_release_id", mapingModel.getFk_release_id());

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

	private boolean dateChecking() {
		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public ResponseDTO updateReleasePhasesmaping(ReleasePhasesmapingModel mapingModel) {
		// TODO Auto-generated method stub
		ResponseDTO responseDTO = new ResponseDTO();
		int update = 0;
		try {
			responseDTO = noChangesFoundForReleasePhasesmaping(mapingModel);
			// System.err.println("innnnnnnnnnnnnnn :::::::::: After NO Changes
			// Found:::::::::::"+responseDTO);
			if (responseDTO.getStatusCode() == 1) {
				String query = "update release_phases_mapping set fk_release_id='" + mapingModel.getFk_release_id()
						+ "',fk_release_phase_id='" + mapingModel.getFk_release_phase_id() + "',"
						+ "fk_release_phase_type_id='" + mapingModel.getFk_release_phase_type_id() + "',start_date='"
						+ mapingModel.getStart_date() + "',end_date='" + mapingModel.getEnd_date() + "',updated_by='"
						+ mapingModel.getUpdated_by() + "'," + "description='" + mapingModel.getDescription()
						+ "',fk_status_id='" + mapingModel.getFk_status_id()
						+ "',updated_on=CURRENT_TIMESTAMP where delete_status=0 and release_phase_map_id='"
						+ mapingModel.getRelease_phase_map_id() + "';";
				update = entityManager.createNativeQuery(query).executeUpdate();
				// System.err.println("innnnnnnnnnnnnnn ::::::::::After
				// query:::::::::::"+update);
				if (update > 0) {
					responseDTO.setMessage("ReleasePhasesmaping updated successfully");
					responseDTO.setStatusCode(1);
				} else {

					responseDTO.setMessage("ReleasePhasesmaping updation Failed");
					responseDTO.setStatusCode(0);
				}
			} else {
				// System.err.println("innnnnnnnnn:::::::::::::::::"+responseDTO);
				return responseDTO;
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseDTO.setStatusCode(0);
			responseDTO.setMessage("Exception occured");
		}
		return responseDTO;
	}

	public ResponseDTO noChangesFoundForReleasePhasesmaping(ReleasePhasesmapingModel mapingModel) {
		ResponseDTO responseDTO = new ResponseDTO();
		try {
			Query query = entityManager.createQuery(
					"from ReleasePhasesmapingModel where release_phase_map_id=:release_phase_map_id and fk_release_id=:fk_release_id "
							+ "and fk_release_phase_id=:fk_release_phase_id and fk_release_phase_type_id=:fk_release_phase_type_id and start_date=:start_date and"
							+ " end_date=:end_date and fk_status_id=:fk_status_id and description=:description and delete_status=:delete_status");
			query.setParameter("release_phase_map_id", mapingModel.getRelease_phase_map_id());
			query.setParameter("fk_release_id", mapingModel.getFk_release_id());
			query.setParameter("fk_release_phase_id", mapingModel.getFk_release_phase_id());
			query.setParameter("fk_release_phase_type_id", mapingModel.getFk_release_phase_type_id());
			query.setParameter("start_date", mapingModel.getStart_date());
			query.setParameter("end_date", mapingModel.getEnd_date());
			query.setParameter("fk_status_id", mapingModel.getFk_status_id());
			query.setParameter("description", mapingModel.getDescription());

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
		}
		return responseDTO;
	}

	@Override
	public ResponseDTO deleteReleasePhasesmaping(ReleasePhasesmapingModel mapingModel) {
		// TODO Auto-generated method stub
		ResponseDTO responseDTO = new ResponseDTO();
		int update = 0;
		try {
			Query hql = entityManager.createQuery(
					"update ReleasePhasesmapingModel set delete_status=:delete_status,deleted_by=:deleted_by"
							+ " ,deleted_on=CURRENT_TIMESTAMP  where  release_phase_map_id=:release_phase_map_id");
			hql.setParameter("deleted_by", mapingModel.getDelete_status());
			hql.setParameter("delete_status", mapingModel.getDeleted_by());
			hql.setParameter("release_phase_map_id", mapingModel.getRelease_phase_map_id());
			update = hql.executeUpdate();
			if (update > 0) {
				responseDTO.setMessage("ReleasePhasesmaping Deleted successfully");
				responseDTO.setStatusCode(1);
			} else {
				responseDTO.setMessage("ReleasePhasesmaping Deletion Failed");
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
	public List<ReleasePhasesmapingListModel> getReleasePhasesmapingDetails(int releasephasemapId) {
		// TODO Auto-generated method stub
		List<ReleasePhasesmapingListModel> releasephasesmapingList = new ArrayList<>();
		ReleasePhasesmapingListModel model = null;
		try {
			String query = "select \n" + "    rpm.release_phase_map_id,\n" + "    rpm.description,\n"
					+ "    rpm.start_date,\n" + "    rpm.end_date,\n" + "    pr.release_name,\n"
					+ "    pr.release_description,\n" + "    pr.release_date_internal,\n"
					+ "    pr.release_date_external,\n" + "    rp.release_phase_name,\n"
					+ "    rp.release_phase_description,\n" + "    rpt.phase_type,\n" + "    prs.status\n" + "from\n"
					+ "    release_phases_mapping rpm\n" + "        JOIN\n"
					+ "    product_releases pr ON rpm.fk_release_id = pr.release_id\n" + "        JOIN\n"
					+ "    release_phases rp ON rpm.fk_release_phase_id = rp.release_phase_id\n" + "        JOIN\n"
					+ "    release_phase_type rpt ON rpm.fk_release_phase_type_id = rpt.phase_type_id\n"
					+ "        JOIN\n" + "    product_release_status prs ON rpm.fk_status_id = prs.status_id\n"
					+ "where\n" + "    rpm.release_phase_map_id ='" + releasephasemapId + "' "
					+ "        And rpm.delete_status = 0;";

			List<Object> list = entityManager.createNativeQuery(query).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					model = new ReleasePhasesmapingListModel();
					model.setRelease_phase_map_id(Integer.parseInt(String.valueOf(obj[0])));
					model.setDescription(String.valueOf(obj[1]));
					model.setStartDate(String.valueOf(obj[2]));
					model.setEndtDate(String.valueOf(obj[3]));
					model.setReleaseName(String.valueOf(obj[4]));
					model.setReleaseDescription(String.valueOf(obj[5]));
					model.setReleaseDateInternal(String.valueOf(obj[6]));
					model.setReleaseDateExternal(String.valueOf(obj[7]));
					model.setRelease_phase_name(String.valueOf(obj[8]));
					model.setRelease_phase_description(String.valueOf(obj[9]));
					model.setRelease_phase_type(String.valueOf(obj[10]));
					model.setStatus(String.valueOf(obj[11]));
					releasephasesmapingList.add(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return releasephasesmapingList;
	}

	@Override
	public List<ReleasePhasesmapingListModel> getAllReleasePhasesmapings() {
		// TODO Auto-generated method stub
		List<ReleasePhasesmapingListModel> releasephasesmapingList = new ArrayList<>();
		ReleasePhasesmapingListModel model = null;
		try {
			String query = "select \n" + "    rpm.release_phase_map_id,\n" + "    rpm.description,\n"
					+ "    rpm.start_date,\n" + "    rpm.end_date,\n" + "    pr.release_name,\n"
					+ "    pr.release_description,\n" + "    pr.release_date_internal,\n"
					+ "    pr.release_date_external,\n" + "    rp.release_phase_name,\n"
					+ "    rp.release_phase_description,\n" + "    rpt.phase_type,\n" + "    prs.status\n" + "from\n"
					+ "    release_phases_mapping rpm\n" + "        JOIN\n"
					+ "    product_releases pr ON rpm.fk_release_id = pr.release_id\n" + "        JOIN\n"
					+ "    release_phases rp ON rpm.fk_release_phase_id = rp.release_phase_id\n" + "        JOIN\n"
					+ "    release_phase_type rpt ON rpm.fk_release_phase_type_id = rpt.phase_type_id\n"
					+ "        JOIN\n" + "    product_release_status prs ON rpm.fk_status_id = prs.status_id\n"
					+ "where  rpm.delete_status = 0;";

			List<Object> list = entityManager.createNativeQuery(query).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					model = new ReleasePhasesmapingListModel();
					model.setRelease_phase_map_id(Integer.parseInt(String.valueOf(obj[0])));
					model.setDescription(String.valueOf(obj[1]));
					model.setStartDate(String.valueOf(obj[2]));
					model.setEndtDate(String.valueOf(obj[3]));
					model.setReleaseName(String.valueOf(obj[4]));
					model.setReleaseDescription(String.valueOf(obj[5]));
					model.setReleaseDateInternal(String.valueOf(obj[6]));
					model.setReleaseDateExternal(String.valueOf(obj[7]));
					model.setRelease_phase_name(String.valueOf(obj[8]));
					model.setRelease_phase_description(String.valueOf(obj[9]));
					model.setRelease_phase_type(String.valueOf(obj[10]));
					model.setStatus(String.valueOf(obj[11]));
					releasephasesmapingList.add(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return releasephasesmapingList;
	}

}
