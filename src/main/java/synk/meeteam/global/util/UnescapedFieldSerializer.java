package synk.meeteam.global.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class UnescapedFieldSerializer extends JsonSerializer<String> {


    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // 구인글 "content"에 한해서 escape 하지 않고 그대로 출력
        // 잠시 이스케이핑 설정을 on/off 하는 방식
        CharacterEscapes characterEscapes = gen.getCharacterEscapes();
        gen.setCharacterEscapes(null);
        gen.writeString(value);
        gen.setCharacterEscapes(characterEscapes);
    }
}

