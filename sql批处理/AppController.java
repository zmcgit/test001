package cn.admin.workPre.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.admin.workPre.service.AppService;

@Controller
@RequestMapping("/api/workLibrary")
public class AppController {

	@Autowired
	private AppService appService;
	
	@RequestMapping(value = "/batchInsert")
	@ResponseBody
	public Map<String, Object> batchInsert(HttpServletRequest request) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			//appService.insertinto();
			appService.insertinto2();
			retMap.put("msg", "操作成功");
			retMap.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("msg", "操作失败");
			retMap.put("success", false);
		}
		return retMap;
	}
	

}
