package org.choviwu.top.qg.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Maps;
import org.choviwu.top.qg.entity.Notice;
import org.choviwu.top.qg.entity.StudentUser;
import org.choviwu.top.qg.ex.CrudException;
import org.choviwu.top.qg.ex.ExceptionEnum;
import org.choviwu.top.qg.mapper.NoticeMapper;
import org.choviwu.top.qg.service.NoticeService;
import org.choviwu.top.qg.service.StudentScoreService;
import org.choviwu.top.qg.service.StudentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/***
 *
 **/
@RequestMapping("/api")
@RestController
public class ConfigController {


    @Autowired
    private NoticeService noticeService;

    @RequestMapping(value = "/config")
    public Object queryScore() throws IOException {

        LambdaQueryWrapper<Notice> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Notice::getState, 1);

        return noticeService.getBaseMapper().selectList(wrapper).stream().limit(3).collect(Collectors.toList());
    }

}
