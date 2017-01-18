package com.ken.wms.controller.commons;

import com.ken.wms.controller.Enum.AccountStatus;
import com.ken.wms.controller.entity.PasswordModification;
import com.ken.wms.controller.util.Response;
import com.ken.wms.controller.util.ResponseUtil;
import com.ken.wms.domain.RepositoryAdmin;
import com.ken.wms.domain.Role;
import com.ken.wms.domain.User;
import com.ken.wms.security.SecurityService;
import com.ken.wms.service.Interface.AccountService;
import com.ken.wms.service.Interface.RepositoryAdminManageService;
import com.ken.wms.service.execption.AccountServiceException;
import com.ken.wms.service.util.CheckCodeGenerator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 处理涉及账户的请求
 *
 * @author Ken
 */
@SessionAttributes(value = {"checkCode"})
@RequestMapping("/commons/account")
@Controller
public class AccountHandler {

    // 日志
    private static Logger log = Logger.getLogger("application");

    @Autowired
    private AccountService accountService;
    @Autowired
    private CheckCodeGenerator checkCodeGenerator;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private RepositoryAdminManageService repositoryAdminService;
    @Autowired
    private ResponseUtil responseUtil;

    /**
     * 登陆
     *
     * @param user    用户信息
     * @param request HttpServletRequest
     * @return 返回一个 Map 对象，其中包含登陆操作的结果
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "signIn", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> signIn(@RequestBody User user, HttpServletRequest request) {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        String result = Response.RESPONSE_RESULT_ERROR;
        String errorMsg = "";
        String targetURL = "";
        String message = ""; // 用于记录日志

        try {
            // 获取 session
            HttpSession session = request.getSession();

            // 获取 Session 中的 checkCode
            String checkCode = (String) session.getAttribute("checkCode");

            // 用户验证
            accountService.signIn(user, checkCode);

            // 获用户角色
            Role role = securityService.getRole(user.getId());
            if (role != null) {
                // 配置 Session
                session.setAttribute("userID", user.getId());
                session.setAttribute("userName", user.getUserName());
                session.setAttribute("account_status", AccountStatus.SIGNIN.toString());
                session.setAttribute("role", role.getRoleName());
                session.setAttribute("requestPrefix", role.getRolePrefix());

                // 如果是仓库管理员，则配置所属的仓库
                List<RepositoryAdmin> repositoryAdmin = (List<RepositoryAdmin>) repositoryAdminService.selectByID(user.getId()).get("data");
                session.setAttribute("repositoryBelong", (repositoryAdmin.isEmpty()) ? "none" : repositoryAdmin.get(0).getRepositoryBelongID());
            }

            targetURL = "";

            result = Response.RESPONSE_RESULT_SUCCESS;

        } catch (AccountServiceException e) {
            errorMsg = e.getErrorType();
        } catch (NullPointerException e) {
            // TODO: handle exception
        } finally {
            // 记录日志
            log.info("操作：用户登陆" + " 用户ID:" + user.getId() + " IP:" + request.getRemoteAddr() + " 状态：" + result + " 信息:"
                    + message);
        }

        // 设置 Response
        responseContent.setResponseResult(result);
        responseContent.setResponseMsg(errorMsg);
        responseContent.setCustomerInfo("url", targetURL);

        return responseContent.generateResponse();
    }

    /**
     * 注销登陆
     *
     * @param request HttpServletRequest
     * @return 返回一个 Map 对象，键值为 result 的内容代表注销操作的结果，值为 success 或 error
     */
    @RequestMapping(value = "signOut", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> signOut(HttpServletRequest request) {
        // 初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

		/* do something when sign out*/

        HttpSession session = request.getSession();
        session.setAttribute("userID", "none");
        session.setAttribute("role", "none");
        session.setAttribute("account_status", AccountStatus.SIGNOUT.toString());

        responseContent.setResponseResult(Response.RESPONSE_RESULT_SUCCESS);
        return responseContent.generateResponse();
    }

    /**
     * 修改账户密码
     *
     * @param passwordInfo 修改密码信息
     * @param request      HttpServletRequest
     * @return 返回一个 Map 对象，其中键值为 result 代表修改密码操作的结果，值为 success 或 error；键值为 msg 代表需要返回给用户的信息
     */
    @RequestMapping(value = "passwordModify")
    public
    @ResponseBody
    Map<String, Object> passwordModify(@RequestBody PasswordModification passwordInfo, HttpServletRequest request) {
        //初始化 Response
        Response responseContent = responseUtil.newResponseInstance();

        String errorMsg = null;
        String result = Response.RESPONSE_RESULT_ERROR;

        // 获取用户 ID
        HttpSession session = request.getSession();
        Integer userID = (Integer) session.getAttribute("userID");

        try {
            // 更改密码
            accountService.passwordModify(userID, passwordInfo);

            result = Response.RESPONSE_RESULT_SUCCESS;
        } catch (NullPointerException e) {
            log.debug("The passwordModification object or userID is null");
        } catch (AccountServiceException e) {
            errorMsg = e.getErrorType();
            log.debug("error type:" + errorMsg);
        }

        // 设置 Resposne
        responseContent.setResponseResult(result);
        responseContent.setResponseMsg(errorMsg);
        return responseContent.generateResponse();
    }

    /**
     * 获取图形验证码 将返回一个包含4位字符（字母或数字）的图形验证码，并且将图形验证码的值设置到用户的 session 中
     *
     * @param time     时间戳
     * @param response 返回的 HttpServletResponse 响应
     * @param map      用于设置 session
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
