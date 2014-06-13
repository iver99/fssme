package oracle.sysman.emaas.platform.savedsearch.wls.lifecycle;

import java.util.ArrayList;
import java.util.List;

import oracle.sysman.emaas.platform.savedsearch.services.ApplicationService;
import weblogic.application.ApplicationException;
import weblogic.application.ApplicationLifecycleEvent;
import weblogic.application.ApplicationLifecycleListener;
import weblogic.i18n.logging.NonCatalogLogger;

public class AbstractServicesManager extends ApplicationLifecycleListener
{
	public static final String APPLICATION_LOGGER_SUBSYSTEM = "oracle.sysman.emaas.platform.savedsearch";

	private final NonCatalogLogger logger = new NonCatalogLogger(APPLICATION_LOGGER_SUBSYSTEM + ".services");

	private List<ApplicationService> registeredServices = new ArrayList<ApplicationService>();

	public AbstractServicesManager(ApplicationService... services)
	{
		for (ApplicationService service : services) {
			registeredServices.add(service);
		}
	}

	@Override
	public void postStart(ApplicationLifecycleEvent evt) throws ApplicationException
	{
		logger.notice("Post-starting registered application services");
		try {
			logger.info("Post-starting 'Service Registry'");
			for (ApplicationService service : registeredServices) {
				service.postStart(evt);
			}
			logger.debug("Post-started 'Service Registry'");
		}
		catch (Throwable t) {
			if (t instanceof VirtualMachineError) {
				throw (VirtualMachineError) t;
			}
			logger.error("Service failed to post-start", t);
			throw new ApplicationException("Service failed to post-start", t);
		}
	}

	@Override
	public void postStop(ApplicationLifecycleEvent evt) throws ApplicationException
	{
		logger.notice("Post-stopping registered application services");
		try {
			logger.info("Post-stopping 'Service Registry'");
			for (ApplicationService service : registeredServices) {
				service.postStop(evt);
			}
			logger.debug("Post-stopped 'Service Regsitry'");
		}
		catch (Throwable t) {
			if (t instanceof VirtualMachineError) {
				throw (VirtualMachineError) t;
			}
			logger.error("Service failed to post-stop", t);
			throw new ApplicationException("some of the essential services failed to post-stop");
		}
	}

	@Override
	public void preStart(ApplicationLifecycleEvent evt) throws ApplicationException
	{
		logger.debug("Pre-starting registered application services");
		try {
			logger.info("Pre-starting 'Service Registry'");
			for (ApplicationService service : registeredServices) {
				service.preStart(evt);
			}
			logger.debug("Pre-started 'Service Registry'");
		}
		catch (Throwable t) {
			if (t instanceof VirtualMachineError) {
				throw (VirtualMachineError) t;
			}
			logger.error("Service failed to pre-start", t);
			throw new ApplicationException("Service failed to pre-start", t);
		}
	}

	@Override
	public void preStop(ApplicationLifecycleEvent evt) throws ApplicationException
	{
		logger.debug("Pre-stopping registered application services");
		try {
			logger.info("Pre-stopping 'Service Registry'");
			for (ApplicationService service : registeredServices) {
				service.preStop(evt);
			}
			logger.debug("Pre-stopped 'Service Registry'");
		}
		catch (Throwable t) {
			if (t instanceof VirtualMachineError) {
				throw (VirtualMachineError) t;
			}
			logger.error("Service failed to pre-stop", t);
			throw new ApplicationException("some of the essential services failed to pre-stop");
		}
	}
}
