# Changes

## Unreleased

- JavaFX (TODO, details)

## 1.3.1

- Implement generation of glyph_sizes.bin when using legacy unicode bitmaps

## 1.3.0

- Complete rewrite to support modern Minecraft versions
    - Legacy support via `pack_format: 1-3` (MC 1.6.1-1.12.2)
    - Font providers via `pack_format: 4` (MC 1.13+)
    - TrueType fonts via `pack-format: 5` (MC 1.15+)
    - Named providers via `pack_format: 6` (MC 1.16+)
- Update Java to 17
  - Java is now bundled, so it does not need to be installed separately
  - Build package using `./gradlew jpackageDist`, zip will be in
    `build/distributions`
- Use gson to generate json files

## 1.2.0

- Use [GNU Unifont](https://unifoundry.com/unifont/index.html) as fallback font, same as Minecraft
- Decrease font size for larger fonts