package com.ottogi.be.friend.repository;

import com.ottogi.be.friend.domain.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    @Query("""
        SELECT CASE WHEN COUNT(f) > 0 THEN TRUE ELSE FALSE END
        FROM Friend f
        WHERE (f.sender.id = :myId AND f.receiver.id = :friendId)
        OR (f.sender.id = :friendId AND f.receiver.id = :myId)
    """)
    Boolean isFriend(@Param("myId") Long myId, @Param("friendId") Long friendId);

    @Query(value = """
            (SELECT f.sender_id
            FROM friend f
            WHERE f.receiver_id = :memberId)
            UNION
            (SELECT f.receiver_id
            FROM friend f
            WHERE f.sender_id = :memberId)
            """, nativeQuery = true)
    List<Long> findByMemberId(@Param("memberId") Long memberId);

}
