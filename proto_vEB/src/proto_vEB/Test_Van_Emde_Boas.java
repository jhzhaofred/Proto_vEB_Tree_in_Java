package proto_vEB;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
public class Test_Van_Emde_Boas {
	 public static void main(String[] args) throws IOException {
			proto_vEB root = new proto_vEB(256);
	        try {
	            File f = new File("src/TestInput.txt");
	            BufferedReader buffer = new BufferedReader(new FileReader(f));
	            String readLine = "";
	            while ((readLine = buffer.readLine()) != null) {
	            		if(readLine.charAt(0)=='I') {
	            			root.insert(Integer.parseInt(readLine.substring(2, readLine.length())));
	            		} else if(readLine.charAt(0)=='D') {
	            			root.delete(Integer.parseInt(readLine.substring(2, readLine.length())));
	            		} else if(readLine.charAt(0)=='S') {
	            			Integer j = root.successor(Integer.parseInt(readLine.substring(2, readLine.length())));
	            			System.out.println(j);
	            		} 
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
}
