
import java.util.*;
import Resume.Resume;
import Database.DB;


class Main12{
    public static ArrayList<Resume> allResumeList=new ArrayList<>();
    static Scanner sc=new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        

        int choice;
        do{
            System.out.println();
            System.out.println("Choice Option");
            System.out.println("(1)Create Resume  \n(2)Read Resume  \n(3)Update Resume \n(4)Exit");
            choice=sc.nextInt();
            switch(choice){
                case 1 : Resume.takeInfo();
                         break;
                case 2 : Resume.read_Resume();
                         break;
                case 3 : Resume.updateResume(DB.allResumeFromDB());;
                         break;
                case 4 : System.exit(0);
                         break;
                default : System.out.println("Enter Valid Option");   
                         break;      
            }
        }while(choice!=4);
    }
}