package org.choviwu.top.qg.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.choviwu.top.qg.controller.web.ApiResponse;
import org.choviwu.top.qg.entity.BindInfoDTO;
import org.choviwu.top.qg.entity.CourseScore;
import org.choviwu.top.qg.entity.StudentUser;
import org.choviwu.top.qg.ex.CrudException;
import org.choviwu.top.qg.ex.ExceptionEnum;
import org.choviwu.top.qg.score.CommonLog;
import org.choviwu.top.qg.score.JwcRequest;
import org.choviwu.top.qg.service.CourseScoreService;
import org.choviwu.top.qg.service.StudentUserService;
import org.choviwu.top.qg.util.WeixinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
public class AuthController {

    @Autowired
    private StudentUserService studentUserService;
    @Autowired
    private CourseScoreService courseScoreService;


    @RequestMapping("/getOpenId")
    public ApiResponse getOpenId(String code) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isEmpty(code)) {
            throw new RuntimeException("code不存在，请重新进入页面");
        }
        try {
            final String appId = CommonLog.CACHE_MAP.get("ydcx_appid");
            final String secret = CommonLog.CACHE_MAP.get("ydcx_secret");
            String xcxOpenId = WeixinUtil.getXCXOpenId(appId, secret, code);
            return ApiResponse.builder().code(200).msg("success").data(xcxOpenId).build();

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
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

        try {
            final List<CourseScore> courseScores = JwcRequest.getCourseScores(JwcRequest.login(userNo, password, bindInfoDTO.getSchoolId().toString()));

            final Integer register = studentUserService.register(StudentUser.builder()
                    .schoolId(bindInfoDTO.getSchoolId())
                    .studentId(bindInfoDTO.getUserNo())
                    .password(bindInfoDTO.getPassword()).state(1)
                    .openId(bindInfoDTO.getOpenId())
                    .build()
            );
            courseScores.forEach(c->{
                c.setUid(register);
            });
            courseScoreService.asynUpdate(courseScores);
        } catch (Exception e) {
            throw new CrudException(ExceptionEnum.account_or_password_error);
        }

        return bindInfoDTO;
    }

    @RequestMapping("/unbind")
    public boolean unbind(String openId, @RequestParam(required = false, defaultValue = "13683") String schoolId) throws Exception {
        if (StringUtils.isEmpty(openId)) {
            throw new RuntimeException("openId不存在，请重新进入页面");
        }

        LambdaQueryWrapper<StudentUser> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(StudentUser::getOpenId, openId);
        wrapper.eq(StudentUser::getSchoolId, schoolId);
        StudentUser studentUser = studentUserService.getBaseMapper().selectOne(wrapper);
        studentUser.setState(-1);
        studentUserService.getBaseMapper().updateById(studentUser);
        return true;
    }

}
