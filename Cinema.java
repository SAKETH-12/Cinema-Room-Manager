package cinema;

import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        // Write your code here
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int row = scan.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int col = scan.nextInt();
        System.out.println();
        
        Room cinemaRoom = new Room(row, col);
        cinemaRoom.setSeats();
        
        loop:
        while(true) {
            System.out.println("1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit");
            int input = scan.nextInt();
            System.out.println();
            
            switch(input) {
                case 1:
                   cinemaRoom.printRoom();
                   break;
                case 2:
                    ticketMachine(scan, cinemaRoom);
                    break;
                case 3:
                    cinemaRoom.statistics();
                    break;
                case 0:
                    break loop;
            }
        }
        
        //System.out.printf("Total income:\n%d" , cinemaRoom.costCalculator());
    }
    
    public static void ticketMachine(Scanner scan, Room cinemaRoom) {
        boolean flag = true;
        while(flag) {
            System.out.println("Enter a row number:");
            int row = scan.nextInt();
            System.out.println("Enter a seat number in that row:");
            int col = scan.nextInt();
            System.out.println();
            if ( row < 1 || !Integer.toString(row).matches("\\d+") || !Integer.toString(col).matches("\\d+") || col < 1 || row > cinemaRoom.getrow() || col > cinemaRoom.getcol()) {
                System.out.println("Wrong input!");
            } else {
                if (cinemaRoom.getSeat(row,col)) {
                    System.out.println("That ticket has already been purchased!\n");
                    
                
                } else {
                    flag = false;
                System.out.printf("Ticket price: $%d\n\n", cinemaRoom.getSeatPrice(row, col));
                }
            }
        }
    }
}

class Room {
    private int row;
    private int col;
    private char[][] cinemaRoom;
    private int seatsPurchased = 0;
    private int totalIncome;
    private int currentIncome = 0;
    
    public Room(int row, int col) {
        this.row = row;
        this.col = col;
        cinemaRoom = new char[row][col];
    }
    public int getrow() {
        return this.row;
    }
    public int getcol() {
        return this.col;
    }
    
    public void setSeats() {
        for(int i = 0; i < row ; i++) {
            for(int j = 0; j < col ; j++) {
                cinemaRoom[i][j] = 'S';
            }
        }
    }
    
    public void printRoom() {
        int i = 0;
        System.out.print("Cinema:\n ");
        for(int j = 1; j <= col; j++) {
            System.out.print(" " + j);
        }
        System.out.println();
        for(char[] characters : cinemaRoom) {
            System.out.print(++i);
            
            for(char c : characters) {
                System.out.print(" " + c);
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public int costCalculator() {
        if(row*col <= 60) {
            return row*col*10;
        }
        
        totalIncome = (int) Math.floor(1.0*row/2)*10*col + (int) Math.ceil(1.0*row/2)*8*col;
        return totalIncome;
    }
    
    public int getSeatPrice(int row, int col) {
        reserveSeat(row, col);
        if(this.row*this.col <= 60) {
            return 10;
        }
        int price = (row <= (int)Math.floor(1.0*this.row/2)) ? 10 : 8;
        currentIncome += price;
        return price;
    }
    
    private void reserveSeat(int row, int col) {
        cinemaRoom[row - 1][col - 1] = 'B';
        seatsPurchased += 1;
    }
    
    public void statistics() {
        System.out.printf("Number of purchased tickets: %d", seatsPurchased);
        System.out.println();
        double seatsPurchasedpercentage = (seatsPurchased * (1.0)/ (row * col)) * 100;
        System.out.printf("Percentage: %.2f%%", seatsPurchasedpercentage);
        System.out.println();
        System.out.printf("Current income: $%d", currentIncome);
        System.out.println();
        System.out.printf("Total income: $%d\n", costCalculator());
        System.out.println();
    }
    
    public boolean getSeat(int row, int col) {
        if (cinemaRoom[row-1][col-1] == 'B') {
            return true;
        } else {
            return false;
        }
    }
}   
