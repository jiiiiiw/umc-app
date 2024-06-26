package umcstudybook.spring.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umcstudybook.spring.apiPayload.code.status.ErrorStatus;
import umcstudybook.spring.apiPayload.exception.handler.MemberHandler;
import umcstudybook.spring.domain.Member;
import umcstudybook.spring.domain.Review;
import umcstudybook.spring.repository.MemberRepository;
import umcstudybook.spring.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryServiceImpl implements MemberQueryService{

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public boolean isExist(Long id) {
        return memberRepository.existsById(id);
    }

    @Override
    public Page<Review> getReviews(Long memberId, Integer page) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        Pageable pageable = PageRequest.of(page, 10);
        return reviewRepository.findReviewsByMemberIsOrderByCreatedAtDesc(pageable, member);
    }
}
