package dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//@Configuration 에서 시작 합니다. → Spring
//@RestController or @Controller에서 시작 합니다. → Spring Boot
@Configuration
public class UserDaoFactory {
    //조립을 해줍니다.
    @Bean
    public UserDao awsUserDao(){ //날개 5개 선풍기
        AwsConnectionMaker awsConnectionMaker = new AwsConnectionMaker();
        //context 재사용 하는 부분이 많은 코드 - UserDao
        UserDao userDao = new UserDao(awsConnectionMaker);
        return userDao;
    }


}