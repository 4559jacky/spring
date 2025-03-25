package com.gn.todo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.gn.todo.dto.PageDto;
import com.gn.todo.dto.SearchDto;
import com.gn.todo.dto.TodoDto;
import com.gn.todo.entity.Todo;
import com.gn.todo.repository.TodoRepository;
import com.gn.todo.specification.TodoSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService {
	
	private final TodoRepository todoRepository;
	
	// 할 일 조회
	public Page<Todo> selectTodoAll(SearchDto searchDto, PageDto pageDto) {
		Pageable pageable = PageRequest.of(pageDto.getNowPage()-1, pageDto.getNumPerPage());
		Specification<Todo> spec = (root,query,criteriaBuilder) -> null;
		spec = spec.and(TodoSpecification.todoContentContains(searchDto.getSearch_text()));
		System.out.println(searchDto.getSearch_text());
		Page<Todo> list = todoRepository.findAll(spec, pageable);
		return list;
	}
	
	// 할 일 추가
	public int createTodo(TodoDto dto) {
		int result = 0;
		
		try {
			
			Todo entity = dto.toEntity();
			
			Todo saved = todoRepository.save(entity);
			
			if(saved != null) {
				result = 1;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	// 상태 변경
	public int updateTodoOne(Long id) {
		int result = 0;
		try {
			Todo entity = todoRepository.findById(id).orElse(null);
			if(entity != null) {
				System.out.println("여기걸림?");
				TodoDto dto = new TodoDto().toDto(entity);
				if(dto.getFlag().equals("Y")) {
					System.out.println("여기걸리면 Y");
					dto.setFlag("N");
				} else if(dto.getFlag().equals("N")) {
					System.out.println("여기걸리면 N");
					dto.setFlag("Y");
				}
				System.out.println(dto);
				
				Todo param = dto.toEntity();
				Todo last = todoRepository.save(param);
				System.out.println(last);
				result = 1;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public int deleteTodo(Long id) {
		int result = 0;
		try {
			
			Todo target = todoRepository.findById(id).orElse(null);
			if(target != null) {
				todoRepository.deleteById(id);
			}
			result = 1;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
