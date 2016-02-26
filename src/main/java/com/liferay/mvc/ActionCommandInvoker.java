package com.liferay.mvc;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import com.liferay.mvc.annotation.Action;
import com.liferay.mvc.annotation.ActionParam;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;

public class ActionCommandInvoker extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		if (action == null) {
			throw new Exception("Action not found!");
		}

		List<Object> args = new ArrayList<Object>();

		for (int p = 0; p < parameters.size(); p++) {
			ActionParam parameter = parameters.get(p);
			args.add(actionRequest.getParameter(parameter.value()));
		}

		args.add(actionRequest);
		args.add(actionResponse);

		action.invoke(this, args.toArray());
	}

	Method action;
	List<ActionParam> parameters = new ArrayList<ActionParam>();

	{
		Method[] methods = this.getClass().getMethods();
		for (int i = 0; i < methods.length; i++) {
			Method m = methods[i];
			if (m.isAnnotationPresent(Action.class)) {
				action = m;
				Parameter[] parameterList = m.getParameters();
				for (int j = 0; j < parameterList.length; j++) {
					Parameter p = parameterList[j];
					if (p.isAnnotationPresent(ActionParam.class)) {
						ActionParam actionParam = p.getAnnotation(ActionParam.class);
						parameters.add(actionParam);
					}
				}
			}
		}
	}

}
