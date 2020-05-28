package org.example;

import org.example.domain.Student;
import org.example.mapper.StudentMapper;
import org.example.utils.DataUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Optional;

/**
 * Hello world!
 *
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableJdbcRepositories
@EnableTransactionManagement
public class Application {

    public static void main( String[] args ) {
        SpringApplication.run(Application.class,args);
    }

    @Bean
    public CommandLineRunner test1(ApplicationContext cxt, StudentMapper studentMapper){
        return args -> {

            Optional<Student> byId = studentMapper.findById("8e9bd0e3320943239669a619409cac15");
            System.out.println(byId.get());

        };
    }


    @Bean
    public ApplicationListener<BeforeSaveEvent<Object>> beforeSave(){
        return event -> {
            ((Student)event.getEntity()).setId(DataUtil.getUUID());
            System.out.println(event);
        };
    }

}