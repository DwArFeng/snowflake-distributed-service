module com.dwarfeng.sfds.stack {

    requires com.dwarfeng.sfds.base;
    requires com.dwarfeng.subgrade.basic;

    exports com.dwarfeng.sfds.stack.bean.dto;
    exports com.dwarfeng.sfds.stack.exception;
    exports com.dwarfeng.sfds.stack.handler;
    exports com.dwarfeng.sfds.stack.service;

    opens com.dwarfeng.sfds.stack.i18n to com.dwarfeng.sfds.base;
}
