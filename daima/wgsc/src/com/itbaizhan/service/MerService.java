package com.itbaizhan.service;

import java.util.*;

import com.itbaizhan.ORM.*;

/** ��Ʒ���ࡢ��Ʒ���ؼ���Ʒҵ���߼�����ӿ� */
public interface MerService {	
	/** �����Ʒ���� */
	public List browseCategory() throws Exception;	
	/** װ��ָ������Ʒ���� */	
	public Category loadCategory(Integer id) throws Exception;	
	/** ɾ��ָ������Ʒ���� */	
	public boolean delCategory(Integer id) throws Exception;	
	/** ������Ʒ���� */	
	public boolean addCategory(Category cate) throws Exception;	
	/** ������Ʒ���� */	
	public boolean updateCategory(Category cate) throws Exception;
	
	/** �����Ʒ */
	public List browseMer(String hql) throws Exception;	
	/** װ��ָ������Ʒ */	
	public Merchandise loadMer(Integer id) throws Exception;	
	/** ɾ��ָ������Ʒ */	
	public boolean delMer(Integer id) throws Exception;	
	/** ������Ʒ */	
	public boolean addMer(Merchandise mer) throws Exception;	
	/** ������Ʒ */	
	public boolean updateMer(Merchandise mer) throws Exception;
	
	/** ��ҳ�����Ʒ */
	public List browseMer(int pageSize,int pageNo,int cateId,boolean isSpecial) throws Exception;
	/** ������Ʒ */
	public List browseMer(int pageSize,int pageNo,String hql) throws Exception;	
	/** ͳ�Ƽ�¼���� */
	public int countRecord(String hql) throws Exception;	
}