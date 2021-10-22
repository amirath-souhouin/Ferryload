import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set; 
public class Main {
	public static int [][] dp;
	static int occupe_bord=0;
	static BufferedReader b;
	static int L_of_total_car=0;
	static int  cases=0;
	static int number_of_file=0;
	static ArrayList<Integer> length_of_boat = new ArrayList<Integer>();
   // Creating a Hashtable
	static Hashtable<Integer, Integer> hashtable = new Hashtable<Integer, Integer>() ;
	static ArrayList<Integer> number_of_car_in_file = new ArrayList<Integer>();
	static  ArrayList<Integer> length = new ArrayList<Integer>();//the array which contains the length of each car.
	
	private static void lire() throws IOException{
		
		int count_car = 0;
		//The number after 0 is the number of car per file
		BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
			number_of_file = Integer.parseInt(b.readLine());//get length in cm in the first line;
			String current_line="";	
			do{
				if(current_line != null){
					if(	current_line.equals("0")){
						count_car-=2;
						number_of_car_in_file.add(count_car);
						count_car=0;
					}
					current_line = b.readLine();
					if( current_line != null){
						if(!current_line.equals("")){
							length.add(Integer.valueOf(current_line));
						}
						if(current_line.equals("") && current_line !=null){
							current_line = b.readLine();
							length_of_boat.add(Integer.valueOf(current_line));
						}
					}	
					count_car++;
				}
				
			}while(current_line !=null );
			b.readLine();
		
	}
	public static void init(int number_of_car,int L){
		hashtable = new Hashtable<Integer, Integer>();
		dp = new int[number_of_car+1][L+1];

		for(int i = 0;i<(number_of_car+1);i++){
			for(int j = 0;j <L+1 ;j++){
				dp[i][j]=-1;
			}
		}
	}
			
	public static int backtrackSolve(int currK, int currS){
			
			if( dp[currK][currS]==-1){
				if(currK == number_of_car_in_file.get(cases) || currS==0){
					dp[currK][currS]=0;
				}
				else if(currS>=length.get(currK)){
					int v1 = backtrackSolve(currK+1,currS);
					int l = backtrackSolve(currK+1,currS-length.get(currK));
					int v2 = length.get(currK) + l;
					
					if(v2>=v1){
						if( length.get(currK)<=currS ){
							dp[currK][currS] = v2;
							hashtable.put(currK,currS);
						}
						else{
							dp[currK][currS] = v2;	
						}
					}
					else {
						dp[currK][currS] = v1;
					}
				}else if(currS < length.get(currK)){	
					dp[currK][currS]=backtrackSolve(currK+1,currS);
				}
			}	
		
		return dp[currK][currS];
	}
	public static boolean contains(int id, int taille, Hashtable<Integer,Integer> hashtable){
		Set<Integer> keys = hashtable.keySet();
		for (Integer key : keys) {
            if(key.equals(id) && hashtable.get(id).equals(taille) ){
				return true;
			} 
		}
		return false;
    }
	
	public static void main(String args[]) throws Exception{
		try{
			lire();
			StringBuilder s;
			while(cases<number_of_file){
				
			int L = length_of_boat.get(cases)*100;
			init(number_of_car_in_file.get(cases),L);
			long startTime = System.nanoTime();
			backtrackSolve(0,L);
			long endTime = System.nanoTime();
			long duration = (endTime-startTime);
			int count=0;
			int rightSum=0;
			int curr = L;
			s = new StringBuilder();
			for (int i=0;i<number_of_car_in_file.get(cases);i++) {
				if(length.get(0)>L){
					break;
				}	
				else if (contains(i,curr,hashtable)==true) {
					curr = curr - length.get(i);
					count++;
					s.append("starboard\n");
				} else if (rightSum+length.get(i)<=L ) {
					rightSum+=length.get(i);
					count++;
						s.append("port\n");
				}
			}
			
			System.out.println(count);
			if(cases==number_of_file-1){
				System.out.print(s.toString());
				System.out.println(duration + "nanoSecond");
			}else{
				System.out.println(s.toString());
			}
			cases++;
			
			
		}
		}catch(RuntimeException e){

		}	
	}
	
}