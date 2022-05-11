package com.ly;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@MapperScan("com.ly.mapper")
@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(final RepositoryService repositoryService,
                                  final RuntimeService runtimeService,
                                  final TaskService taskService) {

        //该bean在项目服务启动的时候就去加载一些数据
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
                //有几个流程定义
                System.out.println("Number of process definitions : "
                        + repositoryService.createProcessDefinitionQuery().count());
                /*
                //有多少个任务
                System.out.println("Number of tasks : " + taskService.createTaskQuery().count());
                runtimeService.startProcessInstanceByKey("oneTaskProcess");
                //开启流程后有多少个任务（+1）
                System.out.println("Number of tasks after process start: "
                        + taskService.createTaskQuery().count());*/
                //myService.createDemoUsers();
            }
        };
    }
}
