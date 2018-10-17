package com.cq.springboot.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @Author: 陈强
 * @Date: 2018/10/9 15:08
 * @Version 1.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfigure {

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cq.springboot.Controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfo("Spring Boot中使用Swagger2构建RESTful APIs", "更多Spring Boot相关文章请关注：http://blog.didispace.com", "1.0", "http://blog.didispace.com/", "程序猿CQ", "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0");
    }
}
