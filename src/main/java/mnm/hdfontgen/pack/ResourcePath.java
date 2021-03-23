package mnm.hdfontgen.pack;

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

    @Override
    public String getFileLocation() {
        return String.format("assets/%s/%s", namespace, path);
    }

}
