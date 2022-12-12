package edu.innotech;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@WireMockTest(httpPort = 5352)
public class WebTests {
    /*WireMockServer wireMockServer;

    @BeforeEach
    void starter() {
        wireMockServer = new WireMockServer(5352);
        wireMockServer.start();
    }

    @AfterEach
    void stopper() {
        wireMockServer.stop();
    }*/

    @Test
    public void testRaiting() {
        configureFor("localhost", 5352);
        stubFor(get(urlMatching("/educ\\?sum=([2-5]*)"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("42"))
        );
        Student stud = new Student("vasia");
        stud.addMark(5);
        /*StudentRepo repo = Mockito.mock(StudentRepo.class);
        stud.setRepo(repo);
        Mockito.when(repo.getRaitingForGradeSum(Mockito.anyInt()))
                        .thenReturn(10);*/
        Assertions.assertEquals(42, stud.rating());
    }

    @ParameterizedTest(name="Добавление неверных оценок кидает исключение")
    @MethodSource(value = "edu.innotech.MarksGenerator#ints")
    public void testCorrectMarkAdding(int x) {
        configureFor("localhost", 5352);
        stubFor(get(urlMatching("/checkMark\\?mark="+x))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withBody(String.valueOf(Boolean.FALSE)))
        );
        Student stud = new Student("vasia");
        Assertions.assertThrows(IllegalArgumentException.class, () -> stud.addMark(x));
    }

    @RepeatedTest(value = 4, name = "Корректные оценки добавляются в список {currentRepetition}")
    public void testWrongMarkAdding(RepetitionInfo repetitionInfo) {
        int num = repetitionInfo.getCurrentRepetition() + 1;
        configureFor("localhost", 5352);
        stubFor(get(urlMatching("/checkMark\\?mark="+num))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(String.valueOf(Boolean.TRUE)))
        );
        Student stud = new Student("vasia");
        assertAll(
           () -> Assertions.assertDoesNotThrow(() -> stud.addMark(num)),
           () -> Assertions.assertEquals(stud.getMarks().get(0),num)
        );
    }
}
