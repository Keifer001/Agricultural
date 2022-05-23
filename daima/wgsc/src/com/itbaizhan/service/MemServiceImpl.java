package com.itbaizhan.service;

import java.util.*;

import org.hibernate.*;

import com.itbaizhan.ORM.*;
import com.itbaizhan.base.*;

public class MemServiceImpl extends BaseLog implements MemService {

	/** ����ע���Ա */
	@Override
	public boolean addMember(Member member) throws Exception {
		Session session = MySessionFactory.getSession();
		Transaction tx = null;
		boolean result = false;
		try{
			tx = session.beginTransaction();
			session.save(member);
			tx.commit();
			result=true;
		}catch(Exception ex){
			if(tx!=null) {
				tx.rollback();
			}
			logger.info("��ִ��MemServiceImpl���е�addMember����ʱ���?\n");
			ex.printStackTrace();
		}finally{
			MySessionFactory.closeSession();
		}	
		return result;
	}

	/** �����Ա���� */
	public List browseMemberLevel() throws Exception {
		Session session = MySessionFactory.getSession();
		Transaction tx = null;
		List list = null;
		try{
			Query query = session.createQuery("from Memberlevel as a order by a.id");
			tx = session.beginTransaction();
			list = query.list();
			tx.commit();
			if (!Hibernate.isInitialized(list))Hibernate.initialize(list);
		}catch(Exception ex){
			if(tx!=null)tx.rollback();
			logger.info("��ִ��MemServiceImpl���е�browseMemberLevel����ʱ���?\n");
			ex.printStackTrace();
		}finally{
			MySessionFactory.closeSession();
		}	
		return list;
	}

	/** ����¼�ʺ��Ƿ���Ч */
	public boolean chkLoginName(String loginName) throws Exception {
		Session session = MySessionFactory.getSession();
		Transaction tx = null;
		boolean result = true;
		try{
			String hql = "select count(*) from Member as a where a.loginName=:loginName";
			Query query = session.createQuery(hql);
			query.setString("loginName", loginName);
			query.setMaxResults(1);
			tx = session.beginTransaction();
			if (((Integer)query.uniqueResult()).intValue()>0)result=false;
			tx.commit();
		}catch(Exception ex){
			if(tx!=null)tx.rollback();
			logger.info("��ִ��MemServiceImpl���е�chkLoginName����ʱ���?\n");
			ex.printStackTrace();
		}finally{
			MySessionFactory.closeSession();
		}	
		return result;
	}

	/** װ�ػ�Ա���� */
	public Memberlevel loadMemberLevel(Integer id) throws Exception {
		Session session = MySessionFactory.getSession();
		Transaction tx = null;
		Memberlevel level = null;
		try{
			tx = session.beginTransaction();
			level = (Memberlevel)session.get(Memberlevel.class, id);
			tx.commit();
		}catch(Exception ex){
			if(tx!=null)tx.rollback();
			logger.info("��ִ��MemServiceImpl���е�loadMemberLevel����ʱ���?\n");
			ex.printStackTrace();
		}finally{
			MySessionFactory.closeSession();
		}	
		return level;
	}

	/** ��Ա��¼ */
	@Override
	public Member memLogin(String loginName, String loginPwd) throws Exception {
		Session session = MySessionFactory.getSession();
		Transaction tx = null;
		Member mem = null;
		try{
			String hql = "select a from Member as a where a.loginName=:loginName and a.loginPwd=:loginPwd";
			Query query = session.createQuery(hql);
			query.setString("loginName", loginName);
			query.setString("loginPwd", loginPwd);
			query.setMaxResults(1);
			tx = session.beginTransaction();
			mem = (Member)query.uniqueResult();
			mem.setLoginTimes(Integer.valueOf(mem.getLoginTimes().intValue()+1));
			mem.setLastDate(new Date());
			session.update(mem);
			tx.commit();
		}catch(Exception ex){
			if(tx!=null)tx.rollback();
			logger.info("��ִ��MemServiceImpl���е�memLogin����ʱ���?\n");
			ex.printStackTrace();
		}finally{
			MySessionFactory.closeSession();
		}	
		return mem;
	}

	/** �޸�ע���Ա */
	public boolean updateMember(Member member) throws Exception {
		Session session = MySessionFactory.getSession();
		Transaction tx = null;
		boolean result = false;
		try{
			tx = session.beginTransaction();
			session.update(member);
			tx.commit();
			result=true;
		}catch(Exception ex){
			if(tx!=null)tx.rollback();
			logger.info("��ִ��MemServiceImpl���е�updateMember����ʱ���?\n");
			ex.printStackTrace();
		}finally{
			MySessionFactory.closeSession();
		}	
		return result;
	}

	/** ���ע���Ա*/
	public List browseMember() throws Exception {
		Session session = MySessionFactory.getSession();
		Transaction tx = null;
		List list = null;
		try{
			Query query = session.createQuery("from Member as a order by a.id");
			tx = session.beginTransaction();
			list = query.list();
			tx.commit();
			if (!Hibernate.isInitialized(list))Hibernate.initialize(list);
		}catch(Exception ex){
			if(tx!=null)tx.rollback();
			logger.info("��ִ��MemServiceImpl���е�browseMember����ʱ���?\n");
			ex.printStackTrace();
		}finally{
			MySessionFactory.closeSession();
		}	
		return list;
	}

	/** ɾ��ע���Ա */
	public boolean delMember(Integer id) throws Exception {
		Session session = MySessionFactory.getSession();
		Transaction tx = null;
		boolean status = false;
		try{
			tx = session.beginTransaction();
			Member member = (Member)session.load(Member.class, id);
			session.delete(member);
			tx.commit();
			status = true;
		}catch(Exception ex){
			if(tx!=null)tx.rollback();
			logger.info("��ִ��MemServiceImpl���е�delMember����ʱ���?\n");
			ex.printStackTrace();
		}finally{
			MySessionFactory.closeSession();
		}	
		return status;
	}

	/**װ��ע���Ա */
	public Member loadMember(Integer id) throws Exception {
		Session session = MySessionFactory.getSession();
		Transaction tx = null;
		Member member = null;
		try{
			tx = session.beginTransaction();
			member = (Member)session.get(Member.class, id);
			tx.commit();
		}catch(Exception ex){
			if(tx!=null)tx.rollback();
			logger.info("��ִ��MemServiceImpl���е�loadMember����ʱ���?\n");
			ex.printStackTrace();
		}finally{
			MySessionFactory.closeSession();
		}	
		return member;
	}
}
