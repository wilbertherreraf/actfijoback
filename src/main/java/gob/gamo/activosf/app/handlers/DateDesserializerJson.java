package gob.gamo.activosf.app.handlers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import gob.gamo.activosf.app.utils.UtilsDate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateDesserializerJson extends StdDeserializer<Date> {
    private SimpleDateFormat formatterDateTime = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    private SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");    
    public DateDesserializerJson() {
        this(null);
    }

    public DateDesserializerJson(Class<?> vc) {
        super(vc);
    }

    @Override
    public Date deserialize(JsonParser jsonparser, DeserializationContext ctxt) throws IOException, JacksonException {
        String date = jsonparser.getText();
        try {
  //          Date dd = UtilsDate.dateFromString(jsonparser.getText(), "yyyy-MM-dd");
            //Date d = formatterDate.parse(date);
//            log.info("deserializer [{}] {} {} => dd: {} new {}", jsonparser.getText(), date, d, dd, UtilsDate.stringFromDate(dd, "dd/MM/yyyy"));
            if  (!StringUtils.isBlank(date)){
                return formatterDate.parse(date);
            } else {
                return null;
            }
        } catch (ParseException e) {
            log.error("Error al deserealizar fecha " + date);
            return null;
            //throw new RuntimeException(e);
        }
    }
}
