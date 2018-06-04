package cn.edu.hit.cs.zhycryptdemo.interceptor;

import cn.edu.hit.cs.zhycryptdemo.common.AESUtils;
import cn.edu.hit.cs.zhycryptdemo.model.EmployeeEnc2;
import cn.edu.hit.cs.zhycryptdemo.vo.req.EmployeeAddReqVo;
import cn.edu.hit.cs.zhycryptdemo.vo.req.EmployeeListReqVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Properties;

@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
@Component
public class SqlLogInterceptor implements Interceptor {

    static int MAPPED_STATEMENT_INDEX = 0;// 这是对应上面的args的序号
    static int PARAMETER_INDEX = 1;
    static int ROWBOUNDS_INDEX = 2;
    static int RESULT_HANDLER_INDEX = 3;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        final Object[] queryArgs = invocation.getArgs();
        final MappedStatement mappedStatement = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
        Object parameter = queryArgs[PARAMETER_INDEX];
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);

        String originalSql = boundSql.getSql();


        switch (mappedStatement.getSqlCommandType()) {
            case SELECT:
                if (originalSql.contains("employee_enc2")) {
                    EmployeeListReqVo tempParameter = (EmployeeListReqVo) parameter;
                    if (!StringUtils.isEmpty(tempParameter.getName())) {
                        tempParameter.setName(AESUtils.encrypt(tempParameter.getName()));
                    }
                    parameter = tempParameter;
                }
                break;
            case INSERT:
            case UPDATE:
                if (originalSql.contains("employee_enc2")) {
                    EmployeeEnc2 tempParameter = (EmployeeEnc2) parameter;
                    tempParameter.setNameEnc(AESUtils.encrypt(tempParameter.getNameEnc()));
                    tempParameter.setAgeEnc(AESUtils.encrypt(tempParameter.getAgeEnc()));
                    tempParameter.setTelEnc(AESUtils.encrypt(tempParameter.getTelEnc()));
                    tempParameter.setIdnumberEnc(AESUtils.encrypt(tempParameter.getIdnumberEnc()));
                    tempParameter.setIncomeEnc(AESUtils.encrypt(tempParameter.getIncomeEnc()));
                    tempParameter.setMonthEnc(AESUtils.encrypt(tempParameter.getMonthEnc()));
                    tempParameter.setOutcomeEnc(AESUtils.encrypt(tempParameter.getOutcomeEnc()));
                    parameter = tempParameter;
                }
                break;
            default:
                break;
        }

        boundSql = mappedStatement.getBoundSql(parameter);

        String newSql = originalSql;
        /*if (originalSql.contains("employee_enc2")){
            newSql = replaceEmployeeEnc2SelectSql(originalSql);
        }

        log.debug("\noriginalSql \n {} ; \nnewSql \n {}", originalSql, newSql);*/
        // 重新new一个查询语句对像
        BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), newSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
        // 把新的查询放到statement里
        MappedStatement newMs = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
        Object returnValue = invocation.proceed();
        log.debug("returnValue Class : {}", returnValue.getClass());
        switch (mappedStatement.getSqlCommandType()) {
            case SELECT:
                if (originalSql.contains("employee_enc2")) {
                    List<EmployeeEnc2> employeeEnc2List = (List<EmployeeEnc2>) returnValue;
                    employeeEnc2List.forEach(o -> {
                                o.setNameEnc(AESUtils.decrypt(o.getNameEnc()));
                                o.setAgeEnc(AESUtils.decrypt(o.getAgeEnc()));
                                o.setTelEnc(AESUtils.decrypt(o.getTelEnc()));
                                o.setIdnumberEnc(AESUtils.decrypt(o.getIdnumberEnc()));
                                o.setIncomeEnc(AESUtils.decrypt(o.getIncomeEnc()));
                                o.setMonthEnc(AESUtils.decrypt(o.getMonthEnc()));
                                o.setOutcomeEnc(AESUtils.decrypt(o.getOutcomeEnc()));
                            }
                    );
                    returnValue = employeeEnc2List;
                }
                break;
            case INSERT:
            case UPDATE:
                break;
            default:
                break;
        }
        return returnValue;

    }

    @Override
    public Object plugin(Object o) {
        // TODO Auto-generated method stub
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // TODO Auto-generated method stub
    }

    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length > 0) {
            builder.keyProperty(ms.getKeyProperties()[0]);
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }

    public static class BoundSqlSqlSource implements SqlSource {
        private BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }

    /*private String replaceEmployeeEnc2SelectSql(String originalSql) {
        String newSql = originalSql
                .replaceAll(",name,", ",name_enc,")
                .replaceAll("and name LIKE", "and name_enc LIKE")
                .replaceAll(",age,", ",age_enc,")
                .replaceAll(",tel,", ",tel_enc,")
                .replaceAll(",idnumber,", ",idnumber_enc,")
                .replaceAll(",income,", ",income_enc,")
                .replaceAll(",month,", ",month_enc,")
                .replaceAll(",outcome,", ",outcome_enc,");
        return newSql;
    }*/
}
