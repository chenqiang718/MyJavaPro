package cq.springmvc.Service;

import cq.springmvc.Dao.UserGetDao;
import cq.springmvc.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
