package com.netease.roommates.po;

import java.util.HashMap;

public class HouseConstProperty {
	
	public static HashMap<Integer,String> priceMap;
	public static HashMap<Integer,String> roomTypeMap;
	
	static{
		priceMap = new HashMap<Integer,String>();
		roomTypeMap = new HashMap<Integer,String>();

		priceMap.put(1, "");//����
		priceMap.put(2, "0-500");//500����
		priceMap.put(3, "500-1000");
		priceMap.put(4, "1000-1500");
		priceMap.put(5, "1500-2000");
		priceMap.put(6, "2000-3000");
		priceMap.put(7, "3000-5000");
		priceMap.put(8, "5000-8000");
		priceMap.put(9, "8000-99999");//8000����
		
		roomTypeMap.put(1, "");//����
		roomTypeMap.put(2, "1��");
		roomTypeMap.put(3, "2��");
		roomTypeMap.put(4, "3��");
		roomTypeMap.put(5, "4��");
		roomTypeMap.put(6, "4������");
	}
	
	public static void main(String[] args) {
		System.out.println(priceMap);
		System.out.println(roomTypeMap);
	}
}
