package com.pgt.data;

import com.pgt.base.service.TransactionService;
import com.pgt.category.bean.Category;
import com.pgt.category.dao.CategoryMapper;
import com.pgt.common.bean.Media;
import com.pgt.common.dao.MediaMapper;
import com.pgt.media.bean.MediaType;
import com.pgt.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * Created by carlwang on 12/17/15.
 */

@Service
public class ImportDataService extends TransactionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImportDataService.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private MediaMapper mediaMapper;

    public boolean createCatalogData(Category catalog) {
        LOGGER.debug("Begin create Catelog.");
        categoryMapper.createCategory(catalog);
        Integer rootCategory = catalog.getId();

        //create front media.
        LOGGER.debug("Begin to create category front media.");
        Media media = catalog.getFrontMedia();
        if (!ObjectUtils.isEmpty(media)) {
            LOGGER.debug("Create category media,the category id is {}.", rootCategory);
            media.setReferenceId(rootCategory);
            media.setType(MediaType.category.toString());
            mediaMapper.createMedia(media);
        }
        LOGGER.debug("End to create category front media");

        //create category's products.
        LOGGER.debug("End to create category sub products.");
        if (!ObjectUtils.isEmpty(catalog.getProducts())) {
            catalog.getProducts().stream().forEach(product ->
                            productService.createProduct(catalog.getId(), product)
            );
        }
        LOGGER.debug("End to create category sub products.");

        //create sub category data.
        if (!ObjectUtils.isEmpty(catalog.getChildren())) {
            catalog.getChildren().stream().forEach(category -> {
                category.setParent(catalog);
                createCatalogData(category);

            });
        }
        LOGGER.debug("End create Catalog.");
        return true;

    }


    public ProductService getProductService() {
        return productService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }


}
