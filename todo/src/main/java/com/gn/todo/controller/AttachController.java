package com.gn.todo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AttachController {
	
	// 1. url : /attach/create
	// 2. 응답 : JSON(Map<String,String>)
	// 3. 메소드명 : createAttachApi
	// 4. 매개변수 : List<MultipartFile>
	@PostMapping("/attach/create")
	@ResponseBody
	public Map<String,String> createAttachApi(@RequestParam("files") List<MultipartFile> files) {
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "500");
		resultMap.put("res_msg", "파일 저장 실패");
		
		for(MultipartFile mf : files) {
			System.out.println(mf.getOriginalFilename());
		}
		
		
		return resultMap;
	}
	
}
