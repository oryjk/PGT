import com.pgt.cart.util.RepositoryUtils;
import com.pgt.internal.controller.InternalTransactionBaseController;

/**
 * Created by Yove on 16/2/15.
 */
public class AdminOgnl extends Ognl {

	public static boolean isAdminQueryId(int pId) {
		return InternalTransactionBaseController.ADMIN_QUERY_IU_ID == pId;
	}

	public static boolean isAdminQueryId(Integer pId) {
		if (pId == null) {
			return false;
		}
		return isAdminQueryId(pId.intValue());
	}

	public static boolean isAdminQueryId(String pId) {
		int intId = RepositoryUtils.safeParseId(pId);
		return isAdminQueryId(intId);
	}
}

