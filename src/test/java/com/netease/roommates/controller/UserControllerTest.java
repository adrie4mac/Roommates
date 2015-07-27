package com.netease.roommates.controller;

import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.netease.exception.ServiceException;
import com.netease.roommates.po.Personality;
import com.netease.roommates.po.User;
import com.netease.user.service.IUserInfoService;

public class UserControllerTest {
	@InjectMocks
	private UserController userController;

	@Mock
	private IUserInfoService userInfoService;

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetUserById() throws ServiceException {
		User user = generateUser();
		when(userInfoService.getUserById(user.getUserId())).thenReturn(user);
		User user2 = userController.getUserById(user.getUserId());
		assertEqualUser(user, user2);
	}

	@Test
	public void testGetUserPersonalityByUserId() throws ServiceException {
		Personality personality = new Personality();
		personality.setUserId(1);
		when(userInfoService.getUserPersonality(1)).thenReturn(personality);
		Personality personality2 = userController.getUserPersonalityById(1);
		assertEquals(personality.getUserId(), personality2.getUserId());
	}

	@Test
	public void testGetUserListByAddress() throws ServiceException, UnsupportedEncodingException {
		String address = "Address:Wall Street";
		List<User> users = new ArrayList<User>();
		for (int i = 0; i < 5; i++) {
			users.add(generateUser(address));
		}
		when(userInfoService.getUserListByAddress(address)).thenReturn(users);
		List<User> userList = userController.getUserListByAddress(address);
		for(int i = 0; i < users.size(); i++) {
			User user1 = users.get(i);
			User user2 = userList.get(i);
			assertEqualUser(user1, user2);
		}
		
	}

	private void assertEqualUser(User u1, User u2) {
		assertEquals(u1.getUserId(), u2.getUserId());
		assertEquals(u1.getAddress(), u2.getAddress());
		assertEquals(u1.getCompany(), u2.getCompany());
	}

	private User generateUser() {
		return generateUser(null);
	}

	private User generateUser(String addr) {
		Random rand = new Random();
		int userId = rand.nextInt();
		User user = new User();
		user.setUserId(userId);
		if (addr != null) {
			user.setAddress(addr);
		} else {
			user.setAddress("address" + userId);
		}
		user.setCompany("company" + userId);
		return user;
	}
}
