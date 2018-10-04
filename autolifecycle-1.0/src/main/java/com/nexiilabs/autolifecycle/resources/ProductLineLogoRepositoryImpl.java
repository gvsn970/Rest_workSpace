package com.nexiilabs.autolifecycle.resources;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Transactional
@Repository
public class ProductLineLogoRepositoryImpl implements ProductLineLogoRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Response addProductLineUploadDetails(ProductLineLogoUploadModel uploadModel) {

		Response userResponse = new Response();
		try {
			entityManager.persist(uploadModel);
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
		return userResponse;
	}

	@Override
	public Response updateProductlineLogo(int fk_product_line_id) {
		Response response = new Response();
		try {
			if (fk_product_line_id != 0) {
				boolean isContainsLogo = isLogoAlreadyExistwithThisID(fk_product_line_id);
				if (isContainsLogo) {
					String hql = "UPDATE productline_logo pll set pll.delete_status=1 "
							+ "where pll.delete_status=0 and pll.fk_product_line_id=" + fk_product_line_id + "";
					int updated = entityManager.createNativeQuery(hql).executeUpdate();
					if (updated > 0) {
						response.setStatus(1);
						response.setMessage("Productline logo upadated succesfully");

					} else {
						response.setStatus(0);
						response.setMessage(
								"Productline logo not updated beacuse delete status is already 1 with this productlineid");
					}

				} else {
					response.setStatus(0);
					response.setMessage("No productline_Logos found with this productlineId");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	private boolean isLogoAlreadyExistwithThisID(int fk_product_line_id) {
		String hql = "select * from productline_logo pll where pll.delete_status=0 and (pll.fk_product_line_id="
				+ fk_product_line_id + ") ";
		boolean isEmpty = entityManager.createNativeQuery(hql).getResultList().isEmpty();
		if (!isEmpty) {
			return true;
		}
		return false;
	}

	@Override
	public ProductLineLogoUploadModel getProductlinelogofile(int fk_product_line_id) {
		
		ProductLineLogoUploadModel productlinelogo1=null;
		try {
			String hql = "select pll.productline_logo_name,pll.productline_logo_location "
					+ "from productline_logo pll where pll.delete_status=0 " + "and pll.fk_product_line_id="
					+ fk_product_line_id + ";";
			
			List<Object> list = entityManager.createNativeQuery(hql).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();
					productlinelogo1=new ProductLineLogoUploadModel();
					productlinelogo1.setLogoName(String.valueOf(obj[0]));
					productlinelogo1.setLogoLocation(String.valueOf(obj[1]));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productlinelogo1;
	}
	
}
