package com.ken.wms.controller.commons;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ken.wms.controller.Enum.AccountStatus;
import com.ken.wms.controller.Enum.ResponseStatus;
import com.ken.wms.domain.User;
import com.ken.wms.security.SecurityService;
import com.ken.wms.service.Interface.AccountService;
import com.ken.wms.service.execption.AccountServiceException;
import com.ken.wms.service.util.CheckCodeGenerator;

@SessionAttributes(value = { "checkCode" })
@RequestMapping("/commons/account")
@Controller
public class AccountHandler {

	private static Logger log = Logger.getLogger("application");

	@Autowired
	private AccountService accountService;
	@Autowired
	private CheckCodeGenerator checkCodeGenerator;
	@Autowired
	private SecurityService securityService;

	/**
	 * 
	 * @param user
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "signIn", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> signIn(@RequestBody User user, HttpServletRequest request) {
		// 初始化返回的结果集
		Map<String, Object> resultSet = new HashMap<>();
		String errorMsg = "";
		String result = ResponseStatus.ERROR.toString();
		String targetURL = "";
		String message = ""; // 用于记录日志

		try {
			// 获取 session
			HttpSession session = request.getSession();

			// 获取 Session 中的 checkCode
			String checkCode = (String) session.getAttribute("checkCode");

			// 用户验证
			accountService.signIn(user, checkCode);

			// 配置 Session
			session.setAttribute("userID", user.getId());
			session.setAttribute("account_status", AccountStatus.SIGNIN.toString());
			session.setAttribute("role", securityService.getRole(user.getId()).getRoleName());

			targetURL = "";

			result = ResponseStatus.SUCCESS.toString();

		} catch (AccountServiceException e) {
			errorMsg = e.getErrorType();
		} finally {
			// 记录日志
			log.info("操作：用户登陆" + " 用户ID:" + user.getId() + " IP:" + request.getRemoteAddr() + " 状态：" + result + " 信息:"
					+ message);
		}

		// 配置结果集
		resultSet.put("result", result);
		resultSet.put("url", targetURL);
		resultSet.put("errorMsg", errorMsg);

		return resultSet;
	}

	/**
	 * 获取图形验证码 将返回一个包含4位字符（字母或数字）的图形验证码，并且将图形验证码的值设置到用户的 session 中
	 * 
	 * @param time
	 *            时间戳
	 * @param response
	 *            返回的 HttpServletResponse 响应
	 * @param map
	 *            用于设置 session
	 */
	@RequestMapping(value = "checkCode/{time}", method = RequestMethod.GET)
	public void getCheckCode(@PathVariable("time") String time, HttpServletResponse response, Map<String, Object> map) {

		BufferedImage checkCodeImage = null;
		String checkCodeString = null;

		// 获取图形验证码
		Map<String, Object> checkCode = checkCodeGenerator.generlateCheckCode();

		if (checkCode != null) {
			checkCodeString = (String) checkCode.get("checkCodeString");
			checkCodeImage = (BufferedImage) checkCode.get("checkCodeImage");
		}

		if (checkCodeString != null && checkCodeImage != null) {
			try (ServletOutputStream outputStream = response.getOutputStream();) {
				// 设置 Session
				map.put("checkCode", checkCodeString);

				// 将验证码输出
				ImageIO.write(checkCodeImage, "png", outputStream);

				response.setHeader("Pragma", "no-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);
				response.setContentType("image/png");
			} catch (IOException e) {
				log.error("fail to get the ServletOutputStream");
			}
		}
	}
}
