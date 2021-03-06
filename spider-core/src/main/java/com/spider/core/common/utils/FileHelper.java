package com.spider.core.common.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

public class FileHelper {
	private static Logger log = LoggerFactory.getLogger(FileHelper.class);

	public static boolean uploadFile(InputStream is, String filePath) {

		boolean retCode = false;
		byte[] buffer = new byte[1024];
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(new File(filePath));

			int n = -1;
			while ((n = is.read(buffer, 0, buffer.length)) != -1) {
				fos.write(buffer, 0, n);
			}

			retCode = true;
			log.debug("upload file success...");
		} catch (FileNotFoundException fnfe) {
			log.debug("fnfe:" + fnfe);
		} catch (IOException ioe) {
			log.debug("ioe:" + ioe);
		} finally {
			if (fos != null) {
				try {
					fos.close();
					fos = null;
				} catch (IOException e) {
					log.error(e.getMessage());
				}
			}
			if (is != null) {
				try {
					is.close();
					is = null;
				} catch (IOException e) {
					log.error(e.getMessage());
				}

			}
		}

		return retCode;
	}

 

	public static String getFileContent(String fileName) {

		BufferedReader reader = null;
		StringBuilder fileContent = new StringBuilder();
		try {
			File f = new File(fileName);

			reader = new BufferedReader(new FileReader(f));
			String line = "";
			while ((line = reader.readLine()) != null) {
				fileContent.append(line);
				fileContent.append("\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
					reader = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileContent.toString();
	}

	public static String getFileContent(InputStream is) {

		BufferedReader reader = null;
		StringBuilder fileContent = new StringBuilder();
		try {
			reader = new BufferedReader(new InputStreamReader(is));
			String line = "";
			while ((line = reader.readLine()) != null) {
				fileContent.append(line);
				fileContent.append("\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
					reader = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileContent.toString();

	}

	public static boolean setFileContent(String path, String content) {
		boolean flag = false;
		DataOutputStream dos = null;
		try {
			if (content != null && content.length() >= 0) {
				byte abyte[] = content.getBytes();
				dos = new DataOutputStream(new FileOutputStream(path));
				dos.write(abyte, 0, abyte.length);
				dos.flush();

				flag = true;
			}
		} catch (FileNotFoundException e) {
			log.error("fnfe:" + e);
		} catch (IOException e) {
			log.error("ioe:" + e);
		} finally {
			if (dos != null) {
				try {
					dos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				dos = null;
			}
		}
		return flag;
	}

	public static String getFileExt(String fileName) {
		if (fileName == null)
			return "";

		String ext = "";
		int lastIndex = fileName.lastIndexOf(".");
		if (lastIndex >= 0) {
			ext = fileName.substring(lastIndex + 1).toLowerCase();
		}

		return ext;
	}

	/**
	 * ????????????
	 */
	private static String defaultCharset = "UTF-8";

	/**
	 * ????????????
	 * 
	 * @param files
	 * @param outFile
	 */
	public static void merge(String[] files, String outFile, boolean header) {
		try {
			FileOutputStream out = new FileOutputStream(outFile, true);
			OutputStreamWriter osw = new OutputStreamWriter(out, defaultCharset);
			BufferedWriter bw = new BufferedWriter(osw);

			// ????????????
			for (int i = 0; i < files.length; i++) {
				File f = new File(files[i]);
				if (f.exists()) {
					InputStreamReader read = new InputStreamReader(
							new FileInputStream(f), defaultCharset);
					BufferedReader br = new BufferedReader(read);

					// ?????????
					String line = br.readLine();
					int lines = 0;
					while (line != null) {
						// ??????
						if (lines != 0 || !header) {
							bw.write(line + "\r\n");
						}

						lines++;

						line = br.readLine();
					}

					br.close();
					read.close();
				}
			}

			bw.close();
			osw.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void merge(String[] files, String outFile) {
		merge(files, outFile, false);
	}

	/**
	 * ????????????
	 * 
	 * @param path
	 */
	public static void delete(String path) {
		try {
			File f = new File(path);
			if (f.exists()) {
				f.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ??????????????????
	 * 
	 * @param path
	 *            ????????????
	 * @param charset
	 *            ????????????
	 * @return
	 */
	public static String read(String path, String charset) {
		String text = null;
		try {
			File f = new File(path);
			if (f.exists()) {
				text = "";
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(f), charset);
				BufferedReader br = new BufferedReader(read);
				String line = br.readLine();
				while (line != null) {
					text += line;
					line = br.readLine();
				}
				br.close();
				read.close();
			}
			f = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

	/**
	 * ??????????????????
	 * 
	 * @param path
	 *            ????????????
	 * @return
	 */
	public static String read(String path) {
		return read(path, defaultCharset);
	}

	/**
	 * ??????????????????????????????
	 * 
	 * @param path
	 * @param text
	 * @param charset
	 */
	public static void append(String path, String text, String charset) {
		// ?????????
		try {
			FileOutputStream out = new FileOutputStream(path, true);
			OutputStreamWriter osw = new OutputStreamWriter(out, charset);
			BufferedWriter bw = new BufferedWriter(osw);
			bw.write(text);
			bw.close();
			osw.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void append(String path, String text) {
		append(path, text, "UTF-8");
	}

	/**
	 * ?????????????????????????????????
	 * 
	 * @param path
	 * @param text
	 * @param charset
	 */
	public static void appendLine(String path, String text, String charset) {
		// ?????????
		append(path, text + "\r\n", charset);
	}

	public static void appendLine(String path, String text) {
		appendLine(path, text, "UTF-8");
	}

	/**
	 * ?????????????????????
	 * 
	 * @param path
	 *            ????????????
	 * @param text
	 *            ????????????
	 * @param charset
	 *            ??????
	 * @param mkdirs
	 *            ???????????????????????????
	 */
	public static void write(String path, String text, String charset,
			boolean mkdirs) {
		File f = new File(path);

		// ????????????????????????????????????
		if (mkdirs && !f.exists()) {
			File folder = new File(getFolderPath(path));
			folder.mkdirs();
			folder = null;
		}

		// ?????????
		try {
			FileOutputStream out = new FileOutputStream(path, false);
			OutputStreamWriter osw = new OutputStreamWriter(out, charset);
			BufferedWriter bw = new BufferedWriter(osw);
			bw.write(text);
			bw.close();
			osw.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			f = null;
		}

	}

	/**
	 * ???????????????
	 * 
	 * @param path
	 *            ????????????
	 * @param text
	 *            ????????????
	 * @param charset
	 *            ??????
	 */
	public static void write(String path, String text, String charset) {
		write(path, text, charset, true);
	}

	/**
	 * ???????????????
	 * 
	 * @param path
	 *            ????????????
	 * @param text
	 *            ????????????
	 */
	public static void write(String path, String text) {
		write(path, text, defaultCharset);
	}

	/**
	 * ?????????????????????????????????
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getCurrentPath(HttpServletRequest request) {
		String currentPath = request.getRequestURI();
		currentPath = currentPath.replace("\\", "/");

		int idxOfPath = currentPath.lastIndexOf("/");

		if (idxOfPath > 0) {
			currentPath = currentPath.substring(0, idxOfPath + 1);
		}

		currentPath = currentPath.replace(request.getContextPath(), "");
		currentPath = request.getRealPath(currentPath);

		return currentPath;
	}

	/**
	 * ???????????????????????????
	 * 
	 * @param request
	 * @return
	 */
	public static String getCurrentURL(HttpServletRequest request) {
		String result = request.getRequestURI();
		String queryString = request.getQueryString();

		if (queryString != null) {
			result += "?" + request.getQueryString();
		}

		return result;
	}

	/**
	 * ???????????????????????????
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFolderPath(String filePath) {
		String path = filePath;
		path = formatFilePath(path);

		int index = path.lastIndexOf("/");
		if (index > 0) {
			path = path.substring(0, index);
		}

		return path;
	}

	/**
	 * ??????URL???????????????
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFolderUrl(String filePath) {
		String path = filePath;
		path = formatFilePath(path);
		// ?????? http://
		if (path.indexOf("://") < 0) {
			path = path.replace(":/", "://");
		}

		int index = path.lastIndexOf("/");
		if (index > 0) {
			path = path.substring(0, index);
		}

		return path;
	}

	public static String getFileName(String filePath) {
		String path = filePath;
		path = formatFilePath(path);

		int index = path.lastIndexOf("/");
		if (index > 0) {
			path = path.substring(index + 1);
		}

		return path;
	}

	/**
	 * ????????????????????????
	 * 
	 * @param filePath
	 */
	public static void makeDirs(String filePath) {
		String path = filePath;
		path = getFolderPath(path);
		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdirs();
		}
	}

	/**
	 * ?????????????????????
	 * 
	 * @param filePath
	 * @return
	 */
	public static String formatFilePath(String filePath) {
		String path = filePath;
		path = path.replace("\\", "/");
		path = path.replace("//", "/");

		return path;
	}

	/**
	 * ??????????????????????????????
	 * 
	 * @param filePath
	 */
	public static void deleteAllFile(String filePath) {

		// ????????????????????????????????????????????????
		File file = new File(filePath);

		if (file.exists()) {

			File[] fileList = file.listFiles();

			if (fileList != null) {
				for (int i = 0; i < fileList.length; i++) {
					// ??????????????????????????????
					if (fileList[i].isFile()) {
						fileList[i].delete();
					}
				}
			}
		}
	}

	/**
	 * ??????????????????
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean exists(String filePath) {
		File f = new File(filePath);
		return f.exists();
	}

	/**
	 * ???????????????msg??????
	 * 
	 * @param fname
	 * @param msg
	 * @throws IOException
	 */
	public void writeFile(String fname, String msg) throws IOException {
		String filePath = fname;
		filePath = filePath.toString();
		File myFilePath = new File(filePath);
		if (!myFilePath.exists()) {
			myFilePath.createNewFile();
		}
		FileWriter fp = new FileWriter(fname, true);
		fp.write(msg);
		fp.close();
	}

	/**
	 * ????????????
	 * 
	 * @param fname
	 * @return
	 * @throws IOException
	 */
	public String readFile(String fname) throws IOException {
		FileReader fp = new FileReader(fname);
		File f = new File(fname);
		int lens = (int) f.length();
		char[] buf = new char[lens];
		int len = fp.read(buf);
		String msg = new String(buf, 0, len);
		fp.close();
		return msg;
	}

	/**
	 * ?????????????????????
	 * 
	 * @param fname
	 * @throws IOException
	 */
	public void readFileLine(String fname) throws IOException {
		FileReader reader = new FileReader(fname);
		BufferedReader br = new BufferedReader(reader);
		while ((br.readLine()) != null) {
			log.debug(br.readLine());
		}
		br.close();
		reader.close();
	}

	public void createBlankFile(String fname) throws IOException {
		try {
			File fn = new File(fname);
			fn.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ???????????????
	 * 
	 * @param folderPath
	 */
	public void createNewFolder(String folderPath) {
		try {
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ??????????????????????????????
	 *
	 * @param filePathAndName
	 * @param fileContent
	 */
	public void createNewFile(String filePathAndName, String fileContent) {

		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			FileWriter resultFile = new FileWriter(myFilePath);
			PrintWriter myFile = new PrintWriter(resultFile);
			String strContent = fileContent;
			myFile.println(strContent);
			resultFile.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * ????????????
	 *
	 * @param filePathAndName
	 */
	public void delFile(String filePathAndName) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myDelFile = new File(filePath);
			myDelFile.delete();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * ????????????
	 *
	 * @param folderPath
	 */
	public void delFolder(String folderPath) {
		try {
			delAllFile(folderPath);
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete();
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/**
	 * ??????????????????????????????
	 *
	 * @param path
	 */
	public void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);
				delFolder(path + "/" + tempList[i]);
			}
		}
	}

	/**
	 * ????????????
	 *
	 * @param oldPath
	 * @param newPath
	 */
	public void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldPath);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/**
	 * ????????????
	 *
	 * @param oldPath
	 * @param newPath
	 */
	public void copyFolder(String oldPath, String newPath) {

		try {
			(new File(newPath)).mkdirs();
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/**
	 * ????????????
	 *
	 * @param oldPath
	 * @param newPath
	 */
	public void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		delFile(oldPath);

	}

	/**
	 * ???????????????
	 *
	 * @param oldPath
	 * @param newPath
	 */
	public void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);
	}

	/**
	 * ??????????????????
	 *
	 * @param file
	 */
	public void clearTempFolder(File file) {
		if (file.isDirectory()) {
			if (!file.delete()) {
				File subs[] = file.listFiles();
				if (subs.length > 0) {
					for (int i = 0; i < subs.length; i++) {
						if (subs[i].isDirectory()) {
							clearTempFolder(subs[i]);
						} else {
							subs[i].delete();
							File parent = subs[i].getParentFile();
							if (parent.equals(file.getParentFile())) {
								return;
							} else {
								clearTempFolder(parent);
							}
						}
					}
				}
				file.delete();
			}
		}
	}

	// ????????????????????????
	public static void writes(String path, String content) {
		SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM");
		Date d = new Date();
		String fileName = sb.format(d);
		FileHelper.append(path + "" + fileName + ".txt", content + "\r\n", "gbk");
	}

	/**
	 * ???????????????
	 *
	 * @param fname
	 * @throws IOException
	 */
	public static BufferedReader readtxt(String path) {
		try {
			FileReader fileread = new FileReader(path);
			BufferedReader bufread = new BufferedReader(fileread);
			return bufread;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ????????????
	 *
	 * @param src
	 * @param dst
	 * @return
	 */
	public static boolean uploadCopy(File src, File dst, final int BUFFER_SIZE) {
		boolean result = false;
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(dst),
					BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * ??????????????????
	 *
	 * @param file
	 * @return
	 * @throws Exception
	 */
	// public static String getFileSize(File file) throws Exception{//??????????????????
	// double size=0;
	// if (file.exists()) {
	// FileInputStream fis = null;
	// fis = new FileInputStream(file);
	// size= fis.available();
	// } else {
	// return "0";
	// }
	// size = size/1024.00;
	// size=Double.parseDouble(size+"");
	// BigDecimal b = new BigDecimal(size);
	// double y1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	// java.text.DecimalFormat df =new java.text.DecimalFormat("#.00");
	// return df.format(y1);
	// }

	public static long getFileSizes(File f) throws Exception {// ??????????????????
		long s = 0;
		if (f.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(f);
			s = fis.available() % 1024 == 0 ? fis.available() / 1024 : fis
					.available() / 1024 + 1;

		} else {
			f.createNewFile();
			log.debug("???????????????");
		}
		return s;
	}

	// ??????
	public static long getForderFileSize(File f) throws Exception// ?????????????????????
	{
		long size = 0;
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getForderFileSize(flist[i]);
			} else {
				size = size + flist[i].length();
			}
		}
		return size;
	}

	/***
	 * ???????????????
	 *
	 * @param filename
	 * @return
	 */
	public static String getExcation(String filename) {
		filename = filename.substring(filename.indexOf("."));
		int rand = new Random().nextInt(10000);
		String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		filename = time + "" + rand + "" + filename;
		return filename;
	}


	public static String getOid(String exp) {
		StringBuffer reStr = new StringBuffer();
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.substring(uuid.lastIndexOf("-") + 1).toUpperCase();
		return exp.toUpperCase() + "" + uuid;
	}

	public static void read() throws IOException {
		BufferedReader in = new BufferedReader(new FileReader("f:vb.txt"));
		String line = null;
		int count = 0;
		while ((line = in.readLine()) != null) {
			log.debug(line);
			if (line.contains("false")) {
				count++;
			}
		}
		log.debug("count is " + count);

	}

	public String getPropertiesByItem(String item, String propertiesname)
			throws Exception {
		URI fileUri = this.getClass().getClassLoader()
				.getResource(propertiesname).toURI();
		File file = new File(fileUri);
		Properties pro = new Properties();
		pro.load(new FileInputStream(file));
		return pro.getProperty(item);
	}

	public static boolean isSuffix(String filename, String suffixArr) {
		// ????????????
		String suffix = "";
		try {
			suffix = filename.substring(filename.lastIndexOf(".") + 1);
			log.debug(suffix);
			if (suffixArr.indexOf(suffix) != -1) {
				return true;
			}
		} catch (Exception e) {
		}

		return false;
	}

	/***
	 * ???????????????
	 *
	 * @param filename
	 * @return
	 */
	public static String fileRename(String path, String filename) {
		// ????????????
		String offixName = "";
		// ????????????
		String suffix = "";
		// ??????????????????????????????
		if (exists(path + filename)) {
			offixName = filename.substring(0, filename.indexOf("."));
			suffix = filename.substring(filename.indexOf("."));
			int i = 0;
			while (true) {
				i++;
				filename = offixName + "(" + i + ")" + suffix;
				if (exists(path + filename)) {
					fileRename(path, filename);
				} else {
					break;
				}
			}
		}
		return filename;
	}

	/****
	 * ??????????????????????????????
	 *
	 * @param path
	 * @param fileName
	 * @param content
	 * @param encode
	 */
	public void writeFiletoDay(String path, String fileName, String content,
			String encode) {
		if (StringUtils.isEmpty(path)) {
			return;
		}
		if (StringUtils.isEmpty(fileName)) {
			return;
		}
		try {
			String yyyyMM = new SimpleDateFormat("yyyyMM")
					.format(new Date());
			String yyyyMMdd = new SimpleDateFormat("yyyyMMdd")
					.format(new Date());

			String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(new Date());
			String folderPath = path + "/" + yyyyMM + "/" + yyyyMMdd;
			// ???????????????
			createNewFolder(folderPath);
			// ????????????
			String filePath = folderPath + "/" + fileName.toUpperCase() + "_"
					+ yyyyMMdd + ".LOG";
			File myFilePath = new File(filePath);
			// ????????????????????????
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			// ????????????????????????
			appendLine(filePath, content, encode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 // ??????????????????  
    public static File makeImg(String imgUrl,String fileSavePath,String fileName) { 
        try {  
            // ?????????  
            BufferedInputStream in = new BufferedInputStream(new URL(imgUrl).openStream());  
            // ???????????????  
            int index = imgUrl.lastIndexOf("/");  
            String suffix = imgUrl.substring(imgUrl.lastIndexOf(".") + 1);
            String sName = imgUrl.substring(index+1, imgUrl.length());  
            System.out.println(sName + "======" + suffix);  
            // ????????????  
            File imgFile = new File(fileSavePath + "/" + fileName + "." + suffix);  
            // ????????????  
            BufferedOutputStream out = new BufferedOutputStream(  
                    new FileOutputStream(imgFile));  
            byte[] buf = new byte[2048];  
            int length = in.read(buf);  
            while (length != -1) {  
                out.write(buf, 0, length);  
                length = in.read(buf);  
            }  
            in.close();  
            out.close();  
            return imgFile;
        } catch (Exception e) {  
            e.printStackTrace();  
        }
        return null;
    }
    
	public static byte[] getFileByte(String networkImageUrl) throws IOException {
		URL url = new URL(networkImageUrl);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		//??????????????????10???
		conn.setConnectTimeout(10*1000);
		//?????????????????????????????????403??????
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		//???????????????
		InputStream inputStream = conn.getInputStream();
		//??????????????????
		return readInputStream(inputStream);

	}
	
	/** 
     * ?????????Url??????????????? 
     * @param urlStr 
     * @param fileName 
     * @param savePath 
     * @throws IOException 
     */  
    public static void  downLoadFromUrl(String urlStr,String savePath,String fileName) throws IOException{  
        URL url = new URL(urlStr);    
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
                //??????????????????10???  
        conn.setConnectTimeout(10*1000);  
        //?????????????????????????????????403??????  
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
  
        //???????????????  
        InputStream inputStream = conn.getInputStream();    
        //??????????????????  
        byte[] getData = readInputStream(inputStream);      
  
        //??????????????????  
        File saveDir = new File(savePath);  
        if(!saveDir.exists()){  
            saveDir.mkdir();  
        }  
        File file = new File(saveDir+File.separator+fileName);      
        FileOutputStream fos = new FileOutputStream(file);       
        fos.write(getData);   
        if(fos!=null){  
            fos.close();    
        }  
        if(inputStream!=null){  
            inputStream.close();  
        }
        
        System.out.println("info:"+url+" download success");   
  
    }  

    public static byte[] readInputStream(InputStream inStream) { 
    	try {
	    	ByteArrayOutputStream outStream = new ByteArrayOutputStream(); 
	    	//????????????Buffer????????? 
	    	byte[] buffer = new byte[1024]; 
	    	//??????????????????????????????????????????-1??????????????????????????? 
	    	int len = 0; 
	    	//????????????????????????buffer???????????????????????? 
	    	while( (len=inStream.read(buffer)) != -1 ){ 
	    	//???????????????buffer???????????????????????????????????????????????????????????????len????????????????????? 
	    	outStream.write(buffer, 0, len); 
	    	} 
	    	//??????????????? 
	    	inStream.close(); 
	    	//???outStream???????????????????????? 
	    	return outStream.toByteArray(); 
    	 } catch (IOException e) {
             e.printStackTrace();
         }
    	return null;
	}

    
    
    
  
    
    public static String getWeixinImagePathByServerId2(String serverId,String fileSavePath,String fileName,String accessToken){
    	if(!StringUtils.isEmpty(serverId)){
			log.info("?????????????????????serverId = " + serverId);
			try {
				//????????????
				String downImageUrl = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=" + accessToken + "&media_id=" + serverId;
				log.info("downImageUrl = " + downImageUrl);
			    FileHelper.makeDirs(fileSavePath);
			    log.info("????????????...");
				FileHelper.downLoadFromUrl(downImageUrl, fileSavePath, fileName);
				log.info("????????????");
				//???????????????OSS
				log.info("???????????????OSS?????????...");
				String petImage = "";
						//FileHelper.getOssNetWorkUrl(fileSavePath,fileName);
				//??????vipeta??????????????????
				log.info("?????????OSS??????????????????" + petImage);
				return petImage;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	return null;
    }
}
