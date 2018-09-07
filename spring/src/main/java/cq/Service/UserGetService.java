package cq.Service;

import cq.Dao.UserGetDao;
import cq.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGetService {
    @Autowired
    private UserGetDao userGetDao;
    public List<User> getAllUser(){
        return userGetDao.getAllUser();
    }
}
