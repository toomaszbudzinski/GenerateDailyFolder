package sample;


import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Main {

    private static LocalDate today;
    private static LocalDate yesterDay;
    private static LocalDate dayBefyesterDay;
    private static File currentDirectory;
    private static File archiveDirectory;


    public static void main(String[] args) throws InterruptedException{
        while(true) {
            today = LocalDate.now();
            yesterDay = today.minusDays(1);
            dayBefyesterDay = today.minusDays(2);
            try {
                try {
                    currentDirectory = new File(new File(".").getCanonicalPath());
                    archiveDirectory = new File(new File(".").getCanonicalPath(),"\\Archived");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                createFolder("Archived",true);
                createFolder(today.toString(),false);

                move(archiveDirectory,currentDirectory, yesterDay.toString(), dayBefyesterDay.toString(),today.toString());

                Thread.sleep(18000000); //10 hours
                System.out.println("Five hours have passed" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted!");
            }
        }
    }

    private static void move(File toDir, File currDir, String date1, String date2, String date3) {
        for (File file : currDir.listFiles()) {
            if (!(file.isDirectory() && file.getName().equals("Archived"))
                && !(file.isDirectory() && file.getName().equals(date1))
                && !(file.isDirectory() && file.getName().equals(date2))
                && !(file.isDirectory() && file.getName().equals(date3))
                && !(file.getName().contains(".jar"))
            ) {

                Path pathobj1 = Paths.get(currDir.getPath().toString(),file.getName().toString());
                Path pathobj2= Paths.get(toDir.getPath(),pathobj1.getFileName().toString());
                try {
                    Files.move(pathobj1 , pathobj2, REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }


    public static void createFolder(String date, boolean isArchiveFolder){
        if(!(today.getDayOfWeek().toString().toLowerCase().equals("saturday") || today.getDayOfWeek().toString().toLowerCase().equals("sunday"))){

            File fileActual = new File(date);
            File fileOld = new File("Archived");

            if(isArchiveFolder){
                if (!fileOld.exists()) {
                    System.out.println("Creating directory: " + fileOld.getName());
                    fileOld.mkdir();
                }
            }else{
                if (!fileActual.exists()) {
                    System.out.println("Creating directory: " + fileActual.getName()+"  at: " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
                    fileActual.mkdir();
                }

            }
        }
    }
}
