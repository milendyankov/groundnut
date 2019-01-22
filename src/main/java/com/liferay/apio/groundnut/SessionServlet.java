package com.liferay.apio.groundnut;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.vaadin.flow.server.ServiceException;
import com.vaadin.flow.server.SessionDestroyEvent;
import com.vaadin.flow.server.SessionDestroyListener;
import com.vaadin.flow.server.SessionInitEvent;
import com.vaadin.flow.server.SessionInitListener;
import com.vaadin.flow.server.VaadinServlet;

@WebServlet("/*")
public class SessionServlet extends VaadinServlet
    implements SessionInitListener, SessionDestroyListener {

	private static final long serialVersionUID = 1L;

	@Override
    protected void servletInitialized() throws ServletException {
        super.servletInitialized();
        getService().addSessionInitListener(this);
        getService().addSessionDestroyListener(this);
    }

    @Override
    public void sessionInit(SessionInitEvent event)
            throws ServiceException {
        System.out.println("NEW SESSION !!!");
    }

    @Override
    public void sessionDestroy(SessionDestroyEvent event) {
        System.out.println("END OF SESSION !!!");
    }
}
