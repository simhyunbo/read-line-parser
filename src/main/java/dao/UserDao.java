package dao;

import java.sql.*;
import java.util.Map;

public class UserDao {

    private ConnectionMaker connectionMaker;

    //빈 UserDao() Constructor에서 초기화
    public UserDao() {

        this.connectionMaker = new AwsConnectionMaker();
    }

    public UserDao(ConnectionMaker connectionMaker){
        this.connectionMaker = connectionMaker;
    }

    public int getCount(){
        return 0;
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
        Connection conn =null;
        PreparedStatement pstmt;

        conn = connectionMaker.getConnection();
        pstmt = conn.prepareStatement("DELETE FROM user");
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();

    }

    //connection 분리
    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Map<String, String> env = System.getenv();
        //DB접속(es sql workbench실행)
        //JDBC 를 사용하여 MYSQL DB에 접근하기 위해서는 제일 먼저 드라이버 클래스를 로드해야 하는데,
        // 그 때 Class.forName을 사용합니다
        //Class.forName -> mysql-connector-java 라이브러리의 Driver 클래스를 불러오는 경로

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(
                env.get("DB_HOST"), env.get("DB_USER"), env.get("DB_PASSWORD"));
        return conn;
    }

    /*
    user을 매개변수로 받는다. user을 받아서 insert하게 refactoring
    user을 받아서 insert하게 refactoring
    1.user를 받도록 add메소드 수정
    2.받은 user를 setString에서 활용하게 수정
    3.main에서 new User()를 해서 user를 넘기게 수정
    */

    public void add(User user) throws SQLException, ClassNotFoundException {

        Connection conn = connectionMaker.getConnection();

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
        Connection conn = connectionMaker.getConnection();

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

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao();
        userDao.add(new User("4","park","0987"));
        User user = userDao.Select("11");
        System.out.println(user.getName());
    }
}