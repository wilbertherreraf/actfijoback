package gob.gamo.activosf.app.handlers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateSerializerJson extends StdSerializer<Date> {

    private SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat formatterDateTime = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

    public DateSerializerJson() {
        this(null);
    }

    public DateSerializerJson(Class t) {
        super(t);
    }

    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider arg2)
            throws IOException, JsonProcessingException {
        if (value != null) {
            String d = formatterDate.format(value);
            // log.info("serialization {} {} - > {}", value, d,
            // UtilsDate.stringFromDate(value, "dd/MM/yyyy"));
            gen.writeString(d);
        }
    }
}
