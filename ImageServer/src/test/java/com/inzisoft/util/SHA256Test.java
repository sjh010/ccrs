package com.inzisoft.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.mobileleader.image.util.CryptoSHA2;

public class SHA256Test {
	
	String src = "8201191234567";
	
	@Test
	public void SHA256Hashing() {
		String encoded = CryptoSHA2.getSHA256Hashing(src);
		System.out.println("1. 오리지널 주민번호: " + src);
		System.out.println("1. 암호화한 주민번호: " + encoded);
	}
	
	@Test
	public void EncodedSHA256Hashing() {
		String encoded2 = CryptoSHA2.getEncodedSHA256Hashing(src);
		System.out.println("2. 오리지널 주민번호: " + src);
		System.out.println("2. 암호화한 주민번호: " + encoded2);
	}
	
	@Test
	public void id_주민번호() {
		String enc = CryptoSHA2.getIdEnc(src);
		System.out.println("3. 주민번호 암호화: " + enc);
		assertThat(enc, is(not(src)));
	}
	
	@Test
	public void id_잘못된주민번호는_암호화_하지않고_그대로_리턴() {
		String wrongId = "820119-11234567";
		String enc = CryptoSHA2.getIdEnc(wrongId);
		System.out.println("4. 주민번호 암호화: " + enc);
		assertThat(enc, is(wrongId));	
	}

}
