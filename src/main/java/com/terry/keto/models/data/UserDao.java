package com.terry.keto.models.data;

import com.terry.keto.models.Comment;
import com.terry.keto.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UserDao extends CrudRepository<User, Integer> {
    public List<User> findByUsername(String username);
    public User findById(int id);
}