module com.dwarfeng.sfds.sdk {

    requires com.dwarfeng.sfds.base;
    requires com.dwarfeng.sfds.stack;
    requires com.dwarfeng.subgrade.basic;
    requires com.dwarfeng.subgrade.web;
    requires com.alibaba.fastjson2;
    requires org.mapstruct;

    exports com.dwarfeng.sfds.sdk.bean;
    exports com.dwarfeng.sfds.sdk.bean.dto;
    exports com.dwarfeng.sfds.sdk.exception;
    exports com.dwarfeng.sfds.sdk.util;

    opens com.dwarfeng.sfds.sdk.i18n to com.dwarfeng.sfds.base;
}
