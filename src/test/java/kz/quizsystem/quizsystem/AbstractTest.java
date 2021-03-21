package kz.quizsystem.quizsystem;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(
        classes ={QuizsystemApplication.class}
)
@WebAppConfiguration
public class AbstractTest {
    protected MockMvc mvc;
    @Autowired
    WebApplicationContext webApplicationContext;

    public AbstractTest(){}

    protected void setUp(){this.mvc= MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    protected String mapToJson(Object o)throws JsonProcessingException{
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(o);
    }
}
