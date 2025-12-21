package hackingtool.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hackingtool.dao.HackingDAO;
import hackingtool.devices.DeviceFactory;
import hackingtool.devices.Hackable;
import hackingtool.services.HackingService;

/**
 * Servlet implementation class NodesServlet
 */
@WebServlet("/Nodes")
public class NodesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String NODES_JSP = "WEB-INF/views/DetectedNodes.jsp";
	private static final HackingService hackServ = new HackingDAO();
	
	private List<Hackable> nodes;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NodesServlet() {
        super();
//        nodes = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//        	hackServ.createNode(DeviceFactory.get("mote"));
//        }
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		request.setAttribute("nodes", nodes);
		// Forward to Nodes.jsp view
		request.getRequestDispatcher(NODES_JSP)
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
