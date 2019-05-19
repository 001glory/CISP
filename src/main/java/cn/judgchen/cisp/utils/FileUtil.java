package cn.judgchen.cisp.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
*@ClassName: FileUtil
*@Description: 
*@author YY 
*@date 2016年8月26日  上午10:26:17
*@version 1.0
*/
public class FileUtil {

	/**
	 * 获取文件类型
	 * @param fileName
	 * @return
	 */
	public static String getFileExtName(String fileName) {
        if (fileName!=null ) {
            int i = fileName.lastIndexOf('.');
            if (i>-1) {
                return fileName.substring(i+1).toLowerCase();
            }else {
                return null;
            }
        }else {
            return null;
        }
    }
	
	/**
	 * 上传文件
	 * @param fileBytes
	 * @param filePath
	 * @param fileName
	 * @throws Exception
	 */
	public static void uploadFile(byte[] fileBytes, String filePath, String fileName) throws Exception {	
		File targetFile = new File(filePath);  
        if(!targetFile.exists()){    
            targetFile.mkdirs();    
        }       
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(fileBytes);
        out.flush();
        out.close();
	}

    //图片存放根目录下的子目录
    @Value("${file.sonPath}")
    private static String SON_PATH;
    //图片存放根路径
    @Value("${file.path}")
    private static String ROOT_PATH;

    public static String fileSave(MultipartFile file){
        String localPath = ROOT_PATH+SON_PATH;
        //定义文件保存的本地路径
        if(!new File(localPath).exists()){
            new File(localPath).mkdirs();
        }
        //定义 文件名
        String filename=null;
        //判断是否为空
        if(!file.isEmpty()){
            //生成uuid作为文件名称
            String uuid = UUID.randomUUID().toString();
            //获得文件类型（可以判断如果不是图片，禁止上传）
            String contentType=file.getContentType();
            //获得文件后缀名
            String suffixName=contentType.substring(contentType.indexOf("/")+1);
            //得到文件名
            filename=uuid+"."+suffixName;
            //文件保存路径
            try {
                //将文件转移到指定位置
                file.transferTo(new File(localPath+filename));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return localPath+filename;
    }

}