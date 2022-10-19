package dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplicationExtensionsKt;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)

class UserDaoTest {
    @Autowired
    ApplicationContext context;
    //alt+insert로 Test Method
    @Test
    void addANDSelect() throws SQLException, ClassNotFoundException {
        //public UserDao awsUserDao(){} 함수 호출을 한다.
        UserDao userDao = context.getBean("awsUserDao",UserDao.class);
        String id = "23";
        userDao.add(new User(id,"Nunu","112233qqww"));
        User user = userDao.Select(id);
        Assertions.assertEquals("Nunu",user.getName());
    }

}
