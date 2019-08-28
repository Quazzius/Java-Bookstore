/*
 * Author: Jeff Flanegan
 * Class: CSC 160
 * Project: Store
 * Due: Mid-Late April
 */

package bar;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

//This program makes and runs a bookstore using classes and things classes can do.
//Arrays are created by reading in a text file and users can enter in new books.
//The program outputs list of books as a menu and users can "purchase" Books.
//users cannot buy items that are out of stock, item quantity is tracked
//as well as sum of order price. Groups can place orders for a single bill.
//A manager option is available that is password protected with the PASSWORD: password
//Manager can add to inventory and quantities of items and updated list is written back to file.
//customers are shown a list of what they have ordered before paying for order.

public class Driver
{
	public static Scanner input = new Scanner ( System.in );
	
	//main method runs program
	public static void main( String[ ] args ) throws IOException
	{
		
		//declares array of Book objects
		Book[] b = Book.bookArray("src/bar/Input.txt");
		
		char openProgram = 'y';
		while(openProgram == 'y') {
			System.out.println ( "Enter user type\n1. Customer\n2. Manager" );
			int userType =  input.nextInt ( );
			if (userType == 2) {
				
				//manager options
				b = manager(b);
			}
			//customer menu
			else if (userType == 1) {
				
				//Store Front. Flanegan's Nerdery LLC.
				Book.storeFront(b);
			}
			System.out.println ( "Continue business?\ny/n?" );
			openProgram = input.next ( ).charAt (0);
		}
		//write days end menu back to input file
		Scanner scan = new Scanner ( new File ( "src/bar/Input.txt" ) );
		scan.useDelimiter ( "\\s*,\\s*" );
		int row = scan.nextInt ( );
		
		// open file writer to write new quantities back to file
		PrintWriter update = new PrintWriter ( "src/bar/Input.txt" );
		try
		{
			// update row amount
			update.println ( row + " ," );

			// write updates to "input" file
			for ( int j = 0; b[j] != null; j++ )
			{
				update.println ( b[j].toString ( ) );
			}

		} catch ( Exception e ) {
			
		}
		scan.close();
		update.close ( );
	}
	//method for manager operations which allows the "manager" to
	//input new book objects and add quantities to Books
	public static Book[ ] manager(Book [] b) throws IOException {
		
		//Manager is password protected
		String password = "password";
		System.out.println ( "Enter Password" );
		
		String mPassword = input.nextLine ( );
		mPassword = input.nextLine ( ); //manages carriage return
		
		//password check
		//password accepted
		if (password.equals ( mPassword ) ){
			System.out.println ( "Password accepted\n" );
			
			//manager has the choice to enter new books
			b = Book.bookEntry(b);
			
			//show manager report
			b = Book.managerReport(b);
		}
		//password not excepted
		else {
			System.out.println ( "Password not recognized" );
		}
		return b;
		
	}
	
}
