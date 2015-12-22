package com.pgt.category;

import com.pgt.category.bean.Category;
import com.pgt.category.bean.CategoryType;
import com.pgt.category.service.CategoryServiceImp;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;

/**
 * Created by carlwang on 11/13/15.
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core-config.xml") // 加载配置
public class CategoryTest {

    @Autowired
    private CategoryServiceImp categoryServiceImp;

    public CategoryServiceImp getCategoryServiceImp() {
        return categoryServiceImp;
    }

    public void setCategoryServiceImp(CategoryServiceImp categoryServiceImp) {
        this.categoryServiceImp = categoryServiceImp;
    }

    @Test
    public void createCategoryTest() {
        Random random = new Random(1000);
        for (int i = 0; i < 100; i++) {
            Category category = new Category();
            category.setType(CategoryType.CATEGORY_HOT);
            category.setName("戒子" + random.nextInt());
            category.setCode(random.nextInt() + "");
            category.setStatus(random.nextInt());
            categoryServiceImp.createCategory(category);
        }

    }

    @Test
    public void updateCategoryTest() {
        Integer id = 23;
        Category category = categoryServiceImp.queryCategory(id);
        category.setType(CategoryType.HIERARCHY);
        categoryServiceImp.updateCategory(category);
    }

    @Test
    public void queryCategoryTest() {
        Integer id = 23;
        Category category = categoryServiceImp.queryCategory(id);
        Assert.assertNotNull(category);
        Assert.assertNotNull(category.getChildren());
        Assert.assertEquals(17, category.getChildren().size());
        Assert.assertNotNull(category.getName());
        Assert.assertNotNull(category.getCode());
        Assert.assertNotNull(category.getStatus());
        Assert.assertNotNull(category.getType());
    }

}
