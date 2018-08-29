package cq.Dao;

import cq.Model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGetDao {
    public List<User> getAllUser();
}
