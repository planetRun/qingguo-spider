package org.choviwu.top.qg.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.choviwu.top.qg.constant.RedisConstant;
import org.choviwu.top.qg.ex.CrudException;
import org.choviwu.top.qg.redis.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.choviwu.top.qg.entity.StudentUser;
import org.choviwu.top.qg.ex.ExceptionEnum;
import org.choviwu.top.qg.mapper.StudentUserMapper;
import org.choviwu.top.qg.score.JwcRequest;
import org.choviwu.top.qg.service.StudentUserService;

import java.math.BigDecimal;
import java.text.MessageFormat;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 奕仁
 * @since 2020-02-25
 */
@Service
public class StudentUserServiceImpl extends ServiceImpl<StudentUserMapper, StudentUser> implements StudentUserService {

    @Autowired
    private RedisRepository redisRepository;

    @Override
    public boolean bindAccount(String openId, String username, String password,String school) {
        //登录 成功
        JwcRequest.login(username,password,school);
        StudentUser user = StudentUser.builder().addtime(new BigDecimal(System.currentTimeMillis()))
                .openId(openId).schoolId(Integer.valueOf(school))
                .studentId(username)
                .password(password)
                .build();
        return true;
    }

    @Override
    public Integer register(StudentUser user) {
        LambdaUpdateWrapper<StudentUser> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(StudentUser::getStudentId, user.getStudentId());
        wrapper.eq(StudentUser::getSchoolId, user.getSchoolId());
        StudentUser studentUser = getBaseMapper().selectOne(wrapper);
        if (studentUser!= null) {
            studentUser.setOpenId(user.getOpenId());
            studentUser.setPassword(user.getPassword());
            studentUser.setState(1);
            updateById(studentUser);
            return user.getId();
        }
        save(user);
        return user.getId();
    }
}
