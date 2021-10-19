package org.choviwu.top.qg.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;
import org.choviwu.top.qg.entity.BindInfoDTO;
import org.choviwu.top.qg.entity.StudentUser;
import org.choviwu.top.qg.ex.CrudException;
import org.choviwu.top.qg.score.CommonLog;
import org.choviwu.top.qg.score.JwcRequest;
import org.choviwu.top.qg.service.StudentUserService;
import org.choviwu.top.qg.util.WeixinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/auth")
@RestController
public class AuthController {

    @Autowired
    private StudentUserService studentUserService;


    @RequestMapping("/getOpenId")
    public Object getOpenId(String code) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isEmpty(code)) {
            throw new RuntimeException("code不存在，请重新进入页面");
        }
        try {
            final String appId = CommonLog.CACHE_MAP.get("ydcx_appid");
            final String secret = CommonLog.CACHE_MAP.get("ydcx_secret");
            return WeixinUtil.getXCXOpenId(appId, secret, code);

        } catch (Exception e) {

        }
        return "";
    }


    @RequestMapping("/isBindJwc")
    public boolean isBindJwc(String openId) throws Exception {
        if (StringUtils.isEmpty(openId)) {
            throw new RuntimeException("openId不存在，请重新进入页面");
        }
        try {
            LambdaQueryWrapper<StudentUser> query = Wrappers.lambdaQuery();
            query.eq(StudentUser::getOpenId, openId);

            StudentUser studentUser = studentUserService.getBaseMapper().selectOne(query);
            if (studentUser != null && studentUser.getState() != -1) {
                return true;
            }
            return false;
        } catch (Exception e) {

        }
        return false;
    }

    @RequestMapping("/bind")
    public Object bind(BindInfoDTO bindInfoDTO) throws Exception {
        if (StringUtils.isEmpty(bindInfoDTO.getOpenId())) {
            throw new RuntimeException("openId不存在，请重新进入页面");
        }
        String userNo = bindInfoDTO.getUserNo();
        String password = bindInfoDTO.getPassword();

        JwcRequest.login(userNo, password, "13683");
        studentUserService.register(StudentUser.builder()
                .schoolId(13683).studentId(bindInfoDTO.getUserNo())
                .password(bindInfoDTO.getPassword()).state(1)
                .openId(bindInfoDTO.getOpenId())
                .build()
        );
        return bindInfoDTO;
    }

    @RequestMapping("/unbind")
    public boolean unbind(String openId) throws Exception {
        if (StringUtils.isEmpty(openId)) {
            throw new RuntimeException("openId不存在，请重新进入页面");
        }

        LambdaQueryWrapper<StudentUser> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(StudentUser::getOpenId, openId);
        wrapper.eq(StudentUser::getSchoolId, "13683");
        StudentUser studentUser = studentUserService.getBaseMapper().selectOne(wrapper);
        studentUser.setState(-1);
        studentUserService.getBaseMapper().updateById(studentUser);
        return true;
    }

}
