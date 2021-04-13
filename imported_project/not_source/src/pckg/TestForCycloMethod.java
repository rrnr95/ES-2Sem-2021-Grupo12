public class TestForCycloMethod {
	
	/* comment 1
	 * long 
	 * very long
	 * even longer
	 * when will it end
	 */
	
	private void method1() {
		int a = 0;
		// comment 2
		while (a != 3 /* if () */ ) { 
			if (a==0) {
				String string = "method1 does nothing really"; // if ()
				System.out.println(string);
			}
			else
				System.out.println("line of method1");
			a++;
		}
		
	}
	
	/* bla bla bla bla bla bla */
	
	public void method2() {
		int a = 0;
		int b = 1;
		String string = "a";
		// coment 3
		if (a==1)
			string = "method2 does nothing really";
		if (b==1)
			System.out.println(string);
	}
	
	// another one
	
	public static void main(String[] args) {
		// DJ KHALED
		
		System.out.println("Hello World!");
	}
}