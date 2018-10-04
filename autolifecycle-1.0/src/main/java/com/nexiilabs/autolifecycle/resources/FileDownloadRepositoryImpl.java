package com.nexiilabs.autolifecycle.resources;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Transactional
@Repository
public class FileDownloadRepositoryImpl implements FileDownloadRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public FileDownloadModel getReleasefile(int fileId) {
		FileDownloadModel fileDownloadModel = null;
		try {
			String hql = "select product_release_attachment_file_name,product_release_attachment_file_location from product_release_attachments where product_release_attachment_id="
					+ fileId;
			List<Object[]> releasefile = entityManager.createNativeQuery(hql).getResultList();
			for (Object[] obj : releasefile) {
				fileDownloadModel = new FileDownloadModel();
				fileDownloadModel.setFileName(String.valueOf(obj[0]));
				fileDownloadModel.setFilePath(String.valueOf(obj[1]));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileDownloadModel;
	}
	@Override
	public FileDownloadModel getProductfile(int fileId) {
		// TODO Auto-generated method stub
		//System.err.println("in dao:::::::::::::::::::");
		FileDownloadModel fileDownloadModel=null;
		try {
			String query="select product_attachment_file_name,product_attachment_file_location from product_attachments where product_attachment_id="+fileId;
			List<Object[]> productfile=entityManager.createNativeQuery(query).getResultList();
			for (Object[] obj : productfile) {
				fileDownloadModel = new FileDownloadModel();
				fileDownloadModel.setFileName(String.valueOf(obj[0]));
				fileDownloadModel.setFilePath(String.valueOf(obj[1]));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		//System.err.println("fileDownloadModel:::::::::::::"+fileDownloadModel.toString());
		return fileDownloadModel;
	}

	@Override
	public FileDownloadModel getProductLinefile(int fileId) {
		FileDownloadModel fileDownloadModel = null;
		try {
			String hql = "select productline_attachment_file_name,productline_attachment_file_location from productline_attachments where productline_attachment_id="
					+ fileId;
			List<Object[]> productLinefile = entityManager.createNativeQuery(hql).getResultList();
			for (Object[] obj : productLinefile) {
				fileDownloadModel = new FileDownloadModel();
				fileDownloadModel.setFileName(String.valueOf(obj[0]));
				fileDownloadModel.setFilePath(String.valueOf(obj[1]));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println(fileDownloadModel.toString());
		return fileDownloadModel;
	}
	@Override
	public FileDownloadModel getFeatureFile(int fileId) {
		// TODO Auto-generated method stub
		FileDownloadModel fileDownloadModel = null;
		try {
			String hql = "select feature_attachment_file_name,feature_attachment_file_location from features_attachments where feature_attachment_id='"+fileId+"' and delete_status=0";
			List<Object[]> productLinefile = entityManager.createNativeQuery(hql).getResultList();
			for (Object[] obj : productLinefile) {
				fileDownloadModel = new FileDownloadModel();
				fileDownloadModel.setFileName(String.valueOf(obj[0]));
				fileDownloadModel.setFilePath(String.valueOf(obj[1]));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println(fileDownloadModel.toString());
		return fileDownloadModel;
	}
	@Override
	public FileDownloadModel getReleasephaseFile(int fileId) {
		// TODO Auto-generated method stub
		FileDownloadModel fileDownloadModel = null;
		try {
			String hql = "select release_phase_attachment_file_name,release_phase_attachment_file_location from release_phase_attachments where release_phase_attachment_id='"+fileId+"' and delete_status=0";
			List<Object[]> productLinefile = entityManager.createNativeQuery(hql).getResultList();
			for (Object[] obj : productLinefile) {
				fileDownloadModel = new FileDownloadModel();
				fileDownloadModel.setFileName(String.valueOf(obj[0]));
				fileDownloadModel.setFilePath(String.valueOf(obj[1]));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println(fileDownloadModel.toString());
		return fileDownloadModel;
	}

}
