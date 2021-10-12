package org.choviwu.top.qg;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import org.choviwu.top.qg.entity.Config;
import org.choviwu.top.qg.mapper.ConfigMapper;
import org.choviwu.top.qg.redis.RedisRepository;
import org.choviwu.top.qg.score.CommonLog;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.text.DateFormat;
import java.util.List;
import java.util.Properties;


@EnableTransactionManagement
@EnableRedisRepositories
@EnableAspectJAutoProxy(proxyTargetClass = true,exposeProxy = true)
@SpringBootApplication(scanBasePackages = "org.choviwu.top.qg")
@EnableAsync(proxyTargetClass = true)
@EnableScheduling
@MapperScan(basePackages = "org.choviwu.top.qg.mapper")
public class Application implements ServletContextInitializer {

    @Autowired
    RedisRepository cached;


    public static void main(String[] args) throws InterruptedException {

        ApplicationContext context = SpringApplication.run(Application.class, args);
        ConfigMapper bean = context.getBean(ConfigMapper.class);
        List<Config> configs = bean.selectList(Wrappers.lambdaQuery());
        configs.forEach(c->{
            CommonLog.CACHE_MAP.put(c.getParam(), c.getResult());
        });


    }


    @Bean
    public HttpMessageConverter message() {
        HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper ob = new ObjectMapper();
        ob.setDateFormat(DateFormat.getInstance());
        ((MappingJackson2HttpMessageConverter) converter).setObjectMapper(ob);
        return converter;
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");

        return page;
    }
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor page = new PerformanceInterceptor();
        page.setFormat(true);
        return page;
    }
    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum","true");
        properties.setProperty("rowBoundsWithCount","true");
        properties.setProperty("reasonable","true");
        properties.setProperty("dialect","mysql");    //配置mysql数据库的方言
        pageHelper.setProperties(properties);
        return pageHelper;
    }
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
    }
}
