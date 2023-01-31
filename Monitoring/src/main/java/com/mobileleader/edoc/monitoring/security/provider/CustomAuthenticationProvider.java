package com.mobileleader.edoc.monitoring.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.mobileleader.edoc.monitoring.common.model.LoginInfo;
import com.mobileleader.edoc.monitoring.db.dto.UserMgmtDto;
import com.mobileleader.edoc.monitoring.db.mapper.UserMgmtMapper;
import com.mobileleader.edoc.monitoring.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component(value = "customAuthenticationProvider")
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private final UserMgmtMapper userMgmtMapper;

	private final LoginService loginService;
	
	private final PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();

		LoginInfo loginInfo = null;
		UserMgmtDto userMgmtDto = null;
		
		try {
			userMgmtDto = userMgmtMapper.selectByEmpNo(username);
			if (userMgmtDto == null) {
				throw new UsernameNotFoundException("등록되지 않은 사용자 아이디입니다.");
			} else {
				if (userMgmtDto.getPasswordErrorCount() >= 5) { // 비밀번호 5회이상 오류시 잠금
					throw new BadCredentialsException("비밀번호가 5회 이상 일치 하지 않습니다. 관리자에게 초기화 요청 바랍니다.");
				} else if (!passwordEncoder.matches(password, userMgmtDto.getPassword())) {
					userMgmtMapper.updatePasswordErrorCount(userMgmtDto.getEmpNo(), userMgmtDto.getPasswordErrorCount() + 1);
					throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
				} else {
					loginInfo = loginService.loadUserByUsername(username);
				}
			}
		} catch (UsernameNotFoundException e) {
			logger.debug(e.toString());
			throw new UsernameNotFoundException(e.getMessage());
		} catch (BadCredentialsException e) {
			logger.debug(e.toString());
			throw new BadCredentialsException(e.getMessage());
		} catch (Exception e) {
			logger.debug(e.toString());
			throw new RuntimeException(e.getMessage());
		}
		
		return new UsernamePasswordAuthenticationToken(loginInfo, userMgmtDto.getPassword(), loginInfo.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
