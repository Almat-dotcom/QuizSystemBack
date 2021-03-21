package kz.quizsystem.quizsystem;

import kz.quizsystem.quizsystem.model.Categories;
import kz.quizsystem.quizsystem.repository.CategoryRepository;
import kz.quizsystem.quizsystem.service.CategoryService;
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

public class CategoryTest extends AbstractTest {
    @Autowired
    private MongoTemplate template;
    @Autowired
    private CategoryRepository repository;
    @Autowired
    private CategoryService service;
    private static final String BASE_API="/api/categories";
    private Categories document;
    private Optional optDoc;

    private CategoryTest(){}

    @BeforeEach
    public void initTest(){super.setUp();}

    @AfterEach
    public void tearDown(){this.template.getMongoDatabaseFactory().getMongoDatabase().drop();}

    private Categories createCategory(){
        Categories c=new Categories();
        c.setName("Fantasy");
        return c;
    }

    @Test
    public void GIVEN_CATEGORY_WHEN_CREATE_THEN_SUCCESS()throws Exception{
        long databaseSizeBefore=this.repository.count();
        this.document=this.createCategory();
        String inputJson=super.mapToJson(this.document);
        ResultActions result=this.mvc.perform(MockMvcRequestBuilders.post(BASE_API,new Object[0]).contentType("application/json").content(inputJson));
        result.andExpect(MockMvcResultMatchers.status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath("$.name",new Object[0]).value("Fantasy"));
        long databaseSizeAfter=this.repository.count();
        Assertions.assertEquals(databaseSizeAfter,databaseSizeBefore+1L);
    }

    @Test
    public void GIVEN_EMPTY_CATEGORY_NAME_WHEN_CREATE_THEN_FAILURE() throws Exception {
        this.document = this.createCategory();
        this.document.setName("");
        String inputJson = super.mapToJson(this.document);
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.post("/api/categories", new Object[0]).contentType("application/json").content(inputJson));
        result.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void GIVEN_CATEGORY_WHEN_UPDATE_THEN_SUCCESS() throws Exception {
        this.document = this.createCategory();
        this.document = (Categories) this.template.save(this.document);
        this.document.setName("Fantasyks");
        String inputJson = super.mapToJson(this.document);
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.put("/api/categories", new Object[0]).contentType("application/json").content(inputJson));
        result.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.name", new Object[0]).value("Fantasyks"));
    }

    @Test
    public void GIVEN_CATEGORY_WHEN_UPDATE_THEN_FAILURE() throws Exception {
        this.document = this.createCategory();
        this.document = (Categories) this.template.save(this.document);
        this.document.setName((String)null);
        String inputJson = super.mapToJson(this.document);
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.put("/api/categories", new Object[0]).contentType("application/json").content(inputJson));
        result.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void GIVEN_CATEGORY_WHEN_GET_THEN_SUCCESS() throws Exception {
        this.document = this.createCategory();
        this.document = (Categories) this.template.save(this.document);
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.get("/api/categories/{id}", new Object[]{this.document.getId()}).contentType("application/json"));
        result.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.name", new Object[0]).value(this.document.getName()));
    }

    @Test
    public void GIVEN_CATEGORIES_WHEN_GET_ALL_THEN_SUCCESS() throws Exception {
        this.document = this.createCategory();
        this.document = (Categories) this.template.save(this.document);
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.get("/api/categories", new Object[0]).contentType("application/json"));
        result.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.[*].name", new Object[0]).value(this.document.getName()));
    }

    @Test
    public void GIVEN_CATEGORY_ID_WHEN_DELETE_THEN_SUCCESS() throws Exception {
        this.document = this.createCategory();
        this.document = (Categories) this.template.save(this.document);
        long databaseSizeBefore = this.repository.count();
        ResultActions result = this.mvc.perform(MockMvcRequestBuilders.delete("/api/categories/{id}", new Object[]{this.document.getId()}).contentType("application/json"));
        result.andExpect(MockMvcResultMatchers.status().isOk());
        long databaseSizeAfter = this.repository.count();
        Assert.assertEquals(databaseSizeBefore, databaseSizeAfter + 1L);
    }
}
