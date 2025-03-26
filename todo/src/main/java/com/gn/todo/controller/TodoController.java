package com.gn.todo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gn.todo.dto.PageDto;
import com.gn.todo.dto.SearchDto;
import com.gn.todo.dto.TodoDto;
import com.gn.todo.entity.Todo;
import com.gn.todo.service.TodoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TodoController {
	
	private final TodoService todoService;
	
	@GetMapping({"","/","/todo"})
	public String selectTodoAll(Model model, SearchDto searchDto, PageDto pageDto) {
		if (pageDto.getNowPage() == 0)
			pageDto.setNowPage(1);
		
		if(searchDto.getSearch_text() == null) {
			searchDto.setSearch_text("");
		}
		
		Page<Todo> resultList = todoService.selectTodoAll(searchDto, pageDto);
		pageDto.setTotalPage(resultList.getTotalPages());
		
//		if(resultList.isEmpty()) {
//			resultList = null;
//		}
		
		model.addAttribute("todoList", resultList.getContent());
		model.addAttribute("searchDto", searchDto);
		model.addAttribute("pageDto", pageDto);
		
		return "home";
	}
	
	// 강사님 버전 할 일 추가
//	@PostMapping("/todo/create")
//	@ResponseBody
//	public Map<String,String> createTodoApi(TodoDto dto) {
//		Map<String,String> resultMap = new HashMap<String,String>();
//		resultMap.put("res_code","500");
//		resultMap.put("res_msg", "할 일 추가 실패");
//		
//		Todo result = todoService.createTodoOne(dto);
//		if(result != null) {
//			resultMap.put("res_code", "200");
//			resultMap.put("res_msg", "할 일이 추가되었습니다.");
//		}
//		
//		return resultMap;
//	}
	
	@PostMapping("/todo/create")
	@ResponseBody
	public Map<String,String> createTodo(TodoDto dto) {
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "500");
		resultMap.put("res_msg", "할 일 추가 중 오류가 발생하였습니다.");
		
		int result = todoService.createTodo(dto);
		if(result > 0) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "할 일이 추가되었습니다.");
		}
		
		return resultMap;
	}
	
	// 상태변경 강사님 버전
	@PostMapping("/todo/{id}/update")
	@ResponseBody
	public Map<String,String> updateTodoApi(@PathVariable("id") Long id) {
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "500");
		resultMap.put("res_msg", "수정 실패");
		
		Todo result = todoService.updateTodo(id);
		if(result != null) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "수정 성공");
		}
		
		return resultMap;
	}
	
	// 삭제 강사님 버전
	@PostMapping("/todo/{id}/delete")
	@ResponseBody
	public Map<String,String> deleteTodoApi(@PathVariable("id") Long id) {
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "500");
		resultMap.put("res_msg", "삭제 실패");
		
		int result = todoService.deleteTodoOne(id);
		if(result > 0) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "삭제 성공");
		}
		
		return resultMap;
	}
	
	
	// 상태변경
//	@PostMapping("/todo/update/{id}")
//	@ResponseBody
//	public Map<String,String> updateTodoOne(@PathVariable("id") Long id) {
//		Map<String,String> resultMap = new HashMap<String,String>();
//		resultMap.put("res_code", "500");
//		resultMap.put("res_res", "상태 변경 실패");
//		
//		int result = todoService.updateTodoOne(id);
//		
//		if(result > 0) {
//			resultMap.put("res_code", "200");
//			resultMap.put("res_msg", "상태 변경 완료");
//		}
//		
//		return resultMap;
//	}
	
//	@DeleteMapping("/todo/{id}")
//	@ResponseBody
//	public Map<String,String> deleteTodo(@PathVariable("id") Long id) {
//		Map<String, String> resultMap = new HashMap<String, String>();
//		resultMap.put("res_code", "500");
//		resultMap.put("res_msg", "삭제에 실패하였습니다.");
//		
//		int result = todoService.deleteTodo(id);
//
//		if (result > 0) {
//			resultMap.put("res_code", "200");
//			resultMap.put("res_msg", "성공적으로 삭제되었습니다.");
//		}
//		return resultMap;
//		
//	}
	
}
