package global.sunil.covidupdates.rest;

import global.sunil.covidupdates.lib.filters.CorsFilter;
import global.sunil.covidupdates.lib.mappers.*;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Sunil on 2021-05-25 - १३:४२
 */
@ApplicationScoped
@ApplicationPath("/v1/covidupdates")
public class RestApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(CovidUpdateResource.class);
        classes.add(CorsFilter.class);
        classes.add(IllegalArgumentException.class);
        classes.add(AppExceptionMapper.class);
        classes.add(InternalServerExceptionMapper.class);
        classes.add(MethodNotAllowedExceptionMapper.class);
        classes.add(NoSuchElementExceptionMapper.class);
        classes.add(NotAuthorizedExceptionMapper.class);
        classes.add(NullPointerExceptionMapper.class);
        classes.add(UnSupportedMediaTypeExceptionMapper.class);
        return classes;
    }
}
