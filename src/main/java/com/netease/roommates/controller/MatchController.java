package com.netease.roommates.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.exception.ServiceException;
import com.netease.match.service.IMatchDataService;
import com.netease.match.service.IMatchDetailService;
import com.netease.roommates.vo.MatchUserDetailInfo;
import com.netease.roommates.vo.MatchUserSimpleInfo;

@Controller
@RequestMapping("/api")
public class MatchController {
	@Autowired
	private IMatchDataService matchDataService;
	@Autowired
	private IMatchDetailService matchDetailService;
	private final static String USER_ID = "userId";
	
	@RequestMapping(value = "/people/list")
	@ResponseBody
	public Map<String, Object> matchPeopleList(@RequestParam("id")int id,
			@RequestParam(value="p", defaultValue="1")int p,
			@RequestParam(value="xb", defaultValue="1")int xb,
			@RequestParam(value="f", defaultValue="1")int f,
			@RequestParam(value="gs", defaultValue="1")int gs,
			@RequestParam(value="cy", defaultValue="1")int cy,
			@RequestParam(value="cw", defaultValue="1")int cw, 
			@RequestParam(value="zx", defaultValue="1")int zx,
			@RequestParam(value="ws", defaultValue="1")int ws,
			@RequestParam(value="xg", defaultValue="1")int xg,
			@RequestParam(value="fk", defaultValue="1")int fk) throws ServiceException {
		
		// logger.info("Parameter: id=" + id + " p");
		
		Map<String, Object> resultMap = new HashMap<String, Object >();
		
		
		List<MatchUserSimpleInfo> resultUserInfo = new ArrayList<MatchUserSimpleInfo>();
		
		resultUserInfo = matchDataService.getMatchUserSimpleInfoByPara(id, p, xb, f, gs, cy, cw, zx, ws, xg, fk);
		
		resultMap.put("data", resultUserInfo);
		resultMap.put("errno", 0);
		return resultMap;//matchPernality.matchResultTest();
	}
	
	@RequestMapping(value = "/people/detail/{id}")
	@ResponseBody
	public Map<String, Object> matchPeopleDetail(HttpSession session, @PathVariable int id) throws ServiceException {
		MatchUserDetailInfo matchUserDetailInfo;// = matchDetailService.getDetailByUser(id);
		if(session==null || session.getAttribute(USER_ID)==null){
			matchUserDetailInfo =  matchDetailService.getDetailByUser(id);
		}else{
			int curUserId = (Integer) session.getAttribute(USER_ID);
			if(curUserId == id) return null;
			matchUserDetailInfo =  matchDetailService.getDetailByUser(curUserId,id);
		}
		Map<String, Object> matchDetailMap = new HashMap<String, Object>();
		if(matchUserDetailInfo != null){
			matchDetailMap.put("data", matchUserDetailInfo);
			matchDetailMap.put("errno", 0);
		} else {
			matchDetailMap.put("data", "用户id不存在");
			matchDetailMap.put("errno", 1);
		}
		
		
		return matchDetailMap;//matchPernality.matchResultTest();
	}

	@RequestMapping(value = "/people/search")
	@ResponseBody
	public Map<String, Object> searchPeopleList(@RequestParam("q")String keyWords,
			@RequestParam("id")int id,
			@RequestParam(value="p", defaultValue="1")int p,
			@RequestParam(value="xb", defaultValue="1")int xb,
			@RequestParam(value="f", defaultValue="1")int f,
			@RequestParam(value="gs", defaultValue="1")int gs,
			@RequestParam(value="cy", defaultValue="1")int cy,
			@RequestParam(value="cw", defaultValue="1")int cw, 
			@RequestParam(value="zx", defaultValue="1")int zx,
			@RequestParam(value="ws", defaultValue="1")int ws,
			@RequestParam(value="xg", defaultValue="1")int xg,
			@RequestParam(value="fk", defaultValue="1")int fk) throws ServiceException {
		
		try {
			keyWords = new String(keyWords.getBytes("iso-8859-1"), "utf8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object >();
		
		List<MatchUserSimpleInfo> resultUserInfo = new ArrayList<MatchUserSimpleInfo>();
		
		resultUserInfo = matchDataService.searchUserSimpleInfoByPara(keyWords,id, p, xb, f, gs, cy, cw, zx, ws, xg, fk);
		
		
		resultMap.put("data", resultUserInfo);
		resultMap.put("errno", 0);
		return resultMap;//matchPernality.matchResultTest();
	}
	
}
