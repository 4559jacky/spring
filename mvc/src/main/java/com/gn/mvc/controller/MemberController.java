package com.gn.mvc.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gn.mvc.dto.MemberDto;
import com.gn.mvc.service.MemberService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor  // final때문에 들어감 final 필드를 활용한 생성자 
public class MemberController {
	
	private final MemberService service; //값이 1회성 -> 순환참조 발생 ㄴ ㄴ 

	@GetMapping("/member/create")
	public String CreateMemberViews() {
	System.out.println("회원가입 화면전환 ");
		return "member/create";
	}
	
	@PostMapping("/member")
	@ResponseBody
	public Map<String,String> createMemberApi(MemberDto dto) {
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "404");
		resultMap.put("res_msg", "회원가입 중 오류가 발생했습니다.");
		
		MemberDto saved = service.createMember(dto);
		
		if(saved.getMember_no() != null) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "회원가입을 성공했습니다.");
		}
		return resultMap;
	}

}