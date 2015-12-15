package com.pgt.internal.dao;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.internal.bean.InternalUser;
import com.pgt.internal.bean.pagination.InternalPagination;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Yove on 10/20/2015.
 */
@Repository(value = "internalUserDao")
public interface InternalUserDao extends SqlMapper {

	InternalUser queryUserByLogin(@Param("login") final String pLogin);

	InternalUser queryUserById(@Param("id") int pId);

	int updateLastLogin(@Param("id") final int pId, @Param("ip") final String pIp);

	int createInternalUser(final InternalUser pInternalUser);

	int updateInternalUser(InternalUser pInternalUser);

	long queryInternalUserCount(InternalPagination pPagination);

	List<InternalUser> queryInternalUserPage(InternalPagination pPagination);

	long queryInternalUserNameCountExcludeId(@Param("name") String pName, @Param("id") Integer pId);

	long queryInternalUserLoginCountExcludeId(@Param("login") String pLogin, @Param("id") Integer pId);

	long queryInternalUserEmailCountExcludeId(@Param("email") String pEmail, @Param("id") Integer pId);

	long queryInternalUserPhoneCountExcludeId(@Param("phone") String pPhone, @Param("id") Integer pId);

	boolean updateBatchInternalUserAvailable(@Param("ids") int[] pIds, @Param("available") boolean pAvailable);
}
