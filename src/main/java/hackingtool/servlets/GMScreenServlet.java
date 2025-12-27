package hackingtool.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import hackingtool.dao.HackingDAO;
import hackingtool.devices.Hackable;
import hackingtool.devices.NetworkFactory;
import hackingtool.devices.User;
import hackingtool.services.HackingService;


/**
 * Servlet implementation class GMScreenServlet
 */
@WebServlet("/GMScreen")
public class GMScreenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String GM_JSP = "WEB-INF/views/GMScreen.jsp";
    private static final HackingService hackServ = new HackingDAO();   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GMScreenServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Hackable> nodes = hackServ.getAllNodes();
		List<User> users = hackServ.getAllUsers();
		
		request.setAttribute("users", users);
		request.setAttribute("nodes", nodes);
		request.getRequestDispatcher(GM_JSP)
		.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String idParam = request.getParameter("nodeID");
		
		if (action != null) {
			// Check action
			if ("createNode".equalsIgnoreCase(action)) {
				
			} else if ("deleteNode".equalsIgnoreCase(action) && idParam != null) {
				int nodeID = Integer.parseInt(idParam);
				hackServ.deleteNode(nodeID);
			} else if ("deleteAllNodes".equalsIgnoreCase(action)) {
				List<Hackable> nodes = hackServ.getAllNodes();
				for (Hackable node : nodes) {
					hackServ.deleteNode(node.getID());
				}
			}
		}

		List<Hackable> nodes = hackServ.getAllNodes();
		List<User> users = hackServ.getAllUsers();
		
		request.setAttribute("users", users);
		request.setAttribute("nodes", nodes);
		request.getRequestDispatcher(GM_JSP)
		.forward(request, response);
	}

}
