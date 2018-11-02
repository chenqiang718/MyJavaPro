package com.cq.springboot.Controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: chenqiang
 * @Date: 2018/10/25 15:09
 * @Version 1.0
 */
@Controller
public class ShiroController {
    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request){
        ModelAndView view=new ModelAndView("html/err/error");
        Subject subject=SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken(request.getParameter("username"), request.getParameter("password"));
        try{
            subject.login(token);
            view.setViewName("html/success");
            return view;
        }catch (IncorrectCredentialsException e) {
            view.addObject("msg", "登录密码错误");
            System.out.println("登录密码错误!!!" + e);
            return view;
        } catch (ExcessiveAttemptsException e) {
            view.addObject("msg", "登录失败次数过多");
            System.out.println("登录失败次数过多!!!" + e);
            return view;
        } catch (LockedAccountException e) {
            view.addObject("msg", "帐号已被锁定");
            System.out.println("帐号已被锁定!!!" + e);
            return view;
        } catch (DisabledAccountException e) {
            view.addObject("msg", "帐号已被禁用");
            System.out.println("帐号已被禁用!!!" + e);
            return view;
        } catch (ExpiredCredentialsException e) {
            view.addObject("msg", "帐号已过期");
            System.out.println("帐号已过期!!!" + e);
            return view;
        } catch (UnknownAccountException e) {
            view.addObject("msg", "帐号不存在");
            System.out.println("帐号不存在!!!" + e);
            return view;
        } catch (AuthenticationException e){
            view.addObject("msg", "该账号未得到相应权限");
            System.out.println("该账号未得到相应权限!!!" + e);
            return view;
        } catch (Exception e){
            view.addObject("msg", "出错");
            System.out.println("出错!!!" + e);
            return view;
        }
    }

    @RequestMapping({"/index","/"})
    public String index(){
        return "html/index";
    }

}
