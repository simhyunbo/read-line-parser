package dao;

import java.sql.*;
import java.util.Map;

public abstract class UserDaoAbstract {

    public abstract Connection makeConnection() throws SQLException, ClassNotFoundException;

    public void add(User user) throws SQLException, ClassNotFoundException {
        Connection conn = makeConnection();
        PreparedStatement ps = conn.prepareStatement("INSERT INTO user(id,name,password)" +
                "VALUES(?,?,?);");

        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();
        ps.close();
        conn.close();
    }
    public User Select(String id) throws ClassNotFoundException, SQLException {
        Connection conn = makeConnection();

        PreparedStatement ps = conn.prepareStatement(
                "SELECT id,name,password FROM user WHERE id = ?");
        ps.setString(1,id);
        ResultSet rs = ps.executeQuery();
        //rs.next()는 다음 데이터로 한칸 이동 -> why? 처음에는 데이터를 읽을 수 없는 가장 앞쪽에 있어서 rs.next()를 호출해야 읽을 수 있다.
        rs.next();
        User user = new User(rs.getString("id")
                ,rs.getString("name"),rs.getString("password"));

        //리소스 반납
        rs.close();
        ps.close();
        conn.close();
        return user;

    }


}