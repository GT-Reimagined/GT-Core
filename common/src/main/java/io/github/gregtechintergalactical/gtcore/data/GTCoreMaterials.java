package io.github.gregtechintergalactical.gtcore.data;

import io.github.gregtechintergalactical.gtcore.GTCore;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.Ref;
import muramasa.antimatter.material.Material;
import muramasa.antimatter.material.TextureSet;

import static muramasa.antimatter.material.Element.*;
import static muramasa.antimatter.material.TextureSet.*;

public class GTCoreMaterials {
    //TODO add Zincite, chromium dioxide(mass multi=3), niobium nitride, nitro carbon, wollastonite, kyanite, chromite, pyrochlore, gypsum,
    // dymethylamine, mirabilite, dolomite, borax, kaolinite, asbestos, glycerol, chlorobenzene, trona, Pollucite, Fullers Earth, alunite, mica, vermiculate, zeolite


    /**
     *** PSE (No Isotopes)
     **/

    public static Material Hydrogen = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "hydrogen", 0x0000ff, NONE, H));
    public static Material Helium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "helium", 0xffff00, NONE, He));
    public static Material Lithium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "lithium", 0xe1dcff, DULL, Li));
    public static Material Beryllium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "beryllium", 0x64b464, METALLIC, Be));
    public static Material Boron = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "boron", 0xfafafa, DULL, B));
    public static Material Carbon = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "carbon", 0x141414, DULL, C));
    public static Material Nitrogen = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "nitrogen", 0x0096c8, NONE, N));
    public static Material Oxygen = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "oxygen", 0x0064c8, NONE, O));
    public static Material Fluorine = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "fluorine", 0xffffff, NONE, F));
    public static Material Neon = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "neon", 0xffff00, NONE, Ne));
    public static Material Sodium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "sodium", 0x000096, METALLIC, Na));
    public static Material Magnesium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "magnesium", 0xffc8c8, METALLIC, Mg));
    public static Material Aluminium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "aluminium", 0x80c8f0, DULL, Al));
    public static Material Silicon = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "silicon", 0x3c3c50, METALLIC, Si));
    public static Material Phosphor = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "phosphor", 0xffff00, DULL, P));
    public static Material Sulfur = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "sulfur", 0xc8c800, DULL, S));
    public static Material Chlorine = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "chlorine", 0x00ffff, NONE, Cl));
    public static Material Argon = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "argon", 0xff00f0, NONE, Ar));
    public static Material Potassium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "potassium", 0xfafafa, METALLIC, K));
    public static Material Calcium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "calcium", 0xfff5f5, METALLIC, Ca));
    public static Material Titanium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "titanium", 0xdca0f0, METALLIC, Ti));
    public static Material Vanadium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "vanadium", 0x323232, METALLIC, V));
    public static Material Chromium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "chromium", 0xffe6e6, SHINY, Cr));
    public static Material Manganese = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "manganese", 0xfafafa, DULL, Mn));
    public static Material Cobalt = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "cobalt", 0x5050fa, METALLIC, Co));
    public static Material Nickel = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "nickel", 0xc8c8fa, METALLIC, Ni));
    public static Material Zinc = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "zinc", 0xfaf0f0, METALLIC, Zn));
    public static Material Gallium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "gallium", 0xdcdcff, SHINY, Ga));
    public static Material Germanium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "germanium", 0xb2a57b, SHINY, Ge));
    public static Material Arsenic = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "arsenic", 0xa6a586, SHINY, As));
    public static Material Selenium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "selenium", 0xb18bd6, SHINY, Se));
    public static Material Krypton = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "krypton", 0xffffff, DULL, Kr));
    public static Material Rubidium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "rubidium", 0x6e6a61, SHINY, Ru));
    public static Material Strontium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "strontium", 0xd0c49e, SHINY, Sr));
    public static Material Yttrium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "yttrium", 0xdcfadc, METALLIC, Y));
    public static Material Zirconium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "zirconium", 0xeee7d7, SHINY, Zr));
    public static Material Niobium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "niobium", 0xbeb4c8, METALLIC, Nb));
    public static Material Molybdenum = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "molybdenum", 0xb4b4dc, SHINY, Mo));
    public static Material Technetium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "technetium", 0xa3a09b, METALLIC, Tc));
    public static Material Ruthenium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "ruthenium", 0x9e9a9e, SHINY, Ru));
    public static Material Rhodium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "rhodium", 0x797665, SHINY, Rh));
    public static Material Palladium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "palladium", 0x808080, SHINY, Pd));
    public static Material Silver = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "silver", 0xdcdcff, SHINY, Ag));
    public static Material Cadmium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "cadmium", 0x32323c, SHINY, Cd));
    public static Material Indium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "indium", 0x400080, METALLIC, In));
    public static Material Tin = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "tin", 0xdcdcdc, DULL, Sn));
    public static Material Antimony = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "antimony", 0xdcdcf0, SHINY, Sb));
    public static Material Tellurium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "tellurium", 0xc1bbc9, SHINY, Te));
    public static Material Iodine = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "iodine", 0xbd4eaa, DULL, I));
    public static Material Xenon = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "xenon", 0xffff00, NONE, Xe));
    public static Material Caesium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "caesium", 0x6c5f3f, SHINY, Cs));
    public static Material Barium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "barium", 0x818ca8, METALLIC, Ba));
    public static Material Lanthanum = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "lanthanum", 0x807e65, METALLIC, La));
    public static Material Cerium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "cerium", 0x8390B2, METALLIC, Ce));
    public static Material Praseodymium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "praseodymium", 0xadac90, METALLIC, Pr));
    public static Material Neodymium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "neodymium", 0x646464, METALLIC, Nd));
    public static Material Promethium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "promethium", 0x4c3d39, SHINY, Pm));
    public static Material Samarium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "samarium", 0xeef2d7, SHINY, Sm));
    public static Material Europium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "europium", 0xc7ae5c, SHINY, Eu));
    public static Material Gadolinium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "gadolinium", 0x86837e, SHINY, Gd));
    public static Material Terbium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "terbium", 0x87897e, METALLIC, Tb));
    public static Material Dysprosium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "dysprosium", 0xcfd2b7, METALLIC, Dy));
    public static Material Holmium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "holmium", 0x9b9d88, METALLIC, Ho));
    public static Material Erbium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "erbium", 0xa8a6b4, SHINY, Er));
    public static Material Thulium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "thulium", 0xa39e9B, SHINY, Tm));
    public static Material Ytterbium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "ytterbium", 0xc1cac5, SHINY, Yb));
    public static Material Lutetium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "lutetium", 0xe1e4dd, SHINY, Lu));
    public static Material Hafnium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "hafnium", 0xa29791, SHINY, Hf));
    public static Material Tantalum = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "tantalum", 0x9da0a5, SHINY, Ta));
    public static Material Tungsten = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "tungsten", 0x323232, METALLIC, W));
    public static Material Rhenium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "rhenium", 0x61615f, SHINY, Re));
    public static Material Osmium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "osmium", 0x3232ff, METALLIC, Os));
    public static Material Iridium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "iridium", 0xf0f0f5, DULL, Ir));
    public static Material Platinum = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "platinum", 0xffffc8, SHINY, Pt));
    public static Material Mercury = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "mercury", 0xffdcdc, SHINY, Hg));
    public static Material Thallium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "thallium", 0xB6B6D2, SHINY, Tl));
    public static Material Lead = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "lead", 0x3c286e, DULL, Pb));
    public static Material Bismuth = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "bismuth", 0x64a0a0, METALLIC, Bi));
    public static Material Polonium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "polonium", 0x707646, SHINY, Po));
    public static Material Astatine = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "astatine", 0x140E14, SHINY, At));
    public static Material Radon = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "radon", 0xff00ff, NONE, Rn));
    public static Material Francium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "francium", 0xaaaaaa, RAD, Fr));
    public static Material Radium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "radium", 0xf1bd3c, RAD, Ra));
    public static Material Actinium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "actinium", 0xb8c5f1, RAD, Ac));
    public static Material Thorium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "thorium", 0x001e00, RAD, Th)).setDisplayNameString("Thorium 232");
    public static Material Protactinium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "protactinium", 0x8a735a, RAD, Pa));
    public static Material Uranium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "uranium", 0x32f032, RAD, U)).setDisplayNameString("Uranium 238");
    public static Material Neptunium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "neptunium", 0x203f64, RAD, Np));
    public static Material Plutonium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "plutonium", 0xf03232, RAD, Pu));
    public static Material Americium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "americium", 0xc8c8c8, RAD, Am));
    public static Material Curium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "curium", 0x664540, RAD, Cm));
    public static Material Berkelium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "berkelium", 0x88490f, RAD, Bk));
    public static Material Californium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "californium", 0xa78100, RAD, Cf));
    public static Material Einsteinium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "einsteinium", 0xaa8400, RAD, Es));
    public static Material Fermium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "fermium", 0x7b3cab, RAD, Fm));
    public static Material Mendelevium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "mendelevium", 0x183dab, RAD, Md));

    /**
     *** Isotopes
     **/
     
    public static Material Deuterium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "deuterium", 0xffff00, NONE, D));
    public static Material Tritium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "tritium", 0xff0000, METALLIC, T));
    public static Material Helium3 = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "helium_3", 0xffffff, NONE, He3));
    public static Material Cobalt60 = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "cobalt_60", 0x5a5afa, RAD, Co60));
    public static Material Thorium230 = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "thorium_230", 0x001400, RAD, Th230));
    public static Material Uranium233 = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "uranium_233", 0x46fa32, RAD, U233));
    public static Material Uranium235 = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "uranium_235", 0x46fa46, RAD, U235));
    public static Material Plutonium239 = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "plutonium_239", 0xeb3232, RAD, Pu239));
    public static Material Plutonium241 = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "plutonium_241", 0xf54646, RAD, Pu241));
    public static Material Plutonium243 = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "plutonium_243", 0xfa4646, RAD, Pu243));
    public static Material Americium241 = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "americium_241", 0xd2d2d2, RAD, Am241));
    public static Material Americium242 = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "americium_242", 0xd2d2d2, RAD, Am242));

    /**
     ** Metals
     **/

    public static Material AnnealedCopper = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "annealed_copper", 0xff7814, SHINY));
    public static Material BatteryAlloy = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "battery_alloy", 0x9c7ca0, DULL));
    public static Material BismuthBronze = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "bismuth_bronze", 0x647d7d, DULL));
    public static Material BlackBronze = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "black_bronze", 0x64327d, DULL));
    public static Material BlackSteel = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "black_steel", 0x646464, METALLIC));
    public static Material BlueSteel = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "blue_steel", 0x64648c, METALLIC));
    public static Material Brass = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "brass", 0xffb400, METALLIC));
    public static Material Bronze = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "bronze", 0xff8000, METALLIC));
    public static Material CobaltBrass = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "cobalt_brass", 0xb4b4a0, METALLIC));
    public static Material Cupronickel = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "cupronickel", 0xe39680, METALLIC));
    public static Material Duranium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "duranium", 0xffffff, METALLIC));
    public static Material Electrum = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "electrum", 0xffff64, SHINY));
    public static Material EnrichedNaquadah = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "enriched_naquadah", 0x323232, SHINY));
    public static Material HSSE = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "hsse", 0x336600, METALLIC)).setDisplayNameString("HSS-E");
    public static Material HSSG = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "hssg", 0x999900, METALLIC)).setDisplayNameString("HSS-G");
    public static Material HSSS = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "hsss", 0x660033, METALLIC)).setDisplayNameString("HSS-S");
    public static Material Invar = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "invar", 0xb4b478, METALLIC));
    public static Material IronMagnetic = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "magnetic_iron", 0xc8c8c8, MAGNETIC)).setMassMultiplierAndDivider(51, 50);
    public static Material Kanthal = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "kanthal", 0xc2d2df, METALLIC));
    public static Material Magnalium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "magnalium", 0xc8beff, DULL));
    public static Material Naquadah = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "naquadah", 0x323232, METALLIC, Naq));
    public static Material NaquadahAlloy = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "naquadah_alloy", 0x282828, METALLIC));
    public static Material Naquadria = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "naquadria", 0x1e1e1e, SHINY));
    public static Material NeodymiumMagnetic = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "magnetic_neodymium", 0x646464, MAGNETIC)).setMassMultiplierAndDivider(51, 50);
    public static Material Neutronium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "neutronium", 0xfafafa, DULL, Nt));
    public static Material Nichrome = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "nichrome", 0xcdcef6, METALLIC));
    public static Material NickelZincFerrite = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "nickel_zinc_ferrite", 0x3c3c3c, ROUGH));
    public static Material NiobiumTitanium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "niobium_titanium", 0x1d1d29, DULL));
    public static Material Osmiridium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "osmiridium", 0x6464ff, METALLIC));
    public static Material RedAlloy = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "red_alloy", 0xc80000, DULL)).setMassMultiplierAndDivider(5, 4);
    public static Material RedSteel = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "red_steel", 0x8c6464, METALLIC));
    public static Material RoseGold = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "rose_gold", 0xffe61e, SHINY));
    public static Material SolderingAlloy = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "soldering_alloy", 0xdcdce6, DULL));
    public static Material Steel = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "steel", 0x808080, METALLIC)).setMassMultiplierAndDivider(51, 50);
    public static Material SteelMagnetic = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "magnetic_steel", 0x808080, MAGNETIC)).setMassMultiplierAndDivider(51, 50);
    public static Material SterlingSilver = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "sterling_silver", 0xfadce1, SHINY));
    public static Material StainlessSteel = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "stainless_steel", 0xc8c8dc, SHINY));
    public static Material TinAlloy = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "tin_alloy", 0x9fadbb, NONE));
    public static Material Tritanium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "tritanium", 0xffffff, SHINY));
    public static Material TungstenCarbide = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "tungsten_carbide", 0x330066, METALLIC));
    public static Material TungstenSteel = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "tungstensteel", 0x6464a0, METALLIC));
    public static Material Ultimet = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "ultimet", 0xb4b4e6, SHINY));
    public static Material VanadiumGallium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "vanadium_gallium", 0x80808c, SHINY));
    public static Material VanadiumSteel = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "vanadium_steel", 0xc0c0c0, METALLIC));
    public static Material Vibranium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "vibranium", 0x00ffff, SHINY));
    public static Material WroughtIron = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "wrought_iron", 0xc8b4b4, METALLIC));
    public static Material YttriumBariumCuprate = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "yttrium_barium_cuprate", 0x504046, METALLIC));


    public static Material Ironwood = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "ironwood", 0x968C6E, WOOD, "twilightforest"));
    public static Material Steeleaf = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "steeleaf", 0x327F32, TextureSets.LEAF, "twilightforest"));
    public static Material Knightmetal = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "knightmetal", 0xD2F0C8, METALLIC, "twilightforest"));
    public static Material FierySteel = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "fiery_steel", 0x400000, TextureSets.FIERY, "twilightforest"));

    /**
     ** Dusts
     **/
    public static Material Brick = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "brick", 0x9b5643, ROUGH));
    public static Material Clay = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "clay", 0xc8c8dc, ROUGH));
    public static Material Energium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "energium", 0xe81e21, NONE));
    public static Material Lapotronium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "lapotronium", 0x6464c8, NONE));
    public static Material Fireclay = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "fireclay", 0xada09b, ROUGH));
    public static Material Beeswax = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "beeswax", 0xfadc6e, TextureSets.FOOD));


    /**
     ** Gems
     **/

    public static Material CoalCoke = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "coal_coke", 0x8c8caa, LIGNITE));
    public static Material LigniteCoke = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "lignite_coke", 0x8c6464, LIGNITE));

    public static Material Apatite = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "apatite", 0x78B4FA, DIAMOND));
    public static Material Amber = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "amber", 0xFFB400, RUBY));
    public static Material Amethyst = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "amethyst", 0xd232d2, RUBY));
    public static Material Sapphire = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "sapphire", 0x6464c8, GEM_V));
    public static Material BlueTopaz = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "blue_topaz", 0x0000ff, GEM_H));
    public static Material MilkyQuartz = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "milky_quartz", 0xd2d2d2, QUARTZ));
    public static Material CertusQuartz = AntimatterAPI.register(Material.class,new Material(GTCore.ID, "certus_quartz", 0xd2d2e6, QUARTZ, Ref.MOD_AE));
    public static Material ChargedCertusQuartz = AntimatterAPI.register(Material.class,new Material(GTCore.ID, "charged_certus_quartz", 0xd2d2e6, QUARTZ, Ref.MOD_AE));
    public static Material Fluix = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "fluix", 0x78468C, QUARTZ, Ref.MOD_AE));
    public static Material Dilithium = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "dilithium", 0xfffafa, DIAMOND));
    public static Material Glass = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "glass", 0xfafafa, SHINY));
    public static Material GreenSapphire = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "green_sapphire", 0x64c882, GEM_H));
    public static Material Jade = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "jade", 0x64ff7d, LAPIS));
    public static Material Lazurite = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "lazurite", 0x6478ff, LAPIS));
    public static Material Monazite = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "monazite", 0x324632, DIAMOND));
    public static Material NetherStar = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "nether_star", 0xffffff, NONE));
    public static Material Olivine = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "olivine", 0x96ff96, RUBY));
    public static Material Opal = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "opal", 0x0000ff, RUBY));
    public static Material Phosphorus = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "phosphorus", 0xffff00, TextureSet.FLINT));
    public static Material RedGarnet = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "red_garnet", 0xc85050, GARNET));
    public static Material Ruby = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "ruby", 0xff6464, RUBY));
    public static Material Sodalite = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "sodalite", 0x1414ff, LAPIS));
    public static Material Tanzanite = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "tanzanite", 0x4000c8, GEM_V));
    public static Material Topaz = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "topaz", 0xff8000, GEM_H));
    public static Material YellowGarnet = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "yellow_garnet", 0xc8c850, GARNET));

    /**
     ** Plastic
     **/
    public static Material Rubber = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "rubber", 0x141414, SHINY));
    public static Material Plastic = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "plastic", 0xc8c8c8, DULL));


    /**
     ** Stones
     **/

    public static Material BlackGranite = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "black_granite", 0x0a0a0a, ROUGH));
    public static Material BlueSchist = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "blue_schist", 0x0569be, NONE));
    public static Material GreenSchist = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "green_schist", 0x69be69, NONE));
    public static Material Kimberlite = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "kimberlite", 0x64460a, NONE));
    public static Material Komatiite = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "komatiite", 0xbebe69, NONE));
    public static Material Limestone = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "limestone", 0xe6c882, NONE));
    public static Material Marble = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "marble", 0xc8c8c8, NONE));
    public static Material Quartzite = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "quartzite", 0xe6cdcd, QUARTZ));
    public static Material RedGranite = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "red_granite", 0xff0080, ROUGH));
    public static Material Shale = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "shale", 0x8E8EA8, NONE));
    public static Material Slate = AntimatterAPI.register(Material.class, new Material(GTCore.ID, "slate", 0x94979C, NONE));

    public static void init() {
    }
}
