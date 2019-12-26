package Controller;

import com.projectHbase.javaHbase.Question;
import com.projectHbase.javaHbase.QuestionRepository;
import com.projectHbase.javaHbase.Question;
import java.io.IOException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
public class QuestionController {
	
    QuestionRepository questionRepository=new QuestionRepository();
    
    @PostMapping("/")
    public void addQuestion(@RequestBody Question question) throws IOException {
    	questionRepository.save(question);
    }
    
    @GetMapping("/{id}")
    public Question getUser(@PathVariable("id") String id) throws IOException {
        return questionRepository.findById(id);
    }

    @GetMapping("/questions")
    public List<Question> findall() throws IOException {
        return questionRepository.finAll();
    }

    public QuestionRepository getQuestionRepository() {
        return questionRepository;
    }

    public void setUserRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

	
	
	

}
