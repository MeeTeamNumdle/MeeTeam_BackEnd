package synk.meeteam.global.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UnescapedFieldSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // HTML escaping을 하지 않고 그대로 출력
        gen.writeRawValue("\"" + value + "\"");
    }
}

