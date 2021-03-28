package mnm.hdfontgen.pack.provider;

import mnm.hdfontgen.pack.PackSettings;
import mnm.hdfontgen.pack.ResourcePath;

public final class StandardFontProviders {

    public static FontProvider<PackSettings.Bitmap> ascii() {
        ResourcePath file = new ResourcePath("font/ascii.png");
        return new BitmapFontProvider(file, 7, ASCII);
    }

    public static FontProvider<PackSettings.Bitmap> accented() {
        ResourcePath file = new ResourcePath("font/accented.png");
        return new BitmapFontProvider(file, 12, 10, ACCENTED);
    }

    public static FontProvider<PackSettings.Bitmap> nonLatinEuropean() {
        ResourcePath file = new ResourcePath("font/nonlatin_european.png");
        return new BitmapFontProvider(file, 7, NONLATIN_EUROPEAN);
    }

    public static FontProvider<PackSettings.Bitmap> unicodePages() {
        ResourcePath sizes = new ResourcePath("font/glyph_sizes.bin");
        ResourcePath template = new ResourcePath("font/unicode_page_%s.png");
        return new LegacyUnicodeFontProvider(sizes, template);
    }

    private static final String[] ASCII = {
            "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130",
            "\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000",
            "\u0020\u0021\"\u0023\u0024\u0025\u0026\u0027\u0028\u0029\u002a\u002b\u002c\u002d\u002e\u002f",
            "\u0030\u0031\u0032\u0033\u0034\u0035\u0036\u0037\u0038\u0039\u003a\u003b\u003c\u003d\u003e\u003f",
            "\u0040\u0041\u0042\u0043\u0044\u0045\u0046\u0047\u0048\u0049\u004a\u004b\u004c\u004d\u004e\u004f",
            "\u0050\u0051\u0052\u0053\u0054\u0055\u0056\u0057\u0058\u0059\u005a\u005b\\\u005d\u005e\u005f",
            "\u0060\u0061\u0062\u0063\u0064\u0065\u0066\u0067\u0068\u0069\u006a\u006b\u006c\u006d\u006e\u006f",
            "\u0070\u0071\u0072\u0073\u0074\u0075\u0076\u0077\u0078\u0079\u007a\u007b\u007c\u007d\u007e\u0000",
            "\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5",
            "\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192",
            "\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb",
            "\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510",
            "\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567",
            "\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580",
            "\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229",
            "\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000"
    };

    private static final String[] ACCENTED = {
            "\u00c0\u00c1\u00c2\u00c3\u00c4\u00c5\u00c6\u00c7\u00c8\u00c9\u00ca\u00cb\u00cc\u00cd\u00ce\u00cf",
            "\u00d0\u00d1\u00d2\u00d3\u00d4\u00d5\u00d6\u00d9\u00da\u00db\u00dc\u00dd\u00e0\u00e1\u00e2\u00e3",
            "\u00e4\u00e5\u00e6\u00e7\u00ec\u00ed\u00ee\u00ef\u00f1\u00f2\u00f3\u00f4\u00f5\u00f6\u00f9\u00fa",
            "\u00fb\u00fc\u00fd\u00ff\u0100\u0101\u0102\u0103\u0104\u0105\u0106\u0107\u0108\u0109\u010a\u010b",
            "\u010c\u010d\u010e\u010f\u0110\u0111\u0112\u0113\u0114\u0115\u0116\u0117\u0118\u0119\u011a\u011b",
            "\u011c\u011d\u1e20\u1e21\u011e\u011f\u0120\u0121\u0122\u0123\u0124\u0125\u0126\u0127\u0128\u0129",
            "\u012a\u012b\u012c\u012d\u012e\u012f\u0130\u0131\u0134\u0135\u0136\u0137\u0139\u013a\u013b\u013c",
            "\u013d\u013e\u013f\u0140\u0141\u0142\u0143\u0144\u0145\u0146\u0147\u0148\u014a\u014b\u014c\u014d",
            "\u014e\u014f\u0150\u0151\u0152\u0153\u0154\u0155\u0156\u0157\u0158\u0159\u015a\u015b\u015c\u015d",
            "\u015e\u015f\u0160\u0161\u0162\u0163\u0164\u0165\u0166\u0167\u0168\u0169\u016a\u016b\u016c\u016d",
            "\u016e\u016f\u0170\u0171\u0172\u0173\u0174\u0175\u0176\u0177\u0178\u0179\u017a\u017b\u017c\u017d",
            "\u017e\u01fc\u01fd\u01fe\u01ff\u0218\u0219\u021a\u021b\u0386\u0388\u0389\u038a\u038c\u038e\u038f",
            "\u0390\u03aa\u03ab\u03ac\u03ad\u03ae\u03af\u03b0\u03ca\u03cb\u03cc\u03cd\u03ce\u0400\u0401\u0403",
            "\u0407\u040c\u040d\u040e\u0419\u0439\u0450\u0451\u0452\u0453\u0457\u045b\u045c\u045d\u045e\u045f",
            "\u0490\u0491\u1e02\u1e03\u1e0a\u1e0b\u1e1e\u1e1f\u1e22\u1e23\u1e30\u1e31\u1e40\u1e41\u1e56\u1e57",
            "\u1e60\u1e61\u1e6a\u1e6b\u1e80\u1e81\u1e82\u1e83\u1e84\u1e85\u1ef2\u1ef3\u00e8\u00e9\u00ea\u00eb",
            "\u0149\u01e7\u01eb\u040f\u1e0d\u1e25\u1e5b\u1e6d\u1e92\u1eca\u1ecb\u1ecc\u1ecd\u1ee4\u1ee5\u2116",
            "\u0207\u0194\u0263\u0283\u2047\u01f1\u01f2\u01f3\u01c4\u01c5\u01c6\u01c7\u01c8\u01ca\u01cb\u01cc",
            "\u2139\u1d6b\ua732\ua733\ua734\ua735\ua736\ua737\ua738\ua73a\ua73c\ua73d\ua74e\ua74f\ua760\ua761",
            "\ufb04\ufb06\u16a1\u16b5\u01a0\u01a1\u01af\u01b0\u1eae\u1eaf\u1ea4\u1ea5\u1ebe\u1ebf\u1ed1\u1eda",
            "\u1edb\u1ee8\u1ee9\u1eb0\u1eb1\u1ea6\u1ea7\u1ec0\u1ec1\u1ed3\u1edc\u1edd\u1eea\u1eeb\u1ea2\u1ea3",
            "\u1eb2\u1eb3\u1ea8\u1ea9\u1eba\u1ebb\u1ed5\u1ede\u1ec2\u1ec3\u1ec8\u1ec9\u1ece\u1ecf\u1ed4\u1edf",
            "\u1ee6\u1ee7\u1eec\u1eed\u1ef6\u1ef7\u1ea0\u1ea1\u1eb6\u1eb7\u1eac\u1ead\u1eb8\u1eb9\u1ec6\u1ec7",
            "\u1ed8\u1ed9\u1ee2\u1ee3\u1ef0\u1ef1\u1ef4\u1ef5\u1ed0\u0195\u1eaa\u1eab\u1ed6\u1ed7\u1eef\u261e",
            "\u261c\u262e\u1eb4\u1eb5\u1ebc\u1ebd\u1ec4\u1ec5\u1ed2\u1ee0\u1ee1\u1eee\u1ef8\u1ef9\u0498\u0499",
            "\u04a0\u04a1\u04aa\u04ab\u01f6\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
    };

    private static final String[] NONLATIN_EUROPEAN = {
            "\u00a1\u2030\u00ad\u00b7\u20b4\u2260\u00bf\u00d7\u00d8\u00de\u04bb\u00f0\u00f8\u00fe\u0391\u0392",
            "\u0393\u0394\u0395\u0396\u0397\u0398\u0399\u039a\u039b\u039c\u039d\u039e\u039f\u03a0\u03a1\u03a3",
            "\u03a4\u03a5\u03a6\u03a7\u03a8\u03a9\u03b1\u03b2\u03b3\u03b4\u03b5\u03b6\u03b7\u03b8\u03b9\u03ba",
            "\u03bb\u03bc\u03bd\u03be\u03bf\u03c0\u03c1\u03c2\u03c3\u03c4\u03c5\u03c6\u03c7\u03c8\u03c9\u0402",
            "\u0405\u0406\u0408\u0409\u040a\u040b\u0410\u0411\u0412\u0413\u0414\u0415\u0416\u0417\u0418\u041a",
            "\u041b\u041c\u041d\u041e\u041f\u0420\u0421\u0422\u0423\u0424\u0425\u0426\u0427\u0428\u0429\u042a",
            "\u042b\u042c\u042d\u042e\u042f\u0430\u0431\u0432\u0433\u0434\u0435\u0436\u0437\u0438\u043a\u043b",
            "\u043c\u043d\u043e\u043f\u0440\u0441\u0442\u0443\u0444\u0445\u0446\u0447\u0448\u0449\u044a\u044b",
            "\u044c\u044d\u044e\u044f\u0454\u0455\u0456\u0458\u0459\u045a\u2013\u2014\u2018\u2019\u201c\u201d",
            "\u201e\u2026\u204a\u2190\u2191\u2192\u2193\u21c4\uff0b\u018f\u0259\u025b\u026a\u04ae\u04af\u04e8",
            "\u04e9\u02bb\u02cc\u037e\u0138\u1e9e\u00df\u20bd\u20ac\u0462\u0463\u0474\u0475\u04C0\u0472\u0473",
            "\u2070\u00b9\u00b3\u2074\u2075\u2076\u2077\u2078\u2079\u207a\u207b\u207c\u207d\u207e\u2071\u2122",
            "\u0294\u0295\u29c8\u2694\u2620\u049a\u049b\u0492\u0493\u04b0\u04b1\u04d8\u04d9\u0496\u0497\u04a2",
            "\u04a3\u04ba\u05d0\u05d1\u05d2\u05d3\u05d4\u05d5\u05d6\u05d7\u05d8\u05d9\u05db\u05dc\u05de\u05dd",
            "\u05e0\u05df\u05e1\u05e2\u05e4\u05e3\u05e6\u05e5\u05e7\u05e8\u00a2\u00a4\u00a5\u00a9\u00ae\u00b5",
            "\u00b6\u00bc\u00bd\u00be\u0387\u2010\u201a\u2020\u2021\u2022\u2031\u2032\u2033\u2034\u2035\u2036",
            "\u2037\u2039\u203a\u203b\u203c\u203d\u2042\u2048\u2049\u204b\u204e\u204f\u2051\u2052\u2057\u2117",
            "\u2212\u2213\u221e\u2600\u2601\u2608\u0404\u2632\u2635\u263d\u2640\u2642\u26a5\u2660\u2663\u2665",
            "\u2666\u2669\u266a\u266b\u266c\u266d\u266e\u266f\u2680\u2681\u2682\u2683\u2684\u2685\u26a0\u26a1",
            "\u26cf\u2714\u2744\u274c\u2764\u2b50\u2e18\u2e2e\u2e35\u2e38\u2E41\u2E4B\u295d\u1614\u0190\u07c8",
            "\u03db\u3125\u2c6f\u15fa\u0186\u15e1\u018e\u2132\u2141\uA7B0\ua780\u0500\ua779\u1d1a\u27d8\u2229",
            "\u0245\u2144\u0250\u0254\u01dd\u025f\u1d77\u0265\u1d09\u027e\u029e\ua781\u026f\u0279\u0287\u028c",
            "\u028d\u028e\u0531\u0532\u0533\u0534\u0536\u0537\u0539\u053a\u053b\u053c\u053d\u053e\u053f\u0540",
            "\u0541\u0542\u0543\u0544\u0545\u0546\u0547\u0548\u0549\u054b\u054c\u054d\u054e\u054f\u0550\u0551",
            "\u0552\u0553\u0554\u0555\u0556\u0559\u0561\u0562\u0563\u0564\u0565\u0566\u0567\u0568\u0569\u056a",
            "\u056b\u056c\u056d\u056e\u056f\u0570\u0571\u0572\u0573\u0574\u0575\u0576\u0577\u0578\u0579\u057a",
            "\u057b\u057c\u057d\u057e\u057f\u0580\u0581\u0582\u0583\u0584\u0585\u0586\u0587\u05e9\u05ea\u0538",
            "\u055a\u055b\u055c\u055d\u055e\u055f\u0560\u0588\u058f\u00af\u017f\u01b7\u0292\u01f7\u01bf\u021c",
            "\u021d\u0224\u0225\u02d9\ua75a\ua75b\u2011\u214b\u23cf\u23e9\u23ea\u23ed\u23ee\u23ef\u23f4\u23f5",
            "\u23f6\u23f7\u23f8\u23f9\u23fa\u23fb\u23fc\u23fd\u2b58\u25b2\u25b6\u25bc\u25c0\u25cf\u25e6\u25d8",
            "\u2693\u26e8\u0132\u0133\u01c9\ua728\ua729\ua739\ua73b\ufb00\ufb01\ufb02\ufb03\ufb05\ufffd\u0535",
            "\u054a\u16a0\u16a2\u16a3\u16a4\u16a5\u16a6\u16a7\u16a8\u16a9\u16aa\u16ab\u16ac\u16ad\u16ae\u16af",
            "\u16b0\u16b1\u16b2\u16b3\u16b4\u16b6\u16b7\u16b8\u16b9\u16ba\u16bb\u16bc\u16bd\u16be\u16bf\u16c0",
            "\u16c1\u16c2\u16c3\u16c4\u16c5\u16c6\u16c7\u16c8\u16c9\u16ca\u16cb\u16cc\u16cd\u16ce\u16cf\u16d0",
            "\u16d1\u16d2\u16d3\u16d4\u16d5\u16d6\u16d7\u16d8\u16d9\u16da\u16db\u16dc\u16dd\u16de\u16df\u16e0",
            "\u16e1\u16e2\u16e3\u16e4\u16e5\u16e6\u16e7\u16e8\u16e9\u16ea\u16eb\u16ec\u16ed\u16ee\u16ef\u16f0",
            "\u16f1\u16f2\u16f3\u16f4\u16f5\u16f6\u16f7\u16f8\u263a\u263b\u00a6\u2639\u05da\u05f3\u05f4\u05f0",
            "\u05f1\u05f2\u05be\u05c3\u05c6\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
    };
}
