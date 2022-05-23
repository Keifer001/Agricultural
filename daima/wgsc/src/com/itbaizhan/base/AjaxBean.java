package com.itbaizhan.base;

import com.itbaizhan.ORM.*;
import com.itbaizhan.service.*;

import java.util.*;

/** ??JSON-RPC??????????AjaxBean */
public class AjaxBean extends BaseLog {
	/**???????????б?*/
	public String[][] getCategory(){
		String[][] options = null;
		MerService service = new MerServiceImpl();
		try{
			List list = service.browseCategory();
			Category cate = null;
			int i = 0;
			if (list!=null){
				options = new String[list.size()][2];
				Iterator it = list.iterator();
				while(it.hasNext()){
					cate = (Category)it.next();
					options[i][0] =cate.getId().toString();
					options[i][1] =cate.getCateName().trim();
					i++;
				}
			}else{
				options = new String[1][2];
				options[0][0] ="0";
				options[0][1] ="?????????";
			}
		}catch(Exception ex){
			logger.info("?????AjaxBean???е?getCategory?????????\n");
			ex.printStackTrace();			
		}
		return options;
	}
	
	/**?????????*/
	public String[][] getMemberLevel(){
		String[][] options = null;
		MemService service = new MemServiceImpl();
		try{
			List list = service.browseMemberLevel();
			Memberlevel ml = null;
			int i = 0;
			if (list!=null){
				options = new String[list.size()][2];
				Iterator it = list.iterator();
				while(it.hasNext()){
					ml = (Memberlevel)it.next();
					options[i][0] =ml.getId().toString();
					options[i][1] =ml.getLevelName().trim();
					i++;
				}
			}else{
				options = new String[1][2];
				options[0][0] ="0";
				options[0][1] ="????????";
			}
		}catch(Exception ex){
			logger.info("?????AjaxBean???е?getMemberLevel?????????\n");
			ex.printStackTrace();			
		}
		return options;
	}
	
	/**??????????Ч?????*/
	public boolean chkLoginName(String loginName){
		MemService service = new MemServiceImpl();
		boolean result = false;
		try{
			result = service.chkLoginName(loginName);
		}catch(Exception ex){
			logger.info("?????AjaxBean???е?chkLoginName?????????\n");
			ex.printStackTrace();			
		}
		return result;
	}
	
	/**?????????????*/
	public boolean modiCart(int id,int number){
		CartService service = new CartServiceImpl();
		boolean result = false;
		try{
			result = service.modiCart(Integer.valueOf(id), number);
		}catch(Exception ex){
			logger.info("?????AjaxBean???е?modiCart?????????\n");
			ex.printStackTrace();			
		}
		return result;
	}
	
	/**??????????*/
	public boolean updateLevel(Integer id,Integer levelId){
		MemService service = new MemServiceImpl();
		boolean result = false;
		try{
			Member member = service.loadMember(id);
			Memberlevel level = service.loadMemberLevel(levelId);
			member.setMemberlevel(level);
			service.updateMember(member);
			result = true;
		}catch(Exception ex){
			logger.info("?????AjaxBean???е?updateLevel?????????\n");
			ex.printStackTrace();
		}
		return result;
	}	
}