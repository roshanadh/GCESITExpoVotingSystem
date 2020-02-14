package np.edu.gces.itexpo;

import java.io.*;
import java.net.*;

public class InternetChecker {
	public static boolean isAvailable() {
		try {
			return InetAddress.getByName("google.com").isReachable(200) ? true : false;
		} catch (Exception e) {
			return false;
		}
	}
}
