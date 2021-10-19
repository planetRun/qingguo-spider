package org.choviwu.top.qg.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.concurrent.TimeUnit;
import org.choviwu.top.qg.constant.RedisConstant;
import org.choviwu.top.qg.entity.CourseScore;
import org.choviwu.top.qg.entity.CourseScoreDTO;
import org.choviwu.top.qg.entity.StudentScore;
import org.choviwu.top.qg.entity.StudentUser;
import org.choviwu.top.qg.ex.CrudException;
import org.choviwu.top.qg.ex.ExceptionEnum;
import org.choviwu.top.qg.mapper.StudentScoreMapper;
import org.choviwu.top.qg.mapper.StudentUserMapper;
import org.choviwu.top.qg.redis.RedisRepository;
import org.choviwu.top.qg.score.JwcRequest;
import org.choviwu.top.qg.service.CourseScoreService;
import org.choviwu.top.qg.service.StudentScoreService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 奕仁
 * @since 2020-02-25
 */
@Service
public class StudentScoreServiceImpl extends ServiceImpl<StudentScoreMapper, StudentScore> implements StudentScoreService {

    @Autowired
    private StudentScoreMapper scoreMapper;
    @Autowired
    private StudentUserMapper studentUserMapper;
    @Autowired
    private CourseScoreService courseScoreService;

    @Autowired
    private StringRedisTemplate redisRepository;


    @Override
    public String getStudentScore( String openId,  String xnxq,  String xn) {
        return null;
    }

    @Override
    public List<CourseScore> getCourseScores(String openId, String xnxq, String xn) {

        StudentUser studentUser = studentUserMapper.selectOne(new QueryWrapper<StudentUser>().eq("openId", openId));
        if (studentUser != null) {
            List<CourseScore> courseScores = courseScoreService.list(new QueryWrapper<CourseScore>().eq("uid", studentUser.getId()).eq("xnxq", xnxq));
            if (!courseScores.isEmpty()) {

                courseScores.forEach(c -> {
                    String courseName = c.getCourseName();
                    c.setCourseName(courseName.substring(courseName.indexOf("]") + 1));
                });
                return courseScores;
            } else {
                List<CourseScore> list = JwcRequest.getCourseScores(JwcRequest.login(studentUser.getStudentId(), studentUser.getPassword(),studentUser.getSchoolId().toString()), xnxq, xn);
                if (list.isEmpty()) {
                    throw new CrudException(ExceptionEnum.user_score_not_public);
                }
                list.forEach(c -> c.setUid(studentUser.getId()));
                courseScoreService.saveBatch(list);
                list.forEach(c -> {
                    String courseName = c.getCourseName();
                    c.setCourseName(courseName.substring(courseName.indexOf("]") + 1));
                });
                return list;
            }
        }
        return null;
    }

    /**
     * @param xqxn  学期 20171
     * @param xn    学年 2017
     * @return
     */
    @Override
    public List<CourseScore> getCourseScoreSchool( String studentId,  String password,Integer school,
                                      String xqxn,  String xn) {
        List<CourseScore> list = JwcRequest.getCourseScores(JwcRequest.login(studentId, password,school.toString()), xqxn, xn);
        if (list.isEmpty()) {
            throw new CrudException(ExceptionEnum.user_score_not_public);
        }
        StudentUser user = studentUserMapper.selectOne(new QueryWrapper<StudentUser>().eq("student_id", studentId));
        if (user == null) {
            user = StudentUser.builder().studentId(studentId).password(password).addtime(new BigDecimal(System.currentTimeMillis())).schoolId(school).build();
//            String userKey = MessageFormat.format(RedisConstant.SCHOOL_STUDENT_INFO,school);
//            redisRepository.hset(userKey,studentId,user);
        }
        int uid = user.getId();
        list.forEach(c -> c.setUid(uid));

        String format = MessageFormat.format(RedisConstant.SCHOOL_STUDENT_SCORE,school.toString(),studentId,xqxn);
        list.forEach(c -> {
            String courseName = c.getCourseName();
            c.setCourseName(courseName.substring(courseName.indexOf("]") + 1));
//            if(!redisRepository.hHasKey(format,c.getCourseName())){
//                redisRepository.hset(format,c.getCourseName(),c);
//            }
        });
        LambdaQueryWrapper<CourseScore> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(CourseScore::getUid, uid);
        List<CourseScore> courseScores = courseScoreService.getBaseMapper().selectList(wrapper);
        for (CourseScore courseScore : list) {
            for (CourseScore score : courseScores) {
                if (courseScore.getCourseName()
                        .equals(score.getCourseName())) {
                    Integer id = score.getId();
                    courseScore.setId(id);
                }
            }
        }
        courseScoreService.asynUpdate(list);

        list.forEach(c -> {
            String courseName = c.getCourseName();
            c.setCourseName(courseName.substring(courseName.indexOf("]") + 1));
        });
        return list;
    }

    @Override
    public List<CourseScoreDTO>  getCourseScore(String openId) {
        LambdaUpdateWrapper<StudentUser> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(StudentUser::getOpenId, openId);
        StudentUser studentUser = studentUserMapper.selectOne(wrapper);
        if (studentUser!= null) {
            final String key = "xacxxy:jwc:score:" + studentUser.getStudentId();
            if (redisRepository.hasKey(key)) {
                List<String> range = redisRepository.opsForList().range(key, 0, 999);
                if (!CollectionUtils.isEmpty(range)) {
                    final List<CourseScoreDTO> collect = range.stream().map(c -> JSON.parseObject(c, CourseScoreDTO.class)).collect(Collectors.toList());
                    return collect;
                }
            }
            List<CourseScore> courseScoreSchool = getCourseScoreSchool(studentUser.getStudentId(), studentUser.getPassword(),
                    studentUser.getSchoolId(), "", "");
            int year = LocalDate.now().getYear();
            List<String> years = new ArrayList<>();
            for (int i = 4; i > 0;i--) {
                for (int j = 0; j < 2; j++) {
                    years.add(year - i + "" + j);
                }
            }


            Map<String, List<CourseScore>> collect =
                    courseScoreSchool.stream().collect(Collectors.groupingBy(CourseScore::getXnxq));
            TreeMap<String, List<CourseScore>> treeMap = new TreeMap<>((o1, o2) -> o1.compareTo(o2));
            treeMap.putAll(collect);
            List<CourseScoreDTO> list = new ArrayList<>();
            treeMap.forEach((k,v) -> {
                CourseScoreDTO courseScoreDTO = new CourseScoreDTO();
                courseScoreDTO.setKey(k);
                courseScoreDTO.setList(v);
                list.add(courseScoreDTO);
            });
            writeCache(list, studentUser.getStudentId());
            return list;
        }
        return new ArrayList<>();
    }

    private void writeCache(List<CourseScoreDTO> list, String studentId) {
        final String key = "xacxxy:jwc:score:" + studentId;
        final List<String> jsonList = list.stream().map(JSON::toJSONString).collect(Collectors.toList());
        redisRepository.opsForList().leftPushAll(key, jsonList);
        redisRepository.expire(key, 5, TimeUnit.MINUTES);
    }


}
