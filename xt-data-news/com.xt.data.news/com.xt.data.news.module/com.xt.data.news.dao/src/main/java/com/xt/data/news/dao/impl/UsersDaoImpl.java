package com.xt.data.news.dao.impl;

import org.springframework.stereotype.Repository;

import com.xt.data.news.dao.BaseDaoImpl;
import com.xt.data.news.entity.Users;
import com.xt.data.news.dao.UsersDao;

/**
 * DaoImpl - 用户
 * 
 * @author vivi207
 * @version xt V0.0.1
 * @date 2021-03-31 10:10:20
 */
@Repository
public class UsersDaoImpl extends BaseDaoImpl<Users, Long> implements UsersDao {

}