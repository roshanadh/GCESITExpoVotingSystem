package np.edu.gces.itexpo;

public class BackgroundRegWorker implements Runnable {
	@Override
	public void run() {
		while(true) {
			try {
				if(!InternetChecker.isAvailable()) {
					if(VoterRegistration.isOn()) {
						VoterRegistration.turnOff();
					}
				} else {
					if(!VoterRegistration.isOn()) {
						VoterRegistration.turnOn();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
