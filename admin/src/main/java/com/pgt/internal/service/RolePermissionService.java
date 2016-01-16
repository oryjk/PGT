package com.pgt.internal.service;

import com.pgt.internal.bean.Role;

/**
 * Created by Yove on 2016/1/16.
 */
public class RolePermissionService {

	public boolean validReadonlyRole (Role pRole) {
		return validReadonlyRoleValue(pRole.getValue());
	}

	public boolean validReadonlyRoleValue (int pValue) {
		return pValue > Role.BROWSER.getValue();
	}

	public boolean validAdministratorRole (Role pRole) {
		return pRole.equals(Role.ADMINISTRATOR);
	}

	public boolean validMerchandiserRole (Role pRole) {
		return pRole.equals(Role.MERCHANDISER) || pRole.equals(Role.PROD_ORDER_MANAGER) || validAdministratorRole(pRole);
	}

	public boolean validInvestorRole (Role pRole) {
		return pRole.equals(Role.INVESTOR) || pRole.equals(Role.IVST_ORDER_MANAGER) || validAdministratorRole(pRole);
	}
}
