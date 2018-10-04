package com.nexiilabs.autolifecycle.resources;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.nexiilabs.autolifecycle.features.FeaturesModel;
import com.nexiilabs.autolifecycle.features.FeaturesUploadModel;
import com.nexiilabs.autolifecycle.product.Product;
import com.nexiilabs.autolifecycle.product.ProductUploadModel;
import com.nexiilabs.autolifecycle.productline.ProductLinePOJO;
import com.nexiilabs.autolifecycle.productline.ProductLineUploadModel;
import com.nexiilabs.autolifecycle.releasephases.ReleasePhasesUploadModel;
import com.nexiilabs.autolifecycle.releases.ProductReleaseUploadModel;
import com.nexiilabs.autolifecycle.releases.ReleaseModel;
import com.nexiilabs.autolifecycle.releases.ResponseDTO;
@Transactional
@Repository
public class AttachmentUploadRepositoryImpl implements AttachmentUploadRepository{
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ResponseDTO addReleaseUploadDetails(ProductReleaseUploadModel uploadModel) {
		ResponseDTO userResponseDTO = new ResponseDTO();
		try {
			entityManager.persist(uploadModel); 
			if (uploadModel.getAttachmentId() != 0) {
					userResponseDTO.setStatusCode(1);
					userResponseDTO.setMessage("Release Upload Details inserted Succesfully");
				} else {
					userResponseDTO.setStatusCode(0);
					userResponseDTO.setMessage("Release Upload Details insertion Failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			userResponseDTO.setStatusCode(0);
			userResponseDTO.setMessage(e.getMessage());
		}
		// System.out.println(userResponseDTO.toString());
		return userResponseDTO;
	}

	@Override
	public ResponseDTO addProductUploadDetails(ProductUploadModel uploadModel) {
		ResponseDTO userResponse = new ResponseDTO();
		try {
			entityManager.persist(uploadModel);
			//System.out.println("uploadModel.getPoUploadId():::" + uploadModel.getAttachmentId());
			if (uploadModel.getAttachmentId() != 0) {
				userResponse.setStatusCode(1);
				userResponse.setMessage("Product Upload Details inserted Succesfully");
				} else {
					userResponse.setStatusCode(0);
					userResponse.setMessage("Product Upload Details insertion Failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			userResponse.setStatusCode(0);
			userResponse.setMessage(e.getMessage());
		}
		// System.out.println(userResponseDTO.toString());
		return userResponse;
	}

	@Override
	public ResponseDTO deleteReleaseRecordInDatabase(int releaseId) {
		ResponseDTO userResponse = new ResponseDTO();
		try {
			ReleaseModel releaseModel = entityManager.find(ReleaseModel.class, releaseId);
			if(releaseModel!=null){
			entityManager.remove(releaseModel);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userResponse;
	}

	@Override
	public ResponseDTO deleteProductRecordInDatabase(int productId) {
		ResponseDTO userResponse = new ResponseDTO();
		try {
			//System.err.println("in delete");
			Product productModel = entityManager.find(Product.class, productId);
			if(productModel!=null){
			entityManager.remove(productModel);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userResponse;
	}

	@Override
	public ResponseDTO addProductLineUploadDetails(ProductLineUploadModel uploadModel) {
		ResponseDTO userResponse = new ResponseDTO();
		try {
			entityManager.persist(uploadModel);
			// System.out.println("uploadModel.getPoUploadId():::" +
			// uploadModel.getAttachmentId());
			if (uploadModel.getAttachmentId() != 0) {
				userResponse.setStatusCode(1);
				userResponse.setMessage("Productline Upload Details inserted Succesfully");
			} else {
				userResponse.setStatusCode(0);
				userResponse.setMessage("Productline Upload Details insertion Failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			userResponse.setStatusCode(0);
			userResponse.setMessage(e.getMessage());
		}
		return userResponse;
	}

	@Override
	public ResponseDTO deleteProductLineRecordInDatabase(int productlineId) {
		ResponseDTO userResponse = new ResponseDTO();
		ProductLinePOJO productLineModel;
		int product_line_id=productlineId;
		try {
			System.err.println("in delete");
			
			productLineModel = entityManager.find(ProductLinePOJO.class, product_line_id);
			if(productLineModel!=null){
			entityManager.remove(productLineModel);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userResponse;
	}
	@Override
	public boolean checkFileExistancyForReleaseFile(int releaseId, String uploadPath) {
		String hql=" select attachmentId,fileName FROM ProductReleaseUploadModel where deleteStatus=0 and fileLocation=:fileLocation and fkReleaseId="+releaseId;
		List  releaseFilelist = entityManager.createQuery(hql).setParameter("fileLocation", uploadPath).getResultList();
		//System.err.println("releaseFilelist:::::::::::::::::"+releaseFilelist.size());
		if(releaseFilelist.size()>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean checkFileExistancyForProductFile(int productId, String uploadPath) {
		String hql=" select attachmentId,fileName FROM ProductUploadModel where delete_status=0 and fileLocation=:fileLocation and fkProductId="+productId;
		List  productFilelist = entityManager.createQuery(hql).setParameter("fileLocation", uploadPath).getResultList();
		//System.err.println("releaseFilelist:::::::::::::::::"+releaseFilelist.size());
		if(productFilelist.size()>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean checkFileExistancyForProductLineFile(int product_line_id, String filePath) {
		String hql=" select attachmentId,fileName FROM ProductLineUploadModel where delete_status=0 and fileLocation=:fileLocation and fk_product_line_id="+product_line_id;
		List  productlineFilelist = entityManager.createQuery(hql).setParameter("fileLocation", filePath).getResultList();
		//System.err.println("releaseFilelist:::::::::::::::::"+releaseFilelist.size());
		if(productlineFilelist.size()>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public ResponseDTO addFeaturesUploadDetails(FeaturesUploadModel uploadModel) {
		ResponseDTO userResponseDTO = new ResponseDTO();
		try {
			entityManager.persist(uploadModel); 
			if (uploadModel.getAttachmentId() != 0) {
					userResponseDTO.setStatusCode(1);
					userResponseDTO.setMessage("Features Upload Details inserted Succesfully");
				} else {
					userResponseDTO.setStatusCode(0);
					userResponseDTO.setMessage("Features Upload Details insertion Failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			userResponseDTO.setStatusCode(0);
			userResponseDTO.setMessage(e.getMessage());
		}
		// System.out.println(userResponseDTO.toString());
		return userResponseDTO;
	}

	@Override
	public ResponseDTO deleteFeaturesRecordInDatabase(int featureId) {
		ResponseDTO userResponse = new ResponseDTO();
		try {
			FeaturesModel featureModel = entityManager.find(FeaturesModel.class, featureId);
			if(featureModel!=null){
			entityManager.remove(featureModel);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userResponse;
	}

	@Override
	public boolean checkFileExistancyForFeaturesFile(int featureId, String uploadPath) {
		String hql=" select attachmentId,fileName FROM FeaturesUploadModel where deleteStatus=0 and fileLocation=:fileLocation and fkfeatureId="+featureId;
		List  featuresFilelist = entityManager.createQuery(hql).setParameter("fileLocation", uploadPath).getResultList();
	
		if(featuresFilelist.size()>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public ResponseDTO addReleasePhaseUploadDetails(ReleasePhasesUploadModel uploadModel) {
		// TODO Auto-generated method stub
		ResponseDTO userResponseDTO = new ResponseDTO();
		try {
			entityManager.persist(uploadModel); 
			if (uploadModel.getAttachmentId() != 0) {
					userResponseDTO.setStatusCode(1);
					userResponseDTO.setMessage("ReleasePhases Upload Details inserted Succesfully");
				} else {
					userResponseDTO.setStatusCode(0);
					userResponseDTO.setMessage("ReleasePhases Upload Details insertion Failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			userResponseDTO.setStatusCode(0);
			userResponseDTO.setMessage(e.getMessage());
		}
		// System.out.println(userResponseDTO.toString());
		return userResponseDTO;
	}

	@Override
	public ResponseDTO deleteReleasePhaseRecordInDatabase(int releasephaseId) {
		// TODO Auto-generated method stub
		ResponseDTO userResponse = new ResponseDTO();
		try {
			ReleasePhasesUploadModel releasePhasesUploadModel = entityManager.find(ReleasePhasesUploadModel.class, releasephaseId);
			if(releasePhasesUploadModel!=null){
			entityManager.remove(releasePhasesUploadModel);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userResponse;
	}

	@Override
	public boolean checkFileExistancyForReleasePhaseFile(int releasephaseId, String uploadPath) {
		// TODO Auto-generated method stub
		String hql=" select attachmentId,fileName FROM ReleasePhasesUploadModel where deleteStatus=0 and fileLocation=:fileLocation and fkreleasephaseId="+releasephaseId;
		List  releasePhasesFilelist = entityManager.createQuery(hql).setParameter("fileLocation", uploadPath).getResultList();
	
		if(releasePhasesFilelist.size()>0){
			return true;
		}else{
			return false;
		}
	}
}
