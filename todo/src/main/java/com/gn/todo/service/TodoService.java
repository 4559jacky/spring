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
	
	// 강사님 할 일 조회
//	public List<Todo> selectTodoAll() {
//		return todoRepository.findAll();
//	}
	
	// 할 일 조회
	public Page<Todo> selectTodoAll(SearchDto searchDto, PageDto pageDto) {
		Pageable pageable 
			= PageRequest.of(pageDto.getNowPage()-1, pageDto.getNumPerPage());
		Specification<Todo> spec = (root,query,criteriaBuilder) -> null;
		spec = spec.and(TodoSpecification.todoContentContains(searchDto.getSearch_text()));
//		if(searchDto.getSearch_text() == null) {
//			spec = spec.and(TodoSpecification.todoContentContains(searchDto.getSearch_text()));
//		}
		Page<Todo> list = todoRepository.findAll(spec, pageable);
		return list;
	}
	
	// 강사님 할 일 추가
//	public Todo createTodoOne(TodoDto dto) {
//		Todo entity = dto.toEntity();
//		Todo result = todoRepository.save(entity);
//		return result;
//	}
	
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
				TodoDto dto = new TodoDto().toDto(entity);
				if(dto.getFlag().equals("Y")) {
					dto.setFlag("N");
				} else if(dto.getFlag().equals("N")) {
					dto.setFlag("Y");
				}
				
				Todo param = dto.toEntity();
				todoRepository.save(param);
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

	// 상태 변경 강사님버전
	public Todo updateTodo(Long id) {
		Todo target = todoRepository.findById(id).orElse(null);

		TodoDto dto = new TodoDto().toDto(target);
		
		if(target != null) {
			if("Y".equals(target.getFlag())) {
				dto.setFlag("N");
			} else {
				dto.setFlag("Y");
			}
		}
		return todoRepository.save(dto.toEntity());
	}
	
	// 삭제 강사님 버전
	public int deleteTodoOne(Long id) {
		int result = 0;
		try {
			Todo target = todoRepository.findById(id).orElse(null);
			if(target != null) {
				todoRepository.delete(target);
			}
			result = 1;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}
