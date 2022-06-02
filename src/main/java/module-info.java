module hdfontgen {
    requires java.desktop;
    requires com.google.gson;
    opens mnm.hdfontgen.pack to com.google.gson;
    opens mnm.hdfontgen.pack.provider to com.google.gson;
    requires jdk.zipfs;
}