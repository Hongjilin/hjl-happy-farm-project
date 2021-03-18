package com.zqsoft.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.zqsoft.bean.CropBean;
import com.zqsoft.bean.UserBean;


public class FileUtils {
	 private String dirPath ;
     private String filename ;
     
	/**
	 * 读取文件，把文件中的每行记录解析成一个字符串，多行记录
			使用集合 List 存放
	 * @param filenName ：完整的文件路径名（包括文件路径）
	 * @return
	 */
	public static List<String> readFile(String filenName){
		List<String> ListData =new ArrayList<String>();
		 //生成字符缓冲流对象
        BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(filenName)));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		  String str="";
		  
		  try {
			while ((str = reader.readLine()) != null) {
				  ListData.add(str);
			  }
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ListData;
	}
	
	/**
	 *  将 str 字符串的内容覆盖到文件中。
	 * @param fileName：完整的文件路径名（包括文件路径）
	 * @param str：需要保存字符串
	 */
	public static void writeFile(String fileName, String str){
		File file=new File(fileName);//创建文件对象
		//判断是否有这个文件 否则创建
	        if (!file.exists()) {
	            try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
	        
		Writer out = null;
		try {
			out = new FileWriter(file);
			out.write(str);
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	

	
	/*

  private boolean creatDir() {
	        if (null != dirPath) {
	            File path = new File(dirPath);
	            if (path.exists()) {
	                return true;
	            }
	            if (true == path.mkdirs() ) {
	                return true;
	            }
	        }
	        return false;
	    }

	    private void creatFile() {
	        if (null != filename) {
	            File file = new File(dirPath, filename);
	            if (false == file.exists()) {
	                try {
	                    file.createNewFile();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }

	
	    private void writeObject(String path, String filename, LinkedList<CropBean> msg) {
	        File file = new File(path, filename);
			//判断是否有这个文件 否则创建
		        if (!file.exists()) {
		            try {
						file.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
		        }
	        
	        if (false == file.isFile()) {
	            return ;
	        }
	        try {
	            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file,false));
	            oos.writeObject(msg);
	            oos.flush();
	            oos.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    @SuppressWarnings("unchecked")
	    private LinkedList<CropBean> readObject(String path, String filename) {
	        File file = new File(path, filename);
	        ObjectInputStream ois = null;
	        LinkedList<CropBean> msgAll = null;

	        try {
	            ois = new ObjectInputStream(new FileInputStream(file));
	            try {
	                msgAll = (LinkedList<CropBean>)ois.readObject();

	            } catch (ClassNotFoundException e) {
	                e.printStackTrace();
	            }
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                ois.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }

	        return msgAll;
	    }
	
	*/
}
