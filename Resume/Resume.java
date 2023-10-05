package Resume;
import java.util.*;

import Database.DB;

import java.io.*;

public class Resume{
    static Scanner sc=new Scanner(System.in);
    public String name;
    public String phone;
    public String email;
    public String github;
    public String linkedIn;
    public ArrayList<String> skills;
    public ArrayList<String> education;
    public ArrayList<String> academicProject;
    public ArrayList<String> hobbies;

    public Resume(String name,String phone,String email,String github,String linkedIn,ArrayList<String> skills,ArrayList<String> education,ArrayList<String> academicProject,ArrayList<String> hobbies){
        this.name=name;
        this.phone=phone;
        this.email=email;
        this.github=github;
        this.linkedIn=linkedIn;
        this.skills=skills;
        this.education=education;
        this.academicProject=academicProject;
        this.hobbies=hobbies;
    }


    public static void takeInfo() throws  Exception {
        boolean flag=false;
        ArrayList<String> skills;
        ArrayList<String> education;
        ArrayList<String> academicProject;
        ArrayList<String> hobbies;
        
        System.out.println();
        System.out.print("Enter Your Name : ");
        String name=sc.nextLine();
        
        String phone;
        boolean flag2=false;
        do{
            System.out.print("Enter Phone Number : ");
            phone=sc.nextLine();
            if(phone.length()==10){
                for(int i=0;i<phone.length();i++){
                    if(phone.charAt(i)<'0' || phone.charAt(i)>'9'){
                        flag2=true;
                    }
                }
            }
        }while(flag2);
        System.out.print("Enter Email : ");
        String email=sc.nextLine();
        System.out.print("Enter Your GitHub ID : ");
        String github=sc.nextLine();
        System.out.print("Enter Your LinkedIn Id : ");
        String linkedIn=sc.nextLine();
        skills=allInfo("Skills");
        education=allInfo("Education");
        academicProject=allInfo("Academic Project");
        hobbies=allInfo("Hobbies");
        Resume r=new Resume(name,phone,email, github, linkedIn, skills, education, academicProject, hobbies);
        saveInFile(r);
        DB.resumeStoreInDB(r);;
        System.out.println();
        System.out.println("Your Resume SuccessFully Created \nYour Resume File : "+name+"_resume.txt");
    }

    public static void updateResume(ArrayList<Resume> allResumes) throws Exception{
        System.out.print("Enter Your Name : ");
        String fileName=sc.nextLine();
        Resume r=getResume(allResumes, fileName);
        if(r!=null){
            int ch;
            String arr[]={"phone","email","github","linkedin","skills","education","academicproject","hobbies"};
            do{
                System.out.println("Select for Update \n(1)phone (2)email (3)github (4)linkedin (5)Skills (6)Education (7)AcademicProject (8)hobbies (9)Exit");
            ch=sc.nextInt();
            if(ch<1 || ch>9){
                System.out.println("Invalid Option");
                return;
            }
            sc.nextLine();
            if(ch>0 && ch<5){
                System.out.println("Enter new "+arr[ch-1]+" : ");
                String update=sc.nextLine();
                if(ch==1){
                    r=new Resume(r.name, update, r.email, r.github, r.linkedIn, r.skills, r.education, r.academicProject, r.hobbies);
                }else if(ch==2){
                    r=new Resume(r.name, r.phone, update, r.github, r.linkedIn, r.skills, r.education, r.academicProject, r.hobbies);
                }else if(ch==3){
                    r=new Resume(r.name, r.phone, r.email, update, r.linkedIn, r.skills, r.education, r.academicProject, r.hobbies);
                }else if(ch==4){
                    r=new Resume(r.name, r.phone, r.email, r.github,update, r.skills, r.education, r.academicProject, r.hobbies);
                }
            }else{
                if(ch==5){
                    ArrayList<String> skill=listUpdate(arr[4],r.skills);
                    r=new Resume(r.name, r.phone, r.email, r.github, r.linkedIn, skill, r.education, r.academicProject, r.hobbies);
                }else if(ch==6){
                    ArrayList<String> education=listUpdate(arr[5],r.education);
                    r=new Resume(r.name, r.phone, r.email, r.github, r.linkedIn, r.skills, education, r.academicProject, r.hobbies);
                }else if(ch==7){
                    ArrayList<String> aProject=listUpdate(arr[6],r.academicProject);
                    r=new Resume(r.name, r.phone, r.email, r.github, r.linkedIn, r.skills, r.education, aProject, r.hobbies);
                }else if(ch==8){
                    ArrayList<String> hobb=listUpdate(arr[7],r.hobbies);
                    r=new Resume(r.name, r.phone, r.email, r.github, r.linkedIn, r.skills, r.education, r.academicProject, hobb);
                }

                if(ch<1 || ch>8){
                    DB.updateResume(r);
                }
            }
            }while(ch!=9);

            System.out.println("Resume Updated....");
            saveInFile(r);
        }else{
            System.out.println("from This name No, Any Resume Available");
        }
    }

    private static ArrayList<String> listUpdate(String name,ArrayList<String> list){
        int a;
        do{
            System.out.println("You Want (1)ADD More (2)Reset (3)Exit");
            a=sc.nextInt();

            switch(a){
                case 1 : int choice;
                            list.add(sc.nextLine());
                            do{
                                System.out.println("(1)For More Add (2)Close");
                                choice=sc.nextInt();
                                sc.nextLine();
                                if(choice==1){
                                    list.add(sc.nextLine());
                                }
                                if(choice < 1 || choice>2){
                                    System.out.println("Invalid Option");
                                }
                            }while(choice!=2);

                case 2 : return allInfo(name);
                case 3 : System.exit(0);
            }
        }while(true);
            
    }

    private static Resume getResume(ArrayList<Resume> allResumes,String name){
        for(int i=0;i<allResumes.size();i++){
            if(allResumes.get(i).name.equalsIgnoreCase(name)){
                return allResumes.get(i);
            }
        }
        return null;
    }

    public static ArrayList<String> allInfo(String name){
        System.out.println("Enter Your "+ name);
        ArrayList<String> list=new ArrayList<>();
        int choice;
        list.add(sc.nextLine());
        do{
            System.out.println("(1)For More Add (2)Close");
            choice=sc.nextInt();
            sc.nextLine();
            if(choice==1){
                list.add(sc.nextLine());
            }
            if(choice < 1 || choice>2 ){
                System.out.println("Invalid Option");
            }
        }while(choice!=2);
        return list;
    }

    public static void saveInFile(Resume data) throws IOException{
        FileWriter fw=new FileWriter(data.name+"_resume.txt");
        BufferedWriter bw=new BufferedWriter(fw);

        System.out.print("Enter Your Name : ");
        bw.write("                 "+data.name);

        bw.newLine();
        bw.write("            "+data.email+"|"+data.phone);

        bw.newLine();
        bw.newLine();

        bw.write("github : @"+data.github);
        bw.write("                              LinkedIn : /"+data.linkedIn);

        bw.newLine();
        bw.newLine();

        bw.write("SKlLLS");
        for(int i=0;i<data.skills.size();i++){
            bw.newLine();
            bw.write("   ✤"+data.skills.get(i));
        }

        bw.newLine();
        bw.newLine();

        bw.write("EDUCATION");
        for(int i=0;i<data.education.size();i++){
            bw.newLine();
            bw.write("   ✤"+data.education.get(i));
        }

        bw.newLine();
        bw.newLine();

        bw.write("ACADEMIC PROJECT");
        for(int i=0;i<data.academicProject.size();i++){
            bw.newLine();
            bw.write("   ✤"+data.academicProject.get(i));
        }

        bw.newLine();
        bw.newLine();

        bw.write("HOBBIES");
        for(int i=0;i<data.hobbies.size();i++){
            bw.newLine();
            bw.write("   ▫︎"+data.hobbies.get(i));
        }

        bw.flush();
        bw.close();
    }

    class MyException extends Exception{
        MyException(String msg){
            super(msg);
        }
    }

    public static void read_Resume() throws IOException{
        sc.nextLine();
        System.out.print("Enter File Name : ");
        String fileName=sc.nextLine();
        File f=new File(fileName);
        if(f.isFile()){
            FileReader fr=new FileReader(f);
            BufferedReader br=new BufferedReader(fr);

            String line=br.readLine();
            while(line!=null){
                System.out.println(line);
                line=br.readLine();
            }
        }
    }
}