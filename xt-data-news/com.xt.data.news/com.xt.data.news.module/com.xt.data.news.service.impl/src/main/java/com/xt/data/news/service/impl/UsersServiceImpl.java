package com.xt.data.news.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import com.xt.data.news.base.Page;
import com.xt.data.news.base.Pageable;

import com.xt.data.news.entity.Users;
import com.xt.data.news.service.UsersService;

/**
 * ServiceImpl - 用户
 * 
 * @author vivi207
 * @version xt V0.0.1
 * @date 2021-03-31 10:10:20
 */
@Service
public class UsersServiceImpl extends BaseServiceImpl<Users, Long> implements UsersService {

	@Override
	@Transactional
	public Users save(Users users) {
		return super.save(users);
	}

	@Override
	@Transactional
	public Users update(Users users) {
		return super.update(users);
	}

	@Override
	@Transactional
	public Users update(Users users, String... ignoreProperties) {
		return super.update(users, ignoreProperties);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	public void delete(Users users) {
		super.delete(users);
	}

}