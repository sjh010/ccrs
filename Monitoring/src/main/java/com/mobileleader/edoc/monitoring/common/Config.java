package com.mobileleader.edoc.monitoring.common;

public class Config {
	
	/**
	 * 전자 문서 열람 파일 첨부 경로
	 */
	private static String fileUploadRootPath;
	
	/**
	 * 파일 첨부 임시 경로
	 */
	private static String fileUploadTempRootPath;
	
	/**
	 * 서버 {기동|중지|재기동} 스크립트 패스
	 */
	private static String shellCommandPath;
	
	/**
	 * 전자문서 모니터링 시스템 was 포트
	 */
	private static String monirotingWasPort;
	
	/**
	 * 전자문서 재처리 체크 URL
	 */
	private static String retryCheckUrl;
	
	/**
	 * 전자문서 재처리 체크 goTo 타입
	 */
	private static String retryCheckGoto;
	
	/**
	 * BgDaemon 로그 디렉토리 경로
	 */
	private static String bgDaemonRootPath;
	
	/**
	 * BgDaemon 로그 디폴트 파일 이름
	 */
	private static String bgDaemonFilename;
	
	/**
	 * BgDaemon 로그 디렉토리 경로
	 */
	private static String bgDaemonFilenamePattern;
	
	/**
	 * EaiServer 로그 디렉토리 경로
	 */
	private static String eaiServerRootPath;
	
	/**
	 * EaiServer 로그 디폴트 파일 이름
	 */
	private static String eaiServerFilename;
	
	/**
	 * EaiServer 로그 디렉토리 경로
	 */
	private static String eaiServerFilenamePattern;
	
	
	/**
	 * ScServer 로그 디렉토리 경로
	 */
	private static String scServerRootPath;
	
	/**
	 * ScServer 로그 디폴트 파일 이름
	 */
	private static String scServerFilename;
	
	/**
	 * ScServer 로그 디렉토리 경로
	 */
	private static String scServerFilenamePattern;
	
	/**
	 * exrepApi_ECM 로그 디렉토리 경로
	 */
	private static String exrepApiRootPath;
	
	/**
	 * exrepApi_ECM 로그 디폴트 파일 이름
	 */
	private static String exrepApiFilename;
	
	/**
	 * exrepApi_ECM 로그 디렉토리 경로
	 */
	private static String exrepApiFilenamePattern;
	
	/**
	 * PPR 서버 프로토콜 정보
	 */
	private static String pprServerProtocol;
	
	/**
	 * PPR 서버 호스트 정보
	 */
	private static String pprServerHost;
	
	/**
	 * PPR 서버 IP 정보
	 */
	private static String pprServerIp;
	
	/**
	 * PPR 서버 PORT 정보
	 */
	private static String pprServerPort;
	
	/**
	 * PPR 서버 Context 정보
	 */
	private static String pprServerContext;
	
	public Config() {}

	public static String getFileUploadRootPath() {
		return fileUploadRootPath;
	}

	public void setFileUploadRootPath(String fileUploadRootPath) {
		Config.fileUploadRootPath = fileUploadRootPath;
	}

	public static String getFileUploadTempRootPath() {
		return fileUploadTempRootPath;
	}

	public void setFileUploadTempRootPath(String fileUploadTempRootPath) {
		Config.fileUploadTempRootPath = fileUploadTempRootPath;
	}

	public static String getShellCommandPath() {
		return shellCommandPath;
	}

	public void setShellCommandPath(String shellCommandPath) {
		Config.shellCommandPath = shellCommandPath;
	}

	public static String getMonirotingWasPort() {
		return monirotingWasPort;
	}

	public void setMonirotingWasPort(String monirotingWasPort) {
		Config.monirotingWasPort = monirotingWasPort;
	}

	public static String getRetryCheckUrl() {
		return retryCheckUrl;
	}

	public void setRetryCheckUrl(String retryCheckUrl) {
		Config.retryCheckUrl = retryCheckUrl;
	}

	public static String getRetryCheckGoto() {
		return retryCheckGoto;
	}

	public void setRetryCheckGoto(String retryCheckGoto) {
		Config.retryCheckGoto = retryCheckGoto;
	}

	public static String getBgDaemonRootPath() {
		return bgDaemonRootPath;
	}

	public void setBgDaemonRootPath(String bgDaemonRootPath) {
		Config.bgDaemonRootPath = bgDaemonRootPath;
	}

	public static String getEaiServerRootPath() {
		return eaiServerRootPath;
	}

	public void setEaiServerRootPath(String eaiServerRootPath) {
		Config.eaiServerRootPath = eaiServerRootPath;
	}

	public static String getScServerRootPath() {
		return scServerRootPath;
	}

	public void setScServerRootPath(String scServerRootPath) {
		Config.scServerRootPath = scServerRootPath;
	}

	public static String getExrepApiRootPath() {
		return exrepApiRootPath;
	}

	public void setExrepApiRootPath(String exrepApiRootPath) {
		Config.exrepApiRootPath = exrepApiRootPath;
	}

	public static String getBgDaemonFilenamePattern() {
		return bgDaemonFilenamePattern;
	}

	public void setBgDaemonFilenamePattern(String bgDaemonFilenamePattern) {
		Config.bgDaemonFilenamePattern = bgDaemonFilenamePattern;
	}

	public static String getEaiServerFilenamePattern() {
		return eaiServerFilenamePattern;
	}

	public void setEaiServerFilenamePattern(String eaiServerFilenamePattern) {
		Config.eaiServerFilenamePattern = eaiServerFilenamePattern;
	}

	public static String getScServerFilenamePattern() {
		return scServerFilenamePattern;
	}

	public void setScServerFilenamePattern(String scServerFilenamePattern) {
		Config.scServerFilenamePattern = scServerFilenamePattern;
	}

	public static String getExrepApiFilenamePattern() {
		return exrepApiFilenamePattern;
	}

	public void setExrepApiFilenamePattern(String exrepApiFilenamePattern) {
		Config.exrepApiFilenamePattern = exrepApiFilenamePattern;
	}

	public static String getBgDaemonFilename() {
		return bgDaemonFilename;
	}

	public void setBgDaemonFilename(String bgDaemonFilename) {
		Config.bgDaemonFilename = bgDaemonFilename;
	}

	public static String getEaiServerFilename() {
		return eaiServerFilename;
	}

	public void setEaiServerFilename(String eaiServerFilename) {
		Config.eaiServerFilename = eaiServerFilename;
	}

	public static String getScServerFilename() {
		return scServerFilename;
	}

	public void setScServerFilename(String scServerFilename) {
		Config.scServerFilename = scServerFilename;
	}

	public static String getExrepApiFilename() {
		return exrepApiFilename;
	}

	public void setExrepApiFilename(String exrepApiFilename) {
		Config.exrepApiFilename = exrepApiFilename;
	}

	public static String getPprServerHost() {
		return pprServerHost;
	}

	public void setPprServerHost(String pprServerHost) {
		Config.pprServerHost = pprServerHost;
	}

	public static String getPprServerIp() {
		return pprServerIp;
	}

	public void setPprServerIp(String pprServerIp) {
		Config.pprServerIp = pprServerIp;
	}

	public static String getPprServerPort() {
		return pprServerPort;
	}

	public void setPprServerPort(String pprServerPort) {
		Config.pprServerPort = pprServerPort;
	}

	public static String getPprServerContext() {
		return pprServerContext;
	}

	public void setPprServerContext(String pprServerContext) {
		Config.pprServerContext = pprServerContext;
	}

	public static String getPprServerProtocol() {
		return pprServerProtocol;
	}

	public void setPprServerProtocol(String pprServerProtocol) {
		Config.pprServerProtocol = pprServerProtocol;
	}
	
}
	