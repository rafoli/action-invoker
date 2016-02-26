```java

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.mvc.ActionCommandInvoker;
import com.liferay.mvc.annotation.Action;
import com.liferay.mvc.annotation.ActionParam;

@Component(
		property = {
			"javax.portlet.name=" + LoginKeys.LOGIN_PORTLET,
			"mvc.command.name=validateUsername"
			
		},
		service = MVCActionCommand.class
	)
public class ValidateUsernameMVCActionCommand extends ActionCommandInvoker {

	@Action
	public void validateUsername(@ActionParam("username") String username,
	                   ActionRequest actionRequest, ActionResponse actionRespose)
		throws Exception {

		String redirect = executeUserValidationFlow(username, actionRequest, actionRespose);
		
		actionRespose.sendRedirect(redirect);
	}
	...
}	
```
