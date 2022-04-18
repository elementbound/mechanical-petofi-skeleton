package com.github.elementbound.mechanical.petofi.poet;

import java.util.List;

public class PoetDescriptorProvider {
    private static final List<PoetDescriptor> descriptors = List.of(
            new PoetDescriptor("Petőfi Sándor", "petofi.txt"),
            new PoetDescriptor("Kosztolányi Dezső", "kosztolanyi.txt"),
            new PoetDescriptor("Radnóti Miklós", "radnoti.txt"),
            new PoetDescriptor("Tóth Árpád", "toth.txt"),
            new PoetDescriptor("Örkény István", "orkeny.txt"),
            new PoetDescriptor("Csokonai Vitéz Mihály", "csokonay.txt")
    );

    public List<PoetDescriptor> getPoets() {
        return descriptors;
    }
}
