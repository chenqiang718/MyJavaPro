package cq.springmvc.Controller;

import cq.springmvc.Model.User;
import cq.springmvc.Service.UserGetService;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.DispatcherServlet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class Control {
    private static Logger logger= Logger.getLogger(Control.class);
    @Autowired
    private UserGetService userGetService;
    @RequestMapping("/HelloWorld")
    public String hello(){
        logger.info("你好");
        return "Hello";
    }

    @ResponseBody
    @RequestMapping("/getUser")
    public String getAllUser(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        StringBuffer sb=new StringBuffer();
        for(User user:userGetService.getAllUser()){
            sb.append(user.toString());
            sb.append("\n");
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    @RequestMapping("/Hello")
    public String HelloWorld(){
        return  "HelloWorld";
    }
}
