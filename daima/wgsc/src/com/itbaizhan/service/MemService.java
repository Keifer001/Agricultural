package com.itbaizhan.service;

import java.util.*;

import com.itbaizhan.ORM.*;

public interface MemService {
	/** ��Ա��¼ */
	public Member memLogin(String loginName,String loginPwd) throws Exception;	
	/** �����Ա���� */
	public List browseMemberLevel() throws Exception;
	/** װ�ػ�Ա���� */
	public Memberlevel loadMemberLevel(Integer id) throws Exception;
	/** ����¼�ʺ��Ƿ���Ч */	
	public boolean chkLoginName(String loginName) throws Exception;	
	/** ����ע���Ա */	
	public boolean addMember(Member member) throws Exception;
	/** �޸�ע���Ա */	
	public boolean updateMember(Member member) throws Exception;
	
	/** ���ע���Ա*/
	public List browseMember() throws Exception;
	/** ɾ��ע���Ա */	
	public boolean delMember(Integer id) throws Exception;
	/**װ��ע���Ա */	
	public Member loadMember(Integer id) throws Exception;	
}
