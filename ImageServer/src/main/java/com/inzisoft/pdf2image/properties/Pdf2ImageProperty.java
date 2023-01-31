package com.inzisoft.pdf2image.properties;

public class Pdf2ImageProperty {

	/**
	 * PDF to image 라이브러리 SO 파일 패스
	 */
	private String soFile;
	
	/**
	 * PDF to image 라이브러리 라이선스 파일
	 */
	private String licenseFile;
	
	public String getSoFile() {
		return soFile;
	}
	public void setSoFile(String soFile) {
		this.soFile = soFile;
	}
	public String getLicenseFile() {
		return licenseFile;
	}
	public void setLicenseFile(String licenseFile) {
		this.licenseFile = licenseFile;
	}
	
	@Override
	public String toString() {
		return "Pdf2ImgProperty [soFile=" + soFile + ", licenseFile=" + licenseFile + "]";
	}
	
	
}
