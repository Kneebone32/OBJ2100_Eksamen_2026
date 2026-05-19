package common.enums;

//Enum som holder de forksjellige kommandoene som kan sendes mellom klient og server
public enum Kommando {
    OPPRETT_HENVENDELSE,
    HENT_HENVENDELSE_STATUS,
    KANSELLER_HENVENDELSE,
    HENT_LEDIG_HENVENDELSE,
    SETT_HENVENDELSE_FULLFØRT
}
