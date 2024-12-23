package com.example.project.currency.domain.model

import currencyconverter.composeapp.generated.resources.Res
import currencyconverter.composeapp.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource

/**
 * created by Karim Haggagi on 12/18/24
 **/
enum class Country(val fullName: String, val flag: DrawableResource) {
    AED("United Arab Emirates", Res.drawable.ae),
    AFN("Afghanistan", Res.drawable.af),
    ALL("Albania", Res.drawable.al),
    AMD("Armenia", Res.drawable.am),
    AOA("Angola", Res.drawable.ao),
    ARS("Argentina", Res.drawable.ar),
    AUD("Australia", Res.drawable.au),
    AWG("Aruba", Res.drawable.aw),
    AZN("Azerbaijan", Res.drawable.az),
    BAM("Bosnia and Herzegovina", Res.drawable.ba),
    BBD("Barbados", Res.drawable.bb),
    BDT("Bangladesh", Res.drawable.bd),
    BGN("Bulgaria", Res.drawable.bg),
    BHD("Bahrain", Res.drawable.bh),
    BIF("Burundi", Res.drawable.bi),
    BMD("Bermuda", Res.drawable.bm),
    BND("Brunei", Res.drawable.bn),
    BRL("Brazil", Res.drawable.br),
    BSD("Bahamas", Res.drawable.bs),
    BTN("Bhutan", Res.drawable.bt),
    BWP("Botswana", Res.drawable.bw),
    BYN("Belarus", Res.drawable.by),
    CAD("Canada", Res.drawable.ca),
    CHF("Switzerland", Res.drawable.ch),
    CNY("China", Res.drawable.cn),
    COP("Colombia", Res.drawable.co),
    CRC("Costa Rica", Res.drawable.cr),
    CUP("Cuba", Res.drawable.cu),
    CZK("Czech Republic", Res.drawable.cz),
    DKK("Denmark", Res.drawable.dk),
    DOP("Dominican Republic", Res.drawable.do_),
    EGP("Egypt", Res.drawable.eg),
    FJD("Fiji", Res.drawable.fj),
    GBP("United Kingdom", Res.drawable.gb),
    GHS("Ghana", Res.drawable.gh),
    HKD("Hong Kong", Res.drawable.hk),
    INR("India", Res.drawable.`in`),
    JPY("Japan", Res.drawable.jp),
    USD("United States", Res.drawable.us),
    DEFAULT("",Res.drawable.compose_multiplatform);

    companion object {
        fun fromCode(code: String): Country? {
            return entries.find { it.name.uppercase() == code.uppercase() }
        }
    }
}
