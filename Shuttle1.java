import java.util.Random;

/*
 * Answer 2 shuttle
 */
public class Shuttle1 extends Thread {
	
	private int id;
	private int location;
	private int dest;
	private int available;
//	private boolean board;
	
	public Shuttle1(int i) {
		id = i;
		location = chooseTerminal();
		available = Controller.N;
	}
	
	public int chooseTerminal() {
		Random rnd = new Random();
		return rnd.nextInt(Controller.K);
	}
	
	public void travel() {
		try {
			sleep((int) Math.random() * 1000);
		} catch (InterruptedException e1) {}
	}
	
	public void run() {
		while (Controller.hasPsg) {
			// go to next terminal
			dest = (location + 1) % Controller.K;
			travel();
			location = dest;			
			try {
				Controller.loading[location].acquire();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Shuttle " + id + " is at terminal " + location + " with " + available + " available seats");
		
			try {
				Controller.waitingMutex.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Controller.ridingMutex.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// passengers get off at location
			for (int i = 0; i < Controller.ridingNum[location]; i++) {			
				Controller.riding[location].release();
				available++;	
//				Controller.ridingNum[location]--;
			}
			
			// passengers at location board shuttle
			if (Controller.waitingNum[location] == 0) Controller.leave.release();
			else {
				int n = Math.min(available, Controller.waitingNum[location]);
				for (int i = 0; i < n; i++) {
					Controller.waiting[location].release();
					available--;
//					board = true;
				}
			}
			
			Controller.ridingMutex.release();
			Controller.waitingMutex.release();
			
			// wait for the last passenger to tell it to leave, what if there is no passenger?
//			if (board) {
			try {
				Controller.leave.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Controller.loading[location].release();
//			}
		}
	}
}
	