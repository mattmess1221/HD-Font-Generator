package mnm.hdfontgen.pack.provider;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.JsonAdapter;
import mnm.hdfontgen.pack.PackSettings;
import mnm.hdfontgen.pack.ResourcePath;
import mnm.hdfontgen.pack.resource.Resource;

import java.lang.reflect.Type;

@JsonAdapter(FontProvider.Serializer.class)
public abstract class FontProvider<T extends PackSettings> {
    public final String type;

    FontProvider(String type) {
        this.type = type;
    }

    public abstract Resource[] getResources(T settings);

    static class Serializer implements JsonSerializer<FontProvider<?>> {

        @Override
        public JsonElement serialize(FontProvider<?> src, Type typeOfSrc, JsonSerializationContext context) {
            return context.serialize(src, src.getClass());
        }
    }
}
