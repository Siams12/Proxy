import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class HttpResponse {
	private int errorCode;
	private byte[] content;
	ArrayList<String> headers = new ArrayList<String>();
	
	//An http response is code 
		//then headers
		//then content.
	
	//Jay said we'd need this don't get rid of it. Also make setters and getters for headers and content.
	public int getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(int code) {
		errorCode = code;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public void setArrayList(ArrayList<String> list) {
		headers = list;
	}
	
	//I think there is an error in this code. 
	//It displays the site but not entirely correctly. There are some extra headers. 
	public void generateHTTPResponse(Socket clientSock) {
		try {
			DataOutputStream output = new DataOutputStream(clientSock.getOutputStream());
			output.write(errorCode);
			//Writes headers 
			//Seems to not be working. Correctly.
			for (int i = 0; i < headers.size(); i++) {
				output.writeUTF(headers.get(i));
			}
			//New line for http.
			output.writeUTF("\n");
			output.write(content);
			//Flush the output before finishing.
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
