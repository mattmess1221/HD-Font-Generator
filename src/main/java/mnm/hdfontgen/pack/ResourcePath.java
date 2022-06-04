package mnm.hdfontgen.pack;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.JsonAdapter;

@JsonAdapter(ResourcePath.Serializer.class)
public record ResourcePath(String namespace, String path) implements ZipPath {

    public ResourcePath(String path) {
        this("minecraft", path);
    }

    @Deprecated
    public String getNamespace() {
        return namespace;
    }

    @Deprecated
    public String getPath() {
        return path;
    }

    @Override
    public String getFileLocation() {
        return String.format("assets/%s/%s", namespace, path);
    }

    @Override
    public String toString() {
        return String.format("%s:%s", namespace, path);
    }

    static class Serializer implements JsonSerializer<ResourcePath> {

        @Override
        public JsonElement serialize(ResourcePath src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }
    }
}
