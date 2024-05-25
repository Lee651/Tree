package top.rectorlee;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"top.rectorlee.mapper"})
public class TreeApplication {
    public static void main(String[] args) {
        SpringApplication.run(TreeApplication.class, args);
    }
}
