package np.edu.gces.itexpo;

public class BackgroundVoterWorker implements Runnable {
	@Override
	public void run() {
		while(true) {
			try {
				if(!InternetChecker.isAvailable()) {
					if(VotingSystem.isOn()) {
						VotingSystem.turnOff();
					}
				} else {
					if(!VotingSystem.isOn()) {
						VotingSystem.turnOn();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
