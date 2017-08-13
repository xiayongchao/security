package com.xyc.security.tag;

import com.xyc.security.bo.LoginUserInfo;
import com.xyc.security.common.Constant;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 开发jsp系统时，我们经常会用到tag来写java的逻辑代码，一般会继承两个类，一个是SimpleTagSupport，另一个是TagSupport，由于TagSupport书写配置比较复杂，
 * 一般采用的继承SimpleTagSupport的时候比较多。
 * Created by xyc on 2017/8/13 0013.
 */
public class SecurityTag extends SimpleTagSupport {
    /**
     * 权限模块值
     */
    private String module;
    /**
     * 权限值
     */
    private String permission;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    /**
     * 注意：
     * 在TagSupport当中有pageContext属性就和好获取session，request对象，但是SimpleTagSupport 当中只有jspContext 我们就将jspContext对象转换为pageContext对象，然后就可以调用session，request对象，
     * 比如：
     * HttpSession session=((PageContext)this.getJspContext()).getSession();
     * HttpServletRequest request=(HttpServletRequest) ((PageContext)this.getJspContext()).getRequest();
     *
     * @throws JspException
     * @throws IOException
     */
    @Override
    public void doTag() throws JspException, IOException {
        /**
         * getJspContext()返回的jspContext是PageContext的一个子类，里面包含了获取各个作用域的属性的方法
         * 1.getJspContext().getAttribute(name, scope)  两个参数的scope 可以取值page ，request ，session ，context
         * 2.getJspContext().findAttribute(name) 这个方法会按page -->request -->session -- >context 去查找，如果查询到对应Name的值，立即返回
         * 3.getJspContext().getAttribute(name) 默认只会在page 作用域去查找属性
         * getJspContext() 本身就是一个PageContext，如果你是获取request,session,serlvetContext .这些对象执行方法的时候那么你需要把这个对象强制转型为pageContext..比如使用PageContext context = (PageContext) getJspContext();
         */
        LoginUserInfo loginUserInfo = (LoginUserInfo) this.getJspContext().findAttribute(Constant.LOGIN_USER_INFO);
        if (loginUserInfo != null) {
            if (this.verifySecurity(loginUserInfo.getMpInfoMap())) {
                this.getJspBody().invoke(null); //默认输出标签体
            }
        }
    }

    private boolean verifySecurity(Map<String, List<String>> mpInfoMap) {
        if (mpInfoMap == null || mpInfoMap.isEmpty() || this.getModule() == null || this.getModule().isEmpty() || this.getPermission() == null || this.getPermission().isEmpty()) {
            return false;
        }
        List<String> pInfoList = mpInfoMap.get(this.getModule());
        if (pInfoList == null || pInfoList.isEmpty()) {
            return false;
        }
        if (pInfoList.contains(this.getPermission())) {
            return true;
        }
        return false;
    }
}
