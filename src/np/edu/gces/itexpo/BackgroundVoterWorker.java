package np.edu.gces.itexpo;

public class BackgroundVoterWorker implements Runnable {
	@Override
	public void run() {
		while(true) {
			try {
				if(!InternetChecker.isAvailable()) {
					if(VoterRegistration.isOn()) {
						VotingSystem.turnOff();
					}
				} else {
					if(!VoterRegistration.isOn()) {
						VotingSystem.turnOn();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
