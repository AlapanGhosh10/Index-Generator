import java.util.*;
import java.io.*;

public class ListMaker {
    private static String getHeading(String s) {
        int i, j;
        for(j = s.length() - 1 ; j >= 0 ; j--)
            if(s.charAt(j) == '/')
                break;
        s = s.substring(j + 1);
        s = s.replace("_", " ");
        s = s.replace("-", " ");
        s = s.trim();
        for(i = 0 ; i < s.length() ; i++)
            if(Character.isAlphabetic(s.charAt(i)))
                break;
        s = s.substring(i);
        return s;
    }
    private static String createHeading() {
        String s = "Heading";
        try {
            File ls = new File("head.txt");
            Scanner sc = new Scanner(ls);
            s = sc.nextLine();
            s = getHeading(s);
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("An Error occurred");
            e.printStackTrace();
        }
        return s;
    }
    private static String getFileName(String s) {
        s = s.replace("_", " ");
        s = s.replace("-", " ");
        s = s.trim();
        int i, j;
        for(i = 0 ; i < s.length() ; i++)
            if(Character.isAlphabetic(s.charAt(i)))
                break;
        for(j = s.length() - 1 ; j >= 0 ; j--)
            if(s.charAt(j) == '.')
                break;
        return s.substring(i, j);
    }

    private static List<String> createNamesList(List<String> list) {
        List<String> temp = new ArrayList<>();
        for(String file : list)
            temp.add(getFileName(file));
        return temp;
    }

    private static boolean isFile(String s) {
        for(int i = 0 ; i < s.length() ; i++)
            if(s.charAt(i) == '.')
                return true;
        return false;
    }
    private static List<String> createFileNamesList(List<String> sysFiles) {
        List<String> temp = new ArrayList<>();
        try {
            File ls = new File("list.txt");
            Scanner sc = new Scanner(ls);
            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                if(!sysFiles.contains(line) && isFile(line))
                    temp.add(line);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("An Error occurred");
            e.printStackTrace();
        }
        Comparator<String> cmp = new Comparator<>() {
            public int compare(String s1, String s2) {
                if(Character.isDigit(s1.charAt(0)) && Character.isDigit(s2.charAt(0))) {
                    int i = 0;
                    Integer num1, num2;
                    while(Character.isDigit(s1.charAt(i)))
                        i++;
                    num1 = Integer.valueOf(s1.substring(0, i));
                    i = 0;
                    while(Character.isDigit(s2.charAt(i)))
                        i++;
                    num2 = Integer.valueOf(s2.substring(0, i));
                    return num1.compareTo(num2);
                } else {
                    return s1.compareTo(s2);
                }
            }
        };
        Collections.sort(temp, cmp);
        return temp;
    }

    private static List<String> createSysFiles() {
        List<String> temp = new ArrayList<>();
        temp.add("run.sh");
        temp.add("ListMaker.java");
        temp.add("list.txt");
        temp.add("ListMaker.class");
        temp.add("List_Maker.iml");
        return temp;
    }

    private static String createHtmlContent(List<String> list, List<String> names, String heading) {
        String content;
        String head = "<head><title>" + heading + "</title></head>";
        String body = "<body><h1>" + heading + "</h1><hr/>";
        for(int i = 0 ; i < list.size() ; i++)
            body = body.concat("<a target=\"_blank\" href=\"" + list.get(i) + "\">" + names.get(i) + "</a><br/>");
        body = body.concat("</body>");
        content = "<html>" + head + body + "</html>";
        return content;
    }

    private static void createIndexFile(List<String> list, List<String> names, String heading) {
        try {
            FileWriter fw = new FileWriter(heading + ".html");
            String fileContents = createHtmlContent(list, names, heading);
            fw.write(fileContents);
            fw.close();
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<String> sysFiles = createSysFiles();
        List<String> list = createFileNamesList(sysFiles);
        List<String> names = createNamesList(list);
        String heading = createHeading();
//        System.out.println(list);
//        System.out.println(names);
//        System.out.println(heading);
        createIndexFile(list, names, heading);
    }
}