package me.jiangminghan.springdeveloper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = "spring.sql.init.mode=never")
class MemberRepositoryTest {
    @Autowired
    TestRepository memberRepository;

    @Sql("/insert-members.sql")
    @Test
    void getAllMembers(){
        //when
        List<Member> members = memberRepository.findAll();

        //then
        assertThat(members).hasSize(3);
    }

    @Sql("/insert-members.sql")
    @Test
    void getMemberById(){
        //when
        Member member = memberRepository.findById(2L).orElseThrow();

        //then
        assertThat(member.getName()).isEqualTo("b");
    }

    @Sql("/insert-members.sql")
    @Test
    void getMemberByName() {
        //when
        Member member = memberRepository.findByName("C").orElseThrow();

        //then
        assertThat(member.getId()).isEqualTo(3L);
    }

    @DisplayName("레코드 삽입 테스트")
    @Test
    void saveMember(){
        //given
        Member m = new Member("scpark", "scpark@test.com");

        //when
        Member saved = memberRepository.save(m);

        //then
        assertThat(memberRepository.findById(saved.getId())).isPresent();
        Member savedMember = memberRepository.findById(saved.getId()).orElseThrow();
        assertThat(savedMember.getName()).isEqualTo("scpark");
        assertThat(savedMember.getEmail()).isEqualTo("scpark@test.com");
    }
}
