package hackingtool.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import hackingtool.dao.HackingDAO;
import hackingtool.devices.Hackable;
import hackingtool.services.HackingService;

/**
 * Servlet implementation class GMControlNodeServlet
 */
@WebServlet("/GMControlNode")
public class GMControlNodeServlet extends HttpServlet {
	private static final String CONTROL_NODE_JSP = "WEB-INF/views/GMControlNode.jsp";
	private static final HackingService hackServ = new HackingDAO();
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GMControlNodeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idParam = request.getParameter("nodeID");
		Hackable node = null;
		int nodeID = 0;
	
		if (idParam != null) {
			nodeID = Integer.parseInt(idParam);
			node = hackServ.getNode(nodeID);
		}
		
		request.setAttribute("node", node);
		request.getRequestDispatcher(CONTROL_NODE_JSP)
		.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		request.getRequestDispatcher(CONTROL_NODE_JSP)
		.forward(request, response);
	}

}
