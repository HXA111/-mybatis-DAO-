package dao;

import vo.User;

import java.util.List;

public interface IUserDAO {
    public User getById(String id) throws Exception;
    public List<User> query()throws Exception;
    public int insert(User user)throws Exception;
    public int update(User user)throws Exception;
    public int delete(String id)throws Exception;
}
