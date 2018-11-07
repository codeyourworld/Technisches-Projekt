package malen.buisnesslogic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IOService {
	
	public static boolean writeObject (Object o, String fileName){
		
		try (
				FileOutputStream fos = new FileOutputStream(fileName);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
		){
			oos.writeObject(o);
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}


	
	public static Object readObject (String fileName){
		Object o = null;
		
		try (
				FileInputStream fis = new FileInputStream(fileName);
				ObjectInputStream ois = new ObjectInputStream(fis);
		){
			
			o = ois.readObject();
			
		} catch (Exception e) {
//			e.printStackTrace();
		}
		
		return o;
	}

	

	public static boolean writeValues (Object o, String fileName){
		
		try (
				FileOutputStream fos = new FileOutputStream(fileName);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
		){
			
			oos.writeObject(o);
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public static boolean writeLine (String str, String filename){
		
		try (
				FileWriter fw = new FileWriter(filename, true);
				BufferedWriter bw = new BufferedWriter(fw);
		){
			
			bw.write(str);
			bw.newLine();
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
		
		return true;
	}

	public static StringBuffer readFile (String filename) {
	
		try (
				FileReader fr = new FileReader(filename);
				BufferedReader br = new BufferedReader(fr);
		){
			StringBuffer file = new StringBuffer();
			String tmp = "";
			tmp = br.readLine();
			while (tmp != null) {
				file.append(tmp + "\n");
				tmp = br.readLine();
			} 
			return file;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
