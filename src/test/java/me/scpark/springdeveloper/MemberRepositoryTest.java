package me.scpark.springdeveloper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = "spring.sql.init.mode=never")
public class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Sql("/insert-members.sql")
    @Test
    void getAllMembers(){
        //given

        //when
        List<Member>members = memberRepository.findAll();

        //then
        assertThat(members.size()).isEqualTo(3);
    }


    @Sql("/insert-members.sql")
    @Test
    void getMemberById(){
        //given

        //when
        Member member = memberRepository.findById(2L).get();

        //then
        assertThat(member.getName()).isEqualTo("B");
    }

    @Sql("/insert-members.sql")
    @Test
    void getMemberByName() {
        //given


        //when
        Member member = memberRepository.findByName("C").get();

        //then
        assertThat(member.getId()).isEqualTo(3);
    }

    @DisplayName("레코드 십입 테스트")
    @Test
    void saveMember(){
        //given
        Member m = new Member("scpark");

        //when
        Member savedMember = memberRepository.save(m);


        //then
        //Optional<Member>
        assertThat(savedMember.getId()).isNotNull();

        Long id = savedMember.getId();
        Optional<Member> result = memberRepository.findById(id);
        Member member = result.get();
        String name = member.getName();
        assertThat(name).isEqualTo("scpark");
        //assertThat(memberRepository.findById(savedMember.getId()).get().getName()).isEqualTo("scpark");

    }
    @DisplayName("2개의 레코드를 한 번에 삽입하는 테스트")
    @Test
    void saveMembers(){

        //given
        List<Member> members = List.of(new Member("HongGilDong"),
                    new Member("Park Munsu"));


        //when
        memberRepository.saveAll(members);

        //then
        assertThat(memberRepository.findAll().size()).isEqualTo(2);
    }

    @Sql("/insert-members.sql")
    @DisplayName("레코드 삭제 데스트")
    @Test
    void deleteAll(){
        //given
        //when
        memberRepository.deleteAll();
        assertThat(memberRepository.findAll().size()).isZero();
        //then
    }
}





















