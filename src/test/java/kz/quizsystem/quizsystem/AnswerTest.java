package kz.quizsystem.quizsystem;

import kz.quizsystem.quizsystem.model.Answers;
import kz.quizsystem.quizsystem.model.Categories;
import kz.quizsystem.quizsystem.model.Questions;
import kz.quizsystem.quizsystem.model.Types;
import kz.quizsystem.quizsystem.repository.AnswerRepository;
import kz.quizsystem.quizsystem.repository.QuestionRepository;
import kz.quizsystem.quizsystem.service.AnswerService;
import kz.quizsystem.quizsystem.service.QuestionService;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

public class AnswerTest extends AbstractTest {
    @Autowired
    private MongoTemplate template;
    @Autowired
    private AnswerRepository repository;
    @Autowired
    private AnswerService service;
    private static final String BASE_API="/api/answers";
    private Answers document;
    private Optional optDoc;

    private AnswerTest(){}

    @BeforeEach
    public void initTest(){super.setUp();}

    @AfterEach
    public void tearDown(){this.template.getMongoDatabaseFactory().getMongoDatabase().drop();}

    private Answers createAnswer(){
        Answers a=new Answers();
        Questions q=new Questions();
        Categories c=new Categories();
        c.setName("Fantasy");
        Types t=new Types();
        t.setName("Fizika");
        q.setCategories(c);
        q.setImage("https://media.sproutsocial.com/uploads/2017/02/10x-featured-social-media-image-size.png");
        q.setIsActive(true);
        q.setText("Some");
        q.setTypes(t);
        a.setIsCorrrect(true);
        a.setPoints("5");
        a.setQuestions(q);
        return a;
    }

    @Test
    public void GIVEN_ANSWER_WHEN_CREATE_THEN_SUCCESS()throws Exception{
        long databaseSizeBefore=this.repository.count();
        this.document=this.createAnswer();
        String inputJson=super.mapToJson(this.document);
        ResultActions result=this.mvc.perform(MockMvcRequestBuilders.post(BASE_API,new Object[0]).contentType("application/json").content(inputJson));
        result.andExpect(MockMvcResultMatchers.status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath("$.points",new Object[0]).value("5"));
        long databaseSizeAfter=this.repository.count();
        Assertions.assertEquals(databaseSizeAfter,databaseSizeBefore+1L);
    }

    @Test
    public void GIVEN_EMPTY_ANSWER_NAME_WHEN_CREATE_THEN_FAILURE() throws Exception {
        this.document = this.createAnswer();
        this.document.setPoints(null);
        this.document.setIsCorrrect(null);
        this.document.setQuestions(null);
        String inputJson = super.mapToJson(this.document);
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.post(BASE_API, new Object[0]).contentType("application/json").content(inputJson));
        result.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void GIVEN_ANSWER_WHEN_UPDATE_THEN_SUCCESS() throws Exception {
        this.document = this.createAnswer();
        this.document = (Answers) this.template.save(this.document);
        this.document.setPoints("10");
        String inputJson = super.mapToJson(this.document);
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.put(BASE_API, new Object[0]).contentType("application/json").content(inputJson));
        result.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.points", new Object[0]).value("10"));
    }

    @Test
    public void GIVEN_ANSWER_WHEN_UPDATE_THEN_FAILURE() throws Exception {
        this.document = this.createAnswer();
        this.document = (Answers) this.template.save(this.document);
        this.document.setPoints((String)null);
        String inputJson = super.mapToJson(this.document);
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.put(BASE_API, new Object[0]).contentType("application/json").content(inputJson));
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void GIVEN_ANSWER_WHEN_GET_THEN_SUCCESS() throws Exception {
        this.document = this.createAnswer();
        this.document = (Answers) this.template.save(this.document);
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.get("/api/answers/{id}", new Object[]{this.document.getId()}).contentType("application/json"));
        result.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.points", new Object[0]).value(this.document.getPoints()));
    }

    @Test
    public void GIVEN_ANSWERS_WHEN_GET_ALL_THEN_SUCCESS() throws Exception {
        this.document = this.createAnswer();
        this.document = (Answers) this.template.save(this.document);
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.get(BASE_API, new Object[0]).contentType("application/json"));
        result.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.[*].points", new Object[0]).value(this.document.getPoints()));
    }

    @Test
    public void GIVEN_ANSWER_ID_WHEN_DELETE_THEN_SUCCESS() throws Exception {
        this.document = this.createAnswer();
        this.document = (Answers) this.template.save(this.document);
        long databaseSizeBefore = this.repository.count();
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.delete("/api/answers/{id}", new Object[]{this.document.getId()}).contentType("application/json"));
        result.andExpect(MockMvcResultMatchers.status().isOk());
        long databaseSizeAfter = this.repository.count();
        Assert.assertEquals(databaseSizeBefore, databaseSizeAfter + 1L);
    }
}
