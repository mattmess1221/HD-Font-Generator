package mnm.hdfontgen.pack;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.JsonAdapter;

import java.lang.reflect.Type;

@JsonAdapter(ResourcePath.Serializer.class)
public final class ResourcePath implements ZipPath {
    private final String namespace;
    private final String path;

    public ResourcePath(String namespace, String path) {
        this.namespace = namespace;
        this.path = path;
    }

    public ResourcePath(String path) {
        this("minecraft", path);
    }

    public String getNamespace() {
        return namespace;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String getFileLocation() {
        return String.format("assets/%s/%s", namespace, path);
    }

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
