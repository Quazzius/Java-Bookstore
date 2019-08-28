package bar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Scanner;

public class Book
{
	public static Scanner input = new Scanner ( System.in );

	// creates linked list
	public static Collection<Book> list = new LinkedList<> ( );

	// data fields
	// Title of book, International Standard Book Number, Price of book.
	private String title, isbn, price;

	// Quantity of book in stock, Publication Date of book
	private int qty, pubDate;

	// Total price of customer order, total sales for the day
	private static double sum, dSales;

	// When a customer attempts to by a book that is
	// out of stock, this number increments.
	private static int stockCount;

	// Constructor creates book objects and gives
	// objects their instances of data
	public Book(String newIsbn, String newTitle, int newPubDate, String newPrice, int newQty)
	{

		title = newTitle;
		price = newPrice;
		isbn = newIsbn;
		qty = newQty;
		pubDate = newPubDate;
	}

	// getters and setters get current current values for objects
	// and set updates for values.
	public static double getdSales( )
	{
		return dSales;
	}

	public static void setdSales( double dSales )
	{
		Book.dSales += dSales;
	}

	public static int getStockCount( )
	{
		return stockCount;
	}

	public static void setStockCount( int stockCount )
	{
		Book.stockCount += stockCount;
	}

	public static double getSum( )
	{
		return sum;
	}

	public static void setSum( double price1 )
	{
		if ( price1 == 0 )
		{
			Book.sum = 0;
		}
		else
		{
			Book.sum += price1;
		}
	}

	public String getIsbn( )
	{
		return isbn;
	}

	public void setIsbn( String isbn )
	{
		this.isbn = isbn;
	}

	public String getPrice( )
	{
		return price;
	}

	public void setPrice( String price1 )
	{
		price += price1;
	}

	public int getQty( )
	{
		return qty;
	}

	public void setQty( int i )
	{
		this.qty += i;
	}

	public String getTitle( )
	{
		return title;
	}

	public void setTitle( String title )
	{
		this.title = title;
	}

	public int getPubDate( )
	{
		return pubDate;
	}

	public void setPubDate( int pubDate )
	{
		this.pubDate = pubDate;
	}

	// converts data to a String for output
	public String toString( )
	{

		return String.format ( "%14s,%20s,%8s,%8s,%5s,", isbn, title, pubDate, price, qty );

	}

	public String toString1( )
	{

		return String.format ( "%14s%20s%8s%8s%5s", isbn, title, pubDate, price, qty );
	}

	// user input method to make more books
	public static Book newBook( )
	{
		Scanner test = new Scanner ( System.in );// new scanner avoids carriage return issue
		String nTitle = "";
		do
		{
			System.out.println ( "Enter Title in under 20 characters" );
			nTitle = test.nextLine ( );
			if ( nTitle.length ( ) > 20 )
			{
				System.out.println ( "Incorrect length" );
			}
		} while ( nTitle.length ( ) > 20 );
		String nIsbn = "";
		do
		{
			System.out.println ( "Enter ISBN (10-13 digits)" );
			nIsbn = test.nextLine ( );
			if ( nIsbn.length ( ) > 13 || nIsbn.length ( ) < 10 )
			{
				System.out.println ( "Incorrect length" );
			}
		} while ( nIsbn.length ( ) > 13 || nIsbn.length ( ) < 10 );
		System.out.println ( "Enter Publication Date" );
		String nPubDate = test.nextLine ( );
		System.out.println ( "Enter Price with 2 decimal places. ex: 99.99" );
		String nPrice = test.nextLine ( );
		System.out.println ( "How many do we have?" );
		String nQty = test.nextLine ( );

		Book nextBook = new Book ( nIsbn, nTitle, (int) Double.parseDouble ( nPubDate ), nPrice,
				(int) Double.parseDouble ( nQty ) );
		
		test.close ( );
		return nextBook;
		
	}

	// read in text Strings from file
	public static Book[ ] bookArray( String fileName ) throws IOException
	{
		Scanner scanFile = new Scanner ( new File ( fileName ) );
		scanFile.useDelimiter ( "\\s*,\\s*" );

		// declare array that reads text from file
		Book[ ] values = new Book[50];

		//
		int rows = scanFile.nextInt ( );
		// loops in values for Array
		try
		{
			for ( int r = 0; r < rows; r++ )
			{
				String isbn = scanFile.next ( );
				String name = scanFile.next ( );
				String date = scanFile.next ( );
				String price = scanFile.next ( );
				String qty = scanFile.next ( );

				values[r] = new Book ( isbn, name, (int) Double.parseDouble ( date ), price,
						(int) Double.parseDouble ( qty ) );
			}
		} catch ( Exception e )
		{

		} finally
		{
			scanFile.close ( );
		}
		return values;

	}

	// method to enter new books
	public static Book[ ] bookEntry( Book[ ] b ) throws IOException
	{
		System.out.println ( "Enter new books?\ny/n?" );
		char enter = input.next ( ).charAt ( 0 );

		// user can add books
		if ( enter == 'y' )
		{
			// get row amount
			Scanner scan = new Scanner ( new File ( "src/bar/Input.txt" ) );
			scan.useDelimiter ( "\\s*,\\s*" );
			int row = scan.nextInt ( );

			// opens files for writing to
			PrintWriter write = new PrintWriter ( "src/bar/Input.txt" );

			System.out.println ( "How many different books?" );
			int amount = input.nextInt ( );

			// "make" new books
			for ( int i = row; i < amount + row; i++ )
			{
				b[i] = Book.newBook ( );
			}
			try
			{

				// writes updated book list back to file
				// update row length to read back from in future
				write.println ( row + amount + " ," );
				for ( int j = 0; b[j] != null; j++ )
				{
					// write to "input" file
					write.println ( b[j].toString ( ) );

				}

			} catch ( Exception e )
			{

			}
			// close writer to save file, close scanner
			write.close ( );
			scan.close();
		}
		
		return b;
	}

	// create array of book objects from 2d arrays taken from files

	// shows customer menu and updates quantities
	public static Book[ ] menu( Book[ ] b ) throws FileNotFoundException
	{
		System.out.println ( "                     Buy somethin' will ya!\n" );
		System.out.println ( "          ISBN             Title       PubDate  Price   Qty" );
		System.out.println ( "         -------          -------      -------  ------  ---" );

		// output list of items for menu
		for ( int i = 0; b[i] != null; i++ )
		{

			System.out.printf ( "%s. %s\n", i + 1, b[i].toString1 ( ) );

		}

		// user chooses book
		System.out.println ( "\nEnter your choice number" );
		int j = input.nextInt ( );
		Scanner scan = new Scanner ( new File ( "src/bar/Input.txt" ) );
		int row = scan.nextInt ( );
		if ( j < 0 || j > row )
		{
			do
			{
				System.out.println ( "Invalid entry, choose 1-" + row );
				j = input.nextInt ( );
			} while ( j < 1 || j > row );
		}

		// check user input to see if item is out of stock
		if ( b[j - 1].getQty ( ) <= 0 )
		{
			// user cannot buy items out of stock
			System.out.println ( "Sorry, this Item is out of stock" );
			setStockCount ( 1 ); // ticks every time someone attempts to buy out-of-stock item
		}
		else
		{
			// add item to liked list
			list.add ( b[j - 1] );

			// set variables to new values
			b[j - 1].setQty ( -1 );
			Book.setSum ( Double.parseDouble ( b[j - 1].price ) );
		}
		System.out.printf ( "Balance = $%3.2f\n", Book.getSum ( ) );
		scan.close ( );
		return b;

	}

	// the process of running the store with customer interaction
	// prompts for amount of users, keeps log of ordered items,
	// outputs receipt for customer review and allows customer
	// to make changes to their order.
	public static void storeFront( Book[ ] b ) throws FileNotFoundException
	{
		// prompt user for group info
		int groupNum = 0;
		System.out.println ( "Buy Books?\ny/n?" );
		char enter = input.next ( ).charAt ( 0 );
		
		//user wants to buy books
		if ( enter == 'y' )
		{
			System.out.println ( "Are you part of a group?" );
			char group = input.next ( ).charAt ( 0 );

			// group order
			if ( group == 'y' )
			{
				System.out.println ( "How many are in your group?" );
				groupNum = input.nextInt ( );

				// uses menu for customer orders.
				// loops depending on amount of customers in group
				for ( int i = 0; i < groupNum; i++ )
				{
					while ( enter == 'y' )
					{
						b = Book.menu ( b );

						// individual customer can add additional books to their order
						System.out.println ( "add another book?\ny/n?" );
						enter = input.next ( ).charAt ( 0 );
					}
					if ( i < groupNum - 1 )
					{
						// moves to "next" customer
						System.out.println ( "NEXT CUSTOMER!" );
						enter = 'y';
					}
				}
			}
			// single person order
			else
			{
				while ( enter == 'y' )
				{
					b = Book.menu ( b );
					System.out.println ( "add another book?\ny/n?" );
					enter = input.next ( ).charAt ( 0 );
				}
			}
			// customer gets receipt with list before paying
			receipt(b);
		}
	}

	// manager report lists book quantities and allows manager to add stock to book quantities
	public static Book[ ] managerReport( Book[ ] b ) throws IOException
	{

		// output list of books
		for ( int i = 0; i < b.length && b[i] != null; i++ )
		{

			System.out.printf ( "%s. %s\n", i + 1, b[i].toString1 ( ) );

		}
		// requirement for part 5, "Keep track of amount of items
		// that could have been sold had there been more inventory."
		if ( Book.getStockCount ( ) > 0 )
		{
			System.out.println ( "\n\nPeople tried to buy out-of-stock items " + Book.getStockCount ( ) + " times\n" );
		}
		System.out.printf ( "\nSales for the day: %3.2f", Book.getdSales ( ) );

		// manager can "order" more books listed on the menu
		System.out.println ( "\nOrder more books?" );
		char order = input.next ( ).charAt ( 0 );

		while ( order == 'y' )
		{
			// get row length
			Scanner scan = new Scanner ( new File ( "src/bar/Input.txt" ) );
			scan.useDelimiter ( "\\s*,\\s*" );
			int row = scan.nextInt ( );

			// open file writers to write new quantities back to file
			PrintWriter update = new PrintWriter ( "src/bar/Input.txt" );

			// manager selects book to add quantity to
			System.out.println ( "Select book to order" );
			int bookNum = input.nextInt ( );
			if ( bookNum < 0 || bookNum > row )
			{
				do
				{
					System.out.println ( "Invalid entry, choose 1-" + row );
					bookNum = input.nextInt ( );
				} while ( bookNum < 1 || bookNum > row );
			}
			System.out.println ( "how many to order?" );
			int orderNum = input.nextInt ( );

			b[bookNum - 1].setQty ( orderNum );

			try
			{
				// update row amount
				update.println ( row + " ," );

				// write updates to "input" file
				for ( int j = 0; b[j] != null; j++ )
				{
					update.println ( b[j].toString ( ) );
				}

			} catch ( Exception e )
			{
			}

			// output list with new quantities to console
			for ( int i = 0; i < b.length && b[i] != null; i++ )
			{

				System.out.printf ( "%s. %s\n", i + 1, b[i].toString1 ( ) );

			}
			System.out.println ( "Order more?" );
			order = input.next ( ).charAt ( 0 );

			// close writer to save text file
			scan.close ( );
			update.close ( );
		}

		return b;
	}
	public static void receipt(Book[] b) throws FileNotFoundException {
		System.out.println ( "Does this look right?\ny/n?" );
		System.out.println ( "----------------------------" );
		System.out.println ( "Reciept:\n" );

		// outputs book title and price for customer review
		for ( int i = 0; i < list.size ( ); i++ )
		{
			System.out.printf ( "%s%14s%8s\n", i + 1, ( (LinkedList<Book>) list ).get ( i ).getTitle ( ),
					( (LinkedList<Book>) list ).get ( i ).getPrice ( ) );
		}
		System.out.println ( "----------------------------" );
		char dec = input.next ( ).charAt ( 0 );

		// customer can make changes to their order
		while ( dec == 'n' )
		{
			System.out.println ( "Remove items?\ny/n?" );
			char sure = input.next ( ).charAt ( 0 );

			// removes items from list
			while ( sure == 'y' )
			{
				System.out.println ( "What number would you like to remove?" );
				int i = input.nextInt ( );
				
				//change customer total sum
				Book.setSum ( -Double.parseDouble ( ( (LinkedList<Book>) list ).get ( i - 1 ).getPrice ( ) ) ); 
				( (LinkedList<Book>) list ).remove ( i - 1 ); //remove item from list
				System.out.println ( "Book added to inspection pile\n" );

				// customer can remove additional books
				// if there are still items in their order.
				if ( list.size ( ) > 0 )
				{
					System.out.println ( "Remove another?\ny/n?" );
					sure = input.next ( ).charAt ( 0 );
				}
				else
				{
					sure = 'n';
				}
			}
			// user can add additional items to their order again
			System.out.println ( "add Items?\ny/n?" );
			char add = input.next ( ).charAt ( 0 );

			// gives the ability to add additional books to order
			// until they are satisfied.
			while ( add == 'y' )
			{
				b = Book.menu ( b );
				System.out.println ( "add another book?\ny/n?" );
				add = input.next ( ).charAt ( 0 );
			}
			// output receipt for review again.
			System.out.println ( "Does this look right?\ny/n?" );
			System.out.println ( "----------------------------" );
			System.out.println ( "Reciept:\n" );
			for ( int i = 0; i < list.size ( ); i++ )
			{
				System.out.printf ( "%s%14s%8s\n", i + 1, ( (LinkedList<Book>) list ).get ( i ).getTitle ( ),
						( (LinkedList<Book>) list ).get ( i ).getPrice ( ) );
			}
			System.out.println ( "----------------------------" );

			// satisfaction loop condition.
			dec = input.next ( ).charAt ( 0 );
		}
		// once user is happy with their receipt, output total for "payment"
		// add sales to total sales for the day, reset sum to 0 for next group/customer
		System.out.printf ( "\nPlease pay $%3.2f\n", Book.getSum ( ) );
		Book.setdSales ( Book.getSum ( ) );
		Book.setSum ( 0 );

		// remove items in linked list for next group/customer
		for ( Book i : (LinkedList<Book>) list  )
		{
			( (LinkedList<Book>) list ).remove ( i );
		}
	}

}
