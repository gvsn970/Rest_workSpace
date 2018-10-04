package com.nexiilabs.autolifecycle.resources;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Transactional
@Repository
public class ProductLogoRepositoryImpl implements ProductLogoRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Response addProductUploadDetails(ProductLogoUploadModel uploadModel) {
		Response userResponse = new Response();
		try {
			entityManager.persist(uploadModel);
			// System.out.println("uploadModel.getPoUploadId():::" +
			// uploadModel.getAttachmentId());
			if (uploadModel.getLogoId() != 0) {
				userResponse.setStatus(1);
				userResponse.setMessage("Productline logo Upload Details inserted Succesfully");
			} else {
				userResponse.setStatus(0);
				userResponse.setMessage("Productline logo Upload Details insertion Failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			userResponse.setStatus(0);
			userResponse.setMessage(e.getMessage());
		}
		// System.out.println(userResponseDTO.toString());
		return userResponse;
	}

	public boolean isValidProductId(int fk_product_id) {
		String query = "SELECT * FROM product_logo pl where  pl.delete_status=0 AND pl.fk_product_id='" + fk_product_id
				+ "'";
		boolean productlist = entityManager.createNativeQuery(query).getResultList().isEmpty();
		if (productlist) {
		//	System.err.println("in ifffffff::" + productlist);
			return true;
		}
		return false;
	}

	@Override
	public Response updateProductUploadDetails(ProductLogoUploadModel uploadModel) {
		Response userResponse = new Response();
		try {
			boolean is_valid_Fk_product_id = isValidProductId(uploadModel.getFkProductId());
			if (!is_valid_Fk_product_id) {
				//System.err.println("in update :::::::::" + is_valid_Fk_product_id);
				String query = " UPDATE product_logo pl set pl.delete_status=1  where  pl.fk_product_id='"
						+ uploadModel.getFkProductId() + "'AND pl.delete_status=0";
				entityManager.createNativeQuery(query).executeUpdate();
			}
			entityManager.persist(uploadModel);
			if (uploadModel.getLogoId() != 0) {
				userResponse.setStatus(1);
				userResponse.setMessage("Product Logo Updated Successfully");

			} else {
				userResponse.setStatus(0);
				userResponse.setMessage("Product Logo Not Updated ");

			}
		} catch (Exception e) {
			e.printStackTrace();
			userResponse.setStatus(0);
			userResponse.setMessage(e.getMessage());
		}
		// System.out.println(userResponseDTO.toString());
		return userResponse;
	}

	@Override
	public List<ProductLogoUploadModel> getProductLogoById(int fk_product_id) {
		// TODO Auto-generated method stub
		List<ProductLogoUploadModel> productlogoList = new ArrayList<ProductLogoUploadModel>();
		try {
		/*	String query = "SELECT pl.product_logo_id,pl.fk_product_id,pl.product_logo_location,pl.product_logo_name,pl.product_logo_type FROM product_logo pl where pl.fk_product_id='"
					+ fk_product_id + "' and pl.delete_status=0;";*/
			String query = "SELECT pl.product_logo_location,pl.product_logo_name,pl.product_logo_type FROM product_logo pl where pl.fk_product_id='"
					+ fk_product_id + "' and pl.delete_status=0;";
			ProductLogoUploadModel product = null;
			List<Object> list = entityManager.createNativeQuery(query).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();
					product = new ProductLogoUploadModel();
					product.setLogoLocation(String.valueOf(obj[0]));
					product.setLogoName(String.valueOf(obj[1]));
					//product.setLogoType(String.valueOf(obj[1]));

					productlogoList.add(product);
				}
			}
		} catch (Exception e) {
				e.printStackTrace();
				
		}
		return productlogoList;
	}

}
