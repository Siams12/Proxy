import java.io.* ;
import java.net.* ;
import java.util.* ;

public class ProxyServer {
	
	//This class should be our client and server.
	//In the first exercise. HTTPRequest acted as our client. Webserver as our server.
	//HttpResponse generates Response.
	//HttpRequest generates request. 
	
	
	//How program works:
	//Client connects to server. BOTH IN THIS CLASS.
	//Generates an HttpRequest using HttpRequestClass
	//Sends it using this class
	//Server receives it in this class
	//Gets HTTPResponse. 
	//Sends it back to client. 
	//End.
	
	
	public static void main(String[] args) {
		ProxyServer proxy = new ProxyServer();
		proxy.go();
}
	
	public void go() {
		int port = 6789;
		ServerSocket listenSocket = null;
		
		try {
			 listenSocket = new ServerSocket(port);
			 System.out.println(listenSocket.getInetAddress().getLocalHost());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Socket client = null;
		while (true) {
			try {
				client = listenSocket.accept();
				System.out.println("We have a client");
				HttpRequest request = null;
				try {
					request = new HttpRequest(client);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.getMessages();
				Socket requestSocket = request.processRequests();
				HttpResponse response = setResponseContent(requestSocket);
				//Generate http response and display it to user.
				//ALMOST gets example website to work.
				response.generateHTTPResponse(client);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	}
	//Sets fields in response 
	//Probably could have put this in the httpresponse class.
	//Now that we have our content out of httprequest and in our new socket we set those values here.
	public HttpResponse setResponseContent(Socket ourSocket) {
		try {
			//Create readerss
			HttpResponse response = new HttpResponse();
			//recommended to use inputstream
			DataInputStream reader = new DataInputStream(ourSocket.getInputStream());
			String line = reader.readLine();
			//Set error code.
			response.setErrorCode(Integer.parseInt(line.split(" ")[1]));
			ArrayList<String> headers = new ArrayList<String>();
			//Just erad lines of headers until null or we get an empty line.
			while(true) {
				line = reader.readLine();
				System.out.println("Our response lines" + line);
				//If line is null or our blank line break
				if (line == null || line.equals("")) {
					break;
				}
				//Add that line.
				headers.add(line);
	}
			//Set our response variables.
			response.setArrayList(headers);
			response.setContent(reader.readAllBytes());		
			//return response to use in our go
			return response;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

