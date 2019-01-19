package com.wemakeprice.controller.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wemakeprice.controller.rest.HtmlController;

import lombok.extern.slf4j.Slf4j;

/**
 * http://localhost:8800/index 로 이동하기 위한 Class
 *
 * @version     1.00 
 * @author      김태형
 * @since       JDK 10
 */
@Slf4j
@Controller
public class HomeController {
	static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
     * index 페이지로 이동   
     *   
     */
	@RequestMapping("/")
    public ModelAndView greeting(Model model) {
		log.info("MOVE INDEX");
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.setViewName("/web/index");

        return modelAndView;
    }
	
	@RequestMapping("/test")
    public ModelAndView test(Model model) {
        ModelAndView modelAndView = new ModelAndView("test");
        modelAndView.setViewName("/web/test");

        return modelAndView;
    }
}
