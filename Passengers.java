/*
 * Answer 2 passengers
 */
import java.util.Random;
import java.util.stream.IntStream;

public class Passengers extends Thread {
	
	private int id;
	private int thisTerminal;
	private int nextTerminal;
	
	public Passengers(int i) {
		id = i;
		thisTerminal = chooseTerminal();
	}
	
	public int chooseTerminal() {
		Random rnd = new Random();
		return rnd.nextInt(Controller.K);
	}
	
	public void run() {
		for (int i = 0; i < 5; i++) {
			// wait for shuttle to come
			try {
				Controller.waitingMutex.acquire();
			} catch (InterruptedException e5) {
				// TODO Auto-generated catch block
				e5.printStackTrace();
			}
			Controller.waitingNum[thisTerminal]++;
			System.out.println("passenger " + id + " is waiting at terminal " + thisTerminal);
			Controller.hasPsg = true;
			Controller.waitingMutex.release();
			try {
				Controller.waiting[thisTerminal].acquire();
			} catch (InterruptedException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}
			
			// board shuttle
			try {
				Controller.waitingMutex.acquire();
			} catch (InterruptedException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			System.out.println("passenger " + id + " boarded shuttle");
			Controller.waitingNum[thisTerminal]--;
			
			// choose destination terminal different from thisTerminal
			try {
				Controller.ridingMutex.acquire();
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			do {nextTerminal = chooseTerminal();
			} while (nextTerminal == thisTerminal);
			Controller.ridingNum[nextTerminal]++;
	//		System.out.println("waitingNum at terminal "+thisTerminal+" is "+Controller.waitingNum[thisTerminal]);
	//		System.out.println("Number of passengers on the shuttle is "+IntStream.of(Controller.ridingNum).sum());
			if (Controller.waitingNum[thisTerminal]==0 || IntStream.of(Controller.ridingNum).sum()==Controller.N) {
				Controller.leave.release(); // if all passengers at the current terminal have boarded or the shuttle is fully loaded, tell it to leave
			}
			Controller.ridingMutex.release();
			Controller.waitingMutex.release();
			try {
				Controller.riding[nextTerminal].acquire();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// wait until shuttle arrives
			try {
				Controller.ridingMutex.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("passenger " + id + " gets off at terminal " + nextTerminal);
			Controller.ridingNum[nextTerminal]--;
			Controller.ridingMutex.release();
			thisTerminal = nextTerminal;	
		}
	}
}
