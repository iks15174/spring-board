package com.example.board.aop;

import java.util.Collections;
import java.util.List;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/*
transactionManger - DBConfiguration 클래스에 빈으로 등록한 PlatformTransactionManger 객체
EXPRESSION - 어드바이스를 적용할 기준(포인트컷), 비지니스 로직을 수행하는 모든 SerciveImpl 클래스의 모든 메서드를 의미합니다.
rollbackRules - 트랙젝션에서 롤백을 수행하는 규칙입니다. RollbackRuleAttribute의 생성자의 인자로 Exception 클래스를 지정하였는데,
                자바에서 모든 예외는 Exception 클래스를 상속받기 때문에 어떠한 예외가 발생해도 무조건 롤백이 수행됩니다.
setNmae - 트랜젝션의 이름을 설정합니다.
pointcut - AOP의 포인트컷을 설정합니다. EXPRESSION에 지정한 ServiceImpl클래스이 모든 메서를 대상으로 설정합니다.
*/

@Configuration
public class TransactionAspect {
    
    @Autowired
    private PlatformTransactionManager transactionManager;

    private static final String EXPRESSION = "execution(* com.example.board..service.*Impl.*(..))";

    @Bean
	public TransactionInterceptor transactionAdvice() {

		List<RollbackRuleAttribute> rollbackRules = Collections.singletonList(new RollbackRuleAttribute(Exception.class));

		RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
		transactionAttribute.setRollbackRules(rollbackRules);
		transactionAttribute.setName("*");

		MatchAlwaysTransactionAttributeSource attributeSource = new MatchAlwaysTransactionAttributeSource();
		attributeSource.setTransactionAttribute(transactionAttribute);

		return new TransactionInterceptor(transactionManager, attributeSource);
	}

	@Bean
	public Advisor transactionAdvisor() {

		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(EXPRESSION);

		return new DefaultPointcutAdvisor(pointcut, transactionAdvice());
	}
}
