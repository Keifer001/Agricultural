package com.itbaizhan.service;

import java.util.*;

import com.itbaizhan.ORM.*;
import com.itbaizhan.base.*;
import com.itbaizhan.service.*;

import org.hibernate.*;

public class WordServiceImpl extends BaseLog implements WordService {

	/** �������� */	
	public boolean addWord(Leaveword word) throws Exception {
		Session session = MySessionFactory.getSession();
		Transaction tx = null;
		boolean status = false;
		try{
			tx = session.beginTransaction();
			session.save(word);
			tx.commit();
			status=true;
		}catch(Exception ex){
			if(tx!=null)tx.rollback();
			logger.info("��ִ��WordServiceImpl���е�addWord����ʱ���?\n");
			ex.printStackTrace();
		}finally{
			MySessionFactory.closeSession();
		}	
		return status;
	}

	/** ��ҳ����������� */
	public List browseWord(int pageSize, int pageNo) throws Exception {
		Session session = MySessionFactory.getSession();
		Transaction tx = null;
		List list = null;
		try{
			Query query = session.createQuery("from Leaveword as a order by a.id desc");
			query.setMaxResults(pageSize);
			query.setFirstResult((pageNo-1)*pageSize);
			tx = session.beginTransaction();
			list = query.list();
			tx.commit();
			if (!Hibernate.isInitialized(list))Hibernate.initialize(list);
		}catch(Exception ex){
			if(tx!=null)tx.rollback();
			logger.info("��ִ��WordServiceImpl���е�browseWord����ʱ���?\n");
			ex.printStackTrace();
		}finally{
			MySessionFactory.closeSession();
		}	
		return list;
	}

	/** ����������� */
	public List browseWord() throws Exception {
		Session session = MySessionFactory.getSession();
		Transaction tx = null;
		List list = null;
		try{
			Query query = session.createQuery("from Leaveword as a order by a.id desc");
			tx = session.beginTransaction();
			list = query.list();
			tx.commit();
			if (!Hibernate.isInitialized(list))Hibernate.initialize(list);
		}catch(Exception ex){
			if(tx!=null)tx.rollback();
			logger.info("��ִ��WordServiceImpl���е�browseWord����ʱ���?\n");
			ex.printStackTrace();
		}finally{
			MySessionFactory.closeSession();
		}	
		return list;
	}

	/** ͳ���������� */
	public int countWord() throws Exception {
		Session session = MySessionFactory.getSession();
		Transaction tx = null;
		int count = 0;
		try{
			tx = session.beginTransaction();
			Query query = session.createQuery("select count(*) from Leaveword as a");
			query.setMaxResults(1);
			count = ((Integer)query.uniqueResult()).intValue();
			tx.commit();
		}catch(Exception ex){
			if(tx!=null)tx.rollback();
			logger.info("��ִ��WordServiceImpl���е�countWord����ʱ���?\n");
			ex.printStackTrace();
		}finally{
			MySessionFactory.closeSession();
		}	
		return count;
	}

	/** ɾ������ */	
	public boolean delWord(Integer id) throws Exception {
		Session session = MySessionFactory.getSession();
		Transaction tx = null;
		boolean status = false;
		try{
			tx = session.beginTransaction();
			Leaveword word = (Leaveword)session.load(Leaveword.class, id);
			session.delete(word);
			tx.commit();
			status = true;
		}catch(Exception ex){
			if(tx!=null)tx.rollback();
			logger.info("��ִ��WordServiceImpl���е�delWord����ʱ���?\n");
			ex.printStackTrace();
		}finally{
			MySessionFactory.closeSession();
		}	
		return status;
	}

	/** װ������ */	
	public Leaveword loadWord(Integer id) throws Exception {
		Session session = MySessionFactory.getSession();
		Transaction tx = null;
		Leaveword word = null;
		try{
			tx = session.beginTransaction();
			word = (Leaveword)session.get(Leaveword.class, id);
			tx.commit();
		}catch(Exception ex){
			if(tx!=null)tx.rollback();
			logger.info("��ִ��WordServiceImpl���е�loadWord����ʱ���?\n");
			ex.printStackTrace();
		}finally{
			MySessionFactory.closeSession();
		}	
		return word;
	}

	/** �ظ����� */	
	public boolean updateWord(Leaveword word) throws Exception {
		Session session = MySessionFactory.getSession();
		Transaction tx = null;
		boolean status = false;
		try{
			tx = session.beginTransaction();
			session.update(word);
			tx.commit();
			status=true;
		}catch(Exception ex){
			if(tx!=null)tx.rollback();
			logger.info("��ִ��WordServiceImpl���е�updateWord����ʱ���?\n");
			ex.printStackTrace();
		}finally{
			MySessionFactory.closeSession();
		}	
		return status;
	}

}
