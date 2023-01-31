package com.inzisoft.crypto.properties;

public class ServiceProperty {
	// 암호화 파일이 저장될 폴더
	private String encryptFolder;
	
	//복호화 파일이 저장될 폴더
	private String decryptFolder;
	
	//라이센스 파일 명
	private String licenseFile;
	
	//암복호화 키파일명
	private String keyFile;
	
	//jni file 파일명
	private String jniFile; 
	
	// 암호화 파일
	private String cryptoFile;
	
	
	public String getEncryptFolder() {
		return encryptFolder;
	}

	public void setEncryptFolder(String encryptFolder) {
		this.encryptFolder = encryptFolder;
	}

	public String getDecryptFolder() {
		return decryptFolder;
	}

	public void setDecryptFolder(String decryptFolder) {
		this.decryptFolder = decryptFolder;
	}

	public String getLicenseFile() {
		return licenseFile;
	}

	public void setLicenseFile(String licenseFile) {
		this.licenseFile = licenseFile;
	}

	public String getKeyFile() {
		return keyFile;
	}

	public void setKeyFile(String keyFile) {
		this.keyFile = keyFile;
	}

	public String getJniFile() {
		return jniFile;
	}

	public void setJniFile(String jniFile) {
		this.jniFile = jniFile;
	}

	public String getCryptoFile() {
		return cryptoFile;
	}

	public void setCryptoFile(String cryptoFile) {
		this.cryptoFile = cryptoFile;
	}
	

}
