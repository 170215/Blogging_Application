package com.practice.DbinheritanceDemo.repositories;

import com.practice.DbinheritanceDemo.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}