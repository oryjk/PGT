package com.pgt.internal.service;

import com.pgt.internal.bean.Role;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

/**
 * Created by Yove on 2016/1/16.
 */
@Service(value = "rolePermissionService")
public class RolePermissionService {

	public boolean checkRole (Role pRole, Role... pValidRoles) {
		if (ArrayUtils.isNotEmpty(pValidRoles)) {
			for (Role validRole : pValidRoles) {
				if (pRole.equals(validRole)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean validReadonlyRole (Role pRole) {
		return pRole.getValue() >= Role.BROWSER.getValue();
	}

	public boolean validAdministratorRole (Role pRole) {
		return pRole.equals(Role.ADMINISTRATOR);
	}

	public boolean validMerchandiserRole (Role pRole) {
		return pRole.equals(Role.MERCHANDISER) || validProductOrderRole(pRole);
	}

	public boolean validInvestorRole (Role pRole) {
		return pRole.equals(Role.INVESTOR) || validInvestOrderRole(pRole);
	}

	public boolean validProductOrderRole (Role pRole) {
		return pRole.equals(Role.PROD_ORDER_MANAGER) || validAdministratorRole(pRole);
	}

	public boolean validInvestOrderRole (Role pRole) {
		return pRole.equals(Role.IVST_ORDER_MANAGER) || validAdministratorRole(pRole);
	}
}
