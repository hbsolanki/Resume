package Database;
import Resume.Resume;
import java.sql.*;
import java.util.ArrayList;

public class DB {

    private static Connection getCon()throws Exception{
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/resumes", "root","");
    }
    
    //resume(name varchar(50),phone varchar(20),email varhcar(50),github varhcar(50),linkedin(50),skills varchar(10000),education varchar(10000),academicproject varchar(10000),hobbies varchar(10000))
    public static void resumeStoreInDB(Resume r)throws Exception{
        Connection con=getCon();

        String sql="insert into resume(name,phone,email,github,linkedin,skills,education,academicproject,hobbies) values(?,?,?,?,?,?,?,?,?)";
        PreparedStatement pst=con.prepareStatement(sql);
        pst.setString(1, r.name);
        pst.setString(2, r.phone);
        pst.setString(3, r.email);
        pst.setString(4, r.github);
        pst.setString(5, r.linkedIn);

        String skill="";
        for(int i=0;i<r.skills.size();i++){
            skill+=r.skills.get(i)+";";
        }
        pst.setString(6, skill);

        String edu="";
        for(int i=0;i<r.education.size();i++){
            skill+=r.education.get(i)+";";
        }
        pst.setString(7, edu);

        String aca="";
        for(int i=0;i<r.academicProject.size();i++){
            skill+=r.academicProject.get(i)+";";
        }
        pst.setString(8, aca);

        String hobb="";
        for(int i=0;i<r.hobbies.size();i++){
            skill+=r.hobbies.get(i)+";";
        }
        pst.setString(9, hobb);

        pst.executeUpdate();
    }

    public static ArrayList<Resume> allResumeFromDB()throws Exception{
        ArrayList<Resume> allresume=new ArrayList<>();

        String sql="select * from resume";
        Connection con=getCon();
        PreparedStatement pst=con.prepareStatement(sql);
        ResultSet rs=pst.executeQuery();

        while(rs.next()){
            String name=rs.getString(1);
            String phone=rs.getString(2);
            String email=rs.getString(3);
            String github=rs.getString(4);
            String linkedin=rs.getString(5);

            String skil=rs.getString(6);
            String edu=rs.getString(7);
            String aca=rs.getString(8);
            String hobb=rs.getString(9);

            Resume r=new Resume(name,phone,email,github, linkedin,removeColon(skil), removeColon(edu), removeColon(aca), removeColon(hobb));
            allresume.add(r);
        }

        return allresume;
    }

    public static ArrayList<String> removeColon(String name){
        ArrayList<String> list=new ArrayList<>();
        String []arr=name.split(";");

        for(int i=0;i<arr.length;i++){
            list.add(arr[i]);
        }

        return list;
    }

    public static void updateResume(Resume r)throws Exception{
        Connection con=getCon();
        String sql="delete from resume where name=?";
        PreparedStatement pst=con.prepareStatement(sql);
        pst.setString(1, r.name);
        pst.executeUpdate();
        resumeStoreInDB(r);
    }
}
