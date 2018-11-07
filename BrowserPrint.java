


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
public class BrowserPrint
{
	public static void main(String[] args) throws Exception {
		int i = Integer.parseInt(args[0]);
		ServerSocket demo = new ServerSocket(i); /*Creating a new unbound socket dmo*/
		while(true) {
			byte host[] = new byte[100];
			byte request[]=new byte[65535];
			Socket cli = demo.accept();
			int Length = cli.getInputStream().read(request,0,65535);
			 doParse(cli, Length, request, host);
		}
	}
	
	private static void doParse(Socket cli, int Length, byte[] request, byte[] host) throws Exception {
		int z = 0;
		System.out.print(new String(request, "UTF-8"));
		System.out.print("\n");
		for(int p = 0;p<Length;p++) {
			if((char)request[p]=='H' && (char)request[p+1]=='o' && (char)request[p+2]=='s' && (char)request[p+3]=='t') {
				p = p+6;
				while ((char)request[p]!='\n') {
					host[z] = request[p];
					z++;
					p++;
				}
				dns(cli, host);
//				cli.close();
			}	
		}
	}
	public static void dns(Socket cli, byte[] host) throws Exception {
		try {
		InetAddress[] inetAddresses = InetAddress.getAllByName(new String(host, "UTF-8").trim());
//		doHTTP(inetAddresses[0]);
		 cli.getOutputStream().write("HTTP/1.0 200 OK\r\n".getBytes("UTF-8"));
		 cli.getOutputStream().write("Content-Type: text/html\r\n".getBytes("UTF-8"));
		 cli.getOutputStream().write("Content-Length: 10000\r\n".getBytes("UTF-8"));
		 cli.getOutputStream().write("\r\n".getBytes("UTF-8"));
		 cli.getOutputStream().write("<TITLE>Hello</TITLE>".getBytes("UTF-8"));
		 cli.getOutputStream().write("<p>Webpage</p>".getBytes("UTF-8"));
		 cli.close();
		System.out.println(inetAddresses[0]);
		}
		catch(Exception e) {
			cli.getOutputStream().write("HTTP/1.0 400 Bad Request\r\n".getBytes("UTF-8"));
			cli.getOutputStream().write("Content-Type: text/html\r\n".getBytes("UTF-8"));
			cli.getOutputStream().write("Content-Length: 10000\r\n".getBytes("UTF-8"));
			cli.getOutputStream().write("\r\n".getBytes("UTF-8"));
			cli.getOutputStream().write("<TITLE>Does Not Exist</TITLE>".getBytes("UTF-8"));
			cli.getOutputStream().write("<p>You mad bruh?</p>".getBytes("UTF-8"));

			cli.close();

		}	
	}
	
//	public static void doHTTP(InetAddress inetAddresses, int port) throws UnknownHostException, IOException {
//		Socket toWeb = new Socket(inetAddresses,port);
//		
//		
//	}
	
	
	
}
