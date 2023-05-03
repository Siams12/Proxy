import java.io.* ;
import java.net.* ;
import java.util.* ;

		final class HttpRequest
		{
			final static String CRLF = "\r\n";
			Socket socket;
			private ArrayList<String> headers = new ArrayList<String>();

			// Constructor
			public HttpRequest(Socket socket) throws Exception 
			{
				this.socket = socket;
			}
			
			//Pretty much just keeps track of our headers
			public void getMessages() {
				try {
				
					BufferedReader lines = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					while (true) {
					String requestLine = lines.readLine();
					System.out.println("this is our requestline " + requestLine);
					if(requestLine != null && !requestLine.isEmpty()) {
						headers.add(requestLine);
					}
					else {
						break;
					}
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			public Socket processRequests() {
				//Need to create a new connection with server.
				String host = null;
				System.out.println("We are in process requests");
				//Find and get the host for our new connection
				for (int i = 0; i < headers.size(); i++) {
					System.out.println(headers.get(i));
					if (headers.get(i).contains("Host:")){
						host = headers.get(i).split(":")[1].trim();
					}
				}
					try {
						// initialize new connection. NEEDS TO BE ON PORT 80 NOT RANDOM PORT.
						Socket test = new Socket(host, 80);
						PrintWriter print = new PrintWriter(test.getOutputStream());
						//send all headers back.
						for (int i =0; i < headers.size(); i++) {
							print.println(headers.get(i));
						}
						print.println();
						print.flush();
						
						return test;
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				}
		}
	