package com.itbaizhan.service;

import java.util.List;

import com.itbaizhan.ORM.*;
import com.itbaizhan.base.*;

import org.hibernate.*;

/** ?????????????? */
public class AdminServiceImpl extends BaseLog implements AdminService {

	/** ?????????? */
	@Override
	public Admin adminLogin(String loginName, String loginPwd) throws Exception {
		Session session = MySessionFactory.getSession();
		Transaction tx = null;
		Admin admin = null;
		try{
			String hql = "select a from Admin as a where a.loginName=:loginName and a.loginPwd=:loginPwd";
			Query query = session.createQuery(hql);
			query.setString("loginName", loginName);
			query.setString("loginPwd", loginPwd);
			query.setMaxResults(1);
			tx = session.beginTransaction();
			admin = (Admin)query.uniqueResult();
			tx.commit();
		}catch(Exception ex){
			if(tx!=null)tx.rollback();
			logger.info("?????AdminServiceImpl???е?adminLogin?????????\n");
			ex.printStackTrace();
		}finally{
			MySessionFactory.closeSession();
		}	
		return admin;
	}

	/** ????????? */	
	public boolean addAdmin(Admin admin) throws Exception {
		Session session = MySessionFactory.getSession();
		Transaction tx = null;
		boolean status = false;
		try{
			tx = session.beginTransaction();
			session.save(admin);
			tx.commit();
			status = true;
		}catch(Exception ex){
			if(tx!=null)tx.rollback();
			logger.info("?????AdminServiceImpl???е?addAdmin?????????\n");
			ex.printStackTrace();
		}finally{
			MySessionFactory.closeSession();
		}	
		return status;
	}

	/** ???????? */
	@Override
	public List browseAdmin() throws Exception {
		Session session = MySessionFactory.getSession();
		Transaction tx = null;
		List list = null;
		try{
			Query query = session.createQuery("from Admin as a order by a.id");
			tx = session.beginTransaction();
			list = query.list();
			tx.commit();
			if (!Hibernate.isInitialized(list))Hibernate.initialize(list);
		}catch(Exception ex){
			if(tx!=null)tx.rollback();
			logger.info("?????AdminServiceImpl???е?browseAdmin?????????\n");
			ex.printStackTrace();
		}finally{
			MySessionFactory.closeSession();
		}	
		return list;
	}

	/** ???????????? */
	public boolean delAdmin(Integer id) throws Exception {
		Session session = MySessionFactory.getSession();
		Transaction tx = null;
		boolean status = false;
		try{
			tx = session.beginTransaction();
			Admin admin = (Admin)session.load(Admin.class, id);
			session.delete(admin);
			tx.commit();
			status = true;
		}catch(Exception ex){
			if(tx!=null)tx.rollback();
			logger.info("?????AdminServiceImpl???е?delAdmin?????????\n");
			ex.printStackTrace();
		}finally{
			MySessionFactory.closeSession();
		}	
		return status;
	}

	/** ???????????? */
	@Override
    public Admin loadAdmin(Integer id) throws Exception {
		Session session = MySessionFactory.getSession();
		Transaction tx = null;
		Admin admin = null;
		try{
			tx = session.beginTransaction();
			admin = (Admin)session.get(Admin.class, id);
			tx.commit();
		}catch(Exception ex){
			if(tx!=null)tx.rollback();
			logger.info("?????AdminServiceImpl???е?loadAdmin?????????\n");
			ex.printStackTrace();
		}finally{
			MySessionFactory.closeSession();
		}	
		return admin;
	}

	/** ???????? */	
	public boolean updateAdmin(Admin admin) throws Exception {
		Session session = MySessionFactory.getSession();
		Transaction tx = null;
		boolean status = false;
		try{
			tx = session.beginTransaction();
			session.update(admin);
			tx.commit();
			status = true;
		}catch(Exception ex){
			if(tx!=null)tx.rollback();
			logger.info("?????AdminServiceImpl???е?updateAdmin?????????\n");
			ex.printStackTrace();
		}finally{
			MySessionFactory.closeSession();
		}	
		return status;
	}
}