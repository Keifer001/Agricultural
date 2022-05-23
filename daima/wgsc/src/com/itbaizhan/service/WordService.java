package com.itbaizhan.service;

import com.itbaizhan.ORM.*;

import java.util.*;

public interface WordService {
	/** �������� */	
	public boolean addWord(Leaveword word) throws Exception;
	/** ��ҳ����������� */
	public List browseWord(int pageSize,int pageNo) throws Exception;
	/** ����������� */
	public List browseWord() throws Exception;
	/** ͳ���������� */
	public int countWord() throws Exception;	
	/** ɾ������ */	
	public boolean delWord(Integer id) throws Exception;	
	/** װ������ */	
	public Leaveword loadWord(Integer id) throws Exception;
	/** �ظ����� */	
	public boolean updateWord(Leaveword word) throws Exception;
}
