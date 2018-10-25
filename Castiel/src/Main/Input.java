package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Input {

	public static void main(String[] args) throws Exception {
		
		BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(System.in));           
            while (true) {

                System.out.print("Enter something : ");
                String input = br.readLine();
                
        }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
	}
}
