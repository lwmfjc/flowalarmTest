package com.ly.config;

import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

/**
 * 说明：Flowable配置
 */
@Controller
@Configuration
public class FlowableConfig implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {

    @Override
    public void configure(SpringProcessEngineConfiguration engineConfiguration) {
        engineConfiguration.setActivityFontName("微软雅黑");
        engineConfiguration.setLabelFontName("微软雅黑");
        engineConfiguration.setAnnotationFontName("微软雅黑");
    }

}
