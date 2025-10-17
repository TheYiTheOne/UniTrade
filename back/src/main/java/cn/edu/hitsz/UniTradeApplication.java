package cn.edu.hitsz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@ServletComponentScan //开启了对servlet组件的支持
@SpringBootApplication
@MapperScan("cn.edu.hitsz.mapper")
public class UniTradeApplication {

    public static void main(String[] args) {
        SpringApplication.run(UniTradeApplication.class, args);
    }

}
