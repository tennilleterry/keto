package com.terry.keto.models.data;

import com.terry.keto.models.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface IngredientDao extends CrudRepository<Ingredient, Integer> {
    public Ingredient findById(int id);


}

