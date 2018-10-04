package com.nexiilabs.autolifecycle.product;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class ProdcutRepositoryImpl implements ProductRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Response addProduct(Product product) {
		Response response = new Response();

		try {

			if (product.getProduct_name() != null && product.getProduct_desc() != null) {
			
				boolean isValidProduct = isValidProduct(product.getProduct_name(), product.getFk_product_line());
				if (isValidProduct) {
					entityManager.persist(product);
					if (product.getProduct_id() != 0) {
						response.setStatus(1);
						response.setMessage("Product created successfully");
						response.setProductId(product.getProduct_id());
					} else {
						response.setStatus(0);
						response.setMessage("Product Not successfully");
					}
				} else {
					response.setStatus(2);
					response.setMessage("Product Already exists");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public boolean isValidProduct(String product_name, int fkProductLineId) {
		String query = "SELECT * FROM product p where p.product_name='" + product_name
				+ "' AND p.delete_status=0 AND p.fk_product_line='" + fkProductLineId + "'";
		boolean productlist = entityManager.createNativeQuery(query).getResultList().isEmpty();
		if (productlist) {
			return true;
		}
		return false;
	}

	@Override
	public List<ProductListDTO> allproducts() {
		List<ProductListDTO> productlist = new ArrayList<ProductListDTO>();
		try {
			
			String query = "SELECT product_id,product_name,product_desc,pl.product_line_name,pl.product_line_desc " + 
					"FROM product p, product_line pl where p.fk_product_line=pl.product_line_id and p.delete_status=0;";
			ProductListDTO product = null;
			List<Object> list = entityManager.createNativeQuery(query).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();
					product = new ProductListDTO();
					product.setProduct_id(Integer.parseInt(String.valueOf(obj[0])));
					product.setProduct_name(String.valueOf(obj[1]));
					product.setProduct_desc(String.valueOf(obj[2]));
					product.setProduct_line_name(String.valueOf(obj[3]));
					product.setProduct_line_desc(String.valueOf(obj[4]));
					List<ProductUploadModel> files = getProductFile(Integer.parseInt(String.valueOf(obj[0])));
					product.setFiles(files);
					productlist.add(product);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return productlist;
	}

	@Override
	public Response updateProduct(int product_id, String product_name, String product_desc, int fk_product_line,
			int fk_product_owner) {
		Response response = new Response();
		Product product = new Product();
		product.setProduct_name(product_name);
		product.setProduct_id(product_id);
		product.setFk_product_line(fk_product_line);
		product.setProduct_desc(product_desc);

		product.setFk_product_owner(fk_product_owner);
		try {
			boolean isValidProduct = isValidProductUpdate(product.getProduct_name(), product.getFk_product_line(),product.getProduct_id());

			response = noChangesFoundForProduct(product);
			if (response.getStatus() == 1) {
				if (isValidProduct) {
					String query = "UPDATE product p set p.product_name='" + product_name + "'," + "p.fk_product_line='"
							+ fk_product_line + "',p.fk_product_owner='" + fk_product_owner + "'," + "p.product_desc='"
							+ product_desc + "',p.updated_on=CURRENT_TIMESTAMP  where "
							+ "p.delete_status=0 and p.product_id='" + product_id + "'";
					int isUpdated = entityManager.createNativeQuery(query).executeUpdate();
					if (isUpdated > 0) {
						response.setMessage("Product updated successfully");
						response.setStatus(1);
					} else {
						response.setMessage("Product Not updated ");
						response.setStatus(0);
					}
				} else {
					response.setMessage("Product Already exists");
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
	public boolean isValidProductUpdate(String product_name, int fkProductLineId,int productId) {
		String query = "SELECT * FROM product p where p.product_name='" + product_name
				+ "' AND p.delete_status=0 AND p.fk_product_line='" + fkProductLineId + "' and p.product_id!='"+productId+"'";
		boolean productlist = entityManager.createNativeQuery(query).getResultList().isEmpty();
		if (productlist) {
			return true;
		}
		return false;
	}
	public Response noChangesFoundForProduct(Product product) {
		Response response = new Response();
		try {
			Query query = entityManager
					.createQuery("from Product where product_name=:product_name and product_id=:product_id "
							+ "and product_desc=:product_desc and fk_product_line=:fk_product_line and fk_product_owner=:fk_product_owner and delete_status=:delete_status");
			query.setParameter("product_name", product.getProduct_name());
			query.setParameter("product_id", product.getProduct_id());
			query.setParameter("fk_product_line", product.getFk_product_line());
			query.setParameter("fk_product_owner", product.getFk_product_owner());
			query.setParameter("product_desc", product.getProduct_desc());

			query.setParameter("delete_status", 0);
			List list = query.getResultList();
			if (list.size() == 0) {
				response.setStatus(1);
				response.setMessage("Changes Found");
			} else {
				response.setStatus(0);
				response.setMessage("No Changes Found");
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return response;
	}

	@Override
	public List<ProductListDTO> getProductDetails(int product_id) {
		// TODO Auto-generated method stub
		List<ProductListDTO> productlist = new ArrayList<ProductListDTO>();
		try {

			/*
			 * String query =
			 * "select product_id,product_name,product_desc,fk_product_line,fk_product_owner FROM product where product_id='"
			 * + product_id + "' AND delete_status=0";
			 */
			String query = "SELECT product_id,product_name,product_desc,pl.product_line_name,pl.product_line_desc\n"
					+ "FROM product p " + " JOIN product_line pl "
					+ "ON p.fk_product_line=pl.product_line_id where p.product_id='" + product_id
					+ "'  AND p.delete_status=0;";

			ProductListDTO product = null;
			Product prodcut = new Product();

			List<Object> list = entityManager.createNativeQuery(query).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();
					product = new ProductListDTO();
					product.setProduct_id(Integer.parseInt(String.valueOf(obj[0])));
					product.setProduct_name(String.valueOf(obj[1]));
					product.setProduct_desc(String.valueOf(obj[2]));
					product.setProduct_line_name(String.valueOf(obj[3]));
					product.setProduct_line_desc(String.valueOf(obj[4]));
					// product.setReleasename(String.valueOf(obj[5]));
					// product.setReleasedesc(String.valueOf(obj[6]));
					// product.setReleaseId(Integer.parseInt(String.valueOf(obj[7])));
					List<ProductUploadModel> files = getProductFile(product.getProduct_id());
					product.setFiles(files);
					productlist.add(product);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productlist;
	}

	@Override
	public List<ProductListDTO> getDetails(int product_id) {
		List<ProductListDTO> productlist = new ArrayList<ProductListDTO>();
		try {
			String query = "SELECT p.product_id,p.product_name,p.product_desc,pl.product_line_name, "
					+ "pl.product_line_desc,pr.release_name,pr.release_description " + ",pr.release_id "
					+ "FROM product p , product_line pl,product_releases pr "
					+ "where p.fk_product_line =pl.product_line_id and p.product_id='" + product_id + "' AND "
					+ "p.delete_status=0 and pr.delete_status=0 and pl.delete_status=0 and pr.fk_product_id =p.product_id;";
			ProductListDTO product = null;
			Product prodcut = new Product();

			List<Object> list = entityManager.createNativeQuery(query).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();
					product = new ProductListDTO();
					product.setProduct_id(Integer.parseInt(String.valueOf(obj[0])));
					product.setProduct_name(String.valueOf(obj[1]));
					product.setProduct_desc(String.valueOf(obj[2]));
					product.setProduct_line_name(String.valueOf(obj[3]));
					product.setProduct_line_desc(String.valueOf(obj[4]));
					product.setReleasename(String.valueOf(obj[5]));
					product.setReleasedesc(String.valueOf(obj[6]));
					product.setReleaseId(Integer.parseInt(String.valueOf(obj[7])));
					List<ProductUploadModel> files = getProductFile(product.getProduct_id());
					product.setFiles(files);
					productlist.add(product);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return productlist;
	}

	@Override
	public int deleteproduct(int product_id) {
		try {
			if (product_id != 0) {
				boolean check = checkReleasesUseingProduct(product_id);
				if (check) {
					String query = "update product p  set p.delete_status=1,p.deleted_on=CURRENT_TIMESTAMP  where p.product_id='"
							+ product_id + "'";
					int updatecheck = entityManager.createNativeQuery(query).executeUpdate();
					if (updatecheck == 1) {
						return 1;
					} else {
						return 2;
					}

				} else {
					return 3;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public boolean checkReleasesUseingProduct(int product_id) {

		String hql1 = "select pr.release_name,pr.release_id from product p,product_releases pr where p.product_id=pr.fk_product_id and "
				+ "p.product_id='" + product_id + "' and " + "p.delete_status=0 and pr.delete_status=0; ";
		boolean isempty = entityManager.createNativeQuery(hql1).getResultList().isEmpty();
		if (isempty) {
			return true;
		}
		return false;
	}

	@Override
	public Response addProductUploadDetails(ProductUploadModel uploadModel) {
		Response userResponse = new Response();
		try {
			entityManager.persist(uploadModel);
			// System.out.println("uploadModel.getPoUploadId():::" +
			// uploadModel.getAttachmentId());
			if (uploadModel.getAttachmentId() != 0) {
				userResponse.setStatus(1);
				userResponse.setMessage("Product Upload Details inserted Succesfully");
			} else {
				userResponse.setStatus(0);
				userResponse.setMessage("Product Upload Details insertion Failed");
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
	public List<ProductUploadModel> getProductFile(int fk_product_id) {
		ProductUploadModel model = null;
		List<ProductUploadModel> filesList = new ArrayList<>();
		try {
			Query hql = entityManager.createNativeQuery(
					"SELECT * FROM product_attachments where fk_product_id=:fk_product_id and delete_status=0");
			hql.setParameter("fk_product_id", fk_product_id);
			List<Object> list = hql.getResultList();
			if (list.size() > 0) {
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					model = new ProductUploadModel();
					model.setAttachmentId(Integer.parseInt(String.valueOf(obj[0])));
					model.setFileType(String.valueOf(obj[3]));
					model.setFileName(String.valueOf(obj[1]));
					model.setFileLocation(String.valueOf(obj[2]));
					model.setFkProductId(Integer.parseInt(String.valueOf(obj[11])));
					filesList.add(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filesList;
	}

	@Override
	public Response deleteProductFiles(ProductUploadModel productUploadModel) {
		Response response = new Response();
		int update = 0;
		try {
			Query hql = entityManager.createQuery("update ProductUploadModel set delete_status=1,deletedBy=:deletedBy"
					+ " ,deleted_on=CURRENT_TIMESTAMP  where  attachmentId=:attachmentId and fkProductId=:fkProductId");
			hql.setParameter("attachmentId", productUploadModel.getAttachmentId());
			hql.setParameter("fkProductId", productUploadModel.getFkProductId());
			hql.setParameter("deletedBy", productUploadModel.getDeletedBy());
			update = hql.executeUpdate();
			if (update > 0) {
				response.setMessage("product File Deleted successfully");
				response.setStatus(1);
			} else {
				response.setMessage("product File Deletion Failed");
				response.setStatus(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(0);
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@Override
	public List<ProductListDTO> unMapedProductswithJiraBoards() {
		// TODO Auto-generated method stub
		List<ProductListDTO> unMapedproductlist = new ArrayList<ProductListDTO>();
		try {

			String query = "SELECT p.product_id,p.product_name,p.product_desc,pl.product_line_name FROM product p,product_line pl where " + 
					"p.fk_jira_boardid=0 and p.delete_status=0 and pl.product_line_id=p.fk_product_line " + 
					"and pl.delete_status=0;";

			ProductListDTO product = null;
			Product prodcut = new Product();

			List<Object> list = entityManager.createNativeQuery(query).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();
					product = new ProductListDTO();
					product.setProduct_id(Integer.parseInt(String.valueOf(obj[0])));
					product.setProduct_name(String.valueOf(obj[1]));
					product.setProduct_desc(String.valueOf(obj[2]));
					product.setProduct_line_name(String.valueOf(obj[3]));
					//product.setProduct_line_desc(String.valueOf(obj[4]));
					//List<ProductUploadModel> files = getProductFile(product.getProduct_id());
					//product.setFiles(files);
					unMapedproductlist.add(product);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return unMapedproductlist;
	}

	@Override
	public List<ProductListDTO> MapedProductswithJiraBoards() {
		// TODO Auto-generated method stub
		List<ProductListDTO> Mapedproductlist = new ArrayList<ProductListDTO>();
		try {

			String query = "SELECT p.product_id,p.product_name,p.product_desc,pl.product_line_name,pl.product_line_id FROM product p,product_line pl where " + 
					"p.fk_jira_boardid>0 and p.delete_status=0 and pl.product_line_id=p.fk_product_line " + 
					"and pl.delete_status=0;";

			ProductListDTO product = null;
			Product prodcut = new Product();

			List<Object> list = entityManager.createNativeQuery(query).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
					Object[] obj = (Object[]) iterator.next();
					product = new ProductListDTO();
					product.setProduct_id(Integer.parseInt(String.valueOf(obj[0])));
					product.setProduct_name(String.valueOf(obj[1]));
					product.setProduct_desc(String.valueOf(obj[2]));
					product.setProduct_line_name(String.valueOf(obj[3]));
					//product.setProduct_line_desc(String.valueOf(obj[4]));
					//List<ProductUploadModel> files = getProductFile(product.getProduct_id());
					//product.setFiles(files);
					Mapedproductlist.add(product);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Mapedproductlist;
	}

}
