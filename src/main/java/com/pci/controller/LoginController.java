package com.pci.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.pci.entity.MtUser;
import com.pci.repository.UserRepository;

/**
 * ログインコントローラ
 * @author endo_k01
 *
 */
@Controller
@SessionAttributes("loginUser")
public class LoginController {
	
	@Autowired
	UserRepository userRepository;
	
	MtUser user;

	/**
	 * /loginで起動されるメソッド
	 * @param mv ModelAndView
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/login",method=RequestMethod.GET)
	public ModelAndView login(ModelAndView mav) {
		mav.addObject("iserror", false);
		mav.setViewName("000login");
		return mav;
	}
	
	/**
	 * /indexで起動されるメソッド
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index",method=RequestMethod.GET)
	public ModelAndView index(
			@RequestParam(name="username") String username,
			@RequestParam(name="password") String password,
			ModelAndView mav) {
		mav.addObject("iserror", false);

		// ダミー
		// パスワード認証なしで実装する
		// パスワードチェックはSpringSecurity組込み後に実装する
		MtUser loginUser = userRepository.findByUserCode(username);
    	mav.addObject("loginUser", loginUser);

    	// ユーザIDが"u002"であればマネージャーロールとして実装
		if(username.equals("u002")) {
        	mav.setViewName("forward:/Mgr/SalesList");
        } else if(username.equals("u003")){
        	mav.setViewName("forward:/Staff/SalesList");	
        } else {
			mav.setViewName("forward:/login-error");	
        }
	    return mav;
	}
	
	@RequestMapping(value = "/login-error",method=RequestMethod.GET)
	public ModelAndView loginError(ModelAndView mav) {
		mav.addObject("iserror", true);
		mav.setViewName("000login");
		return mav;
	}
}