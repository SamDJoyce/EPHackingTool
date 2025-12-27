package hackingtool.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import hackingtool.devices.NetworkFactory;

/**
 * Servlet implementation class CreateNetwork
 */
@WebServlet("/CreateNetwork")
public class CreateNetworkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CreateNet_JSP = "WEB-INF/views/CreateNetwork.jsp";   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateNetworkServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher(CreateNet_JSP)
		.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String owner 	 = "Person";
		String netType 	 = "PAN";
		String hubDevice = "ecto";
		String numDevicesParam;
		int numDevices = 0;
		
		if (request.getParameter("owner") != null) {
			owner = request.getParameter("owner");
		}
		if (request.getParameter("netType") != null) {
			netType = request.getParameter("netType");
		}
		if(request.getParameter("hubDevice") != null) {
			hubDevice = request.getParameter("hubDevice");
		}
		if (request.getParameter("numDevices") != null) {
			numDevicesParam = request.getParameter("numDevices");
			numDevices = Integer.parseInt(numDevicesParam);
		}
		
		NetworkFactory.random(netType, owner, numDevices, hubDevice);
		
		
		request.getRequestDispatcher("GMScreen")
		.forward(request, response);
	}

}
