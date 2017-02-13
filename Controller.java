/*
 * Answer 2 controller
 */
import java.util.concurrent.Semaphore;

public class Controller {
	
	public static boolean hasPsg = false;
	public static final int N = 10; // shuttle capacity
	public static final int K = 6; // number of stations
	public static final int M = 2; // number of shuttles
	public static int waitingNum[] = new int[K]; // number of passengers waiting at index terminal
	public static int ridingNum[] = new int[K]; // number of passengers going to index terminal

	public static Semaphore waitingMutex = new Semaphore(1); // mutex that protects waitingNum[]
	public static Semaphore ridingMutex = new Semaphore(1); // mutex that protects ridingNum[]
	public static Semaphore waiting[] = new Semaphore[6]; // force passengers to wait at index terminal
	public static Semaphore riding[] = new Semaphore[6]; // force passengers to wait until reach index terminal
//	public static Semaphore capacity = new Semaphore(N); // force passengers to wait if there's no more space on the shuttle
	public static Semaphore leave = new Semaphore(0); // force shuttle to wait until the last passenger to tell it to leave
	public static Semaphore loading[] = new Semaphore[6]; // a list of 6 mutexes to protect each terminal is a shared resource
	
	public Controller() {
		for (int i = 0; i < K; i++) {
			waiting[i] = new Semaphore(0);
			riding[i] = new Semaphore(0);
			loading[i] = new Semaphore(1);
		}
	}
	
	public static void main(String[] args) {
		Controller LoganFleet = new Controller();
		Passengers p[] = new Passengers[50];
		Shuttle1 s[] = new Shuttle1[M];
		for (int i = 0; i < 50; i++) {
			p[i] = new Passengers(i);
			p[i].start();
		}
		for (int m = 0; m < M; m++) {
			s[m] = new Shuttle1(m);
			s[m].start();
		}
	}
}
