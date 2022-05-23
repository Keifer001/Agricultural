package com.itbaizhan.service;

import java.sql.*;
import java.util.*;

import com.itbaizhan.ORM.*;
import com.itbaizhan.base.BaseLog;

import org.hibernate.*;

public class CartServiceImpl extends BaseLog implements CartService {

	/** ѡ����Ʒ */	
	@Override
    public boolean addCart(Member member, Merchandise mer, int number)
			throws Exception {
		Session session = MySessionFactory.getSession();
		Transaction tx = null;
		boolean status = false;
		try{
			Cartselectedmer sel = null;
			int favourable = member.getMemberlevel().getFavourable().intValue();			
			//�жϸû�Ա�Ƿ��Ѿ���ʹ���еĹ��ﳵ
			String hql ="from Cart as a where a.member=:member and a.cartStatus=0";
			Query query = session.createQuery(hql);
			query.setEntity("member", member);
			query.setMaxResults(1);
			tx = session.beginTransaction();
			Cart cart = (Cart)query.uniqueResult();
			if (cart==null){
				cart = new Cart();
				cart.setCartStatus(new Integer(0));
				cart.setMember(member);
				cart.setMoney(Double.valueOf(number*mer.getPrice().doubleValue()));
				cart.getMerchandises().add(mer);
				session.save(cart);
			}else{				
				//���ѡ�������Ѿ�ѡ�������Ʒ��ֻ������Ʒ��������
				hql = "select a from Cartselectedmer as a where a.cart=:cartid and a.merchandise=:merid order by a.id desc";
				query = session.createQuery(hql);
				query.setInteger("cartid", cart.getId().intValue());
				query.setInteger("merid", mer.getId().intValue());
				query.setMaxResults(1);
				sel = (Cartselectedmer)query.uniqueResult();
				int total = number;
				System.out.println((total*mer.getPrice().doubleValue()*favourable/100));
				if(sel!=null){
					total = number+sel.getNumber().intValue();
					sel.setNumber(Integer.valueOf(total));
					if (mer.getSpecial().intValue()==1){//�ؼ���Ʒ
						sel.setPrice(mer.getSprice());
						sel.setMoney(Double.valueOf(total*mer.getSprice().doubleValue()));
					}else{//��ͨ��Ʒ
						sel.setPrice(Double.valueOf(mer.getPrice().doubleValue()*favourable/100));
						sel.setMoney(Double.valueOf(total*mer.getPrice().doubleValue()*favourable/100));
					}						
					session.update(sel);
				}else{
					cart.getMerchandises().add(mer);
				}
				if (mer.getSpecial().intValue()==1){//�ؼ���Ʒ
					cart.setMoney(Double.valueOf(cart.getMoney().doubleValue()+number*mer.getSprice().doubleValue()));
				}else{//��ͨ��Ʒ
					cart.setMoney(Double.valueOf(cart.getMoney().doubleValue()+number*mer.getPrice().doubleValue()*favourable/100));
				}				
				session.update(cart);
			}
			tx.commit();

			//�������δѡ�������Ҫ�޸�ѡ����¼���й��ֶ�
			if(sel==null){
				tx = session.beginTransaction();				
				hql = "select a from Cartselectedmer as a where a.cart=:cartid and a.merchandise=:merid order by a.id desc";
				query = session.createQuery(hql);
				query.setInteger("cartid", cart.getId().intValue());
				query.setInteger("merid", mer.getId().intValue());
				query.setMaxResults(1);
				sel = (Cartselectedmer)query.uniqueResult();
				if(sel!=null){
					sel.setNumber(Integer.valueOf(number));
					if (mer.getSpecial().intValue()==1){//�ؼ���Ʒ
						sel.setPrice(mer.getSprice());
						sel.setMoney(Double.valueOf(number*mer.getSprice().doubleValue()));
					}else{//��ͨ��Ʒ
						sel.setPrice(Double.valueOf(mer.getPrice().doubleValue()*favourable/100));
						sel.setMoney(Double.valueOf(number*mer.getPrice().doubleValue()*favourable/100));
					}					
					session.update(sel);
				}
				tx.commit();
			}			
			status = true;
		}catch(Exception ex){
			if(tx!=null)tx.rollback();
			logger.info("��ִ��CartServiceImpl���е�addCart����ʱ���?\n");
			ex.printStackTrace();
		}finally{
			MySessionFactory.closeSession();
		}	
		return status;
	}

	/** �鿴���ﳵ�е�ѡ����Ʒ */
	@Override
	public List browseCart(Member member) throws Exception {
		Session session = MySessionFactory.getSession();
		Transaction tx = null;
		List result = null;
		try{
			//ȡ�øû�Ա��ʹ���й��ﳵID
			Integer cartid = new Integer(0);
			String hql ="from Cart as a where a.member=:member and a.cartStatus=0";
			Query query = session.createQuery(hql);
			query.setEntity("member", member);
			query.setMaxResults(1);
			tx = session.beginTransaction();
			Cart cart = (Cart)query.uniqueResult();
			if (cart!=null){
				cartid = cart.getId();
			}
			tx.commit();

			//������ﳵ�е�����ѡ����¼
			hql ="from Cartselectedmer as a where a.cart=:cartid";
			query = session.createQuery(hql);
			query.setInteger("cartid", cartid.intValue());
			tx = session.beginTransaction();
			result = query.list();
			if(!Hibernate.isInitialized(result)){
				Hibernate.initialize(result);
			}
			tx.commit();
		}catch(Exception ex){
			if(tx!=null)tx.rollback();
			logger.info("��ִ��CartServiceImpl���е�browseCart����ʱ���?\n");
			ex.printStackTrace();
		}finally{
			MySessionFactory.closeSession();
		}	
		return result;
	}

	/** ��չ��ﳵ */	
	@Override
	public boolean clearCart(Member member) throws Exception {
		Session session = MySessionFactory.getSession();
		Transaction tx = null;
		boolean status = false;
		try{
			//ȡ�øû�Ա��ʹ���й��ﳵID
			Integer cartid = new Integer(0);
			String hql ="from Cart as a where a.member=:member and a.cartStatus=0";
			Query query = session.createQuery(hql);
			query.setEntity("member", member);
			query.setMaxResults(1);
			tx = session.beginTransaction();
			Cart cart = (Cart)query.uniqueResult();
			if (cart!=null){
				cartid = cart.getId();
				cart.setMoney(Double.valueOf(0));
				session.update(cart);
			}
			tx.commit();

			//ɾ���ﳵ�е�����ѡ����¼(ͨ��JDBC��������ɾ��)
			Connection con = session.connection();
			Statement stat = con.createStatement();			
			String sql ="delete from Cartselectedmer where cart="+cartid;
			tx = session.beginTransaction();
			stat.executeUpdate(sql);
			tx.commit();
			if (stat!=null)stat.close();
			status = true;
		}catch(Exception ex){
			if(tx!=null)tx.rollback();
			logger.info("��ִ��CartServiceImpl���е�clearCart����ʱ���?\n");
			ex.printStackTrace();
		}finally{
			MySessionFactory.closeSession();
		}	
		return status;
	}

	/** ɾ����ѡ����Ʒ */	
	@Override
	public boolean delCart(Integer id) throws Exception {
		Session session = MySessionFactory.getSession();
		Transaction tx = null;
		boolean status = false;
		try{
			//ɾ��ָ����ѡ����¼
			double money = 0;
			Integer cartid = new Integer(0);
			tx = session.beginTransaction();
			Cartselectedmer sel = (Cartselectedmer)session.get(Cartselectedmer.class, id);			
			if (sel!=null){
				money = sel.getMoney().doubleValue();
				cartid = sel.getCart();
				session.delete(sel);				
			}
			tx.commit();

			//���¹��ﳵ�ܽ��
			tx = session.beginTransaction();
			Cart cart = (Cart)session.get(Cart.class, cartid);
			if (cart!=null){
				cart.setMoney(Double.valueOf(cart.getMoney().doubleValue()-money));
				session.update(cart);
			}
			tx.commit();
			status = true;
		}catch(Exception ex){
			if(tx!=null)tx.rollback();
			logger.info("��ִ��CartServiceImpl���е�delCart����ʱ���?\n");
			ex.printStackTrace();
		}finally{
			MySessionFactory.closeSession();
		}	
		return status;
	}

	/** װ��ָ����Ա�Ĺ��ﳵ */	
	@Override
	public Cart loadCart(Member member) throws Exception {
		Session session = MySessionFactory.getSession();
		Transaction tx = null;
		Cart cart = null;
		try{
			//ȡ�øû�Ա��ʹ���й��ﳵ
			String hql ="from Cart as a where a.member=:member and a.cartStatus=0";
			Query query = session.createQuery(hql);
			query.setEntity("member", member);
			query.setMaxResults(1);
			tx = session.beginTransaction();
			cart = (Cart)query.uniqueResult();
			tx.commit();
		}catch(Exception ex){
			if(tx!=null)tx.rollback();
			logger.info("��ִ��CartServiceImpl���е�loadCart����ʱ���?\n");
			ex.printStackTrace();
		}finally{
			MySessionFactory.closeSession();
		}	
		return cart;
	}

	/** ����ѡ����Ʒ������ */	
	@Override
	public boolean modiCart(Integer id, int number) throws Exception {
		Session session = MySessionFactory.getSession();
		Transaction tx = null;
		boolean status = false;
		try{
			//�޸�ָ����ѡ����¼
			double diff = 0;
			Integer cartid = new Integer(0);
			tx = session.beginTransaction();
			Cartselectedmer sel = (Cartselectedmer)session.get(Cartselectedmer.class, id);			
			if (sel!=null){
				cartid = sel.getCart();
				diff = (number - sel.getNumber().intValue())*sel.getPrice().doubleValue();
				sel.setNumber(Integer.valueOf(number));
				sel.setMoney(Double.valueOf(sel.getPrice().doubleValue()*number));
				session.update(sel);				
			}
			tx.commit();

			//���¹��ﳵ�ܽ��
			tx = session.beginTransaction();
			Cart cart = (Cart)session.get(Cart.class, cartid);
			if (cart!=null){
				cart.setMoney(Double.valueOf(cart.getMoney().doubleValue()+diff));
				session.update(cart);
			}
			tx.commit();
			status = true;
		}catch(Exception ex){
			if(tx!=null)tx.rollback();
			logger.info("��ִ��CartServiceImpl���е�modiCart����ʱ���?\n");
			ex.printStackTrace();
		}finally{
			MySessionFactory.closeSession();
		}	
		return status;
	}

	/** ���¹��ﳵ */	
	@Override
	public boolean updateCart(Cart cart) throws Exception {
		Session session = MySessionFactory.getSession();
		Transaction tx = null;
		boolean status = false;
		try{
			tx = session.beginTransaction();
			session.update(cart);
			tx.commit();
			status = true;
		}catch(Exception ex){
			if(tx!=null)tx.rollback();
			logger.info("��ִ��CartServiceImpl���е�updateCart����ʱ���?\n");
			ex.printStackTrace();
		}finally{
			MySessionFactory.closeSession();
		}	
		return status;
	}

}
