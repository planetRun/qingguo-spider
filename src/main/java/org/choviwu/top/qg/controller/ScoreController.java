package org.choviwu.top.qg.controller;

import com.google.common.collect.Maps;
import org.choviwu.top.qg.entity.StudentUser;
import org.choviwu.top.qg.ex.CrudException;
import org.choviwu.top.qg.ex.ExceptionEnum;
import org.choviwu.top.qg.score.JwcRequest;
import org.choviwu.top.qg.service.StudentScoreService;
import org.choviwu.top.qg.service.StudentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;

/***
 *
 **/
@Controller
@RequestMapping("/api")
public class ScoreController {

    @Autowired
    private StudentScoreService studentScoreService;
    @Autowired
    private StudentUserService userService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 查询分数页
     *
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/queryScore")
    public Object queryScore() throws IOException {
        if (request.getSession().getAttribute("user") != null) {
            return "score";
        } else {
            return "error";
        }
    }

    /**
     * 绑定页
     * @throws IOException
     */
    @GetMapping(value = "/login")
    public Object bindPage() throws IOException {
        return "bind";
    }

    /**
     * 获取分数接口
     */
    @ResponseBody
    @RequestMapping(value = "/getScore")
    public Object getScore2(@RequestParam String xqxn,
                            @RequestParam String xn) throws IOException {
        StudentUser user = (StudentUser) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CrudException(ExceptionEnum.sys_error);
        }
        xqxn = xn + xqxn;
        return studentScoreService.getCourseScoreSchool(user.getStudentId(), user.getPassword(),user.getSchoolId(), xqxn, xn);
    }

    /**
     * 绑定账号
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/bindAccount")
    public Object bindAccount(@RequestParam String username,
                              @RequestParam String password,
                              @RequestParam String school) {

        Object openIdSession = request.getSession().getAttribute("openId");
        if (openIdSession == null) {
            throw new CrudException(ExceptionEnum.sys_error);
        }
        if (!userService.bindAccount(openIdSession.toString(), username, password,school)) {
            throw new CrudException(ExceptionEnum.sys_error);
        }
        return openIdSession;
    }

    /**
     * 注册 登录
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/login")
    public Object login(@RequestParam String username,
                        @RequestParam String password,
                        @RequestParam String school) {

//        JwcRequest.login(username, password,school);
        StudentUser user = StudentUser.builder().studentId(username)
                .schoolId(Integer.valueOf(school))
                .openId("")
                .addtime(new BigDecimal(System.currentTimeMillis()))
                .password(password).build();
//  todo 自行校验
//        userService.register(user);
        request.getSession().setAttribute("user", user);
        return Maps.newHashMap();
    }

}
