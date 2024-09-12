package kopo.delivery.session;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class SessionInvalidatorListener implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("서버 시작");
		sce.getServletContext().setAttribute("invalidateSessions", true);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("서버 종료");
	}

}
