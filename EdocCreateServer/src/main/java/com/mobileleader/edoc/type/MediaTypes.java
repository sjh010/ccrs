package com.mobileleader.edoc.type;

import java.util.Arrays;

import org.springframework.http.MediaType;

public enum MediaTypes{
	
	DEFAULT	("???", "application/octet-stream"),
	
	ACX		("acx", "application/internet-property-stream"),
	AI		("ai", "application/postscript"),
	AIF		("aif", "audio/x-aiff"),
	AIFC	("aifc", "audio/x-aiff"),
	AIFF	("aiff", "audio/x-aiff"),
	ASF		("asf", "video/x-ms-asf"),
	ASR		("asr", "video/x-ms-asf"),
	ASX		("asx", "video/x-ms-asf"),
	AU		("au", "audio/basic"),
	AVI		("avi", "video/x-msvideo"),
	AXS		("axs", "application/olescript"),
	BAS		("bas", "text/plain"),
	BCPIO	("bcpio", "application/x-bcpio"),
	BIN		("bin", "application/octet-stream"),
	BMP		("bmp", "image/bmp"),
	C		("c", "text/plain"),
	CAT		("cat", "application/vnd.ms-pkiseccat"),
	CDF		("cdf", "application/x-cdf"),
	CDF_NEF	("cdf", "application/x-netcdf"),
	CER		("cer", "application/x-x509-ca-cert"),
	CLAZZ	("class", "application/octet-stream"),
	CLP		("clp", "application/x-msclip"),
	CMX		("cmx", "image/x-cmx"),
	COD		("cod", "image/cis-cod"),
	CPIO	("cpio", "application/x-cpio"),
	CRD		("crd", "application/x-mscardfile"),
	CRL		("crl", "application/pkix-crl"),
	CRT		("crt", "application/x-x509-ca-cert"),
	CSH		("csh", "application/x-csh"),
	CSS		("css", "text/css"),
	DCR		("dcr", "application/x-director"),
	DER		("der", "application/x-x509-ca-cert"),
	DIR		("dir", "application/x-director"),
	DLL		("dll", "application/x-msdownload"),
	DMS		("dms", "application/octet-stream"),
	DOC		("doc", "application/msword"),
	DOCM	("docm", "application/vnd.ms-word.document.macroEnabled.12"),
	DOCX	("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
	DOT		("dot", "application/msword"),
	DOTM	("dotm", "application/vnd.ms-word.template.macroEnabled.12"),
	DOTX	("dotx", "application/vnd.openxmlformats-officedocument.wordprocessingml.template"),
	DVI		("dvi", "application/x-dvi"),
	DXR		("dxr", "application/x-director"),
	EPS		("eps", "application/postscript"),
	ETX		("etx", "text/x-setext"),
	EVY		("evy", "application/envoy"),
	EXE		("exe", "application/octet-stream"),
	FIF		("fif", "application/fractals"),
	FLR		("flr", "x-world/x-vrml"),
	GIF		("gif", "image/gif"),
	GTAR	("gtar", "application/x-gtar"),
	GZ		("gz", "application/x-gzip"),
	H		("h", "text/plain"),
	HDF		("hdf", "application/x-hdf"),
	HLP		("hlp", "application/winhlp"),
	HQX		("hqx", "application/mac-binhex40"),
	HTA		("hta", "application/hta"),
	HTC		("htc", "text/x-component"),
	HTM		("htm", "text/html"),
	HTML	("html", "text/html"),
	HTT		("htt", "text/webviewhtml"),
	ICO		("ico", "image/x-icon"),
	IEF		("ief", "image/ief"),
	III		("iii", "application/x-iphone"),
	INS		("ins", "application/x-internet-signup"),
	ISP		("isp", "application/x-internet-signup"),
	JFIF	("jfif", "image/pipeg"),
	JPE		("jpe", "image/jpeg"),
	JPEG	("jpeg", "image/jpeg"),
	JPG		("jpg", "image/jpeg"),
	JS		("js", "application/x-javascript"),
	LATEX	("latex", "application/x-latex"),
	LHA		("lha", "application/octet-stream"),
	LSF		("lsf", "video/x-la-asf"),
	LSX		("lsx", "video/x-la-asf"),
	LZH		("lzh", "application/octet-stream"),
	M13		("m13", "application/x-msmediaview"),
	M14		("m14", "application/x-msmediaview"),
	M3U		("m3u", "audio/x-mpegurl"),
	MAN		("man", "application/x-troff-man"),
	MDB		("mdb", "application/x-msaccess"),
	ME		("me", "application/x-troff-me"),
	MHT		("mht", "message/rfc822"),
	MHTML	("mhtml", "message/rfc822"),
	MID		("mid", "audio/mid"),
	MNY		("mny", "application/x-msmoney"),
	MOV		("mov", "video/quicktime"),
	MOVIE	("movie", "video/x-sgi-movie"),
	MP2		("mp2", "video/mpeg"),
	MP3		("mp3", "audio/mpeg"),
	MPA		("mpa", "video/mpeg"),
	MPE		("mpe", "video/mpeg"),
	MPEG	("mpeg", "video/mpeg"),
	MPG		("mpg", "video/mpeg"),
	MPP		("mpp", "application/vnd.ms-project"),
	MPV2	("mpv2", "video/mpeg"),
	MS		("ms", "application/x-troff-ms"),
	MSG		("msg", "application/vnd.ms-outlook"),
	MVB		("mvb", "application/x-msmediaview"),
	NC		("nc", "application/x-netcdf"),
	NWS		("nws", "message/rfc822"),
	ODA		("oda", "application/oda"),
	P10		("p10", "application/pkcs10"),
	P12		("p12", "application/x-pkcs12"),
	P7B		("p7b", "application/x-pkcs7-certificates"),
	P7C		("p7c", "application/x-pkcs7-mime"),
	P7M		("p7m", "application/x-pkcs7-mime"),
	P7R		("p7r", "application/x-pkcs7-certreqresp"),
	P7S		("p7s", "application/x-pkcs7-signature"),
	PBM		("pbm", "image/x-portable-bitmap"),
	PDF		("pdf", "application/pdf"),
	PFX		("pfx", "application/x-pkcs12"),
	PGM		("pgm", "image/x-portable-graymap"),
	PKO		("pko", "application/ynd.ms-pkipko"),
	PMA		("pma", "application/x-perfmon"),
	PMC		("pmc", "application/x-perfmon"),
	PML		("pml", "application/x-perfmon"),
	PMR		("pmr", "application/x-perfmon"),
	PMW		("pmw", "application/x-perfmon"),
	PNG		("png", "image/png"),
	PNM		("pnm", "image/x-portable-anymap"),
	POT		("pot", "application/vnd.ms-powerpoint"),
	POTM	("potm", "application/vnd.ms-powerpoint.presentation.macroEnabled.12"),
	POTX	("potx", "application/vnd.openxmlformats-officedocument.presentationml.template"),
	PPA		("ppa", "application/vnd.ms-powerpoint"),
	PPAM	("ppam", "application/vnd.ms-powerpoint.addin.macroEnabled.12"),
	PPM		("ppm", "image/x-portable-pixmap"),
	PPS		("pps", "application/vnd.ms-powerpoint"),
	PPSM	("ppsm", "application/vnd.ms-powerpoint.slideshow.macroEnabled.12"),
	PPSX	("ppsx", "application/vnd.openxmlformats-officedocument.presentationml.slideshow"),
	PPT		("ppt", "application/vnd.ms-powerpoint"),
	PPTM	("pptm", "application/vnd.ms-powerpoint.presentation.macroEnabled.12"),
	PPTX	("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"),
	PRF		("prf", "application/pics-rules"),
	PS		("ps", "application/postscript"),
	PUB		("pub", "application/x-mspublisher"),
	QT		("qt", "video/quicktime"),
	RA		("ra", "audio/x-pn-realaudio"),
	RAM		("ram", "audio/x-pn-realaudio"),
	RAS		("ras", "image/x-cmu-raster"),
	RGB		("rgb", "image/x-rgb"),
	RMI		("rmi", "audio/mid"),
	ROFF	("roff", "application/x-troff"),
	RTF		("rtf", "application/rtf"),
	RTX		("rtx", "text/richtext"),
	SCD		("scd", "application/x-msschedule"),
	SCT		("sct", "text/scriptlet"),
	SETPAY	("setpay", "application/set-payment-initiation"),
	SETREG	("setreg", "application/set-registration-initiation"),
	SH		("sh", "application/x-sh"),
	SHAR	("shar", "application/x-shar"),
	SIT		("sit", "application/x-stuffit"),
	SND		("snd", "audio/basic"),
	SPC		("spc", "application/x-pkcs7-certificates"),
	SPL		("spl", "application/futuresplash"),
	SRC		("src", "application/x-wais-source"),
	SST		("sst", "application/vnd.ms-pkicertstore"),
	STL		("stl", "application/vnd.ms-pkistl"),
	STM		("stm", "text/html"),
	SV4CPIO	("sv4cpio", "application/x-sv4cpio"),
	SV4CRC	("sv4crc", "application/x-sv4crc"),
	SVG		("svg", "image/svg+xml"),
	SWF		("swf", "application/x-shockwave-flash"),
	T		("t", "application/x-troff"),
	TAR		("tar", "application/x-tar"),
	TCL		("tcl", "application/x-tcl"),
	TEX		("tex", "application/x-tex"),
	TEXI	("texi", "application/x-texinfo"),
	TEXINFO	("texinfo", "application/x-texinfo"),
	TEXT_323("323", "text/h323"),
	TGZ		("tgz", "application/x-compressed"),
	TIF		("tif", "image/tiff"),
	TIFF	("tiff", "image/tiff"),
	TR		("tr", "application/x-troff"),
	TRM		("trm", "application/x-msterminal"),
	TSV		("tsv", "text/tab-separated-values"),
	TXT		("txt", "text/plain"),
	ULS		("uls", "text/iuls"),
	URL		("url", "text/x-url"),
	USTAR	("ustar", "application/x-ustar"),
	VCF		("vcf", "text/x-vcard"),
	VRML	("vrml", "x-world/x-vrml"),
	WAV		("wav", "audio/x-wav"),
	WCM		("wcm", "application/vnd.ms-works"),
	WDB		("wdb", "application/vnd.ms-works"),
	WKS		("wks", "application/vnd.ms-works"),
	WMF		("wmf", "application/x-msmetafile"),
	WPS		("wps", "application/vnd.ms-works"),
	WRI		("wri", "application/x-mswrite"),
	WRL		("wrl", "x-world/x-vrml"),
	WRZ		("wrz", "x-world/x-vrml"),
	XAF		("xaf", "x-world/x-vrml"),
	XBM		("xbm", "image/x-xbitmap"),
	XLA		("xla",	"application/vnd.ms-excel"),
	XLAM	("xlam", "application/vnd.ms-excel.addin.macroEnabled.12"),
	XLC		("xlc",	"application/vnd.ms-excel"),
	XLM		("xlm",	"application/vnd.ms-excel"),
	XLS		("xls",	"application/vnd.ms-excel"),
	XLSB	("xlsb", "application/vnd.ms-excel.sheet.binary.macroEnabled.12"),
	XLSM	("xlsm", "application/vnd.ms-excel.sheet.macroEnabled.12"),
	XLSX	("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
	XLT		("xlt",	"application/vnd.ms-excel"),
	XLTM	("xltm", "application/vnd.ms-excel.template.macroEnabled.12"),
	XLTX	("xltx", "application/vnd.openxmlformats-officedocument.spreadsheetml.template"),
	XLW		("xlw",	"application/vnd.ms-excel"),
	XOF		("xof",	"x-world/x-vrml"),
	XPM		("xpm",	"image/x-xpixmap"),
	XWD		("xwd",	"image/x-xwindowdump"),
	Z		("z",	"application/x-compress"),
	ZIP		("zip",	"application/zip"),
	JOSN 	("json", "application/json");
	
	private final String extension;
	private final String mime;
	private MediaType mediaType;

	private MediaTypes(String extension, String mime) {
		this.extension = extension;
		this.mime = mime;
		this.mediaType = MediaType.valueOf(mime);
	}
	
	public String getExtension() {
		return extension;
	}
	
	public String getMime() {
		return mime;
	}
	
	public MediaType getMediaType() {
		return mediaType;
	}
	
	public boolean isImage(){		
		return mime.startsWith("image");
	}
	
	public static MediaTypes fromExtension(String extension) {
		return Arrays.stream(MediaTypes.values())
						.filter(types -> types.getExtension().equalsIgnoreCase(extension))
						.findAny()
						.orElse(DEFAULT);
	}
	
	public static MediaTypes fromFileName(String fileName) {
		
		String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);
		
		return fromExtension(fileExtension);
	}
	
	public static MediaTypes fromMime(String mime) {
		return Arrays.stream(MediaTypes.values())
						.filter(types -> types.getMime().equalsIgnoreCase(mime))
						.findAny()
						.orElse(DEFAULT);
	}
	
	
	public static boolean isImageFromExtension(String mime)
	{
		MediaTypes c = fromExtension(mime);
		
		return c.mime.startsWith("image");
	}
	
	public static boolean isAudioFromExtension(String extension)
	{
		MediaTypes c = fromExtension(extension);
		
		return c.mime.startsWith("audio");
	}
	
	public static boolean isVideoFromExtension(String s)
	{
		MediaTypes c = fromExtension(s);
		
		return c.mime.startsWith("video");
	}
	
	public static boolean isTextFromExtension(String s)
	{
		MediaTypes c = fromExtension(s);
		
		return c.mime.startsWith("text");
	}
	
	public static String getMainTypeFromExtension(String s)
	{
		MediaTypes c = fromExtension(s);
		
		String mime = c.mime;
		return mime.substring(0, mime.indexOf("/"));
	}

}
