package com.gn.mvc.service;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gn.mvc.dto.MemberDto;
import com.gn.mvc.entity.Member;
import com.gn.mvc.repository.MemberRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final DataSource dataSource;
	private final UserDetailsService userDetailsService;
	
	// 그냥 멤버가 엔티티고 엔티티는 테이블하고 연관이 되어있던 애고 데베 통신에만 사용
	
	
	public int deleteMember(Long id) {
		int result = 0;
		try {
			Member member = repository.findById(id).orElse(null);
			if(member != null) {
				repository.deleteById(id);
				result = 1;
				JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
				String sql = "DELETE FROM persistent_logins WHERE username = ?";
				jdbcTemplate.update(sql, member.getMemberId());
				SecurityContextHolder.getContext().setAuthentication(null);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 1. 반환형 : int
	// 2. 메소드명 : updateMember
	// 3. 매개변수 : MemberDto
	// 4. 역할
	public int updateMember(MemberDto param) {
		int result = 0;
		try {
			// (1) 데이터베이스 회원 정보 수정
			param.setMember_pw(passwordEncoder.encode(param.getMember_pw()));
			Member updated = repository.save(param.toEntity());
			if(updated != null) {
				// (2) remember-me(**DB**, cookie)가 있다면 무효화
				JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
				String sql = "DELETE FROM persistent_logins WHERE username = ?";
				jdbcTemplate.update(sql, param.getMember_id());
				// (3) 변경된 회원 정보 Security Context에 즉시 반영
				UserDetails updateUserDetails
					= userDetailsService.loadUserByUsername(param.getMember_id());
				Authentication newAuth = new UsernamePasswordAuthenticationToken(
						updateUserDetails,
						updateUserDetails.getPassword(),
						updateUserDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(newAuth);
				result = 1;
			}
			
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Member selectMemberOne(Long id) {
		return repository.findById(id).orElse(null);
	}
	
	
	public MemberDto createMember(MemberDto dto) {
		dto.setMember_pw(passwordEncoder.encode(dto.getMember_pw()));
		
		Member param = dto.toEntity(); // 멤버 디티오에 사용자가 입력한 값 담아서 그냥 멤버로 전해준다 (앤티티로 바꾸겟다)
		
		Member result = repository.save(param); // 바뀐애를 인서트 
		
		return new MemberDto().toDto(result);
	}
}