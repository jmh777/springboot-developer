package me.scpark.springdeveloper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {
    @Autowired
    private  TestService testService;

    @GetMapping("/test")
    public ResponseEntity<List<Member>> getAllMembers(){
    //public List<Member> getAllMembers(){


        return ResponseEntity.ok(testService.getAllMembers());
        //return testService.getAllMembers();
    }

    @PostMapping("/test")
    public ResponseEntity<Member> createMember(@RequestBody Member member){
        return  ResponseEntity.ok(testService.saveMember(member));
        //return testService.saveMember(member);
    }

    @GetMapping("/test2")
    public ResponseEntity<String> test(){
        return new  ResponseEntity<String>("Hello World", HttpStatus.CREATED);
    }
}
