package com.sec.singletable.service.impl;

import com.sec.singletable.entity.User;
import com.sec.singletable.mapper.UserMapper;
import com.sec.singletable.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sec
 * @since 2022-08-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
