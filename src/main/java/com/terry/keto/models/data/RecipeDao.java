package com.terry.keto.models.data;

import com.terry.keto.models.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface RecipeDao extends CrudRepository<Recipe, Integer> {

}
