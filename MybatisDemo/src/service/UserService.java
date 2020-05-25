package service;

import dao.IUserDAO;
import org.apache.ibatis.session.SqlSession;
import util.MybatisUtils;
import util.check;
import vo.User;

public class UserService {
    private IUserDAO userDAO=null;
    private SqlSession sqlSession=null;
    public static User user1;

    public UserService(){
    }

    public int checkLogin(User user){
        this.sqlSession= MybatisUtils.getSqlSession();
        this.userDAO=sqlSession.getMapper(IUserDAO.class);
        int s=1;
        try {
            user1=userDAO.getById(user.getUserName());
            if(user1==null){
                s=0;
            }else {
                if(!user1.getPassword().equals(user.getPassword())){
                    s=2;
                }
                else s=1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }
        return s;
    }

    public boolean changePassword(String newPassword){
        this.sqlSession= MybatisUtils.getSqlSession();
        this.userDAO=sqlSession.getMapper(IUserDAO.class);
        boolean flag=false;
        User user=new User();
        user.setUserName(user1.getUserName());
        user.setPassword(check.md5(newPassword));
        user.setChrName(user1.getChrName());
        //user.setRole(user1.getRole());
        try {
            userDAO.update(user);
            sqlSession.commit();
            flag=true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }
        return flag;
    }
}
