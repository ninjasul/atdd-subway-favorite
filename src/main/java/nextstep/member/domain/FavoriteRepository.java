package nextstep.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findByMember(Member member);

    void deleteByMemberAndId(Member member, Long id);
}