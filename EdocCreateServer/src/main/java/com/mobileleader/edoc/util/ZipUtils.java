package com.mobileleader.edoc.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * [ ZipUtil ]
 * <p/>
 * ZIP 함수 유틸 Class<br>
 * <p/>
 * <pre>
 * 1. zip 함수
 * 2. unzip 함수
 * </pre>
 *
 * @author	(주)인지소프트 조래훈
 * @version	2.0
 * @since	2017.09.22
 */
public class ZipUtils {

	/**
	 * ZIP 파일 압축 해제(WithJavaUtilZip)
	 *
	 * @param	inPath		압축파일경로
	 * @param	outPath		압축해제파일경로
	 * @return	void
	 * @throws Exception 
	 * @throws Exception 
	 */
	public static void unzip(String inPath, String outPath) throws Exception{
		ZipFile zipFile = null;
		ZipEntry zipEntry = null;
		File file = null;
		InputStream is = null;
		FileOutputStream fos = null;
		byte[] bytes = null;
		
		try {
			FileUtils.makeFolder(outPath);
			
			zipFile = new ZipFile(inPath);
	
			Enumeration<?> enu = zipFile.entries();
			while(enu.hasMoreElements()) {
				zipEntry = (ZipEntry)enu.nextElement();
				String name = zipEntry.getName();
				if("/".equals(File.separator))
					name = name.replace("\\", File.separator);
				else
					name = name.replace("/", File.separator);
	
				if(!outPath.endsWith(File.separator))
					outPath += File.separator;
	
				file = new File(outPath + name);
				if(name.endsWith(File.separator)){
					file.mkdirs();
					continue;
				} else {
					String parentDir = file.getAbsolutePath().substring(
							0, file.getAbsolutePath().lastIndexOf(File.separator) + 1);
					File parent = new File(parentDir);
					parent.mkdirs();
					parent = null;
				}
	
				is = zipFile.getInputStream(zipEntry);
				fos = new FileOutputStream(file);
	
				bytes = new byte[1024];
				int length;
				while((length = is.read(bytes)) >= 0) {
					fos.write(bytes, 0, length);
				}
	
				is.close();
				fos.close();
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if(is != null) is.close();
			if(fos != null) fos.close();
			zipFile.close();
		}
	}

//	public static void unzipWithNetSfJazz(String inPath, String outPath) throws Exception
//	{
//		try
//		{
//			FileUtil.makeFolder(outPath);
//
//			ZipInputStream zis = new ZipInputStream(new FileInputStream(inPath));
//			net.sf.jazzlib.ZipEntry zipEntry = null;
//			while((zipEntry = zis.getNextEntry()) != null)
//			{
//				String name = zipEntry.getName();
//				if("/".equals(File.separator))
//					name = name.replace("\\", File.separator);
//				else
//					name = name.replace("/", File.separator);
//
//				if(!outPath.endsWith(File.separator))
//					outPath += File.separator;
//
//				System.out.println(outPath + name + " 파일이 들어 있습니다.");
//
//				File file = new File(outPath + name);
//				if(name.endsWith(File.separator))
//				{
//					file.mkdirs();
//					continue;
//				}
//				else
//				{
//					String parentDir = file.getAbsolutePath().substring(
//							0, file.getAbsolutePath().lastIndexOf(File.separator) + 1);
//					File parent = new File(parentDir);
//					parent.mkdirs();
//					parent = null;
//				}
//
//				FileOutputStream fos = new FileOutputStream(file);
//
//				byte[] bytes = new byte[1024];
//				int length;
//				while((length = zis.read(bytes)) >= 0)
//				{
//					fos.write(bytes, 0, length);
//				}
//
//				fos.close();
//			}
//
//			zis.close();
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//			throw e;
//		}
//		finally
//		{
//			// Nothing to do...
//		}
//	}

//	public static void unzip(String inPath, String outPath) throws Exception
//	{
//		logger.debug("ZipUtil.unzip starts...");
//
//		ZipFile zipFile = null;
//		ZipEntry zipEntry = null;
//		File file = null;
//		InputStream is = null;
//		FileOutputStream fos = null;
//		byte[] bytes = null;
//
//		try
//		{
//			FileUtil.makeFolder(outPath);
//			logger.debug("inPath = " + inPath);
//			logger.debug("outPath = " + outPath);
//
////			zipFile = new org.apache.tools.zip.ZipFile(inPath);
//			zipFile = new org.apache.tools.zip.ZipFile(inPath, "EUC-KR");
//
//			Enumeration<?> enu = zipFile.getEntries();
//			while(enu.hasMoreElements())
//			{
//				zipEntry = (ZipEntry)enu.nextElement();
//				String name = zipEntry.getName();
//				if("/".equals(File.separator))
//					name = name.replace("\\", File.separator);
//				else
//					name = name.replace("/", File.separator);
//				logger.debug("Entry name = " + name);
//
//				if(!outPath.endsWith(File.separator))
//					outPath += File.separator;
//				logger.debug("outPath = " + outPath);
//
//				logger.debug("File path = " + outPath + name);
//				file = new File(outPath + name);
//				if(name.endsWith(File.separator))
//				{
//					file.mkdirs();
//					continue;
//				}
//				else
//				{
//					String parentDir = file.getAbsolutePath().substring(
//							0, file.getAbsolutePath().lastIndexOf(File.separator) + 1);
//					File parent = new File(parentDir);
//					parent.mkdirs();
//					parent = null;
//				}
//
//				is = zipFile.getInputStream(zipEntry);
//				fos = new FileOutputStream(file);
//
//				bytes = new byte[1024];
//				int length;
//				while((length = is.read(bytes)) >= 0)
//				{
//					fos.write(bytes, 0, length);
//				}
//
//				is.close();
//				fos.close();
//			}
//
//			zipFile.close();
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//			throw e;
//		}
//		finally
//		{
//			zipFile = null;
//			zipEntry = null;
//			file = null;
//			is = null;
//			fos = null;
//			bytes = null;
//		}
//	}

//	public static void zip(String sourcePath, String targetPath) throws Exception
//	{
//		File sourceFile = new File(sourcePath);
//		if(!sourceFile.isFile() && !sourceFile.isDirectory())
//		{
//			logger.error("압축 대상 파일이나 폴더를 찾을 수가 없습니다.");
//			throw new Exception("압축 대상 파일이나 폴더를 찾을 수가 없습니다.");
//		}
//
//		if(!StringUtils.substringAfterLast(targetPath, ".").equalsIgnoreCase("zip"))
//		{
//			logger.error("압축 저장 파일명의 확장자가 \"zip\"이 아닙니다.");
//			throw new Exception("압축 저장 파일명의 확장자가 \"zip\"이 아닙니다.");
//		}
//
//		File targetFile = new File(targetPath);
//		FileOutputStream fos = null;
//		BufferedOutputStream bos = null;
//		java.util.zip.ZipOutputStream zos = null;
//
//		try
//		{
//			fos = new FileOutputStream(targetFile);
//			bos = new BufferedOutputStream(fos);
//			zos = new java.util.zip.ZipOutputStream(bos);
//			logger.debug("Default Charset = " + Charset.defaultCharset());
//			zos.setLevel(8);	// 압축 레벨 : 최대 압축률은 9, 디폴트 8
//
//			zipEntry(sourceFile, sourceFile.getPath(), zos, targetFile);
//
//			zos.finish();
//		}
//		finally
//		{
//			if(zos != null) zos.close();
//			if(bos != null) bos.close();
//			if(fos != null) fos.close();
//		}
//	}

//	private static void zipEntry(File sourceFile, String basePath, java.util.zip.ZipOutputStream zos, File targetFile) throws Exception
//	{
//		if(sourceFile.isDirectory())
//		{
//			if(sourceFile.getName().equalsIgnoreCase(".metadata"))
//				return;
//
//			File[] fileArray = sourceFile.listFiles();
//			for(int i = 0; i < fileArray.length; i++)
//			{
//				if(fileArray[i].getAbsolutePath().equals(targetFile.getAbsolutePath()))
//					continue;
//
//				zipEntry(fileArray[i], basePath, zos, targetFile);
//			}
//		}
//		else
//		{
//			BufferedInputStream bis = null;
//			try
//			{
//				String sourceFilePath = sourceFile.getPath();
//				String zipEntryName = sourceFilePath.substring(basePath.length() + 1);
//
//				bis = new BufferedInputStream(new FileInputStream(sourceFile));
//				java.util.zip.ZipEntry zentry = new ZipEntry(zipEntryName);
//				zentry.setTime(sourceFile.lastModified());
//				zos.putNextEntry(zentry);
//
//				byte[] buffer = new byte[2048];
//				int cnt = 0;
//				while((cnt = bis.read(buffer, 0, 2048)) > 0)
//					zos.write(buffer, 0, cnt);
//				zos.closeEntry();
//			}
//			finally
//			{
//				if(bis != null) bis.close();
//			}
//		}
//	}
}