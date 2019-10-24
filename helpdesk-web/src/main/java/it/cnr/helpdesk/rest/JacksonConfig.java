package it.cnr.helpdesk.rest;

import it.cnr.helpdesk.valueobjects.ComponentValueObject;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JacksonConfig extends JacksonJaxbJsonProvider {

    public JacksonConfig() {
          ObjectMapper mapper = new ObjectMapper();
          mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
          mapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
          mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
          mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
          mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
          mapper.addMixInAnnotations(ComponentValueObject.class, AbstractMixIn.class);
//          if (Boolean.getBoolean("it.cnr.oil.json.indented"))
//        	  mapper.enable(SerializationFeature.INDENT_OUTPUT);
          super.setMapper(mapper);
    }
}