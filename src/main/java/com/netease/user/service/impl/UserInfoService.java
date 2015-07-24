package com.netease.user.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.netease.exception.ServiceException;
import com.netease.exception.StorageException;
import com.netease.roommates.mapper.UserMapper;
import com.netease.roommates.po.Personality;
import com.netease.roommates.po.User;
import com.netease.user.service.IUserInfoService;

@Service
public class UserInfoService implements IUserInfoService {
	private Logger log = LoggerFactory.getLogger(UserInfoService.class);
	@Autowired
	private UserMapper userMapper;

	@Cacheable(value = "userCache", key = "'User' + #id") //Need to be removed since the query sql is same. 
	public User getUserById(int id) throws ServiceException {
		try {
			log.debug("UserInfoService.getUserById");
			return userMapper.getUserById(id);
		} catch (StorageException se) {
			log.error("error getting target user by id: " + id, se);
			throw new ServiceException(se);
		}
	}

	@Override
	@CacheEvict(value = "userCache", key = "'User' + #user.getUserId()")
	public void updateUserBasicInfo(User user) throws ServiceException {
		try {
			userMapper.updateUserBasicInfo(user);
		} catch (StorageException se) {
			log.error("error updatting target user: " + user, se);
			throw new ServiceException(se);
		}
	}

	@Override
	public void insertUser(User user) throws ServiceException {
		try {
			userMapper.insertUser(user);
		} catch (StorageException se) {
			log.error("error updatting target user: " + user, se);
			throw new ServiceException(se);
		}
	}

	@Override
	@Cacheable(value = "userCache", key = "'personality' + #id")
	public Personality getUserPersonality(int id) throws ServiceException {
		try {
			return userMapper.getUserPersonality(id);
		} catch (StorageException se) {
			log.error("error getting target user personality by id: " + id, se);
			throw new ServiceException(se);
		}
	}

	@Override
	public void insertUserPersonality(Personality personality) throws ServiceException {
		try {
			userMapper.insertUserPersonality(personality);
		} catch (StorageException se) {
			log.error("error updatting target user personality: " + personality, se);
			throw new ServiceException(se);
		}
	}

	@Override
	@CacheEvict(value = "userCache", key = "'personality' + #personality.getId()")
	public void updateUserPersonality(Personality personality) throws ServiceException {
		try {
			userMapper.updateUserPersonality(personality);
		} catch (StorageException se) {
			log.error("error updatting target user personality: " + personality, se);
			throw new ServiceException(se);
		}
	}
	
	
	@Override
	public User getUserByName(String name) throws ServiceException {
		try {
			return userMapper.getUserByName(name);
		} catch (StorageException se) {
			log.error("error get user by name:" + name, se);
			throw new ServiceException(se);
		}
	}

	
}
