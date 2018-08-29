package cq.common;

import org.apache.commons.io.IOUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @author: 尚正平(shangzhengping@adtime.com)
 * @date: 2017年10月13日 下午2:14:02
 * @Copyright: 2016 浙江德嘉信息技术有限公司 All Rights Reserved
 */
public class ScriptIn {

	private static final Logger logger = LoggerFactory.getLogger(ScriptIn.class);

	private static void initJQuery(JavascriptExecutor driver) {
		Boolean haveJQ = false;
		try {
			haveJQ = (Boolean) driver.executeScript("if(typeof jQuery!='undefined' && typeof $!='undefined'){return false;}else{return true;}");
		} catch (WebDriverException e) {
			haveJQ = false;
		}
		if (haveJQ) {
			InputStream input = null;
			try {
				/*input = new FileInputStream("/javascript/jquery-2.0.3.min.js");
				driver.executeScript(IOUtils.toString(input));*/
				driver.executeScript(IOUtils.toString(ScriptIn.class.getClassLoader().getResourceAsStream("javascript/jquery-2.0.3.min.js")));
			} catch (IOException e) {
				logger.error("jquery加载出现异常", e);
			}finally{
				if(input != null){
					try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static Object runJs(WebDriver driver, String script, Object... args) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		initJQuery(js);
		return js.executeScript(script, args);
	}

	public static Object runJsAsync(WebDriver driver, String script, Object... args) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		initJQuery(js);
		return js.executeAsyncScript(script, args);
	}
}