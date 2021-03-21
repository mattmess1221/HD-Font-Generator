package mnm.hdfontgen.pack;

public interface PackGeneratorFactory {
    PackGenerator create(int packFormat);
}
