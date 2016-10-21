package oracle.sysman.emaas.platform.savedsearch.entity.Redirector;

import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsFolder;

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

public class EmAnalyticsFolderRedirector implements QueryRedirector{

	@Override
	public Object invokeQuery(DatabaseQuery query, Record arguments,
			Session session) {
		
		ClassDescriptor cd = session.getDescriptor(query.getReferenceClass());
		Object permanant = session.getActiveSession().getProperty("soft.deletion.permanent");
		if (query.isDeleteObjectQuery()) {
			if (!Boolean.TRUE.equals(permanant)) {
				DeleteObjectQuery doq = (DeleteObjectQuery) query;
				EmAnalyticsFolder emObject = (EmAnalyticsFolder) doq.getObject();
				emObject.setDeleted(emObject.getFolderId());
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
			EmAnalyticsFolder emObject = (EmAnalyticsFolder) ioq.getObject();

			UnitOfWork uow = session.acquireUnitOfWork();
			String sql = "DELETE FROM EMS_ANALYTICS_FOLDERS p WHERE p.NAME='" + emObject.getName() 
					+ "' AND p.PARENT_ID='" + emObject.getParentId()
					+ "' AND p.OWNER='" + emObject.getOwner() + "' AND p.TENANT_ID='"
					+ session.getActiveSession().getProperty("tenant.id") + "' AND p.DELETED='" + emObject.getFolderId() + "'";
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
