package com.mobileleader.edoc.monitoring.password;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.mobileleader.edoc.monitoring.common.annotation.TestDescription;

public class PasswordEncoderTest {

    PasswordEncoder passwordEncoder;

    @Before
    public void setUp() {
        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
     
    @Test
    @TestDescription("패스워드 인코더 value 테스트")
    public void encodePassword() {
        // Given
        String rawPassword = "A1234567";
        
        // When
        String encodedPassword = passwordEncoder.encode(rawPassword);
        String encodedPassword2 = passwordEncoder.encode(rawPassword);
        
        // Then
        assertThat(passwordEncoder.matches(rawPassword, encodedPassword2)).isTrue();
        assertThat(encodedPassword).isNotEqualTo(encodedPassword2);
    }
}
