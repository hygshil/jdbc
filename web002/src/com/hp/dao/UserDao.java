package com.hp.dao;

import com.hp.bean.User;
import com.hp.util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//dao层应该是个接口，为什么因为可以使用aop，目前不用aop，可以直接写成类
public class UserDao {
    //查询
    public List<User> selectAll(){
        //dao层如何和数据库做对接，我们用的知识点叫做jdbc，很基础的一个必须的技术
        //步骤1：创建出连接对象
        ArrayList<User> users = new ArrayList<>();
        Connection connection = DBHelper.getConnection();
        //步骤2：创建出sql语句
        String sql = "select * from t_user";
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            //步骤3：使用连接对象，获取预编译对象
            ps = connection.prepareStatement(sql);
            //步骤4：执行预编译对象，得出结果集
            rs = ps.executeQuery();
            //步骤5：遍历结果集，一个一个获取对象
            while(rs.next()){
//                System.out.println(rs.getString("username"));  //拿到了每个数据的行
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setCreate_time(rs.getString("create_time"));
                user.setImg(rs.getString("img"));
                user.setIs_del(rs.getInt("is_del"));
                user.setModify_time(rs.getString("modify_time"));
                user.setPassword(rs.getString("password"));
                user.setReal_name(rs.getString("real_name"));
                user.setType(rs.getInt("type"));
                user.setUsername(rs.getString("username"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }
    //新增
    public int addUser(User user){
        //第1：创建链接对象
        Connection connection = DBHelper.getConnection();
        //第2：sql语句，因为添加的是数据变量 所以用问号代替
        String sql="insert into t_user values(null,?,?,?,?,?,?,?,?)";
        //第3：预编译sql
        PreparedStatement ps=null;
        int i=0;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1,user.getUsername());
            ps.setString(2,user.getPassword());
            ps.setString(3,user.getReal_name());
            ps.setString(4,user.getImg());
            ps.setInt(5,user.getType());
            ps.setInt(6,user.getIs_del());
            ps.setString(7,user.getCreate_time());
            ps.setString(8,user.getModify_time());
            i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return i;
    }

    //删除
    public int deleteId(int id){
        Connection connection = DBHelper.getConnection();
        String sql = "delete from t_user where id=?";
        int i=0;
        PreparedStatement ps=null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
            i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return i;
    }
    //修改
    public int update(User user){
        Connection connection = DBHelper.getConnection();
        String sql = "update t_user set username=?,password=?,real_name=?,img=?,type=?,is_del=?,create_time=?,modify_time=? where id=?";
        PreparedStatement ps=null;
        int i=0;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1,user.getUsername());
            ps.setString(2,user.getPassword());
            ps.setString(3,user.getReal_name());
            ps.setString(4,user.getImg());
            ps.setInt(5,user.getType());
            ps.setInt(6,user.getIs_del());
            ps.setString(7,user.getCreate_time());
            ps.setString(8,user.getModify_time());
            ps.setInt(9,user.getId());
            i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return i;
    }
    public static void main(String[] args) {
        UserDao dao = new UserDao();
        //全查
//        List<User> users = dao.selectAll();
//        for (User user : users) {
//            System.out.println(user);
//        }
        //添加
//        User user = new User();
//        user.setUsername("小乔");
//        user.setType(1);
//        user.setReal_name("小乔");
//        user.setPassword("123456");
//        user.setModify_time("2013-09-07");
//        user.setIs_del(1);
//        user.setImg("xxx");
//        user.setCreate_time("2013-09-07");
//        int i = dao.addUser(user);
//        System.out.println(i);
        //删除
//        int i = dao.deleteId(63);
//        System.out.println(i);
        //修改
        User user = new User();
        user.setId(62);
        user.setUsername("小乔");
        user.setPassword("123456");
        user.setReal_name("小乔");
        user.setImg("xxx");
        user.setType(1);
        user.setIs_del(1);
        user.setCreate_time("2013-09-07");
        user.setModify_time("2013-09-07");
        int i = dao.update(user);
        System.out.println(i);
        System.out.println(user);
    }
}
