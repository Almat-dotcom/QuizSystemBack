package kz.quizsystem.quizsystem;

import kz.quizsystem.quizsystem.model.Categories;
import kz.quizsystem.quizsystem.model.Questions;
import kz.quizsystem.quizsystem.model.Types;
import kz.quizsystem.quizsystem.repository.QuestionRepository;
import kz.quizsystem.quizsystem.repository.TypeRepository;
import kz.quizsystem.quizsystem.service.QuestionService;
import kz.quizsystem.quizsystem.service.TypeService;
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

public class QuestionTest extends AbstractTest {
    @Autowired
    private MongoTemplate template;
    @Autowired
    private QuestionRepository repository;
    @Autowired
    private QuestionService service;
    private static final String BASE_API="/api/questions";
    private Questions document;
    private Optional optDoc;

    private QuestionTest(){}

    @BeforeEach
    public void initTest(){super.setUp();}

    @AfterEach
    public void tearDown(){this.template.getMongoDatabaseFactory().getMongoDatabase().drop();}

    private Questions createQuestion(){
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
        return q;
    }

    @Test
    public void GIVEN_QUESTION_WHEN_CREATE_THEN_SUCCESS()throws Exception{
        long databaseSizeBefore=this.repository.count();
        this.document=this.createQuestion();
        String inputJson=super.mapToJson(this.document);
        ResultActions result=this.mvc.perform(MockMvcRequestBuilders.post(BASE_API,new Object[0]).contentType("application/json").content(inputJson));
        result.andExpect(MockMvcResultMatchers.status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath("$.text",new Object[0]).value("Some"));
        long databaseSizeAfter=this.repository.count();
        Assertions.assertEquals(databaseSizeAfter,databaseSizeBefore+1L);
    }

    @Test
    public void GIVEN_EMPTY_QUESTION_NAME_WHEN_CREATE_THEN_FAILURE() throws Exception {
        this.document = this.createQuestion();
        this.document.setTypes(null);
        this.document.setText("");
        this.document.setIsActive(null);
        this.document.setImage("");
        this.document.setCategories(null);
        String inputJson = super.mapToJson(this.document);
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.post(BASE_API, new Object[0]).contentType("application/json").content(inputJson));
        result.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void GIVEN_QUESTION_WHEN_UPDATE_THEN_SUCCESS() throws Exception {
        this.document = this.createQuestion();
        this.document = (Questions) this.template.save(this.document);
        this.document.setText("Other");
        String inputJson = super.mapToJson(this.document);
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.put(BASE_API, new Object[0]).contentType("application/json").content(inputJson));
        result.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.text", new Object[0]).value("Other"));
    }

    @Test
    public void GIVEN_QUESTION_WHEN_UPDATE_THEN_FAILURE() throws Exception {
        this.document = this.createQuestion();
        this.document = (Questions) this.template.save(this.document);
        this.document.setText((String)null);
        String inputJson = super.mapToJson(this.document);
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.put(BASE_API, new Object[0]).contentType("application/json").content(inputJson));
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void GIVEN_QUESTION_WHEN_GET_THEN_SUCCESS() throws Exception {
        this.document = this.createQuestion();
        this.document = (Questions) this.template.save(this.document);
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.get("/api/questions/{id}", new Object[]{this.document.getId()}).contentType("application/json"));
        result.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.text", new Object[0]).value(this.document.getText()));
    }

    @Test
    public void GIVEN_QUESTIONS_WHEN_GET_ALL_THEN_SUCCESS() throws Exception {
        this.document = this.createQuestion();
        this.document = (Questions) this.template.save(this.document);
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.get(BASE_API, new Object[0]).contentType("application/json"));
        result.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.[*].text", new Object[0]).value(this.document.getText()));
    }

    @Test
    public void GIVEN_QUESTION_ID_WHEN_DELETE_THEN_SUCCESS() throws Exception {
        this.document = this.createQuestion();
        this.document = (Questions) this.template.save(this.document);
        long databaseSizeBefore = this.repository.count();
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.delete("/api/questions/{id}", new Object[]{this.document.getId()}).contentType("application/json"));
        result.andExpect(MockMvcResultMatchers.status().isOk());
        long databaseSizeAfter = this.repository.count();
        Assert.assertEquals(databaseSizeBefore, databaseSizeAfter + 1L);
    }
}
