package hackingtool.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import hackingtool.devices.Device;
import hackingtool.devices.Hackable;
import hackingtool.devices.OS;
import hackingtool.devices.User;
import hackingtool.hacking.Alerts;
import hackingtool.hacking.Hacking;

/**
 * Servlet implementation class HackingServlet
 */
@WebServlet("/Hacking")
public class HackingServlet extends HttpServlet {
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
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HackingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Hacking hack = new Hacking(target,hacker,false);
		request.setAttribute("hack", hack);
		request.getRequestDispatcher("WEB-INF/views/Hacking.jsp")
				.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
