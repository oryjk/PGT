package com.pgt.cart.dao;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.cart.bean.Favourite;
import com.pgt.cart.bean.pagination.InternalPagination;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Yove on 11/12/2015.
 */
@Repository(value = "userFavouriteDao")
public interface UserFavouriteDao extends SqlMapper {

	List<Favourite> queryFavouritePage(@Param("userId") int pUserId, @Param("pagination") InternalPagination pPagination);

	Favourite queryFavouriteByProduct(@Param("userId") int pUserId, @Param("productId") int pProductId);

	int createFavouriteItem(Favourite pFavourite);

	int deleteFavouriteItem(@Param("favouriteId") int pFavouriteId);

	Favourite queryFavourite(@Param("favouriteId") int pFavouriteId);

	List<Favourite> queryFavourites(@Param("userId") int pUserId);

	long queryFavouriteCount(@Param("userId") int pUserId, @Param("pagination") InternalPagination pPagination);
}
