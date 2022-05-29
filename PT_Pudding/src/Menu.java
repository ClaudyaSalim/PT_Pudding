import java.util.Scanner;

public class Menu {
	
	
	public void run() {
		Scanner scan = new Scanner(System.in);
		String kode;
		do {
			System.out.println("Input Kode: ");
			kode = scan.nextLine();
		} while(validateKode(kode)==false);
		
		System.out.println("Kode Sukses");
		
		scan.close();
	}
	
	
	public boolean isNum(String kode) {
		char[] arr = {'0','1','2','3','4','5','6','7','8','9'};
		int count=0;
		
		for (int i=3; i<6; i++) {
			for(int j=0; j< 10; j++) {
				if(kode.charAt(i)==arr[j]) {
					count++;
					break;
				}
			}
		}
		
		if(count==3) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean validateKode(String kode) {

		if (kode.charAt(0)=='P' && kode.charAt(1)=='D' && kode.charAt(2)=='-' && isNum(kode)==true && kode.length()==6) {
			return true;
		}
		
		return false;
	}

}
