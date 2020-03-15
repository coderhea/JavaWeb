package myspring.user.controller;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import myspring.user.service.UserService;
import myspring.user.vo.UserVO;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/getUserList.do")
	public String getUserLlist(Model model) {
		List<UserVO> userList= userService.getUserList();
		model.addAttribute("userList",userList); //역시 여기도 EL ${userList}로 충 
		return "userList"; //이제 beans-web.xml jsp확장자 지정	
	}
	
	@RequestMapping("/getUser.do")
	public ModelAndView getUser(@RequestParam String id) {
		UserVO user= userService.getUser(id);
		return new ModelAndView("userInfo", "user", user);
	}
	
	@RequestMapping("/insertUserForm.do")
	public ModelAndView insertUserForm() {
		List<String> genderList = new ArrayList<String>();
		genderList.add("m");
		genderList.add("f");
		List<String> cityList = new ArrayList<String>();
		cityList.add("seoul");
		cityList.add("busan");
		cityList.add("daegu");
		
		Map<String, List<String>> map = new HashMap<>();
		map.put("genderList", genderList);
		map.put("cityList", cityList);
		
		return new ModelAndView("userInsert","map",map);
	}
	
	@RequestMapping("/insertUser.do")
	public String insertUser(@ModelAttribute UserVO user) {
		if(user!=null)
			userService.insertUser(user);
		return "redirect:/getUserList.do";
	}
}
