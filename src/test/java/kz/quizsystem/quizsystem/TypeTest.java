package kz.quizsystem.quizsystem;

import kz.quizsystem.quizsystem.model.Categories;
import kz.quizsystem.quizsystem.model.Types;
import kz.quizsystem.quizsystem.repository.CategoryRepository;
import kz.quizsystem.quizsystem.repository.TypeRepository;
import kz.quizsystem.quizsystem.service.CategoryService;
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

public class TypeTest extends AbstractTest {
    @Autowired
    private MongoTemplate template;
    @Autowired
    private TypeRepository repository;
    @Autowired
    private TypeService service;
    private static final String BASE_API="/api/types";
    private Types document;
    private Optional optDoc;

    private TypeTest(){}

    @BeforeEach
    public void initTest(){super.setUp();}

    @AfterEach
    public void tearDown(){this.template.getMongoDatabaseFactory().getMongoDatabase().drop();}

    private Types createType(){
        Types t=new Types();
        t.setName("Biologia");
        return t;
    }

    @Test
    public void GIVEN_TYPE_WHEN_CREATE_THEN_SUCCESS()throws Exception{
        long databaseSizeBefore=this.repository.count();
        this.document=this.createType();
        String inputJson=super.mapToJson(this.document);
        ResultActions result=this.mvc.perform(MockMvcRequestBuilders.post(BASE_API,new Object[0]).contentType("application/json").content(inputJson));
        result.andExpect(MockMvcResultMatchers.status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath("$.name",new Object[0]).value("Biologia"));
        long databaseSizeAfter=this.repository.count();
        Assertions.assertEquals(databaseSizeAfter,databaseSizeBefore+1L);
    }

    @Test
    public void GIVEN_EMPTY_TYPE_NAME_WHEN_CREATE_THEN_FAILURE() throws Exception {
        this.document = this.createType();
        this.document.setName("");
        String inputJson = super.mapToJson(this.document);
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.post(BASE_API, new Object[0]).contentType("application/json").content(inputJson));
        result.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void GIVEN_TYPE_WHEN_UPDATE_THEN_SUCCESS() throws Exception {
        this.document = this.createType();
        this.document = (Types) this.template.save(this.document);
        this.document.setName("Fizika");
        String inputJson = super.mapToJson(this.document);
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.put(BASE_API, new Object[0]).contentType("application/json").content(inputJson));
        result.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.name", new Object[0]).value("Fizika"));
    }

    @Test
    public void GIVEN_TYPE_WHEN_UPDATE_THEN_FAILURE() throws Exception {
        this.document = this.createType();
        this.document = (Types) this.template.save(this.document);
        this.document.setName((String)null);
        String inputJson = super.mapToJson(this.document);
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.put(BASE_API, new Object[0]).contentType("application/json").content(inputJson));
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void GIVEN_TYPE_WHEN_GET_THEN_SUCCESS() throws Exception {
        this.document = this.createType();
        this.document = (Types) this.template.save(this.document);
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.get("/api/types/{id}", new Object[]{this.document.getId()}).contentType("application/json"));
        result.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.name", new Object[0]).value(this.document.getName()));
    }

    @Test
    public void GIVEN_TYPES_WHEN_GET_ALL_THEN_SUCCESS() throws Exception {
        this.document = this.createType();
        this.document = (Types) this.template.save(this.document);
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.get(BASE_API, new Object[0]).contentType("application/json"));
        result.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.[*].name", new Object[0]).value(this.document.getName()));
    }

    @Test
    public void GIVEN_TYPE_ID_WHEN_DELETE_THEN_SUCCESS() throws Exception {
        this.document = this.createType();
        this.document = (Types) this.template.save(this.document);
        long databaseSizeBefore = this.repository.count();
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.delete("/api/types/{id}", new Object[]{this.document.getId()}).contentType("application/json"));
        result.andExpect(MockMvcResultMatchers.status().isOk());
        long databaseSizeAfter = this.repository.count();
        Assert.assertEquals(databaseSizeBefore, databaseSizeAfter + 1L);
    }
}
