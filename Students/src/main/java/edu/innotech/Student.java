package edu.innotech;

import lombok.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ToString @EqualsAndHashCode
public class Student {
   /* @Setter
    private StudentRepo repo;*/

    @Getter @Setter
    private String name;
    private List<Integer> marks = new ArrayList<>();

    public Student(String name) {
        this.name = name;
    }

    public List<Integer> getMarks() {

        return List.copyOf(marks);

        /*return marks;*/
    }

    public void name() {
        System.out.println();;
    }
    @SneakyThrows
    public void addMark(int mark) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://localhost:5352/checkMark?mark="+mark);
        CloseableHttpResponse httpResponse = httpClient.execute(request);
        HttpEntity entity = httpResponse.getEntity();
        if(!Boolean.parseBoolean(EntityUtils.toString(entity))){
            throw new IllegalArgumentException(mark + " is wrong mark");
        }
        marks.add(mark);

        /*if(mark<2||mark>5) {
            throw new IllegalArgumentException("wrong mark "+mark);
        }
        marks.add(mark);*/
    }

    @SneakyThrows
    public int rating() {
        /*Connection con = DriverManager.getConnection("jdbc:postgresql://localhost/StudentDB");
        ResultSet rs = con.createStatement().executeQuery("SELECT and other staff");
        rs.next();
        return rs.getInt("pos");*/

        /*return repo.getRatingForGradeSum(
                marks.stream()
                        .mapToInt(x->x)
                        .sum()
        );*/
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://localhost:5352/educ?sum="+marks.stream().mapToInt(x->x).sum());
        CloseableHttpResponse httpResponse = httpClient.execute(request);
        HttpEntity entity = httpResponse.getEntity();
        return Integer.parseInt(EntityUtils.toString(entity));
    }
}
