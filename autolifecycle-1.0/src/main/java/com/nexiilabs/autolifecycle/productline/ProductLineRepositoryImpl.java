package com.nexiilabs.autolifecycle.productline;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Transactional
@Repository
public class ProductLineRepositoryImpl implements ProductLineRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Response addProductLineUploadDetails(ProductLineUploadModel uploadModel) {
		Response userResponse = new Response();
		try {
			entityManager.persist(uploadModel);
			// System.out.println("uploadModel.getPoUploadId():::" +
			// uploadModel.getAttachmentId());
			if (uploadModel.getAttachmentId() != 0) {
				userResponse.setStatus(1);
				userResponse.setMessage("Productline Upload Details inserted Succesfully");
			} else {
				userResponse.setStatus(0);
				userResponse.setMessage("Productline Upload Details insertion Failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			userResponse.setStatus(0);
			userResponse.setMessage(e.getMessage());
		}
		// System.out.println(userResponseDTO.toString());
		return userResponse;
	}

	private boolean isProductLine(String product_line_name) {
		String hql1 = "select * from product_line pl where (pl.product_line_name='" + product_line_name
				+ "') and pl.delete_status=0";
		boolean isempty = entityManager.createNativeQuery(hql1).getResultList().isEmpty();
		if (isempty) {
			return true;
		}
		return false;
	}

	@Override
	public List<ProductLinePOJO> listofProductLines() {
		List<ProductLinePOJO> productlinelist = new ArrayList<ProductLinePOJO>();
		try {

			String hql = "SELECT pl.product_line_id,pl.product_line_name," + "pl.product_line_desc,count(p.product_id) "
					+ "FROM product_line pl LEFT JOIN product p on pl.product_line_id =p.fk_product_line and p.delete_status=0 "
					+ "where pl.delete_status=0  "
					+ "group by pl.product_line_id,pl.product_line_name,pl.product_line_desc;";
			ProductLinePOJO productline = null;
			List<Object> list = entityManager.createNativeQuery(hql).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();
					productline = new ProductLinePOJO();
					productline.setProduct_line_id(Integer.parseInt(String.valueOf(obj[0])));
					productline.setProduct_line_name(String.valueOf(obj[1]));
					productline.setProduct_line_desc(String.valueOf(obj[2]));
					productline.setCount(Integer.parseInt(String.valueOf(obj[3])));
					List<ProductLineUploadModel> files = getProductLineFiles(Integer.parseInt(String.valueOf(obj[0])));
					productline.setProductLineUploadModel(files);
					productlinelist.add(productline);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productlinelist;
	}

	@Override
	public List<ProductLinePOJO> listOfSpecificProductLine(int product_line_id) {
		List<ProductLinePOJO> productlinelist = new ArrayList<ProductLinePOJO>();
		try {
			String hql = "SELECT pl.product_line_id,pl.product_line_name,"
					+ "pl.product_line_desc FROM product_line pl where " + "delete_status=0 and pl.product_line_id='"
					+ product_line_id + "'";
			ProductLinePOJO productline = null;
			List<Object> list = entityManager.createNativeQuery(hql).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();
					productline = new ProductLinePOJO();
					productline.setProduct_line_id(Integer.parseInt(String.valueOf(obj[0])));
					productline.setProduct_line_name(String.valueOf(obj[1]));
					productline.setProduct_line_desc(String.valueOf(obj[2]));
					List<ProductLineUploadModel> files = getProductLineFiles(product_line_id);
					productline.setProductLineUploadModel(files);
					productlinelist.add(productline);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productlinelist;
	}

	public List<ProductLineUploadModel> getProductLineFiles(int product_line_id) {
		ProductLineUploadModel model = null;
		List<ProductLineUploadModel> filesList = new ArrayList<>();
		try {
			Query hql = entityManager
					.createNativeQuery("SELECT productline_attachment_id,productline_attachment_file_type,"
							+ "productline_attachment_file_name,productline_attachment_file_location,fk_product_line_id FROM productline_attachments where fk_product_line_id="
							+ product_line_id + " and delete_status=0");

			List<Object> list = hql.getResultList();
			if (list.size() > 0) {
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					model = new ProductLineUploadModel();
					model.setAttachmentId(Integer.parseInt(String.valueOf(obj[0])));
					model.setFileType(String.valueOf(obj[1]));
					model.setFileName(String.valueOf(obj[2]));
					model.setFileLocation(String.valueOf(obj[3]));
					model.setFk_product_line_id(Integer.parseInt(String.valueOf(obj[4])));
					filesList.add(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filesList;
	}

	@Override
	public int deleteProductLine(int product_line_id) {

		try {
			if (product_line_id != 0) {
				boolean check = checkProductsUseingProductLine(product_line_id);
				// System.err.println("::::::::::::::::::::::"+check);
				if (check) {
					String hql = "UPDATE product_line pl set pl.delete_status=1,pl.deleted_on=CURRENT_TIMESTAMP where "
							+ " pl.product_line_id='" + product_line_id + "'";
					int isUpdated = entityManager.createNativeQuery(hql).executeUpdate();
					if (isUpdated == 1) {
						return 1;
					}
					return 2;
				} else {
					return 3;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public boolean checkProductsUseingProductLine(int product_line_id) {

		String hql1 = "select p.product_name,p.product_id from product_line pl,product p where p.fk_product_line=pl.product_line_id "
				+ " and pl.product_line_id='" + product_line_id + "' and pl.delete_status=0 and p.delete_status=0;";
		boolean isempty = entityManager.createNativeQuery(hql1).getResultList().isEmpty();
		if (isempty) {
			return true;
		}
		return false;
	}

	@Override
	public Response addProductLines(String product_line_name, String product_line_desc) {
		Response response = new Response();

		try {

			if (product_line_name != null && product_line_desc != null) {
				ProductLinePOJO product = new ProductLinePOJO();

				product.setProduct_line_name(product_line_name);
				product.setProduct_line_desc(product_line_desc);

				boolean isValidProductline = isProductLine(product.getProduct_line_name());
				if (isValidProductline) {
					entityManager.persist(product);
					if (product.getProduct_line_id() != 0) {
						response.setStatus(1);
						response.setMessage("Productline created successfully");
						response.setProductlineId(product.getProduct_line_id());
					} else {
						response.setStatus(0);
						response.setMessage("Productline Not successfully");
					}
				} else {
					response.setStatus(2);
					response.setMessage("Productline Already exists");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public Response updateProductLine(int product_line_id, String product_line_name, String product_line_desc) {
		Response response = new Response();
		try {
			boolean check = isProductLineUpdate(product_line_name,product_line_id);
			response = noChangesFoundForProductLine(product_line_id, product_line_name, product_line_desc);
			if (response.getStatus() == 1) {
				if(check ) {
				String query = "UPDATE product_line pl set pl.product_line_name='" + product_line_name + "',"
						+ "pl.product_line_desc='" + product_line_desc + "',pl.updated_on=CURRENT_TIMESTAMP  where "
						+ "pl.delete_status=0 and pl.product_line_id=" + product_line_id + "";
				int isUpdated = entityManager.createNativeQuery(query).executeUpdate();
				if (isUpdated > 0) {
					response.setMessage("Productline updated successfully");
					response.setStatus(1);
				} else {
					response.setMessage("Productline Not updated ");
					response.setStatus(0);
				}
				}else {
					response.setMessage("Productline Already exists");
					response.setStatus(2);
				}
			} else {
				return response;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	private boolean isProductLineUpdate(String product_line_name,int product_line_id) {
		String hql1 = "SELECT * FROM product_line pl WHERE pl.product_line_name='"+product_line_name+"' AND  pl.product_line_id!='"+product_line_id+"' and pl.delete_status=0";
		boolean isempty = entityManager.createNativeQuery(hql1).getResultList().isEmpty();
		if (isempty) {
			return true;
		}
		return false;
	}

	public Response noChangesFoundForProductLine(int product_line_id, String product_line_name,
			String product_line_desc) {
		Response response = new Response();
		try {
			String query = "Select * from product_line where delete_status=0 and " + " product_line_name ='"
					+ product_line_name + "' and  product_line_desc='" + product_line_desc + "' and product_line_id="
					+ product_line_id + ";";
			List list = entityManager.createNativeQuery(query).getResultList();
			if (list.size() == 0) {
				response.setStatus(1);
				response.setMessage("changes found");

			} else {
				response.setStatus(0);
				response.setMessage("No changes found");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return response;
	}

	@Override
	public List<ProductLinePOJO> listOfProductNamesByProductlineId(int product_line_id) {
		List<ProductLinePOJO> productlinelist = new ArrayList<ProductLinePOJO>();
		try {
			String hql = "SELECT p.product_id,p.product_name "
					+ "FROM product_line pl,product p where p.fk_product_line =" + product_line_id
					+ " and p.delete_status=0 group by p.product_id;";
			ProductLinePOJO productline = null;
			List<Object> list = entityManager.createNativeQuery(hql).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();
					productline = new ProductLinePOJO();
					productline.setProduct_id(Integer.parseInt(String.valueOf(obj[0])));
					productline.setProduct_name(String.valueOf(obj[1]));
					productlinelist.add(productline);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productlinelist;
	}

	@Override
	public Response deleteProductLineFiles(ProductLineUploadModel productLineUploadModel) {
		Response responseDTO = new Response();
		int update = 0;
		try {
			Query hql = entityManager
					.createQuery("update ProductLineUploadModel set delete_status=1,deletedBy=:deletedBy"
							+ " ,deletedOn=CURRENT_TIMESTAMP  where  attachmentId=:attachmentId and fk_product_line_id=:fk_product_line_id");
			hql.setParameter("attachmentId", productLineUploadModel.getAttachmentId());
			hql.setParameter("fk_product_line_id", productLineUploadModel.getFk_product_line_id());
			hql.setParameter("deletedBy", productLineUploadModel.getDeletedBy());
			update = hql.executeUpdate();
			if (update > 0) {
				responseDTO.setMessage("ProductLine Deleted successfully");
				responseDTO.setStatus(1);
			} else {
				responseDTO.setMessage("ProductLine Deletion Failed");
				responseDTO.setStatus(0);
			}
		} catch (Exception e) {
			responseDTO.setStatus(0);
			responseDTO.setMessage(e.getMessage());
		}
		return responseDTO;
	}
}
