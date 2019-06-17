package com.isoft.igis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@EnableAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
@EnableTransactionManagement
@ServletComponentScan
@MapperScan("com.isoft.igis.*.dao")
@SpringBootApplication
@EnableCaching
@EnableScheduling
public class BootdoApplication  extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder)
	{
		return builder.sources(BootdoApplication.class);
	}

    public static void main(String[] args) {
        SpringApplication.run(BootdoApplication.class, args);
        System.out.println("ヾ(◍°∇°◍)ﾉﾞ    IGIS Park启动成功      ヾ(◍°∇°◍)ﾉﾞ\n" +
                " .----------------.  .----------------.  .----------------.  .----------------.   .----------------.  .----------------.  .----------------.  .----------------. \r\n" +
                "| .--------------. || .--------------. || .--------------. || .--------------. | | .--------------. || .--------------. || .--------------. || .--------------. |\r\n" +
                "| |     _____    | || |    ______    | || |     _____    | || |    _______   | | | |   ______     | || |      __      | || |  _______     | || |  ___  ____   | |\r\n" +
                "| |    |_   _|   | || |  .' ___  |   | || |    |_   _|   | || |   /  ___  |  | | | |  |_   __ \\   | || |     /  \\     | || | |_   __ \\    | || | |_  ||_  _|  | |\r\n" +
                "| |      | |     | || | / .'   \\_|   | || |      | |     | || |  |  (__ \\_|  | | | |    | |__) |  | || |    / /\\ \\    | || |   | |__) |   | || |   | |_/ /    | |\r\n" +
                "| |      | |     | || | | |    ____  | || |      | |     | || |   '.___`-.   | | | |    |  ___/   | || |   / ____ \\   | || |   |  __ /    | || |   |  __'.    | |\r\n" +
                "| |     _| |_    | || | \\ `.___]  _| | || |     _| |_    | || |  |`\\____) |  | | | |   _| |_      | || | _/ /    \\ \\_ | || |  _| |  \\ \\_  | || |  _| |  \\ \\_  | |\r\n" +
                "| |    |_____|   | || |  `._____.'   | || |    |_____|   | || |  |_______.'  | | | |  |_____|     | || ||____|  |____|| || | |____| |___| | || | |____||____| | |\r\n" +
                "| |              | || |              | || |              | || |              | | | |              | || |              | || |              | || |              | |\r\n" +
                "| '--------------' || '--------------' || '--------------' || '--------------' | | '--------------' || '--------------' || '--------------' || '--------------' |\r\n" +
                " '----------------'  '----------------'  '----------------'  '----------------'   '----------------'  '----------------'  '----------------'  '----------------'");
    }
}
