package cn.edu.xaut.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.xaut.demo.RegexToMinimumDFA;

/**
 * Servlet implementation class ToMinimumDFA
 */
public class ToMinimumDFA extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置响应内容类型
		response.setContentType("text/json; charset=utf-8");
		String formula = request.getParameter("formula");
		response.getWriter().write(new RegexToMinimumDFA().getResult(formula));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
