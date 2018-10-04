package com.nexiilabs.autolifecycle.usermanagement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class UserRepositoryImpl implements UserRepository{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<UserModel> getAll() {
		List<UserModel> userList=new ArrayList<UserModel>();
		UserModel model=null;
		/*userModel.setUserId(1);
		userModel.setUser_first_name("Gv");
		userModel.setUser_last_name("Surya");
		userModel.setUser_name(userModel.getUser_first_name()+userModel.getUser_last_name());
		userModel.setUser_email("surya@nexiilabs.com");
		userModel.setUser_password("123456789");
		userList.add(userModel);
		UserModel userModel1=new UserModel();
		userModel1.setUserId(2);
		userModel1.setUser_first_name("satiri");
		userModel1.setUser_last_name("Rahul");
		userModel1.setUser_name(userModel1.getUser_first_name()+userModel1.getUser_last_name());
		userModel1.setUser_email("Rahul@nexiilabs.com");
		userModel1.setUser_password("987654321");
		userList.add(userModel1);*/
		
		try {
			String query = "SELECT u.user_id,u.user_name,u.user_email FROM users u where u.delete_status=0 ;";

			List<Object> list = entityManager.createNativeQuery(query).getResultList();
			if (!list.isEmpty()) {
				Iterator iterator = list.iterator();
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] obj = (Object[]) itr.next();
					model = new UserModel();
					model.setUserId(Integer.parseInt(String.valueOf(obj[0])));
					model.setUser_name(String.valueOf(obj[1]));
					model.setUser_email(String.valueOf(obj[2]));
				
					userList.add(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return userList;
	}

}
