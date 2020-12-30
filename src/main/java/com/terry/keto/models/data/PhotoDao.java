package com.terry.keto.models.data;

//import com.terry.keto.models.Comment;
import com.terry.keto.models.Photo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PhotoDao extends CrudRepository<Photo, Integer> {
    public Photo findById(int id);

}