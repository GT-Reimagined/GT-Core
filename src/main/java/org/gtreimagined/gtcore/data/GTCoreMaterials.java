package org.gtreimagined.gtcore.data;

import org.gtreimagined.gtcore.GTCore;
import org.gtreimagined.gtlib.GTAPI;
import org.gtreimagined.gtlib.Ref;
import org.gtreimagined.gtlib.material.Material;
import org.gtreimagined.gtlib.material.TextureSet;

import static org.gtreimagined.gtlib.material.Element.*;
import static org.gtreimagined.gtlib.material.TextureSet.*;


public class GTCoreMaterials {
    //TODO add Zincite, chromium dioxide(mass multi=3), niobium nitride, nitro carbon, wollastonite, kyanite, chromite, pyrochlore, gypsum,
    // dymethylamine, mirabilite, dolomite, borax, kaolinite, asbestos, glycerol, chlorobenzene, trona, Pollucite, Fullers Earth, alunite, mica, vermiculate, zeolite


    /**
     *** PSE (No Isotopes)
     **/

    public static Material Hydrogen = GTAPI.register(Material.class, new Material(GTCore.ID, "hydrogen", 0x0000ff, NONE, H));
    public static Material Helium = GTAPI.register(Material.class, new Material(GTCore.ID, "helium", 0xffff00, NONE, He));
    public static Material Lithium = GTAPI.register(Material.class, new Material(GTCore.ID, "lithium", 0xe1dcff, DULL, Li));
    public static Material Beryllium = GTAPI.register(Material.class, new Material(GTCore.ID, "beryllium", 0x64b464, METALLIC, Be));
    public static Material Boron = GTAPI.register(Material.class, new Material(GTCore.ID, "boron", 0xfafafa, DULL, B));
    public static Material Carbon = GTAPI.register(Material.class, new Material(GTCore.ID, "carbon", 0x141414, DULL, C));
    public static Material Nitrogen = GTAPI.register(Material.class, new Material(GTCore.ID, "nitrogen", 0x0096c8, NONE, N));
    public static Material Oxygen = GTAPI.register(Material.class, new Material(GTCore.ID, "oxygen", 0x0064c8, NONE, O));
    public static Material Fluorine = GTAPI.register(Material.class, new Material(GTCore.ID, "fluorine", 0xffffff, NONE, F));
    public static Material Neon = GTAPI.register(Material.class, new Material(GTCore.ID, "neon", 0xffff00, NONE, Ne));
    public static Material Sodium = GTAPI.register(Material.class, new Material(GTCore.ID, "sodium", 0x000096, METALLIC, Na));
    public static Material Magnesium = GTAPI.register(Material.class, new Material(GTCore.ID, "magnesium", 0xffc8c8, METALLIC, Mg));
    public static Material Aluminium = GTAPI.register(Material.class, new Material(GTCore.ID, "aluminium", 0x80c8f0, DULL, Al));
    public static Material Silicon = GTAPI.register(Material.class, new Material(GTCore.ID, "silicon", 0x3c3c50, METALLIC, Si));
    public static Material Phosphor = GTAPI.register(Material.class, new Material(GTCore.ID, "phosphor", 0xffff00, DULL, P));
    public static Material Sulfur = GTAPI.register(Material.class, new Material(GTCore.ID, "sulfur", 0xc8c800, DULL, S));
    public static Material Chlorine = GTAPI.register(Material.class, new Material(GTCore.ID, "chlorine", 0x00ffff, NONE, Cl));
    public static Material Argon = GTAPI.register(Material.class, new Material(GTCore.ID, "argon", 0xff00f0, NONE, Ar));
    public static Material Potassium = GTAPI.register(Material.class, new Material(GTCore.ID, "potassium", 0xfafafa, METALLIC, K));
    public static Material Calcium = GTAPI.register(Material.class, new Material(GTCore.ID, "calcium", 0xfff5f5, METALLIC, Ca));
    public static Material Titanium = GTAPI.register(Material.class, new Material(GTCore.ID, "titanium", 0xdca0f0, METALLIC, Ti));
    public static Material Vanadium = GTAPI.register(Material.class, new Material(GTCore.ID, "vanadium", 0x323232, METALLIC, V));
    public static Material Chromium = GTAPI.register(Material.class, new Material(GTCore.ID, "chromium", 0xffe6e6, SHINY, Cr));
    public static Material Manganese = GTAPI.register(Material.class, new Material(GTCore.ID, "manganese", 0xfafafa, DULL, Mn));
    public static Material Cobalt = GTAPI.register(Material.class, new Material(GTCore.ID, "cobalt", 0x5050fa, METALLIC, Co));
    public static Material Nickel = GTAPI.register(Material.class, new Material(GTCore.ID, "nickel", 0xc8c8fa, METALLIC, Ni));
    public static Material Zinc = GTAPI.register(Material.class, new Material(GTCore.ID, "zinc", 0xfaf0f0, METALLIC, Zn));
    public static Material Gallium = GTAPI.register(Material.class, new Material(GTCore.ID, "gallium", 0xdcdcff, SHINY, Ga));
    public static Material Germanium = GTAPI.register(Material.class, new Material(GTCore.ID, "germanium", 0xb2a57b, SHINY, Ge));
    public static Material Arsenic = GTAPI.register(Material.class, new Material(GTCore.ID, "arsenic", 0xa6a586, SHINY, As));
    public static Material Selenium = GTAPI.register(Material.class, new Material(GTCore.ID, "selenium", 0xb18bd6, SHINY, Se));
    public static Material Krypton = GTAPI.register(Material.class, new Material(GTCore.ID, "krypton", 0xffffff, DULL, Kr));
    public static Material Rubidium = GTAPI.register(Material.class, new Material(GTCore.ID, "rubidium", 0x6e6a61, SHINY, Ru));
    public static Material Strontium = GTAPI.register(Material.class, new Material(GTCore.ID, "strontium", 0xd0c49e, SHINY, Sr));
    public static Material Yttrium = GTAPI.register(Material.class, new Material(GTCore.ID, "yttrium", 0xdcfadc, METALLIC, Y));
    public static Material Zirconium = GTAPI.register(Material.class, new Material(GTCore.ID, "zirconium", 0xeee7d7, SHINY, Zr));
    public static Material Niobium = GTAPI.register(Material.class, new Material(GTCore.ID, "niobium", 0xbeb4c8, METALLIC, Nb));
    public static Material Molybdenum = GTAPI.register(Material.class, new Material(GTCore.ID, "molybdenum", 0xb4b4dc, SHINY, Mo));
    public static Material Technetium = GTAPI.register(Material.class, new Material(GTCore.ID, "technetium", 0xa3a09b, METALLIC, Tc));
    public static Material Ruthenium = GTAPI.register(Material.class, new Material(GTCore.ID, "ruthenium", 0x9e9a9e, SHINY, Ru));
    public static Material Rhodium = GTAPI.register(Material.class, new Material(GTCore.ID, "rhodium", 0x797665, SHINY, Rh));
    public static Material Palladium = GTAPI.register(Material.class, new Material(GTCore.ID, "palladium", 0x808080, SHINY, Pd));
    public static Material Silver = GTAPI.register(Material.class, new Material(GTCore.ID, "silver", 0xdcdcff, SHINY, Ag));
    public static Material Cadmium = GTAPI.register(Material.class, new Material(GTCore.ID, "cadmium", 0x32323c, SHINY, Cd));
    public static Material Indium = GTAPI.register(Material.class, new Material(GTCore.ID, "indium", 0x400080, METALLIC, In));
    public static Material Tin = GTAPI.register(Material.class, new Material(GTCore.ID, "tin", 0xdcdcdc, DULL, Sn));
    public static Material Antimony = GTAPI.register(Material.class, new Material(GTCore.ID, "antimony", 0xdcdcf0, SHINY, Sb));
    public static Material Tellurium = GTAPI.register(Material.class, new Material(GTCore.ID, "tellurium", 0xc1bbc9, SHINY, Te));
    public static Material Iodine = GTAPI.register(Material.class, new Material(GTCore.ID, "iodine", 0xbd4eaa, DULL, I));
    public static Material Xenon = GTAPI.register(Material.class, new Material(GTCore.ID, "xenon", 0xffff00, NONE, Xe));
    public static Material Caesium = GTAPI.register(Material.class, new Material(GTCore.ID, "caesium", 0x6c5f3f, SHINY, Cs));
    public static Material Barium = GTAPI.register(Material.class, new Material(GTCore.ID, "barium", 0x818ca8, METALLIC, Ba));
    public static Material Lanthanum = GTAPI.register(Material.class, new Material(GTCore.ID, "lanthanum", 0x807e65, METALLIC, La));
    public static Material Cerium = GTAPI.register(Material.class, new Material(GTCore.ID, "cerium", 0x8390B2, METALLIC, Ce));
    public static Material Praseodymium = GTAPI.register(Material.class, new Material(GTCore.ID, "praseodymium", 0xadac90, METALLIC, Pr));
    public static Material Neodymium = GTAPI.register(Material.class, new Material(GTCore.ID, "neodymium", 0x646464, METALLIC, Nd));
    public static Material Promethium = GTAPI.register(Material.class, new Material(GTCore.ID, "promethium", 0x4c3d39, SHINY, Pm));
    public static Material Samarium = GTAPI.register(Material.class, new Material(GTCore.ID, "samarium", 0xeef2d7, SHINY, Sm));
    public static Material Europium = GTAPI.register(Material.class, new Material(GTCore.ID, "europium", 0xc7ae5c, SHINY, Eu));
    public static Material Gadolinium = GTAPI.register(Material.class, new Material(GTCore.ID, "gadolinium", 0x86837e, SHINY, Gd));
    public static Material Terbium = GTAPI.register(Material.class, new Material(GTCore.ID, "terbium", 0x87897e, METALLIC, Tb));
    public static Material Dysprosium = GTAPI.register(Material.class, new Material(GTCore.ID, "dysprosium", 0xcfd2b7, METALLIC, Dy));
    public static Material Holmium = GTAPI.register(Material.class, new Material(GTCore.ID, "holmium", 0x9b9d88, METALLIC, Ho));
    public static Material Erbium = GTAPI.register(Material.class, new Material(GTCore.ID, "erbium", 0xa8a6b4, SHINY, Er));
    public static Material Thulium = GTAPI.register(Material.class, new Material(GTCore.ID, "thulium", 0xa39e9B, SHINY, Tm));
    public static Material Ytterbium = GTAPI.register(Material.class, new Material(GTCore.ID, "ytterbium", 0xc1cac5, SHINY, Yb));
    public static Material Lutetium = GTAPI.register(Material.class, new Material(GTCore.ID, "lutetium", 0xe1e4dd, SHINY, Lu));
    public static Material Hafnium = GTAPI.register(Material.class, new Material(GTCore.ID, "hafnium", 0xa29791, SHINY, Hf));
    public static Material Tantalum = GTAPI.register(Material.class, new Material(GTCore.ID, "tantalum", 0x9da0a5, SHINY, Ta));
    public static Material Tungsten = GTAPI.register(Material.class, new Material(GTCore.ID, "tungsten", 0x323232, METALLIC, W));
    public static Material Rhenium = GTAPI.register(Material.class, new Material(GTCore.ID, "rhenium", 0x61615f, SHINY, Re));
    public static Material Osmium = GTAPI.register(Material.class, new Material(GTCore.ID, "osmium", 0x3232ff, METALLIC, Os));
    public static Material Iridium = GTAPI.register(Material.class, new Material(GTCore.ID, "iridium", 0xf0f0f5, DULL, Ir));
    public static Material Platinum = GTAPI.register(Material.class, new Material(GTCore.ID, "platinum", 0xffffc8, SHINY, Pt));
    public static Material Mercury = GTAPI.register(Material.class, new Material(GTCore.ID, "mercury", 0xffdcdc, SHINY, Hg));
    public static Material Thallium = GTAPI.register(Material.class, new Material(GTCore.ID, "thallium", 0xB6B6D2, SHINY, Tl));
    public static Material Lead = GTAPI.register(Material.class, new Material(GTCore.ID, "lead", 0x3c286e, DULL, Pb));
    public static Material Bismuth = GTAPI.register(Material.class, new Material(GTCore.ID, "bismuth", 0x64a0a0, METALLIC, Bi));
    public static Material Polonium = GTAPI.register(Material.class, new Material(GTCore.ID, "polonium", 0x707646, SHINY, Po));
    public static Material Astatine = GTAPI.register(Material.class, new Material(GTCore.ID, "astatine", 0x140E14, SHINY, At));
    public static Material Radon = GTAPI.register(Material.class, new Material(GTCore.ID, "radon", 0xff00ff, NONE, Rn));
    public static Material Francium = GTAPI.register(Material.class, new Material(GTCore.ID, "francium", 0xaaaaaa, RAD, Fr));
    public static Material Radium = GTAPI.register(Material.class, new Material(GTCore.ID, "radium", 0xf1bd3c, RAD, Ra));
    public static Material Actinium = GTAPI.register(Material.class, new Material(GTCore.ID, "actinium", 0xb8c5f1, RAD, Ac));
    public static Material Thorium = GTAPI.register(Material.class, new Material(GTCore.ID, "thorium", 0x001e00, RAD, Th)).setDisplayNameString("Thorium 232");
    public static Material Protactinium = GTAPI.register(Material.class, new Material(GTCore.ID, "protactinium", 0x8a735a, RAD, Pa));
    public static Material Uranium = GTAPI.register(Material.class, new Material(GTCore.ID, "uranium", 0x32f032, RAD, U)).setDisplayNameString("Uranium 238");
    public static Material Neptunium = GTAPI.register(Material.class, new Material(GTCore.ID, "neptunium", 0x203f64, RAD, Np));
    public static Material Plutonium = GTAPI.register(Material.class, new Material(GTCore.ID, "plutonium", 0xf03232, RAD, Pu));
    public static Material Americium = GTAPI.register(Material.class, new Material(GTCore.ID, "americium", 0xc8c8c8, RAD, Am));
    public static Material Curium = GTAPI.register(Material.class, new Material(GTCore.ID, "curium", 0x664540, RAD, Cm));
    public static Material Berkelium = GTAPI.register(Material.class, new Material(GTCore.ID, "berkelium", 0x88490f, RAD, Bk));
    public static Material Californium = GTAPI.register(Material.class, new Material(GTCore.ID, "californium", 0xa78100, RAD, Cf));
    public static Material Einsteinium = GTAPI.register(Material.class, new Material(GTCore.ID, "einsteinium", 0xaa8400, RAD, Es));
    public static Material Fermium = GTAPI.register(Material.class, new Material(GTCore.ID, "fermium", 0x7b3cab, RAD, Fm));
    public static Material Mendelevium = GTAPI.register(Material.class, new Material(GTCore.ID, "mendelevium", 0x183dab, RAD, Md));

    /**
     *** Isotopes
     **/
     
    public static Material Deuterium = GTAPI.register(Material.class, new Material(GTCore.ID, "deuterium", 0xffff00, NONE, D));
    public static Material Tritium = GTAPI.register(Material.class, new Material(GTCore.ID, "tritium", 0xff0000, METALLIC, T));
    public static Material Helium3 = GTAPI.register(Material.class, new Material(GTCore.ID, "helium_3", 0xffffff, NONE, He3));
    public static Material Cobalt60 = GTAPI.register(Material.class, new Material(GTCore.ID, "cobalt_60", 0x5a5afa, RAD, Co60));
    public static Material Thorium230 = GTAPI.register(Material.class, new Material(GTCore.ID, "thorium_230", 0x001400, RAD, Th230));
    public static Material Uranium233 = GTAPI.register(Material.class, new Material(GTCore.ID, "uranium_233", 0x46fa32, RAD, U233));
    public static Material Uranium235 = GTAPI.register(Material.class, new Material(GTCore.ID, "uranium_235", 0x46fa46, RAD, U235));
    public static Material Plutonium239 = GTAPI.register(Material.class, new Material(GTCore.ID, "plutonium_239", 0xeb3232, RAD, Pu239));
    public static Material Plutonium241 = GTAPI.register(Material.class, new Material(GTCore.ID, "plutonium_241", 0xf54646, RAD, Pu241));
    public static Material Plutonium243 = GTAPI.register(Material.class, new Material(GTCore.ID, "plutonium_243", 0xfa4646, RAD, Pu243));
    public static Material Americium241 = GTAPI.register(Material.class, new Material(GTCore.ID, "americium_241", 0xd2d2d2, RAD, Am241));
    public static Material Americium242 = GTAPI.register(Material.class, new Material(GTCore.ID, "americium_242", 0xd2d2d2, RAD, Am242));

    /**
     ** Metals
     **/

    public static Material Brass = GTAPI.register(Material.class, new Material(GTCore.ID, "brass", 0xffb400, METALLIC));
    public static Material Bronze = GTAPI.register(Material.class, new Material(GTCore.ID, "bronze", 0xff8000, METALLIC));
    public static Material Cupronickel = GTAPI.register(Material.class, new Material(GTCore.ID, "cupronickel", 0xe39680, METALLIC));
    public static Material Electrum = GTAPI.register(Material.class, new Material(GTCore.ID, "electrum", 0xffff64, SHINY));
    public static Material Invar = GTAPI.register(Material.class, new Material(GTCore.ID, "invar", 0xb4b478, METALLIC));
    public static Material IronMagnetic = GTAPI.register(Material.class, new Material(GTCore.ID, "magnetic_iron", 0xc8c8c8, MAGNETIC)).setMassMultiplierAndDivider(51, 50);
    public static Material Kanthal = GTAPI.register(Material.class, new Material(GTCore.ID, "kanthal", 0xc2d2df, METALLIC));
    public static Material LeadedRedstone = GTAPI.register(Material.class, new Material(GTCore.ID, "leaded_redstone", 0x9a0000, DULL)).setMassMultiplierAndDivider(5, 4);
    public static Material NeodymiumMagnetic = GTAPI.register(Material.class, new Material(GTCore.ID, "magnetic_neodymium", 0x646464, MAGNETIC)).setMassMultiplierAndDivider(51, 50);
    public static Material Nichrome = GTAPI.register(Material.class, new Material(GTCore.ID, "nichrome", 0xcdcef6, METALLIC));
    public static Material Osmiridium = GTAPI.register(Material.class, new Material(GTCore.ID, "osmiridium", 0x6464ff, METALLIC));
    public static Material RedAlloy = GTAPI.register(Material.class, new Material(GTCore.ID, "red_alloy", 0xc80000, DULL)).setMassMultiplierAndDivider(5, 4);
    public static Material SolderingAlloy = GTAPI.register(Material.class, new Material(GTCore.ID, "soldering_alloy", 0xdcdce6, DULL));
    public static Material Steel = GTAPI.register(Material.class, new Material(GTCore.ID, "steel", 0x808080, METALLIC)).setMassMultiplierAndDivider(51, 50);
    public static Material SteelMagnetic = GTAPI.register(Material.class, new Material(GTCore.ID, "magnetic_steel", 0x808080, MAGNETIC)).setMassMultiplierAndDivider(51, 50);
    public static Material StainlessSteel = GTAPI.register(Material.class, new Material(GTCore.ID, "stainless_steel", 0xc8c8dc, SHINY));
    public static Material TungstenCarbide = GTAPI.register(Material.class, new Material(GTCore.ID, "tungsten_carbide", 0x330066, METALLIC));
    public static Material TungstenSteel = GTAPI.register(Material.class, new Material(GTCore.ID, "tungstensteel", 0x6464a0, METALLIC));
    public static Material WroughtIron = GTAPI.register(Material.class, new Material(GTCore.ID, "wrought_iron", 0xc8b4b4, METALLIC));

    public static Material Signalum = GTAPI.register(Material.class, new Material(GTCore.ID, "signalum", 0xFF4000, SHINY));
    public static Material Lumium = GTAPI.register(Material.class, new Material(GTCore.ID, "lumium", 0xffff50, SHINY));
    public static Material Enderium = GTAPI.register(Material.class, new Material(GTCore.ID, "enderium", 0x3c7d73, SHINY, "thermal"));

    public static Material Ironwood = GTAPI.register(Material.class, new Material(GTCore.ID, "ironwood", 0x968C6E, WOOD, "twilightforest"));
    public static Material Steeleaf = GTAPI.register(Material.class, new Material(GTCore.ID, "steeleaf", 0x327F32, TextureSets.LEAF, "twilightforest"));
    public static Material Knightmetal = GTAPI.register(Material.class, new Material(GTCore.ID, "knightmetal", 0xD2F0C8, METALLIC, "twilightforest"));
    public static Material FierySteel = GTAPI.register(Material.class, new Material(GTCore.ID, "fiery_steel", 0x400000, TextureSets.FIERY, "twilightforest"));

    /**
     ** Dusts
     **/
    public static Material Brick = GTAPI.register(Material.class, new Material(GTCore.ID, "brick", 0x9b5643, ROUGH));
    public static Material Clay = GTAPI.register(Material.class, new Material(GTCore.ID, "clay", 0xc8c8dc, ROUGH));
    public static Material Energium = GTAPI.register(Material.class, new Material(GTCore.ID, "energium", 0xe81e21, NONE));
    public static Material Lapotronium = GTAPI.register(Material.class, new Material(GTCore.ID, "lapotronium", 0x6464c8, NONE));
    public static Material Fireclay = GTAPI.register(Material.class, new Material(GTCore.ID, "fireclay", 0xada09b, ROUGH));
    public static Material Beeswax = GTAPI.register(Material.class, new Material(GTCore.ID, "beeswax", 0xfadc6e, TextureSets.FOOD));


    /**
     ** Gems
     **/
    public static Material Glass = GTAPI.register(Material.class, new Material(GTCore.ID, "glass", 0xfafafa, SHINY));

    /**
     ** Plastic
     **/
    public static Material Rubber = GTAPI.register(Material.class, new Material(GTCore.ID, "rubber", 0x141414, SHINY));
    public static Material Plastic = GTAPI.register(Material.class, new Material(GTCore.ID, "plastic", 0xc8c8c8, DULL));


    /**
     ** Stones
     **/

    public static Material Basalt = GTAPI.register(Material.class, new Material(GTCore.ID, "basalt", 0x1e1414, ROUGH));
    public static Material BlackGranite = GTAPI.register(Material.class, new Material(GTCore.ID, "black_granite", 0x0a0a0a, ROUGH));
    public static Material BlueSchist = GTAPI.register(Material.class, new Material(GTCore.ID, "blue_schist", 0x0569be, NONE));
    public static Material GreenSchist = GTAPI.register(Material.class, new Material(GTCore.ID, "green_schist", 0x69be69, NONE));
    public static Material Kimberlite = GTAPI.register(Material.class, new Material(GTCore.ID, "kimberlite", 0x64460a, NONE));
    public static Material Komatiite = GTAPI.register(Material.class, new Material(GTCore.ID, "komatiite", 0xbebe69, NONE));
    public static Material Limestone = GTAPI.register(Material.class, new Material(GTCore.ID, "limestone", 0xe6c882, NONE));
    public static Material Marble = GTAPI.register(Material.class, new Material(GTCore.ID, "marble", 0xc8c8c8, NONE));
    public static Material Quartzite = GTAPI.register(Material.class, new Material(GTCore.ID, "quartzite", 0xe6cdcd, QUARTZ));
    public static Material RedGranite = GTAPI.register(Material.class, new Material(GTCore.ID, "red_granite", 0xff0080, ROUGH));
    public static Material Shale = GTAPI.register(Material.class, new Material(GTCore.ID, "shale", 0x8E8EA8, NONE));
    public static Material Slate = GTAPI.register(Material.class, new Material(GTCore.ID, "slate", 0x94979C, NONE));

    public static void init() {
    }
}
