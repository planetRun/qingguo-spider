package org.choviwu.top.qg.service;

import org.choviwu.top.qg.entity.StudentUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 奕仁
 * @since 2020-02-25
 */
public interface StudentUserService extends IService<StudentUser> {


    boolean bindAccount(String openId, String username, String password,String school);

    boolean register(StudentUser user);
}
