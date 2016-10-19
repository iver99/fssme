package oracle.sysman.emaas.platform.savedsearch.entity.Redirector;

import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsCategory;

import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.internal.sessions.AbstractRecord;
import org.eclipse.persistence.internal.sessions.AbstractSession;
import org.eclipse.persistence.queries.DatabaseQuery;
import org.eclipse.persistence.queries.DeleteObjectQuery;
import org.eclipse.persistence.queries.InsertObjectQuery;
import org.eclipse.persistence.queries.QueryRedirector;
import org.eclipse.persistence.queries.SQLCall;
import org.eclipse.persistence.queries.UpdateObjectQuery;
import org.eclipse.persistence.sessions.Record;
import org.eclipse.persistence.sessions.Session;
import org.eclipse.persistence.sessions.UnitOfWork;

public class EmAnalyticsCategoryRedirector implements QueryRedirector {

	@Override
	public Object invokeQuery(DatabaseQuery query, Record arguments,
			Session session) {
		
		ClassDescriptor cd = session.getDescriptor(query.getReferenceClass());
		boolean permanant = (boolean) session.getActiveSession().getProperty("soft.deletion.permanent");
		if (query.isDeleteObjectQuery()) {
			if (!permanant) {
				DeleteObjectQuery doq = (DeleteObjectQuery) query;
				EmAnalyticsCategory emObject = (EmAnalyticsCategory) doq.getObject();
				emObject.setDeleted(emObject.getCategoryId());
				UpdateObjectQuery uoq = new UpdateObjectQuery(emObject);
				cd.addDirectQueryKey("deleted", "DELETED");
				uoq.setDescriptor(cd);
				doq.setDescriptor(uoq.getDescriptor());
				return uoq.execute((AbstractSession) session, (AbstractRecord) arguments);
			} else {
				return query.execute((AbstractSession) session, (AbstractRecord) arguments);
			}
			
		}
		else if (query.isInsertObjectQuery()) {
			InsertObjectQuery ioq = (InsertObjectQuery) query;
			EmAnalyticsCategory emObject = (EmAnalyticsCategory) ioq.getObject();

			UnitOfWork uow = session.acquireUnitOfWork();
			String sql = "DELETE FROM EMS_ANALYTICS_CATEGORY p WHERE p.NAME='" + emObject.getName()
					+ "' AND p.OWNER='" + emObject.getOwner() + "' AND p.TENANT_ID='"
					+ session.getActiveSession().getProperty("tenant.id") + "' AND p.DELETED='" + emObject.getCategoryId() + "'";
			uow.executeNonSelectingCall(new SQLCall(sql));
			uow.commit();

			ioq = new InsertObjectQuery(emObject);
			ioq.setDoNotRedirect(true);
			InsertObjectQuery old = cd.getQueryManager().getInsertQuery();
			cd.getQueryManager().setInsertQuery(ioq);
			Object rtn = ioq.execute((AbstractSession) session, (AbstractRecord) arguments);
			cd.getQueryManager().setInsertQuery(old);
			return rtn;
		}
		else {
			return query.execute((AbstractSession) session, (AbstractRecord) arguments);
		}
	}

}
