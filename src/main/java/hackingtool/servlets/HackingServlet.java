package hackingtool.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import hackingtool.devices.Alerts;
import hackingtool.devices.Device;
import hackingtool.devices.OS;
import hackingtool.devices.User;
import hackingtool.hacking.Hacking;

/**
 * Servlet implementation class HackingServlet
 */
@WebServlet("/Hacking")
public class HackingServlet extends HttpServlet {
	private static final String SUBVERSION = "subversion";
	private static final String IMPROVE_STATUS = "improveStatus";
	private static final String HACKING_JSP = "WEB-INF/views/Hacking.jsp";
	private static final String BRUTE_FORCE = "bruteForce";
	private static final String ACTION 	  = "action";
	private static final String INTRUSION = "intrusion";

	private static final long serialVersionUID = 1L;
	
	// Create example system for testing
	private static final Device target = new Device.Builder()
												   .setSystemName("Test System")
												   .setOS(new OS(10))
												   .setFirewall(50)
												   .setInfosec(50)
												   .setAlert(Alerts.NONE)
												   .build();
	// Create example user for testing
	private static final User hacker = new User("TestUser", 75, 75, 10);
	private Hacking hack;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HackingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
// 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//String action;
		if (hack != null) {
			request.setAttribute("test",   hack.getTest());
			request.setAttribute("target", hack.getTarget());
			request.setAttribute("hacker", hack.getHacker());
		} else {
			request.setAttribute("target", target);
			request.setAttribute("hacker", hacker);
		}

		request.getRequestDispatcher(HACKING_JSP)
				.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String  action;
		String  bruteForceParam;
		Boolean bruteForce = false;
		if (request.getParameter(ACTION) != null) {
			action = request.getParameter(ACTION);
		} else {
			action = "none";
		}
		if (request.getParameter(BRUTE_FORCE) != null) {
			bruteForceParam = request.getParameter(BRUTE_FORCE);
			bruteForce = Boolean.valueOf(bruteForceParam);
		}
		// Check action
		if (action != null && !action.isEmpty()) {
			hack = new Hacking(target, hacker, bruteForce);
			if (INTRUSION.equalsIgnoreCase(action)) {
				hack.intrusion();
			} else if (IMPROVE_STATUS.equalsIgnoreCase(action)) {
				hack.upgradeStatus();
			} else if (SUBVERSION.equalsIgnoreCase(action)) {
				hack.subvertSystem();
			}
		}
		doGet(request,response);
	}

}
